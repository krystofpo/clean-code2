package cz.polansky.cleancode.ocp.pokus2;


import java.util.List;

public class Library {

    public void printAllLibraryItemsTitle(List<Item> items) {
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

    //now this class is closed for change so that adding new types of items
    //does not require to change this class. This class only depends on
    //abstraction Item and there is no reference to concrete implementatiton of Item.
    //addding new methods is easy.
    //also, modules that use this class dont need any update when new Item type is added.
    //But the client wants to print all library items in order, so that books come first, magazines second, CDs third
    //so a new design
}
