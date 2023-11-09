package view.admin;

import config.Format;
import config.Status;
import config.Validate;
import model.Order;
import model.Product;
import model.User;
import service.order.IOrderService;
import service.order.OrderServiceIMPL;
import service.product.IProductService;
import service.product.ProductServiceIMPL;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.text.DecimalFormat;
import java.util.List;

import static config.Color.*;
import static config.Color.RESET;

public class OrderManagement {
    static IProductService productService = new ProductServiceIMPL();
    static IOrderService orderService = new OrderServiceIMPL();
    static IUserService userService = new UserServiceIMPL();

    public void orderManagement() {
        int choice = 0;
        while (choice != 4) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".-----------------------------QUẢN LÝ ĐƠN HÀNG------------------------------.");
            System.out.println("|" + RESET + "                    " + WHITE_BOLD_BRIGHT + "[1] Danh sách đơn hàng                                 " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + RESET + "                    " + WHITE_BOLD_BRIGHT + "[2] Tìm kiếm đơn hàng theo userName                    " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + RESET + "                    " + WHITE_BOLD_BRIGHT + "[3] Sắp xếp đơn hàng theo thời gian khởi tạo           " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + RESET + "                    " + WHITE_BOLD_BRIGHT + "[4] Quay lại                                           " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("'---------------------------------------------------------------------------'" + RESET);
            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn : " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    while (true) {
                        System.out.println(YELLOW_BOLD_BRIGHT + ".------------------------------------------------------------------------------------------------------------.");
                        System.out.println("|                                          DANH SÁCH ĐƠN HÀNG                                " + RED_BOLD_BRIGHT + "[0] Quay lại    " + YELLOW_BOLD_BRIGHT + "|");
                        System.out.println("|------------------------------------------------------------------------------------------------------------|");
                        System.out.println("|  ID  |     Người đặt hàng     |   Tổng thành tiền   |   Thời gian khởi tạo đơn hàng  |      Trạng thái     |");
                        System.out.println("|------------------------------------------------------------------------------------------------------------|");
                        if (orderService.findAll().isEmpty()) {
                            System.out.println("|                                        " + RED_BOLD_BRIGHT + "Danh sách trống !                                                   " + YELLOW_BOLD_BRIGHT + "|");
                            System.out.println("'------------------------------------------------------------------------------------------------------------'" + RESET);
                            break;
                        } else {
                            for (Order order : orderService.findAll()) {
                                System.out.printf("|  " + WHITE_BOLD_BRIGHT + "%-2d  " + YELLOW_BOLD_BRIGHT + "|   " + WHITE_BOLD_BRIGHT + "%-19s  " + YELLOW_BOLD_BRIGHT + "|  " + WHITE_BOLD_BRIGHT + "%18s " + YELLOW_BOLD_BRIGHT + "|       " + WHITE_BOLD_BRIGHT + "%-23s  " + YELLOW_BOLD_BRIGHT + "|  " + WHITE_BOLD_BRIGHT + "%-28s  " + YELLOW_BOLD_BRIGHT + "|\n", order.getId(), userService.findById(order.getIdUser()).getUsername(), formatCurrency(order.getTotal()), Format.getCurrentYearMonth(order.getBuyDate()), Status.getStatusByCodeForAdmin(order.getStatus()));
                            }
                            System.out.println("'------------------------------------------------------------------------------------------------------------'" + RESET);
                        }
                        System.out.print(WHITE_BOLD_BRIGHT + "Nhập ID đơn hàng để xem chi tiết (Nhập [0] để quay lại) : " + RESET);
                        int choiceId = Validate.validateInt();
                        if (choiceId == 0) {
                            break;
                        }
                        orderDetails(choiceId);
                    }
                    break;
                case 2:
                    System.out.print(WHITE_BOLD_BRIGHT + "Nhập userName cần tìm kiếm đơn hàng : " + RESET);
                    String str = Validate.validateString();
                    List<Order> orderListFound = orderService.findOrderByUserName(str);
                    if (orderListFound.isEmpty()) {
                        System.out.println(RED_BOLD_BRIGHT + "Không tìm thấy đơn hàng nào của [ " + str + " ] !" + RESET);
                    } else {
                        while (true) {
                            System.out.println(YELLOW_BOLD_BRIGHT + ".------------------------------------------------------------------------------------------------------------.");
                            System.out.println("|                                          DANH SÁCH ĐƠN HÀNG                                " + RED_BOLD_BRIGHT + "[0] Quay lại    " + YELLOW_BOLD_BRIGHT + "|");
                            System.out.println("|------------------------------------------------------------------------------------------------------------|");
                            System.out.println("|  ID  |     Người đặt hàng     |   Tổng thành tiền   |   Thời gian khởi tạo đơn hàng  |      Trạng thái     |");
                            System.out.println("|------------------------------------------------------------------------------------------------------------|");
                            for (Order order : orderListFound) {
                                System.out.printf("|  " + WHITE_BOLD_BRIGHT + "%-2d  " + YELLOW_BOLD_BRIGHT + "|   " + WHITE_BOLD_BRIGHT + "%-19s  " + YELLOW_BOLD_BRIGHT + "|  " + WHITE_BOLD_BRIGHT + "%18s " + YELLOW_BOLD_BRIGHT + "|       " + WHITE_BOLD_BRIGHT + "%-23s  " + YELLOW_BOLD_BRIGHT + "|  " + WHITE_BOLD_BRIGHT + "%-28s  " + YELLOW_BOLD_BRIGHT + "|\n", order.getId(), userService.findById(order.getIdUser()).getUsername(), formatCurrency(order.getTotal()), Format.getCurrentYearMonth(order.getBuyDate()), Status.getStatusByCodeForAdmin(order.getStatus()));
                            }
                            System.out.println("'------------------------------------------------------------------------------------------------------------'" + RESET);
                            System.out.print(WHITE_BOLD_BRIGHT + "Nhập ID đơn hàng để xem chi tiết (Nhập [0] để quay lại) : " + RESET);
                            int choiceId = Validate.validateInt();
                            if (choiceId == 0) {
                                break;
                            }
                            orderDetails(choiceId);
                        }
                    }
                    break;
                case 3:
                    while (true) {
                        orderService.sortOrderByDate();
                        System.out.println(YELLOW_BOLD_BRIGHT + ".------------------------------------------------------------------------------------------------------------.");
                        System.out.println("|                           DANH SÁCH ĐƠN HÀNG SẮP XẾP THEO THỜI GIAN KHỞI TẠO               " + RED_BOLD_BRIGHT + "[0] Quay lại    " + YELLOW_BOLD_BRIGHT + "|");
                        System.out.println("|------------------------------------------------------------------------------------------------------------|");
                        System.out.println("|  ID  |     Người đặt hàng     |   Tổng thành tiền   |   Thời gian khởi tạo đơn hàng  |      Trạng thái     |");
                        System.out.println("|------------------------------------------------------------------------------------------------------------|");
                        if (orderService.findAll().isEmpty()) {
                            System.out.println("|                                        " + RED_BOLD_BRIGHT + "Danh sách trống !                                                   " + YELLOW_BOLD_BRIGHT + "|");
                            System.out.println("'------------------------------------------------------------------------------------------------------------'" + RESET);
                        } else {
                            for (Order order : orderService.findAll()) {
                                System.out.printf("|  " + WHITE_BOLD_BRIGHT + "%-2d  " + YELLOW_BOLD_BRIGHT + "|   " + WHITE_BOLD_BRIGHT + "%-19s  " + YELLOW_BOLD_BRIGHT + "|  " + WHITE_BOLD_BRIGHT + "%18s " + YELLOW_BOLD_BRIGHT + "|       " + WHITE_BOLD_BRIGHT + "%-23s  " + YELLOW_BOLD_BRIGHT + "|  " + WHITE_BOLD_BRIGHT + "%-28s  " + YELLOW_BOLD_BRIGHT + "|\n", order.getId(), userService.findById(order.getIdUser()).getUsername(), formatCurrency(order.getTotal()), Format.getCurrentYearMonth(order.getBuyDate()), Status.getStatusByCodeForAdmin(order.getStatus()));
                            }
                            System.out.println("'------------------------------------------------------------------------------------------------------------'" + RESET);
                        }
                        System.out.print(WHITE_BOLD_BRIGHT + "Nhập ID đơn hàng để xem chi tiết (Nhập [0] để quay lại) : " + RESET);
                        int choiceId = Validate.validateInt();
                        if (choiceId == 0) {
                            break;
                        }
                        orderDetails(choiceId);
                    }
                    break;
                case 4:
                    break;
            }
        }
    }

    private String formatCurrency(double money) {
        DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
        return formatter.format(money);
    }

    private void orderDetails(int choiceId) {
        while (true) {
            if (orderService.findById(choiceId) != null) {
                Order order = orderService.findById(choiceId);
                User user = userService.findById(order.getIdUser());
                System.out.println(YELLOW_BOLD_BRIGHT + ".------------------------------------------------------------------------------------------------------------------------------.");
                System.out.println("|                                                    CHI TIẾT ĐƠN HÀNG                                                         |");
                System.out.println("|------------------------------------------------------------------------------------------------------------------------------|");
                System.out.printf("|     " + WHITE_BOLD_BRIGHT + "Tên đăng nhập   :  " + BLUE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "|        " + WHITE_BOLD_BRIGHT + "Tên người nhận hàng         :  " + BLUE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "|\n", user.getFullName(), order.getReceiver());
                System.out.printf("|     " + WHITE_BOLD_BRIGHT + "Email           :  " + BLUE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "|        " + WHITE_BOLD_BRIGHT + "Tổng thành tiền             :  " + BLUE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "|\n", user.getEmail(), formatCurrency(order.getTotal()));
                System.out.printf("|     " + WHITE_BOLD_BRIGHT + "Địa chỉ         :  " + BLUE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "|        " + WHITE_BOLD_BRIGHT + "Trạng thái đơn hàng         :  " + BLUE_BOLD_BRIGHT + "%-41s " + YELLOW_BOLD_BRIGHT + "|\n", order.getAddress(), Status.getStatusByCodeForAdmin(order.getStatus()));
                System.out.printf("|     " + WHITE_BOLD_BRIGHT + "Điện thoại      :  " + BLUE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "|        " + WHITE_BOLD_BRIGHT + "Thời gian khởi tạo đơn hàng :  " + BLUE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "|\n", order.getNumberPhone(), Format.getCurrentYearMonth(order.getBuyDate()));
                System.out.println("|                                                                                                                              |");
                System.out.println("|--ID-------------------Tên sản phẩm-----------------------Số lượng------------Đơn giá---------------------Thành tiền----------|");
                for (Integer idProduct : order.getOrderDetail().keySet()) {
                    Product product = productService.findById(idProduct);
                    System.out.printf("|  " + WHITE_BOLD_BRIGHT + "%-2d  " + YELLOW_BOLD_BRIGHT + "|   " + WHITE_BOLD_BRIGHT + "%-42s " + YELLOW_BOLD_BRIGHT + "|     " + WHITE_BOLD_BRIGHT + "%-7s  " + YELLOW_BOLD_BRIGHT + "| " + WHITE_BOLD_BRIGHT + "%22s " + YELLOW_BOLD_BRIGHT + "| " + WHITE_BOLD_BRIGHT + "%30s " + YELLOW_BOLD_BRIGHT + "|\n", product.getProductId(), product.getProductName(), order.getOrderDetail().get(idProduct), formatCurrency(product.getUnitPrice()), formatCurrency(product.getUnitPrice() * order.getOrderDetail().get(idProduct)));
                }
                System.out.println("|------------------------------------------------------------------------------------------------------------------------------|");
                if (order.getStatus() == 0) {
                    System.out.println("|               [1] Xác nhận đơn hàng                 |                 [2] Hủy đơn hàng                  |    " + RED_BOLD_BRIGHT + "[0] Quay lại    " + YELLOW_BOLD_BRIGHT + "|");
                    System.out.println("'------------------------------------------------------------------------------------------------------------------------------'" + RESET);
                    System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn của bạn : " + RESET);
                    int choiceChangeStatus;
                    do {
                        choiceChangeStatus = Validate.validateInt();
                        switch (choiceChangeStatus) {
                            case 1:
                                order.setStatus(1);
                                orderService.save(order);
                                System.out.println(GREEN_BOLD_BRIGHT + "Đơn hàng đã được xác nhận !" + RESET);
                                choiceChangeStatus = 0;
                                break;
                            case 2:
                                order.setStatus(2);
                                orderService.save(order);
                                for (int idProduct : order.getOrderDetail().keySet()) {
                                    Product product = productService.findById(idProduct);
                                    product.setStock(product.getStock() + order.getOrderDetail().get(idProduct));
                                    productService.save(product);
                                }
                                System.out.println(RED_BOLD_BRIGHT + "Đơn hàng đã bị hủy !" + RESET);
                                choiceChangeStatus = 0;
                                break;
                            case 0:
                                break;
                            default:
                                System.out.print(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại : " + RESET);

                        }
                    } while (choiceChangeStatus != 0);
                    break;
                } else {
                    System.out.println("|                                                    " + RED_BOLD_BRIGHT + "[0] Quay lại    " + YELLOW_BOLD_BRIGHT + "                                                          |");
                    System.out.println("'------------------------------------------------------------------------------------------------------------------------------'" + RESET);
                    int choiceChangeStatus = -1;
                    System.out.print(WHITE_BOLD_BRIGHT + "Nhập [0] để quay lại : " + RESET);
                    while (choiceChangeStatus != 0) {
                        choiceChangeStatus = Validate.validateInt();
                        if (choiceChangeStatus != 0) {
                            System.out.print(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại : " + RESET);
                        }
                    }
                    break;
                }
            } else {
                System.out.println(RED_BOLD_BRIGHT + "Không tồn tại đơn hàng !" + RESET);
                break;
            }
        }
    }
}
