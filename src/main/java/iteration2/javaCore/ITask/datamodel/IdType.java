package iteration2.javaCore.ITask.datamodel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdType<T extends Long,String> {
    T id;
}
