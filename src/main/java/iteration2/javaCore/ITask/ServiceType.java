package iteration2.javaCore.ITask;

import iteration2.javaCore.ITask.datamodel.DataReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum ServiceType implements DataReference {
    BASE(0, "Базовый"),
    EXTENDED(1, "Расширенный"),
    PREMIUM(2, "Премиум"),
    VIP(3, "VIP Обслуживание");

    private final int code;
    private final String title;

    @Override
    public int getRefCode() {
        return code;
    }
}
