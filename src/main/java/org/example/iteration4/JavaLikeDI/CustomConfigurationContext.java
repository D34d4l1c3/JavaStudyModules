package org.example.iteration4.JavaLikeDI;


import org.example.iteration3.version1.model.Circle;

import java.util.Map;

public class CustomConfigurationContext {
    //Map<Имя,<Класс,Это Прототип?>>
    public static final Map<String,Map<Class<?>,Boolean>> context = Map.of("simplepuzle",Map.of(SimplePuzzle.class,false),
            "flatshape",Map.of(PuzzleEl.class,true));
}
