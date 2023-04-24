package cz.polansky.cleancode.ocp.pokus3;

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

    @Override
    public int compareTo(Item o) {
        if (o instanceof Book) return 0;
        return -1;
    }
}
