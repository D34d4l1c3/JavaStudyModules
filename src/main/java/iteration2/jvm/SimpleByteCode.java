package iteration2.jvm;

import iteration2.algoritm.BigOExample;

import java.util.ArrayList;
import java.util.List;

public class SimpleByteCode //L0
{
    static {
        List<Integer> listNum = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listNum.add(i);
        }
        BigOExample.addValElements(listNum,3);
    }
//    ASTORE
//    Записать элемент в массив (a* - ссылок, b* - byte или boolean, c* - char, d* - double, i* - integer, f* - float, l* - long, s* - short)
}
//L0
//    LINENUMBER 8 L0
//    ALOAD 0
//    INVOKESPECIAL java/lang/Object.<init> ()V
//    RETURN
//LINENUMBER 11 L0
//        NEW java/util/ArrayList
//        DUP
//        INVOKESPECIAL java/util/ArrayList.<init> ()V
//        ASTORE 0
//   L1
//           LINENUMBER 12 L1
//           ICONST_0
//           ISTORE 1
//L4
//        LINENUMBER 13 L4
//        ALOAD 0
//        ILOAD 1
//        INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
//        INVOKEINTERFACE java/util/List.add (Ljava/lang/Object;)Z (itf)
//        POP --Выбрасывает 1 элемент с вершины стека