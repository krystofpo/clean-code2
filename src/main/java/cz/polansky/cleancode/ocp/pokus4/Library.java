package cz.polansky.cleancode.ocp.pokus4;


import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class Library {

    private final Comparator<Item> comparator;

    public Library(Comparator<Item> comparator) {
        this.comparator = comparator;
    }


    public void printAllLibraryItemsTitle(List<Item> items) {
        Collections.sort(items, comparator);
        for (Item item : items) {
            System.out.println(item.getName());
        }
    }

    public void printAllBorrowableItems(List<Item> items) {
        for (Item item : items) {
            if (item.isBorrowable()) {
                System.out.println(item.getName());
            } else {
                //do nothing
            }
        }
    }
    //this class is closed for change because abstract comparator is injected
    //as a dependency, so different comparators can be created without the need to change this class.
}
