package org.example.iteration4.Model;

import org.example.iteration3.version1.model.Shape;

import java.util.List;
import java.util.Optional;
@FunctionalInterface
public interface IRefreshShape {
    /**
     *
     * @param shapes
     * @return
     */
    List<Optional<Shape>> refreshShapesById(Shape... shapes);
}
