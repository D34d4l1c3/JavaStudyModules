package org.example.iteration2.jvm;

import org.example.iteration2.algoritm.BigOExample;

import java.util.ArrayList;
import java.util.List;

public class SimpleByteCode //L0
{
    private String string1 =  "String1";
    private static int num;
    static {
        num = 4;
    }
    public void testMethod(){
        List<Integer> listNum = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listNum.add(i);
        }
        BigOExample.addValElements(listNum,3);
    }
//    ASTORE
//    Записать элемент в массив (a* - ссылок, b* - byte или boolean, c* - char, d* - double, i* - integer, f* - float, l* - long, s* - short)
}
// class version 61.0 (61)
// access flags 0x21
//public class org/example/iteration2/jvm/SimpleByteCode {
//
//// compiled from: SimpleByteCode.java
//
//// access flags 0x2
//private Ljava/lang/String; string1
//
//// access flags 0xA
//private static I num
//
//// access flags 0x1
//public <init>()V
//        L0 - это фрейм?
//        LINENUMBER 8 L0
//        ALOAD 0
//        INVOKESPECIAL java/lang/Object.<init> ()V
//        L1
//        LINENUMBER 10 L1
//        ALOAD 0
//        LDC "String1"
//        PUTFIELD org/example/iteration2/jvm/SimpleByteCode.string1 : Ljava/lang/String;
//        RETURN
//        L2
//        LOCALVARIABLE this Lorg/example/iteration2/jvm/SimpleByteCode; L0 L2 0
//        MAXSTACK = 2
//        MAXLOCALS = 1
//
//// access flags 0x1
//public testMethod()V
//        L0
//        LINENUMBER 16 L0
//        NEW java/util/ArrayList
//        DUP
//        INVOKESPECIAL java/util/ArrayList.<init> ()V
//        ASTORE 1
//        L1
//        LINENUMBER 17 L1
//        ICONST_0
//        ISTORE 2
//        L2
//        FRAME APPEND [java/util/List I]
//        ILOAD 2
//        BIPUSH 10
//        IF_ICMPGE L3
//        L4
//        LINENUMBER 18 L4
//        ALOAD 1
//        ILOAD 2
//        INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
//        INVOKEINTERFACE java/util/List.add (Ljava/lang/Object;)Z (itf)
//        POP
//        L5
//        LINENUMBER 17 L5
//        IINC 2 1
//        GOTO L2
//        L3
//        LINENUMBER 20 L3
//        FRAME CHOP 1
//        ALOAD 1
//        ICONST_3
//        INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
//        INVOKESTATIC org/example/iteration2/algoritm/BigOExample.addValElements (Ljava/util/Collection;Ljava/lang/Integer;)Ljava/util/List;
//        POP
//        L6
//        LINENUMBER 21 L6
//        RETURN
//        L7
//        LOCALVARIABLE i I L2 L3 2
//        LOCALVARIABLE this Lorg/example/iteration2/jvm/SimpleByteCode; L0 L7 0
//        LOCALVARIABLE listNum Ljava/util/List; L1 L7 1
//        // signature Ljava/util/List<Ljava/lang/Integer;>;
//        // declaration: listNum extends java.util.List<java.lang.Integer>
//        MAXSTACK = 2
//        MAXLOCALS = 3
//
//// access flags 0x8
//static <clinit>()V
//        L0
//        LINENUMBER 13 L0
//        ICONST_4
//        PUTSTATIC org/example/iteration2/jvm/SimpleByteCode.num : I
//        L1
//        LINENUMBER 14 L1
//        RETURN
//        MAXSTACK = 1
//        MAXLOCALS = 0
//        }
