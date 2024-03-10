package iteration2.javaCore.ITask;

import iteration2.javaCore.ITask.datamodel.Account;
import iteration2.javaCore.ITask.datamodel.DataModel;
import iteration2.javaCore.ITask.datamodel.DataMutable;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
public class Person implements DataModel<Long>, DataMutable {
    private static Long lastId = 0L;
    private String name;
    private ServiceType serviceType;
    private final Account account;
    private final Long id;
    private final Date created;
    private Boolean inactive = false;
    private Date lastChangeDate = new Date();
    private Integer changeCount = 0;

    public Person(String name, ServiceType serviceType) {
        this.account = new Account(0d, this);
        this.name = name;
        this.serviceType = serviceType;
        this.id = lastId++;
        this.created = new Date();
    }

    public void changePerson(String name, ServiceType serviceType, Boolean inactive) {
        lastChangeDate = new Date();
        this.changeCount++;
        this.name = name;
        this.serviceType = serviceType;
        this.inactive = inactive;

    }

    public void donate(Double amount, Person person) {
        this.account.setAmount(amount, person);
    }

}
