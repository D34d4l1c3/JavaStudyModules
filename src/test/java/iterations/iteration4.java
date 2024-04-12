package iterations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.example.iteration3.version1.exception.BadDataException;
import org.example.iteration3.version1.exception.NullParamException;
import org.example.iteration3.version1.model.Circle;
import org.example.iteration3.version1.model.FlatShape;
import org.example.iteration3.version1.model.Shape;
import org.example.iteration3.version1.model.ShapeConstants;
import org.example.iteration3.version1.model.Sphere;
import org.example.iteration3.version1.model.Square;
import org.example.iteration3.version1.service.ShapeService;
import org.example.iteration4.IRefreshShape;
import org.example.iteration4.exception.CriticalException;
import org.example.iteration4.exception.NonCriticalException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class iteration4 {
    Map<Long, Shape> shapeDB = ShapeService.shapeMap;
    Map<Long, Shape> cacheMap = ShapeService.cacheMap;

    @SneakyThrows
    @Test
    public void testFunctionInterface() {

        List<Shape> customShapeForCache = shapeDB.values().stream().filter(shape -> shape.getId()%10 == 0).toList();
        Shape[] shapes = customShapeForCache.toArray(new Shape[0]);
        Shape testShape = shapeDB.get(10L);
//        List<Optional<Shape>> list = ShapeService.refreshShapesById(10L,20L,3L);
//        List<Optional<Shape>> list2 = ShapeService.refreshShapesById(10L,20L,353L);
//        List<Optional<Shape>> list3 = ShapeService.refreshShapesById(322L,863L,900L);
        int b = 3;
        //        log.info(System.currentTimeMillis());

        log.info(System.currentTimeMillis());
        ShapeService.cacheShapes(6000L,shapes);
        ShapeService.cacheShapes(6000L,shapes);
        ShapeService.cacheShapes(6000L,shapes);
        ShapeService.cacheShapes(6000L,shapes);
        Thread.sleep(2000);
        ShapeService.cacheShapes(6000L,shapes);
        ShapeService.cacheShapes(6000L,shapes);
        ShapeService.cacheShapes(6000L,shapes);
        Thread.sleep(4000);
        ShapeService.cacheShapes(6000L,shapes);
        ShapeService.cacheShapes(6000L,shapes);

//        Optional<Shape> optShape = ShapeService.refreshShape(testShape);
//        optShape.ifPresent(value -> log.info(value.toString()));
//        // Удаляем из "бд" тестовую фигуру неоткуда рефрешить
//        shapeDB.remove(10L);
//        optShape= ShapeService.refreshShape(testShape);
//        optShape.ifPresent(value -> log.info(value.toString()));

    }


}
