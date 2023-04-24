package cz.polansky.cleancode.ocp.pokus3;

public class Cd implements Item {


    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean isBorrowable() {
        return false;
    }

    @Override
    public int compareTo(Item o) {
        if (o instanceof Cd) return 0;
        else return 1;
    }
}
