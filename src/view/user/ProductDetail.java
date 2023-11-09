package view.user;

import config.Config;
import config.Validate;
import model.Cart;
import model.Product;
import model.User;
import service.cart.CartServiceIMPL;
import service.cart.ICartService;
import service.product.IProductService;
import service.product.ProductServiceIMPL;

import java.text.DecimalFormat;
import java.util.HashMap;

import static config.Color.*;

public class ProductDetail {
    static IProductService productService = new ProductServiceIMPL();
    static ICartService cartService = new CartServiceIMPL();

    public void productDetail(int id) {
        Product product = productService.findById(id);
        int choice = 0;
        while (choice != 2) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".--------------------------------------------------------.");
            System.out.println("|                   CHI TIẾT SẢN PHẨM                    |");
            System.out.println("|--------------------------------------------------------|");
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "  Tên sản phẩm      :" + BLUE_BOLD_BRIGHT + " %-32s  " + YELLOW_BOLD_BRIGHT + "|\n", product.getProductName());
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "  Danh mục sản phẩm :" + BLUE_BOLD_BRIGHT + " %-32s  " + YELLOW_BOLD_BRIGHT + "|\n", product.getCategory().getCategoryName());
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "  Mô tả             :" + BLUE_BOLD_BRIGHT + " %-32s  " + YELLOW_BOLD_BRIGHT + "|\n", product.getDescription().substring(0, (product.getDescription().length() > 32 ? 32 : (product.getDescription().length() - 1))));
            if (product.getDescription().length() > 32) {
                for (int i = 0; i <= (product.getDescription().length() - 32) / 52; i++) {
                    System.out.printf("|" + BLUE_BOLD_BRIGHT + "  %-52s  " + YELLOW_BOLD_BRIGHT + "|\n", product.getDescription().substring(32 + (i * 52), (product.getDescription().length() > 84 + (i * 52) ? 84 + (i * 52) : (product.getDescription().length() - 1))));
                }
            }
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "  Đơn giá           :" + BLUE_BOLD_BRIGHT + " %-32s  " + YELLOW_BOLD_BRIGHT + "|\n", formatCurrency(product.getUnitPrice()));
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "  Trạng thái        :" + (product.getStock() > 0 ? GREEN_BOLD_BRIGHT : RED_BOLD_BRIGHT) + " %-32s  " + YELLOW_BOLD_BRIGHT + "|\n", (product.getStock() > 0 ? "Còn hàng" : "Hết hàng"));
            System.out.println("|--------------------------------------------------------|");
            System.out.println("|" + YELLOW_BOLD_BRIGHT + "   [1] Cho vào giỏ hàng     |" + RED_BOLD_BRIGHT + "       [2] Quay lại        " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("'--------------------------------------------------------'" + RESET);
            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn của bạn : " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    Cart cart = cartService.findCartByUserLogin();
                    User userLogin = new Config<User>().readFile(Config.URL_USER_LOGIN);
                    if (cart == null) {
                        cart = new Cart(cartService.getNewId(), userLogin.getUserId(), new HashMap<>(), false);
                    }
                    if (cart.getProductCart().containsKey(id)) {
                        cart.getProductCart().put(id, cart.getProductCart().get(id) + 1);
                    } else {
                        cart.getProductCart().put(id, 1);
                    }
                    cartService.save(cart);
                    System.out.println(GREEN_BOLD_BRIGHT + "Đã thêm sản phẩm vào giỏ hàng !" + RESET);
                    choice = 2;
                    break;
                case 2:
                    break;
                default:
                    System.out.print(RED_BOLD_BRIGHT + "Lựa chọn không phù hợp mời nhập lại : " + RESET);
                    break;
            }
        }
    }

    private String formatCurrency(double money) {
        DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
        return formatter.format(money);
    }
}
