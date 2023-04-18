package cz.polansky.cleancode.naming;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {

    public Report process(List<Product> products){
        Report report = new Report();

        for (Product product: products) {

            if (product.getDay() == 6 || product.getDay() == 7) {
                if(product.getDate().toLocalTime().isAfter(LocalTime.of(11,0)) &&
                product.getDate().toLocalTime().isBefore(LocalTime.of(13,0))){
                    if (product.getProductId().equals("menu1445") || product.getProductId().equals("drink1620")){
                        if (!report.getItems().containsKey(product.getDate())){
                            report.getItems().put(product.getDate(), 1);
                        } else {
                            report.getItems().put(product.getDate(), (report.getItems().get(product.getDate()) + 1));
                        }
                    }
                }
            } else {
                if(product.getDate().toLocalTime().isAfter(LocalTime.of(12,30)) &&
                        product.getDate().toLocalTime().isBefore(LocalTime.of(14,0))){
                    if (product.getProductId().equals("menu1445") || product.getProductId().equals("drink1620")
                    || product.getProductId().equals("menu1321")){
                        if (!report.getItems().containsKey(product.getDate())){
                            report.getItems().put(product.getDate(), 1);
                        } else {
                            report.getItems().put(product.getDate(), (report.getItems().get(product.getDate()) + 1));
                        }
                    }
                }
            }

        }
        Map<LocalDateTime,Integer> newItems = report.getItems().entrySet().stream()
                .filter(entry -> entry.getValue() > 5) //TODO ta petka co je za magickou knoinstantu
                .collect(Collectors.toMap());
        report.setItems(newItems); //TODO pojmenovani newItems je nicnerikajici
        return report;
    }
}
