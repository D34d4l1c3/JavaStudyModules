package iteration2.algoritm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//O, читается как «О», «О-большое» или «биг (big) О», описывает оценку сложности сверху.
//То есть максимальное количество операций, которое алгоритм может выполнить в худшем случае.
//В скобках после О указывают функцию, которая ограничивает сложность.

public class BigOExample {
    // O(N) линейная сложность зависит только от колчества элементов коллекции
    public static List<Integer> addValElements(Collection<Integer> collection, Integer value) {
        return collection.stream().map(el -> el + value).collect(Collectors.toList());
    }

    // A+B по смыслу 2N по сути O(N)
    public static Integer sumLists(List<Integer> a, List<Integer> b) {
        return a.stream().reduce(Integer::sum).orElse(0) + b.stream().reduce(Integer::sum).orElse(0);
    }

    // O(N^2) квадратичная сложность, от количества элементов (туда же пузырьковая сортировка)
    public static List<Integer> sumAllElements(Collection<Integer> collection) {
        List<Integer> list = new ArrayList<>();
        for (int val : collection) {
            int sum = 0;
            for (int sumVal : collection) {
                sum = sum + sumVal;
            }
            list.add(val + sum);
        }
        return list;
    }

    // Добавить бинарный поиск O(Log N) - при увеличении количества элементов сложность не увеличивается линейно
    public static int runBinarySearch (int[] sortedArray, int key, int low, int high) {
        int middle = low + ((high - low) / 2);
        if (key == sortedArray[middle]) {
            return middle;
        } else if (key < sortedArray[middle]) {
            return runBinarySearch (sortedArray, key, low, middle - 1);
        } else {
            return runBinarySearch (sortedArray, key, middle + 1, high);
        }
    }
}
