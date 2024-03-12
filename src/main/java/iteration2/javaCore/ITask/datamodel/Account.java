package iteration2.javaCore.ITask.datamodel;

import iteration2.javaCore.ITask.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class Account implements DataModel<Long>, DataExternalMutable {
    private static Long lastId = 0L;
    private final Long id;
    private final Date created;
    private final Person owner;
    @Setter
    private Boolean inactive = false;
    private String lastUpdatedBy;
    private Date lastChangeDate;
    private Integer changeCount = 0;
    private Double amount;

    public Account(Double amount, Person owner) {
        this.id = lastId++;
        this.created = new Date();
        this.lastChangeDate = new Date();
        this.owner = owner;
        this.amount = amount;
    }

    public void setAmount(Double amount, Person person) {
        lastUpdatedBy = person.getName();
        lastChangeDate = new Date();
        changeCount++;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "\nName: " + amount + "\n";
    }
}
