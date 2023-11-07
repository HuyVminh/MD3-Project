package config;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Format {
    public String formatCurrency(double money) {
        DecimalFormat formatter = new DecimalFormat("###,###,### VND");
        return formatter.format(money);
    }
    public static String getCurrentYearMonth(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return date.format(formatter);
    }
}

