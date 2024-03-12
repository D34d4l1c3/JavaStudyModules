package iteration2.jvm;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StaticTest {


    @SneakyThrows
    public void populateList(int val,List<String> list) {
        for (int i = 0; i < val; i++) {
            Thread.sleep(5);
            for (int b = 0; b < val; b++) {
                list.add(new String("TT" + i));
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) { //999 test value
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        Scanner scanner= new Scanner(System.in);
        int val = Integer.parseInt(scanner.nextLine());
        System.out.println("Start");
        new StaticTest().populateList(val,list1);
        System.out.println("End 1");
        Thread.sleep(3000);
        new StaticTest().populateList(val,list2);
        System.out.println("End");
        Thread.sleep(60000);
    }
}