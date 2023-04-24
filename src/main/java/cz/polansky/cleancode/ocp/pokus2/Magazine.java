package cz.polansky.cleancode.ocp.pokus2;

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
}
