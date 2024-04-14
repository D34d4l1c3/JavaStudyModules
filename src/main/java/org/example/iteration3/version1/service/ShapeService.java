package org.example.iteration3.version1.service;


import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.example.iteration3.version1.model.Shape;
import org.example.iteration3.version1.model.IRound;
import org.example.iteration3.version1.model.FlatShape;
import org.example.iteration3.version1.model.Sphere;
import org.example.iteration3.version1.model.VolumetricShape;
import org.example.iteration4.IRefreshShape;
import org.example.iteration4.entity.Cacheable;
import org.example.iteration4.exception.CriticalException;
import org.example.iteration4.exception.NonCriticalException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
@Log4j2
public class ShapeService {
    /**
     * @param shapes
     * @return
     */
    public final Map<Long, Shape> cacheMap = new HashMap<>();
    public final Map<Long, Shape> shapeMap;

    //Наполняем якобы БД
    static {
        shapeMap = new HashMap<Long, Shape>();
        Shape shape;
        for (int i = 10; i < 50; i++) {
            shape = new Sphere(BigDecimal.valueOf(i), BigDecimal.TEN, 1);
            shapeMap.put(shape.getId(), shape);
        }
    }

    private final IRefreshShape iRefreshShape = (ids -> {
        try {
            return getShapesByIdsFromDB(ids);
        } catch (NonCriticalException e) {
            return List.of(Optional.empty());
        } catch (Exception e) {
            throw new CriticalException("Непонятно что случилось" + e.getMessage());
        }
    });

    public IRefreshShape getFuncRefresh(){
        return iRefreshShape;
    }
    @SneakyThrows(CriticalException.class)
    public static List<Optional<Shape>> refreshShapesById(Long... ids) {
        return iRefreshShape.refreshShapesById(ids);
    }

    @SneakyThrows({NonCriticalException.class})
    public List<Optional<Shape>> getShapesByIdsFromDB(Long... ids) {
        if (ids.length == 0) throw new NonCriticalException("Не были запрошены идентификаторы для запроса в бд");
        log.info("Запрошены фигуры с ид " + Arrays.toString(ids) + "из бд");
        List<Long> lostIds = new ArrayList<>();
        List<Optional<Shape>> result = Arrays.stream(ids).map(id -> {
                    if (Optional.ofNullable(shapeMap.get(id)).isEmpty()) {
                        lostIds.add(id);
                    }
                    return Optional.ofNullable(shapeMap.get(id));
                })
                .toList();
        if (lostIds.size() == ids.length) {
            throw new NonCriticalException("Не найдена ни одна фигура: " + lostIds.toString());
        } else if (lostIds.size() > 0) {
            log.info("Не найдены фигуры в бд: " + lostIds.toString());
        }
        return result;
    }

    private Shape getShapeByIdFromDB(Long id) {
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
    public static <T extends Shape & Cacheable> void cacheShapes(long CACHE_TIME_MS, T... shapes) {
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
            cacheMap.putAll(getShapesByIdsFromDB(refreshShapesDB.stream().map(Shape::getId).toArray(Long[]::new)).stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .peek(shape -> shape.setCachedTime(System.currentTimeMillis()))
                    .collect(Collectors.toMap(Shape::getId, shape -> shape)));
        }
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
