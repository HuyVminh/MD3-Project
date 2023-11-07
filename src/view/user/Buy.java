package view.user;

import config.Config;
import config.Validate;
import model.Cart;
import model.Category;
import model.Product;
import model.User;
import service.cart.CartServiceIMPL;
import service.cart.ICartService;
import service.product.IProductService;
import service.product.ProductServiceIMPL;

import java.util.HashMap;

public class Buy {
    static IProductService productService = new ProductServiceIMPL();
    static ICartService cartService = new CartServiceIMPL();

    public void buy() {
        System.out.println("Danh sách sản phẩm đang bán");
        for (Product product : productService.findAll()) {
            if (product.isStatus()) {
                product.display();
            }
        }
        int idBuy = Validate.validateInt();
        Product productBuy = productService.findById(idBuy);
        if (productBuy == null) {
            System.out.println("Không tồn tại sản phẩm vừa nhập");
        } else {
//            int newStock = productBuy.getStock() - 1;
//            productBuy.setStock(newStock);
            Cart cart = cartService.findCartByUserLogin();
            User userLogin = new Config<User>().readFile(Config.URL_USER_LOGIN);
            if (cart == null) {
                cart = new Cart(cartService.getNewId(), userLogin.getUserId(), new HashMap<>(), false);
            }
            if (cart.getProductCart().containsKey(idBuy)) {
                cart.getProductCart().put(idBuy, cart.getProductCart().get(idBuy) + 1);
            } else {
                cart.getProductCart().put(idBuy, 1);
            }
            cartService.save(cart);
            System.out.println("Đã thêm sản phẩm vào giỏ hàng");
        }
        for (Cart cart : cartService.findAll()){
            System.out.println(cart);
        }
    }
}
