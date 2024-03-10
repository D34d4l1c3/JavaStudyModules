package iteration2.javaCore.ITask.datamodel;


import java.util.Date;

public interface DataModel<T> {
    T getId();
    Date getCreated();

    Boolean getInactive();
}
