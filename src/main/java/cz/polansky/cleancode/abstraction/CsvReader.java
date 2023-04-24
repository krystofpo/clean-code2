package cz.polansky.cleancode.abstraction;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CsvReader {

    public List<String> readContent(boolean containsHeader, List<String> lines) {
        return new ArrayList<>();
    }
}
