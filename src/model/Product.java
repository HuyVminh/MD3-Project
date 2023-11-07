package model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import static config.Color.*;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private int productId;
    private String productName;
    private Category category;
    private String description;
    private double unitPrice;
    private int stock;
    private boolean status = true;

    public Product(int productId, String productName, Category category, String description, double unitPrice, int stock, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.status = status;
    }

    public Product() {
    }

    public void display() {
        double money = unitPrice;
        Locale locale = new Locale("vi", "VN");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        String moneyString = formatter.format(money);
        System.out.printf(YELLOW_BOLD_BRIGHT + "| " + WHITE_BOLD_BRIGHT + "%-4d" + YELLOW_BOLD_BRIGHT + " |" + WHITE_BOLD_BRIGHT + " %-22s " + YELLOW_BOLD_BRIGHT + "| " + WHITE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "| " + WHITE_BOLD_BRIGHT + "%-20s " + YELLOW_BOLD_BRIGHT + "| " + WHITE_BOLD_BRIGHT + "%15s " + YELLOW_BOLD_BRIGHT + "| " + WHITE_BOLD_BRIGHT + "     %-10s" + YELLOW_BOLD_BRIGHT + "| " + (status ? (GREEN_BOLD_BRIGHT + "   %-16s " + YELLOW_BOLD_BRIGHT) : (RED_BOLD_BRIGHT + "   %-16s " + YELLOW_BOLD_BRIGHT)) + "|\n", productId, productName, (description.length() > 25 ? (description.substring(0, 25) + "...") : description), category.getCategoryName(), moneyString, stock, (status ? "Đang bán" : "Dừng bán") + RESET);
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
