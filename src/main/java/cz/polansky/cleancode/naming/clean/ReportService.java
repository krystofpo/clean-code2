package cz.polansky.cleancode.naming.clean;

import cz.polansky.cleancode.naming.Product;

import java.time.LocalTime;
import java.util.List;

public class ReportService {

    private LocalTime weekendHappyHourStart = LocalTime.of(10, 0);
    private LocalTime weekendHappyHourEnd = LocalTime.of(12, 0);
    private LocalTime workdayHappyHourStart = LocalTime.of(12, 30);
    private LocalTime workdayHappyHourEnd = LocalTime.of(14, 0);
    private String hamburger = "menu1445";
    private String pizza = "menu1321";
    private String juice = "drink1620";
    private int minimumAmountOfSoldProduct = 5;


    public Report countDiscountedProductsSoldInHappyHoursForEachDay(List<Product> products) {
        Report report = new Report();
        for (Product product : products) {
            if (wasSoldDuringWeekendHappyHour(product)) {
                if (isOneOfWeekendDiscountedProducts(product.getProductId())) {
                    increaseSoldDiscountedProductsByOne(report, product);
                }
            } else {
                if (wasSoldDuringWorkdayHappyHour(product)) {
                    if (isOneOfWorkdayDiscountedProducts(product)) {
                        increaseSoldDiscountedProductsByOne(report, product);
                    }
                }
            }
        }
        removeProductsBelowSoldTreshold(report);
        return report;
    }

    private void removeProductsBelowSoldTreshold(Report report) {
        report.getCountOfSoldProductsInEachDay().entrySet().stream()
                .filter(entry -> entry.getValue() < minimumAmountOfSoldProduct)
                .forEach(entry -> report.getCountOfSoldProductsInEachDay().remove(entry.getKey()));
    }

    private boolean isOneOfWorkdayDiscountedProducts(Product product) {
        return product.getProductId().equals(hamburger) || product.getProductId().equals(pizza)
                || product.getProductId().equals(juice);
    }

    private boolean wasSoldDuringWorkdayHappyHour(Product product) {
        return product.getDate().toLocalTime().isAfter(workdayHappyHourStart) &&
                product.getDate().toLocalTime().isBefore(workdayHappyHourEnd);
    }

    private void increaseSoldDiscountedProductsByOne(Report report, Product product) {
        if (!report.getCountOfSoldProductsInEachDay().containsKey(product.getDate())){
            report.getCountOfSoldProductsInEachDay().put(product.getDate(), 1);
        } else {
            report.getCountOfSoldProductsInEachDay().put(product.getDate(), (report.getCountOfSoldProductsInEachDay().get(product.getDate()) + 1));
        }
    }

    private boolean isOneOfWeekendDiscountedProducts(String productId) {
        //TODO
    }

    private boolean wasSoldDuringWeekendHappyHour(Product product) {
        return false; //TODO
    }
}
