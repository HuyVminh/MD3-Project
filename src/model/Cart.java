package model;

import java.io.Serializable;
import java.util.Map;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private int cartId;
    private int idUser;
    private Map<Integer, Integer> productCart;
    private boolean status = false;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Map<Integer, Integer> getProductCart() {
        return productCart;
    }

    public void setProductCart(Map<Integer, Integer> productCart) {
        this.productCart = productCart;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Cart(int cartId, int idUser, Map<Integer, Integer> productCart, boolean status) {
        this.cartId = cartId;
        this.idUser = idUser;
        this.productCart = productCart;
        this.status = status;
    }

    public Cart() {

    }
}
