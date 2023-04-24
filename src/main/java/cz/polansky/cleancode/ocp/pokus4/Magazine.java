package cz.polansky.cleancode.ocp.pokus4;

import lombok.Data;

@Data
public class Magazine implements Item {
    private String title;
    private String volume;

    @Override
    public String getName() {
        return title + volume;
    }

    @Override
    public boolean isBorrowable() {
        return false;
    }

    //now all the types of Items are not closed for change because adding a new type means changing
    //comparable method in every type and also each type has a reference to other types which is bad,
    //classes should be decoupled as much as possible.
}
