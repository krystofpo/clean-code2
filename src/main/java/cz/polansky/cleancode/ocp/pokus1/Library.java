package cz.polansky.cleancode.ocp.pokus1;

import java.util.List;

public class Library {

    public void printAllLibraryItemsTitle(List<Item> items) {
        for (Item item : items) {
            if (item instanceof Book) {
                Book book = (Book) item;
                System.out.println(book.getAuthor() + book.getTitle());
            } else if (item instanceof Magazine) {
                Magazine magazine = (Magazine) item;
                System.out.println(magazine.getTitle() + magazine.getVolume());
            }
        }
    }
    //this class is not closed for change. Creating a new library item means modification of this class.
    //client wants to add a new type of item. This class is part of some .jar library which is deployed somewhere.
    //Adding new type of items means to deploy a new version of this library and also the modules, which use this library
    //have to be updated to use the new version. The change cascades to other parts of the system
    //client also wants to print all borrowable items, but magazine are not borrowable
    //you have to add this:

    public void printAllBorrowableItems(List<Item> items) {
        for (Item item : items) {
            if (item instanceof Book) {
                Book book = (Book) item;
                System.out.println(book.getAuthor() + book.getTitle());
            } else if (item instanceof Magazine) {
                //do nothing
            }
        }
    }
}
