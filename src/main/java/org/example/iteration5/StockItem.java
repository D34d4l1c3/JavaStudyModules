package org.example.iteration5;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class StockItem {
    Integer id;
    String name;
    Integer count;

    private static AtomicInteger atomicCountBuy = new AtomicInteger(0);
    @Getter
    private static Integer countBuy = 0;
    @Getter
    private static Integer countBuyAtomic = 0;

    public StockItem(Integer id, Integer count) {
        this.id = id;
        this.name = "item_" + id;
        this.count = count;
    }

    @Override
    public String toString() {
        return name+" count = " + count;
    }

    public void buy() {
        if (count > 0) {
            count--;
            countBuy++;
            countBuyAtomic = atomicCountBuy.incrementAndGet();
       }
    }


}
