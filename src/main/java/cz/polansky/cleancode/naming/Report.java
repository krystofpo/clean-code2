package cz.polansky.cleancode.naming;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class Report {
    private Map<LocalDateTime, Integer> items = new HashMap<>();
}
