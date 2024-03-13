package org.example.iteration2.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleClass implements Comparable<SimpleClass>{
    private int simpleNum;
    private String name;

    @Override
    public int compareTo(SimpleClass o) {
        return Integer.compare(o.getSimpleNum(), this.getSimpleNum());
    }
}
