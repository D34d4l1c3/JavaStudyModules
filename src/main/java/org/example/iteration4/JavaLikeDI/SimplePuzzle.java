package org.example.iteration4.JavaLikeDI;

import org.example.iteration3.version1.model.Circle;
import org.example.iteration3.version1.model.FlatShape;


public class SimplePuzzle {
    PuzzleEl flatShape = CustomApplicationContext.getCustomBean("flatshape", PuzzleEl.class); //Зависимость - мне нужен экземпляр эдакий DI

    public SimplePuzzle() {
        //Не принимаю его из конструктора а решаю получать из некого менеджера зависимостей
    }

}
