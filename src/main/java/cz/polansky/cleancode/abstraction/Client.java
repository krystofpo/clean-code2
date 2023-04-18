package cz.polansky.cleancode.abstraction;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class Client {
    private String remoteDirectory;
    private boolean containsHeader;
    private Format format;
    private TimeRange timeRange;
}
