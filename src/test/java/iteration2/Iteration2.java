package iteration2;

import iteration2.algoritm.BigOExample;
import iteration2.javaCore.ITask.ChatMessage;
import iteration2.javaCore.ITask.ServiceType;
import iteration2.javaCore.ITask.Person;
import iteration2.javaCore.ITask.datamodel.ChatService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

        System.out.println("Сумма массивов А И Б " + BigOExample.sumLists(listNum, listNumRes));

        int[] sortedArray = new int[1000000];
        for (var i = 0; i < sortedArray.length; i++) {
            sortedArray[i] = i;
        }
        System.out.println("Бинарный поиск в большом числовом массиве числа 8766 " + BigOExample.runBinarySearch(sortedArray, 8766, 0, sortedArray.length));
    }

    @Test
    void testTask() {

        Person person1 = new Person("Jack", ServiceType.BASE);
        Person person2 = new Person("twichPers",ServiceType.PREMIUM);
        Person person3 = new Person("Gera44",ServiceType.VIP);

        List<ChatMessage> chat = new ArrayList<>();
        ChatService.sendMessage("Привет! Закиньте донаты, кушать нечего",person1);
        ChatService.sendMessage("Сообщение 2",person1);
        ChatService.sendMessage("Всем ку!",person2);
        ChatService.replyMessage("Ща кину",ChatService.getChat().get(0),person3);
        person1.donate(200d,person3);
        person1.donate(10d,person2);
        ChatService.sendMessage("Спасибо всем теперь у меня есть: "+person1.getAccount().getAmount()+
                " и я смогу купить себе дошика",person1);

        System.out.println(ChatService.getChat());

    }


}
