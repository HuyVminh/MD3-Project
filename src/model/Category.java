package model;

import java.io.Serializable;

import static config.Color.*;

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private int categoryId;
    private String categoryName;
    private String description;
    private boolean status;

    public Category(int categoryId, String categoryName, String description, boolean status) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.status = status;
    }

    public Category() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int id) {
        categoryId = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String name) {
        categoryName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Category{" +
                "CategoryId=" + categoryId +
                ", CategoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    public void display() {
        System.out.printf(YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " %-4d " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " %-22s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " %-30s " + YELLOW_BOLD_BRIGHT + "|" + ((status) ? (GREEN_BOLD_BRIGHT + " %-20s " + YELLOW_BOLD_BRIGHT) : (RED_BOLD_BRIGHT + " %-20s " + YELLOW_BOLD_BRIGHT)) + "|\n", categoryId, categoryName, (description.length() > 25 ? (description.substring(0, 25) + "...") : description), (status ? "Đang hoạt động" : "Đang ẩn"));
    }
}
