package model;

import java.io.Serializable;
import java.util.List;

import static config.Color.*;
import static model.RoleName.ADMIN;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int userId;
    private String username;
    private String email;
    private String fullName;
    private String password;
    private RoleName role = RoleName.USER;
    private boolean status = true;
    private String phone;

    public User() {
    }

    public User(int userId, String username, String email, String fullName, String password, RoleName role, boolean status, String phone) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
        this.status = status;
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void display() {
        System.out.printf(YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " %-4d " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " %-22s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " %-23s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " %-19s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " %-15s " + YELLOW_BOLD_BRIGHT + "|" + (role == ADMIN ? (BLUE_BOLD_BRIGHT + " %-14s" + YELLOW_BOLD_BRIGHT) : (WHITE_BOLD_BRIGHT + " %-14s" + YELLOW_BOLD_BRIGHT)) + "|" + (status ? (GREEN_BOLD_BRIGHT + " %-13s " + YELLOW_BOLD_BRIGHT) : (RED_BOLD_BRIGHT + " %-13s " + YELLOW_BOLD_BRIGHT)) + "|\n", userId, fullName, username, email, phone, role, (status ? "Hoạt động" : "Bị khoá"))
        ;
    }
}
