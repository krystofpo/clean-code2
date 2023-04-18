package cz.polansky.cleancode.abstraction;

import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class ClientY extends Client{
    public ClientY() {
        super("yyy",
                false,
                Format.B,
                new TimeRange(LocalTime.of(10,0),
                        LocalTime.of(18,0)));
    }
}
