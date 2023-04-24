package cz.polansky.cleancode.ocp.pokus4;

import lombok.Data;

@Data
public class Book implements Item {
    private String author;
    private String title;

    @Override
    public String getName() {
        return author + title;
    }

    @Override
    public boolean isBorrowable() {
        return true;
    }

    //types of items are closed for change in terms of sorting, because comparator is abstracted away from them.
    //adding new types of items does not mean change of code of existing types.

}
