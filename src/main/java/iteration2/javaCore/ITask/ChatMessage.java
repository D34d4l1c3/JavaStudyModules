package iteration2.javaCore.ITask;

import iteration2.javaCore.ITask.datamodel.ChatService;
import iteration2.javaCore.ITask.datamodel.DataModel;
import iteration2.javaCore.ITask.datamodel.DataOrderable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class ChatMessage implements DataOrderable, DataModel<Long> {
    private final Long id;
    private final Date created;
    @Setter
    private Boolean inactive = false;
    private static Double lastOrderNum = 0d;
    private static long lastId;
    private final String message;
    private final Person owner;
    private final Double orderNum;

    public ChatMessage(String message, Person person) {
        this.message = message;
        this.owner = person;
        this.created = new Date();
        this.id = lastId++;
        orderNum = lastOrderNum++;
    }

    public ChatMessage(String message, Person person, Double orderNum) {
        this.message = message;
        this.owner = person;
        this.created = new Date();
        this.id = lastId++;
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return this.owner.getName() + ":" + this.getMessage();
    }
}
