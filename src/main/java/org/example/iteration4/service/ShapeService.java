package org.example.iteration4.service;


import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.example.iteration3.version1.model.Circle;
import org.example.iteration3.version1.model.Shape;
import org.example.iteration3.version1.model.IRound;
import org.example.iteration3.version1.model.FlatShape;
import org.example.iteration3.version1.model.Sphere;
import org.example.iteration3.version1.model.VolumetricShape;
import org.example.iteration4.Model.IRefreshShape;
import org.example.iteration4.Model.Cacheable;
import org.example.iteration4.exception.CriticalException;
import org.example.iteration4.exception.NonCriticalException;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@UtilityClass
@Log4j2
public class ShapeService {
    /**
     * @param shapes
     * @return
     */
    public final Map<Long, Shape> cacheMap = new HashMap<>();
    public final Map<Long, FlatShape> flatShapeMap = new HashMap<>();
    public final Map<Long, VolumetricShape> volumetricShapeMap = new HashMap<>();
    public final Map<Long, Shape> shapeMap = new HashMap<>();
    public final Supplier<FlatShape> getFlatShape = () -> new Circle(BigDecimal.valueOf(33), 1);
    public final Consumer<Shape> saveShapes2DB = (shape) -> {
        if(shape instanceof FlatShape){
            flatShapeMap.put(shape.getId(), (FlatShape) shape);
        } else if (shape instanceof VolumetricShape) {
            volumetricShapeMap.put(shape.getId(), (VolumetricShape) shape);
        }
//        switch (shape) {
//            case FlatShape flatShape -> flatShapeMap.put(shape.getId(), (FlatShape) shape);
//            case VolumetricShape volumetricShape -> volumetricShapeMap.put(shape.getId(), (VolumetricShape) shape);
//            default -> {
//                break;
//            }
//        }
        shapeMap.put(shape.getId(), shape);
    };
    public final Supplier<VolumetricShape> getVolumeShape = () -> new Sphere(BigDecimal.valueOf(33), BigDecimal.TEN, 8);

    private List<Optional<Shape>> getShapeFromDB(Class<? extends Shape> cl, List<Long> lostIds, Long... ids) {
        return Arrays.stream(ids).map(id -> {
            Optional<Shape> shape = getShapesByClassFromDB(cl, id);
            if (shape.isEmpty()) {
                lostIds.add(id);
            }
            return shape;
        }).collect(Collectors.toList());
    }

    private Optional<Shape> getShapesByClassFromDB(Class<? extends Shape> cl, Long id) {
        return Optional.ofNullable(cl.equals(FlatShape.class) ?
                flatShapeMap.get(id) : cl.equals(VolumetricShape.class) ? volumetricShapeMap.get(id) : shapeMap.get(id));
    }

    //Наполняем якобы БД
    static {
        for (int i = 10; i < 50; i++) {
            if (i % 10 == 0) {
                saveShapes2DB.accept(getFlatShape.get());
            } else {
                saveShapes2DB.accept(getVolumeShape.get());
            }
        }
    }

    private final IRefreshShape iRefreshShape = (shapes -> {
        try {
            List<Optional<Shape>> result = new ArrayList<>();
            Long[] idsFlat = Arrays.stream(shapes).filter(shape -> shape instanceof FlatShape)
                    .map(Shape::getId).toArray(Long[]::new);
            Long[] idsVolume = Arrays.stream(shapes).filter(shape -> shape instanceof VolumetricShape)
                    .map(Shape::getId).toArray(Long[]::new);
            if(idsFlat.length == 0 && idsVolume.length == 0) throw new CriticalException("Нет фигур для поиска");
            else if(idsFlat.length > 0) result.addAll(getShapesByIdsFromDB(FlatShape.class, idsFlat));
            else result.addAll(getShapesByIdsFromDB(VolumetricShape.class, idsVolume));
            return result;
        } catch (NonCriticalException e) {
            return List.of(Optional.empty());
        } catch (Exception e) {
            throw new CriticalException("Непонятно что случилось" + e.getMessage());
        }
    });

    public IRefreshShape getFuncRefresh() {
        return iRefreshShape;
    }


    @SneakyThrows({NonCriticalException.class})
    public List<Optional<Shape>> getShapesByIdsFromDB(Class<? extends Shape> cl, Long... ids) {
        List<Optional<Shape>> result;
        List<Long> lostIds = new ArrayList<>();
        log.info("Запрошены фигуры с ид " + Arrays.toString(ids) + " из бд");
        result = getShapeFromDB(cl,lostIds,ids);
        if (lostIds.size() == ids.length) {
            throw new NonCriticalException("Не найдена ни одна фигура: " + lostIds);
        } else if (lostIds.size() > 0) {
            log.info("Не найдены фигуры в бд: " + lostIds);
        }
        return result;
    }

    public Shape getShapeByIdFromDB(Long id) {
        log.info("Запрошена фигура с ид " + id + "из бд");
        return Optional.ofNullable(shapeMap.get(id)).orElseThrow(() -> new NonCriticalException("Не найдено такой фигуры в бд"));
    }

    public static BigDecimal sumArea(@NotNull Shape... shapes) {
        return Arrays.stream(shapes)
                .map(Shape::getArea)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }
    @SafeVarargs
    public static <T extends Shape & Cacheable> BigDecimal cacheShapesAndCalcSum(long CACHE_TIME_MS, T... shapes){
        List<Shape> res = cacheShapes(CACHE_TIME_MS,shapes);
        res.forEach(x-> x.setScale(1));
        return sumArea(res.toArray(Shape[]::new));

    }

    @SafeVarargs
    public static <T extends Shape & Cacheable> List<Shape> cacheShapes(long CACHE_TIME_MS, T... shapes) {
        log.info("Вызвано кеширование");

        //Список просроченных и тех что нужно перезапросить
        List<T> refreshShapesDB = Arrays.stream(shapes)
                .filter(Objects::nonNull)
//                .filter(shape -> cacheMap.containsKey(shape.getId()))
                .filter(shape -> shape.getCachedTime() != null)
                .filter(shape -> System.currentTimeMillis() - shape.getCachedTime() > CACHE_TIME_MS)
                .toList();

        //Ранее не кешируемые объекты - ставится время кеша и добавляется в список кеша
        cacheMap.putAll(Arrays.stream(shapes)
                .filter(Objects::nonNull)
                .filter(shape -> shape.getCachedTime() == null || !cacheMap.containsKey(shape.getId()))
                .peek(shape -> shape.setCachedTime(System.currentTimeMillis()))
                .collect(Collectors.toMap(Shape::getId, shape -> shape)));

        //Перезапрашиваем с бд список просрочки и добавляем в кешу и ставим дату кеша актуальную для того что есть
        if (!refreshShapesDB.isEmpty()) {
            cacheMap.putAll(iRefreshShape.refreshShapesById(refreshShapesDB.toArray(Shape[]::new)).stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .peek(shape -> shape.setCachedTime(System.currentTimeMillis()))
                    .collect(Collectors.toMap(Shape::getId, shape -> shape)));
        }
        return cacheMap.values().stream().toList();
    }

    public static BigDecimal sumPerimeter(@NotNull FlatShape... shapes) {
        return Arrays.stream(shapes)
                .map(FlatShape::getPerimeter)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    public static BigDecimal sumVolume(@NotNull VolumetricShape... shapes) {
        return Arrays.stream(shapes)
                .map(VolumetricShape::getVolume)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    public static BigDecimal calculateAverageRadius(@NotNull IRound... shapes) {
        return Arrays.stream(shapes)
                .map(IRound::getRadius)
                .reduce(BigDecimal::add)
                .map(sum -> sum.divide(BigDecimal.valueOf(shapes.length), MathContext.UNLIMITED.getRoundingMode()))
                .orElseThrow();
    }

    public static BigDecimal sumWeight(@NotNull VolumetricShape... shapes) {
        return Arrays.stream(shapes)
                .map(VolumetricShape::getWeight)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }
}
