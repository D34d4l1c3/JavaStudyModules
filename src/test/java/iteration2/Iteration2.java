package iteration2;

import org.example.iteration2.algoritm.BigOExample;
import org.example.iteration2.basic.SimpleClass;
import org.example.iteration2.javaCore.ITask.ServiceType;
import org.example.iteration2.javaCore.ITask.Person;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Iteration2 {
    @Test
    void bigO_N() {

        List<Integer> dayHour = new ArrayList<>();
        List<String> namesEmp = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10000; i++) { // OutOfMemory 100000
            dayHour.add(i);
            numbers.add((int) (Math.random() * 100));
            namesEmp.add("Name_" + i);
        }
        BigOExample.getReport(namesEmp, dayHour);

//        BigOExample.bubbleSort(numbers); //25s
//        BigOExample.mergeSort(numbers); // моментально


//        List<String> listStr =  new ArrayList<>();
//        List<Integer> listNum = new ArrayList<>();
//        for (int i = 0; i < 10000000; i++) {
//            listNum.add(i);
//            listStr.add("T"+i);
//        }
//        System.out.println("Оригинальный массив: А");
//        System.out.println(listNum);
//
//        List<Integer> listNumRes = BigOExample.addValElements(listNum, 5);
//        System.out.println("Массив А с добавленным значением 5: Б");
//        System.out.println(listNumRes);
//
//        List<String> t = BigOExample.sumString(listStr);
//
//        List<Integer> listAllSum = BigOExample.sumAllElements(listNum);
//        System.out.println("Массив А где каждый элемент массива сумма со всеми элементами: Д");
//        System.out.println(listAllSum);
//
//        System.out.println("Сумма массивов А И Б " + BigOExample.sumLists(listNum, listNumRes));
//
//        int[] sortedArray = new int[1000000];
//        for (var i = 0; i < sortedArray.length; i++) {
//            sortedArray[i] = i;
//        }
//        ArrayList<Integer> list = new ArrayList<Integer>();
//        list.indexOf(8766);
//        BigOExample.runBinarySearch(sortedArray, 8766, 0, sortedArray.length);
//        System.out.println("Бинарный поиск в большом числовом массиве числа 8766 " + BigOExample.runBinarySearch(sortedArray, 8766, 0, sortedArray.length));
    }

    @SneakyThrows
    @Test
    void testTask() {
        List<String> messageList = List.of("MSG1", "MSG2", "MSG3", "RMSG4", "MSG5", "MSG6");
        System.out.println("Начало работы программа - чат");
//        Класс будет загружен при обращении к статическому классу или методу
        System.out.println("Статическая переменная в классе до создания экземпляра" + Person.testStatField);

        // Класс будет загружен при создании первого экземпляра
        // Первый блок мусорных данных
        Person person1 = new Person("Person1", ServiceType.BASE, "person1@mail.com");
//        System.out.println("Статическая переменная в классе после "+Person.testStatField);
//        Person jack = new Person("Jack", ServiceType.BASE, "jack@mail.com");
//        Person jack2 = new Person("Jack", ServiceType.BASE, "jack1983@mail.com");
//        Person dubJack = new Person("Jack", ServiceType.BASE, "jack@mail.com");
//        Person person2 = new Person("twichPerson", ServiceType.PREMIUM, null);
//        Person twichPerson = new Person("twichPerson", ServiceType.PREMIUM, null);
//        Person person3 = new Person("Gera44", ServiceType.VIP, "gera@must.die");

        // Данные клиента из файла
//        ObjectMapper om = new ObjectMapper(new YAMLFactory());
//        File file = new File("src/main/java/iteration2/javaCore/ITask/data/persons.yaml");

        // Исходный файл содержит дубликаты по переопределенный функции. Видно разницу между листом и сетом
//        List<Person> personList = om.readerForListOf(Person.class).readValue(file);
//        Set<Person> personSet = new HashSet<>(personList);
//        System.out.println(personList);
//        System.out.println(personSet);


//        Random rand = new Random();
//        messageList.forEach(x->ChatService.sendMessage(x,personList.get(rand.nextInt(personList.size()))));
//        personList.forEach(x-> x.donate((double) rand.nextInt(1000),personList.get(rand.nextInt(personList.size()))));
//        ChatService.getChat().forEach(System.out::println);

        // Типа реализация функционала работы с моделью
//        ChatService.sendMessage("Привет! Закиньте донаты, кушать нечего", person1);
//        ChatService.sendMessage("Сообщение 2", person1);
//        ChatService.sendMessage("Всем ку!", person2);
//        ChatService.replyMessage("Ща кину", ChatService.getChat().get(0), person3);
//        person1.donate(200d, person3);
//        person1.donate(100d, person2);
//        ChatService.sendMessage("Спасибо всем теперь у меня есть: " + person1.getAccount().getAmount() +
//                " и я смогу купить себе дошика", person1);

    }

    @Test
    void testEquals() {
        String s1 = "TEST";
        String s2 = new String("TEST");
        String s3 = new String("NO_TEST");
        String s4 = "TEST";
        String s5 = new String("TEST");

        System.out.println("String equals "+s1.equals(s2)); // true
        System.out.println("String equals "+s2.equals(s5)); // true
        System.out.println("String equals "+s1.equals(s3)); // false
        System.out.println("String equals "+s1.equals(s4)); // true

        SimpleClass simpleClass = new SimpleClass(1,"name");
        SimpleClass simpleClass2 = new SimpleClass(1,"name");
        SimpleClass simpleClass3 = new SimpleClass(2,"name");
        SimpleClass simpleClass4 = new SimpleClass(3,"name");

        System.out.println("SimpleClass equals "+simpleClass.equals(simpleClass2)); // false т.к не переопределялись методы а ссылки разные

        System.out.println(simpleClass.compareTo(simpleClass2)); // 0
        System.out.println(simpleClass.compareTo(simpleClass3)); // 1
        System.out.println(simpleClass.compareTo(simpleClass4)); // 1
        System.out.println(simpleClass4.compareTo(simpleClass)); // -1

//        Person person = new Person("Anna",ServiceType.VIP,"anna@mail.com");
//        Person person2 = new Person("Anna",ServiceType.VIP,"anna@mail.com");
//        System.out.println("Person equals "+person.equals(person2)); // true т.к переопределялись метод сроавнивающи  объект
//        System.out.println("Person == "+(person == person2));      // false т.к только ссылки как раньше equals был
    }
}
