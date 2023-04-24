package cz.polansky.cleancode.abstraction;

public interface Formatter {
    Publication convertToPublication(String content) throws Exception;
}
