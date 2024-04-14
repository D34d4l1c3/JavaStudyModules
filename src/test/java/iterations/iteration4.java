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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Log4j2
public class iteration4 {
    Map<Long, Shape> shapeDB = ShapeService.shapeMap;
    Map<Long, Shape> cacheMap = ShapeService.cacheMap;


    //Функционаьный интерфейс, используется в многопоточке чтобы поток мог выполнить задачу что реализует run
    @Test
    public void testRunnable() {
        Runnable runnable = () -> {
            System.out.println("Я что-то выполняю - я полезный");
        };
        Runnable runSayHello = () -> System.out.println("Привет");
        String param = "я параметр запускаемого потока с лямбдой!";
        new Thread(() -> System.out.println("Моя особая миссия: " + param)).start();

        // анонимный класс
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Я только что реализовал функциональный интерфейс Runnable.")
            }
        }).start();


    }

//     Лямбда это та функция что не привязана к классу, либо изврат через стат метод либо лямда либо функциональный интерфейс
//     Лямбда-выражения, по сути, это анонимный класс или метод. Лямбда-выражение не выполняется само по себе.
//     Вместо этого, оно используется для реализации метода, определенного в функциональном интерфейсе.
    @Test
    public void testLambda(){
//        Однострочные
//        System.out.println("Анна упала с дерева");

//        Блочные
//        () -> {
//            double pi = 3.1415;
//            return pi;
//        };

    }



//     ------ Function ------
//     Принимает один аргумент и выдает результат.
//     В нем применяется единый абстрактный метод (SAM), который принимает аргумент типа T и выдает результат типа R.
//     Одним из распространенных вариантов использования этого интерфейса является метод Stream.map.
    @Test
    public void testFunction(){
        Function<Shape,BigDecimal> shapeBigDecimalFunction = Shape::getArea;
        List<BigDecimal> shapeAreas = shapeDB.values().stream()
                //Вот тут функция
                .map(shapeBigDecimalFunction)
                .toList();
        //Или мы можем в него сохранить наш SAM который кастомный - через определнный изврат
        Function<Long,List<Optional<Shape>>> refreshFunction = (Function<Long, List<Optional<Shape>>>) ShapeService.getFuncRefresh();

        //В целом позволяет определить ряд простых функций и наверное реализовать паттерн цепочка отвественности
        // c последовательным выполнением каждой функции с объектом
    }

    // ------ Predicate (предикат) ------
    // Интерфейс Predicate представляет собой логическую функцию аргумента.
    @Test
    public void testPredicate() {
        // Он в основном используется для фильтрации данных из потока (stream) Java.
        // Метод фильтра потока принимает предикат для фильтрации данных и возврата нового потока, удовлетворяющего предикату.
        // У предиката есть метод test(), который принимает аргумент и возвращает логическое значение.

        Predicate<Shape> isTenLikeNumPredicate = (shape -> shape.getId() % 10 == 0);
        //В живом примере:
        Boolean isAnyTenLike = shapeDB.values().stream()
                //Вот тут предиткаты
                .filter(shape -> shape.getCachedTime() == null)
                .anyMatch(isTenLikeNumPredicate);

        //Чисто гипотетически из предикатов можно сделать валидационный менеджер - например %)
        Map<Class,Predicate<? extends Object>> testValueMap = new HashMap<>();
        testValueMap.put(Integer.class, (Integer num) -> num > 0);
        testValueMap.put(Number.class, (Integer num) -> num % 10 == 0);
        testValueMap.put(String.class, (String s) -> !Objects.equals(s, ""));
        testValueMap.put(Shape.class, (Shape shape) -> Integer.valueOf(shape.getArea().intValue()) <= 0);

        //Можно сделать цепочку предикатов например число больше 0 и кратно 10
        Predicate<Integer> pr = (Predicate<Integer>) testValueMap.get(Integer.class);
        Predicate<Integer> pr2 = (Predicate<Integer>) testValueMap.get(Number.class);
        Boolean result = pr.and(pr2).test(30);

    }

    // ------ Supplier  - поставщик ------
    // Это простой интерфейс, указывающий, что данная реализация является поставщиком какого то результа.
    @Test
    public void testSuppler() {
        // тот интерфейс, не накладывает никаких дополнительных ограничений,
        // которые реализация поставщика должна возвращать при каждом новом получении результата.

        // У поставщика есть только один метод get() и нет никаких других методов по умолчанию или статических методов.

        Supplier<Long> integerSupplier = () -> 1L;
        Supplier<String> stringSupplier = () -> "куку";
//      В отличие от методов, функции существуют сами по себе без привязки к классу.

//      Одно из основных применений этого интерфейса это использование для включения отложенного выполнения.
//      Это означает отсрочку выполнения до тех пор, пока оно не понадобится.
//      Например, в классе Optional есть метод orElseGet.
//      Этот метод срабатывает, если у option нет данных. Это показано ниже:
        String test = null;
        Optional.ofNullable(test).orElseGet(() -> "Пусто :)"); //
        Optional.ofNullable(test).orElseGet(stringSupplier);
    }

    // ------ Consumer - потребитель --------
    // Функциональный интерфейс, который принимает один параметр на вход и не возвращает никаких выходных данных.
    @Test
    public void testConsumer() {
        Consumer<Integer> consumerInt = (i) -> System.out.println(i++);
        Consumer<String> HiConsumer = (name) -> System.out.println("Привет: " + name);

        // Пример сложного использования потребителя - например для цепочки потребителей:
        List<String> names = Arrays.asList("Anna", "Misha", "Olga");
        Consumer<List<String>> helloNameConsumer = list -> {
            list.replaceAll(s -> "Привет: " + s + "!");
        };
        Consumer<List<String>> sayAllHello = listNames -> listNames.forEach(System.out::println);
        Consumer<List<String>> calcLength = stringList -> stringList.forEach(string -> System.out.println("Длинна строки: " + string + " равна " + string.length()));
//        helloNameConsumer.andThen(sayAllHello).accept(names);
        helloNameConsumer.andThen(sayAllHello).andThen(calcLength).accept(names);

        Consumer<Shape> shapeConsumerId = Shape::getArea;
        Optional<Shape> optionalShape = Optional.of(new Circle(BigDecimal.TEN, 10, 0));
        optionalShape.ifPresent(shapeConsumerId); // Например некоторые методы Optional принимают consumer

        //Например forEach - в качестве аргумента принимает потребителя для действие над одним элементом коллекции
        names.forEach(x -> log.info(x.length()));
    }

    @SneakyThrows
    @Test
    public void testTaskCache() {
        List<Shape> customShapeForCache = shapeDB.values().stream().filter(shape -> shape.getId() % 10 == 0).toList();
        Shape[] shapes = customShapeForCache.toArray(new Shape[0]);
        Shape testShape = shapeDB.get(10L);

        log.info(System.currentTimeMillis());
        ShapeService.cacheShapes(6000L, shapes);
        ShapeService.cacheShapes(6000L, shapes);
        ShapeService.cacheShapes(6000L, shapes);
        ShapeService.cacheShapes(6000L, shapes);
        Thread.sleep(2000);
        ShapeService.cacheShapes(6000L, shapes);
        ShapeService.cacheShapes(6000L, shapes);
        ShapeService.cacheShapes(6000L, shapes);
        Thread.sleep(4000);
        ShapeService.cacheShapes(6000L, shapes);
        ShapeService.cacheShapes(6000L, shapes);

//        Optional<Shape> optShape = ShapeService.refreshShape(testShape);
//        optShape.ifPresent(value -> log.info(value.toString()));
//        // Удаляем из "бд" тестовую фигуру неоткуда рефрешить
//        shapeDB.remove(10L);
//        optShape= ShapeService.refreshShape(testShape);
//        optShape.ifPresent(value -> log.info(value.toString()));
    }


}
