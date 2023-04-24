package cz.polansky.cleancode.ocp.pokus4;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;

@Component
public class ItemComparator implements Comparator<Item> {


    private final Map<Class, Integer> orderOfItems = Map.ofEntries(
            Map.entry(Book.class, 3),
            Map.entry(Magazine.class, 1),
            Map.entry(Cd.class, 3)
            Map.entry(XXX.class, 3)
    );

    @Override
    public int compare(Item item1, Item item2) {
        int order1 = orderOfItems.get(item1.getClass());
        int order2 = orderOfItems.get(item2.getClass());
        return order1 - order2;
    }

    //order of items is hardcoded here, in a map which is easy to change and mainting,
    //adding new types is easy. reordering is easy.
    //order can be even possibly loaded from application.properties, configuration server etc...
    //ordering logic is separated from Library class, so they are decoupled and independent.
    //TAKEAWAY LESSON : abstraction is the key to clean code.
    //in this application there are 3 things:
    //1. behavior of Items.
    //2. ordering of Items
    //3. work with Items
    //There is clear separation of concerns:
    //1.behavior of Items only exists in one place : in concrete implementations of Items - book, CD, magazine
    //2.ordering of Item only exists in concrete ItemComparator
    //3. work with Items only exists in Library which is some kind of orchestrator
    //The main design principle is Abstraction:
    //Library does not know anything about concrete implementation of Items, it is decoupled thanks to abstraction.
    //Library does not know anything about ordering, it is decoupled thanks to abstraction.
    //So the 3 parts of application are independent of each other, each part is responsible for one thing and nothing
    //else. The single responsibility principle is also fullfilled here.

}
