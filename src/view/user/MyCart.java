package view.user;

import config.Config;
import config.Validate;
import model.Cart;
import model.Order;
import model.Product;
import service.cart.CartServiceIMPL;
import service.cart.ICartService;
import service.order.IOrderService;
import service.order.OrderServiceIMPL;
import service.product.IProductService;
import service.product.ProductServiceIMPL;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;

import static config.Color.*;

public class MyCart {
    static ICartService cartService = new CartServiceIMPL();
    static IProductService productService = new ProductServiceIMPL();
    static IUserService userService = new UserServiceIMPL();
    static IOrderService orderService = new OrderServiceIMPL();

    public void cart() {
        Cart cart = cartService.findCartByUserLogin();
        int choice = 0;
        while (choice != 4) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".-----------------------------------------------------------------------------------------------------------------.");
            System.out.println("|                                                  GIỎ HÀNG                                                       |");
            System.out.println("|-----------------------------------------------------------------------------------------------------------------|");
            System.out.println("| ID Sản phẩm |              Tên Sản Phẩm               |  Số lượng  |      Đơn giá       |      Thành Tiền       |");
            System.out.println("|-----------------------------------------------------------------------------------------------------------------|");
            if (cart.getProductCart().isEmpty()) {
                System.out.println("|" + RED_BOLD_BRIGHT + "  GIỎ HÀNG RỖNG !                                                                                                " + YELLOW_BOLD_BRIGHT + "|");
            } else {
                for (int idProduct : cart.getProductCart().keySet()) {
                    Product product = productService.findById(idProduct);
                    System.out.printf("|    " + WHITE_BOLD_BRIGHT + "%3d      " + YELLOW_BOLD_BRIGHT + "| " + WHITE_BOLD_BRIGHT + "%-39s " + YELLOW_BOLD_BRIGHT + "|     " + WHITE_BOLD_BRIGHT + "%2d     " + YELLOW_BOLD_BRIGHT + "| " + WHITE_BOLD_BRIGHT + "%18s " + YELLOW_BOLD_BRIGHT + "| " + WHITE_BOLD_BRIGHT + "%21s " + YELLOW_BOLD_BRIGHT + "|\n", idProduct, product.getProductName(), cart.getProductCart().get(idProduct), formatCurrency(product.getUnitPrice()), formatCurrency(product.getUnitPrice() * cart.getProductCart().get(idProduct)));
                }
            }
            System.out.println("|-----------------------------------------------------------------------------------------------------------------|");
            System.out.printf("|                                       TỔNG GIÁ TIỀN                                     |" + PURPLE_BOLD_BRIGHT + " %21s " + YELLOW_BOLD_BRIGHT + "|\n", formatCurrency(totalBill()));
            System.out.println("|-----------------------------------------------------------------------------------------------------------------|");
            System.out.println("|    [1] Thay đổi số lượng sản phẩm     |  [2] Xóa sản phẩm  |         " + PURPLE_BOLD_BRIGHT + "[3] Thanh Toán          " + YELLOW_BOLD_BRIGHT + "|   " + RED_BOLD_BRIGHT + "[4] Quay lại   " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("'-----------------------------------------------------------------------------------------------------------------'" + RESET);
            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn của bạn : " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    System.out.print("Nhập ID sản phẩm cần thay đổi số lượng : ");
                    int idUpdate = Validate.validateInt();
                    if (cart.getProductCart().containsKey(idUpdate)) {
                        System.out.print("Nhập số lượng cần thay đổi : ");
                        int number = Validate.validateInt();
                        cart.getProductCart().put(idUpdate, number);
                        cartService.save(cart);
                        System.out.println(GREEN_BOLD_BRIGHT + "Đã cập nhật số lượng sản phẩm !" + RESET);
                    } else {
                        System.out.println(RED_BOLD_BRIGHT + "Không tồn tại sản phẩm đã nhập trong giỏ hàng !" + RESET);
                    }
                    break;
                case 2:
                    System.out.print("Nhập ID sản phẩm muốn xóa : ");
                    int idDelete = Validate.validateInt();
                    if (cart.getProductCart().containsKey(idDelete)) {
                        System.out.print("Bạn có chắc chắn muốn xóa sản phẩm (Y/N) : ");
                        while (true) {
                            String str = Validate.validateString();
                            if (str.equalsIgnoreCase("y")) {
                                cart.getProductCart().remove(idDelete);
                                cartService.save(cart);
                                System.out.println(GREEN_BRIGHT + "Đã xóa sản phẩm thành công !" + RESET);
                                break;
                            } else if (str.equalsIgnoreCase("n")) {
                                break;
                            } else {
                                System.out.print(RED_BRIGHT + "Không hợp lệ, vui lòng nhập lại : " + RESET);
                            }
                        }
                    } else {
                        System.out.println(RED_BOLD_BRIGHT + "Không tồn tại sản phẩm đã nhập trong giỏ hàng !" + RESET);
                    }
                    break;
                case 3:
                    boolean check = true;
                    for (int idProduct : cart.getProductCart().keySet()) {
                        if (productService.findById(idProduct).getStock() >= cart.getProductCart().get(idProduct)) {
                            productService.findById(idProduct).setStock(productService.findById(idProduct).getStock() - cart.getProductCart().get(idProduct));
                        } else {
                            System.out.println("Sản phẩm [" + productService.findById(idProduct).getProductName() + "] trong kho không đủ, vui lòng điều chỉnh lại số lượng !");
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        Order order = new Order();
                        order.setOrderDetail(cart.getProductCart());
                        order.setId(orderService.getNewId());
                        order.setIdUser(cart.getIdUser());
                        order.setStatus(0);
                        order.setBuyDate(LocalDateTime.now());
                        order.setTotal(totalBill());
                        System.out.print("Nhập tên người nhận hàng : ");
                        String userBuy = Config.scanner().nextLine();
                        if (userBuy.isEmpty()) {
                            order.setReceiver(userService.findById(cart.getIdUser()).getFullName());
                        } else {
                            order.setReceiver(userBuy);
                        }
                        System.out.print("Nhập địa chỉ nhận hàng : ");
                        String address = Validate.validateString();
                        order.setAddress(address);
                        System.out.println("Nhập số điện thoại người nhận hàng : ");
                        while (true) {
                            String phone = Config.scanner().nextLine();
                            if (phone.isEmpty()) {
                                order.setNumberPhone(userService.findById(cart.getIdUser()).getPhone());
                                break;
                            } else if (Validate.validatePhoneNumber(phone)) {
                                order.setNumberPhone(phone);
                                break;
                            } else {
                                System.out.print("Số điện thoại không đúng định dạng mời nhập lại : ");
                            }
                        }
                        orderService.save(order);
                        cart.setProductCart(new HashMap<>());
                        cartService.save(cart);
                        System.out.println(GREEN_BOLD_BRIGHT + "Thanh toán thành công !" + RESET);
                        choice = 4;
                    }
                    break;
                case 4:
                    break;
                default:
                    System.out.println(RED_BRIGHT + "Lựa chọn không phù hợp. Mời nhập lại ! " + RESET);
                    break;
            }
        }

    }

    private String formatCurrency(double money) {
        DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
        return formatter.format(money);
    }

    public double totalBill() {
        double totalBill = 0;
        for (int id : cartService.findCartByUserLogin().getProductCart().keySet()) {
            totalBill += cartService.findCartByUserLogin().getProductCart().get(id) * productService.findById(id).getUnitPrice();
        }
        return totalBill;
    }
}
