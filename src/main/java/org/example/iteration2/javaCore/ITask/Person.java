package org.example.iteration2.javaCore.ITask;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import org.example.iteration2.javaCore.ITask.datamodel.Account;
import org.example.iteration2.javaCore.ITask.datamodel.ChatService;
import org.example.iteration2.javaCore.ITask.datamodel.DataModel;
import org.example.iteration2.javaCore.ITask.datamodel.DataMutable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;


@Getter
@JsonIncludeProperties({"name","email","serviceType"})
@NoArgsConstructor
public class Person implements DataModel<Long>, DataMutable {
    private static Long lastId;
    @Setter
    private String name;
    private ServiceType serviceType;
    private final Account account = new Account(0d, this);;
    private final Long id = lastId++;;
    private final Date created = new Date();
    private Boolean inactive = false;
    private Date lastChangeDate = new Date();
    private Integer changeCount = 0;
    public static String testStatField;
    @Setter
    private String email;
    static {
        System.out.println("Класс: Статический блок класса Person");
        lastId = 0L;
        testStatField = "test Stat block";
    }
    {
        System.out.println("Имя:"+name);
        System.out.println("Экземпляр: Анонимный блок класса Person");
        name = "Anon name!";
        System.out.println("Имя:"+name);
    }

    public Person(String name, ServiceType serviceType, String email) {
        System.out.println("Экземпляр: Конструктор класса Person");
        this.email = email;
        this.name = name;
        this.serviceType = serviceType;
        System.out.println("Имя:"+name);
    }

    public void changePerson(String name, ServiceType serviceType, Boolean inactive) {
        lastChangeDate = new Date();
        this.changeCount++;
        this.name = name;
        this.serviceType = serviceType;
        this.inactive = inactive;

    }

    public void donate(Double amount, Person person) {
        ChatService.sendMessage(person.getName()+" задонатил "+this.name+" "+amount+" денег! Спасибо",this);
        this.account.setAmount(this.account.getAmount()+amount, person);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || ((Person) o).email == null) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
    @Override
    public String toString() {
        return name + "Email: " + email;
    }
}
