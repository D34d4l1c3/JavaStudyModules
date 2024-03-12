package iteration2.javaCore.ITask;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import iteration2.javaCore.ITask.datamodel.Account;
import iteration2.javaCore.ITask.datamodel.ChatService;
import iteration2.javaCore.ITask.datamodel.DataModel;
import iteration2.javaCore.ITask.datamodel.DataMutable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;


@Getter
@JsonIncludeProperties({"name","email","serviceType"})
@NoArgsConstructor
public class Person implements DataModel<Long>, DataMutable {
    private static Long lastId = 0L;
    @Setter
    private String name;
    private ServiceType serviceType;
    private final Account account = new Account(0d, this);;
    private final Long id = lastId++;;
    private final Date created = new Date();
    private Boolean inactive = false;
    private Date lastChangeDate = new Date();
    private Integer changeCount = 0;
    @Setter
    private String email;

    public Person(String name, ServiceType serviceType, String email) {
        this.email = email;
        this.name = name;
        this.serviceType = serviceType;
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
