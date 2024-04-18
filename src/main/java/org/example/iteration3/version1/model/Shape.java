package org.example.iteration3.version1.model;

import lombok.Data;
import lombok.Setter;
import org.example.iteration2.javaCore.ITask.datamodel.DataModel;
import org.example.iteration4.Model.Cacheable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.Objects;

@Data
public abstract class Shape implements DataModel<Long>, Cacheable {
    private static Long lastId = 0L;
    @NotNull
    private final BigDecimal area;

    @Override
    public Date getCreated() {
        return null;
    }

    @Override
    public Boolean getInactive() {
        return null;
    }
    @Nullable
    private Long CachedTime;

    @Nullable
    private Integer scale;
    @NotNull
    private final Long id;

    @Override
    public String toString() {
        return "Shape{" +
                "area=" + area +
                ", scale=" + scale +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
        return area.equals(shape.area) && Objects.equals(scale, shape.scale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, scale);
    }

    Shape(@NotNull BigDecimal area, int scale) {
        this.id = lastId++;
        this.scale = scale;
        this.area = area.setScale(scale, MathContext.UNLIMITED.getRoundingMode());
    }
    Shape(@NotNull BigDecimal area) {
        this.id = lastId++;
        this.area = area;
        this.scale = 1;
    }

    public @NotNull BigDecimal getArea() {
        return area;
    }

}
