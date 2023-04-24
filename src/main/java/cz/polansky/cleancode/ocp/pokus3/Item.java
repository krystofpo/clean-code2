package cz.polansky.cleancode.ocp.pokus3;

public interface Item extends Comparable<Item> {
    public String getName();

    public boolean isBorrowable();
}
