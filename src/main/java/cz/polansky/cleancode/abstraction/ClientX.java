package cz.polansky.cleancode.abstraction;

import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class ClientX extends Client{
    public ClientX() {
        super("xxx",
                true,
                Format.A,
                new TimeRange(LocalTime.of(10,0),
                        LocalTime.of(18,0))
                );
    }
}
