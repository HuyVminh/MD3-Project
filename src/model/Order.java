package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int idUser;
    private double total;
    private LocalDateTime buyDate;
    private String receiver;
    private String numberPhone;
    private String address;
    private int status = 0;
    private Map<Integer, Integer> orderDetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDateTime buyDate) {
        this.buyDate = buyDate;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<Integer, Integer> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(Map<Integer, Integer> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Order() {
    }

    public Order(int id, int idUser, double total, LocalDateTime buyDate, String receiver, String numberPhone, String address, int status) {
        this.id = id;
        this.idUser = idUser;
        this.total = total;
        this.buyDate = buyDate;
        this.receiver = receiver;
        this.numberPhone = numberPhone;
        this.address = address;
        this.status = status;
    }
}
