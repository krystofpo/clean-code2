package cz.polansky.cleancode.ocp.pokus2;

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
}
