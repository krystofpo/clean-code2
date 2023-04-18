package cz.polansky.cleancode.naming;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Product {
    private String productId;
    private LocalDateTime date;
    private int day;

}
