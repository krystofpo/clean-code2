package cz.polansky.cleancode.naming.clean;

import cz.polansky.cleancode.naming.Product;
import cz.polansky.cleancode.naming.clean.Report;

import java.time.LocalTime;
import java.util.List;

public class ReportService {


    //TODO product id, co to je za  produkt, enum s hodnotou, nebo aspon konstanta, seznam produktu pro vikend a snzma produktu pro working days
    //TODO konstanty por vikend, pojemnovani product.getday na day of week, peakhour pro vikend a pro working day do konstatny nebo jako Pair ci tuple
    //TODO omezit uroven ifu
    public Report countDiscountedProductsSoldInHappyHoursForEachDay(List<Product> products){
        Report report = new Report();
        for (Product product: products) {
            if (wasSoldDuringWeekendHappyHour(product)) {
                    if (isOneOfWeekendDiscountedProducts(product.getProductId())){
                        increaseSoldDiscountedProductsByOne(report, product);
                    }
            } else {
                if(wasSoldDuringWorkdayHappyHour(product)){
                    if (isOneOfWorkdayDiscountedProducts(product)){
                        increaseSoldDiscountedProductsByOne(report, product);
                    }
                }
            }
        }
        return report;
    }

    private boolean isOneOfWorkdayDiscountedProducts(Product product) {
        return product.getProductId().equals("menu1445") || product.getProductId().equals("drink1620")
                || product.getProductId().equals("menu1321");
    }

    private boolean wasSoldDuringWorkdayHappyHour(Product product) {
        return product.getDate().toLocalTime().isAfter(LocalTime.of(12, 30)) &&
                product.getDate().toLocalTime().isBefore(LocalTime.of(14, 0));
    }

    private void increaseSoldDiscountedProductsByOne(Report report, Product product) {
        if (!report.getCountOfSoldProductsInEachDay().containsKey(product.getDate())){
            report.getItems().put(product.getDate(), 1);
        } else {
            report.getItems().put(product.getDate(), (report.getItems().get(product.getDate()) + 1));
        }
    }

    private boolean isOneOfWeekendDiscountedProducts(String productId) {
        //TODO
    }

    private boolean wasSoldDuringWeekendHappyHour(Product product) {
        return false; //TODO
    }
}
