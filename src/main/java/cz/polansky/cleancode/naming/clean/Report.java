package cz.polansky.cleancode.naming.clean;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class Report {
    private Map<LocalDate, Integer> countOfSoldProductsInEachDay = new HashMap<>();
}
