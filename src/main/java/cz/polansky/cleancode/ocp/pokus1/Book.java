package cz.polansky.cleancode.ocp.pokus1;

import lombok.Data;

@Data
public class Book implements Item {
    private String author;
    private String title;
}
