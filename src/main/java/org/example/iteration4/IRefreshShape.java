package org.example.iteration4;

import org.example.iteration3.version1.model.Shape;

import java.util.List;
import java.util.Optional;
@FunctionalInterface
public interface IRefreshShape {
    /**
     *
     * @param ids
     * @return
     */
    List<Optional<Shape>> refreshShapesById(Long... ids);
}
