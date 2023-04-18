package cz.polansky.cleancode.abstraction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TimeRange {
    private LocalTime from;
    private LocalTime to;
}
