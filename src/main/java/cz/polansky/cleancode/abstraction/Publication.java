package cz.polansky.cleancode.abstraction;

import java.time.LocalDateTime;

public interface Publication {
    void setCreated(LocalDateTime now);
    void setLink(Link link);
}
