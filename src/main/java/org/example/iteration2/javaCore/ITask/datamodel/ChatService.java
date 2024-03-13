package org.example.iteration2.javaCore.ITask.datamodel;


import org.example.iteration2.javaCore.ITask.ChatMessage;
import org.example.iteration2.javaCore.ITask.Person;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ChatService {
    static {
        System.out.println("Я инициализируюсь когда класс.");
    }
    @Getter
    private static List<ChatMessage> chat = new ArrayList<>();
    public static void sendMessage(String message, Person person){
        chat.add(new ChatMessage(message,person));
    }
    public static void replyMessage(String message,ChatMessage chatMessage,Person person){
        chat.add(new ChatMessage("Ответ от: "+person.getName()+""+message,person,chatMessage.getId()+0.1));
    }
}
