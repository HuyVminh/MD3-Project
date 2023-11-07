package service.cart;

import model.Cart;
import service.IGenericService;

public interface ICartService extends IGenericService<Cart> {
    Cart findCartByUserLogin();
}
