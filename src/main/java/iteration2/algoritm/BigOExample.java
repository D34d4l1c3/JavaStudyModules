package iteration2.algoritm;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // O(NlogN)
    public static List<Integer> mergeSort(List<Integer> list) {
        if (list.size() < 2) {
            return list;
        }
        int mid = list.size()/2;
        return merged(
                mergeSort(list.subList(0, mid)),
                mergeSort(list.subList(mid, list.size())));
    }

    private static List<Integer> merged(List<Integer> left, List<Integer> right) {
        int leftIndex = 0;
        int rightIndex = 0;
        List<Integer> merged = new ArrayList<Integer>();

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex) < right.get(rightIndex)) {
                merged.add(left.get(leftIndex++));
            } else {
                merged.add(right.get(rightIndex++));
            }
        }
        merged.addAll(left.subList(leftIndex, left.size()));
        merged.addAll(right.subList(rightIndex, right.size()));
        return merged;
    }

    //O(N^2)
    public static List<Integer> bubbleSort(List<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    int temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        return list;
    }

    // O(N^2) квадратичная сложность, от количества элементов (туда же пузырьковая сортировка)
    @SneakyThrows
    public static List<Integer> sumAllElements(Collection<Integer> collection) {
        List<Integer> list = new ArrayList<>();
        for (int val : collection) {
//          Thread.sleep(10);
            int sum = 0;
            for (int sumVal : collection) {
//                Thread.sleep(3000);
                sum = sum + sumVal;
            }
            list.add(0, val + sum);
        }
        return list;
    }

    // O(N^2) квадратичная сложность включая память т.к объем массива и занимаемой памяти растет
    @SneakyThrows
    public static void getReport(Collection<String> employes, Collection<Integer> days) {
        Map<String, List<Integer>> report = new HashMap<>();
        for (String name : employes) {
            Thread.sleep(3);
            List<Integer> hourADay = new ArrayList<>();
           // hourADay.addAll(days);
            for (int hour : days) {
                hourADay.add(hour);
            }
            report.put(name, hourADay);
        }
//        report.keySet().forEach(name-> System.out.println(name + ":"+ report.get(name)));
    }

    // Добавить бинарный поиск O(Log N) - при увеличении количества элементов сложность не увеличивается линейно
    public static int runBinarySearch(int[] sortedArray, int key, int low, int high) {
        int middle = low + ((high - low) / 2);
        if (key == sortedArray[middle]) {
            return middle;
        } else if (key < sortedArray[middle]) {
            return runBinarySearch(sortedArray, key, low, middle - 1);
        } else {
            return runBinarySearch(sortedArray, key, middle + 1, high);
        }
    }
}
