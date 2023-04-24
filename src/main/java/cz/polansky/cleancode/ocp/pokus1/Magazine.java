package cz.polansky.cleancode.ocp.pokus1;

import lombok.Data;

@Data
public class Magazine implements Item {
    private String title;
    private String volume;
}
