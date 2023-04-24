package cz.polansky.cleancode.ocp.pokus3;


import java.util.Collections;
import java.util.List;

public class Library {

    public void printAllLibraryItemsTitle(List<Item> items) {
        Collections.sort(items);
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
}
