package cz.polansky.cleancode.abstraction;

public enum Format {
    A("aaa"),
    B("bbb");

    public final String prefix;

    private Format(String prefix) {
        this.prefix = prefix;
    }
}
