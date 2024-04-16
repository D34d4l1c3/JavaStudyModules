package org.example.iteration4.Model;

import org.example.iteration3.version1.model.FlatShape;
import org.jetbrains.annotations.Async;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class FLatShapeMesh {
    private String name;
    List<? extends FlatShape> flatShapes;

}
