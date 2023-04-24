package cz.polansky.cleancode.ocp.pokus3;

import lombok.Data;

@Data
public class Magazine implements Item {
    private String title;
    private String volume;

    @Override
    public String getName() {
        return title + volume + xxx;
    }

    @Override
    public boolean isBorrowable() {
        return false;
    }

    @Override
    public int compareTo(Item o) {
        if (o instanceof Magazine) {
            return 0;
        } else if (o instanceof Book) {
            return -1;
        } else if (o instanceof Cd) {
            return 1;
        } else {
            return -1;
        }
    }

    //now all the types of Items are not closed for change because adding a new type means changing
    //comparable method in every type and also each type has a reference to other types which is bad,
    //classes should be decoupled as much as possible.
}
