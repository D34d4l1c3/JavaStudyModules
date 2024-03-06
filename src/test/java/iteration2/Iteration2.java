package iteration2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Iteration2 {
    @Test
    void bigO_N() {
        List<Integer> listNum = List.of(5, 0, 0, 1, 1, 4, 2, 3);
        System.out.println("Оригинальный массив: А");
        System.out.println(listNum);

        List<Integer> listNumRes = BigOExample.addValElements(listNum, 5);
        System.out.println("Массив А с добавленным значением 5: Б");
        System.out.println(listNumRes);


        List<Integer> listAllSum = BigOExample.sumAllElements(listNum);
        System.out.println("Массив А где каждый элемент массива сумма со всеми элементами: Д");
        System.out.println(listAllSum);

        System.out.println("Сумма массивов А И Б "+ BigOExample.sumLists(listNum,listNumRes));

        int[] sortedArray = new int[1000000];
        for (var i = 0; i < sortedArray.length; i++) {
            sortedArray[i] = i;
        }
        System.out.println("Бинарный поиск в большом числовом массиве числа 8766 "+BigOExample.runBinarySearch(sortedArray,8766,0,sortedArray.length));

    }


}
