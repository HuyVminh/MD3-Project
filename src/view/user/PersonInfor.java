package view.user;

import config.Config;
import config.Format;
import config.Status;
import config.Validate;
import model.Category;
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

public class PersonInfor {
    IUserService userService = new UserServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    static Config<User> config = new Config<>();
    public static User userLogin;

    static {
        userLogin = config.readFile(Config.URL_USER_LOGIN);
    }

    public void personInfor() {
        int choice = 0;
        while (choice != 4) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".----------------------------------------------------------------------.");
            System.out.println("|                              TÀI KHOẢN                               |");
            System.out.println("|----------------------------------------------------------------------|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "                    [1] Thông tin cá nhân                             " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "                    [2] Lịch sử đặt hàng                              " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "                    [3] Thay đổi mật khẩu                             " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "                    [4] Quay lại                                      " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("'----------------------------------------------------------------------'" + RESET);
            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn của bạn (1/2/3/4): " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    inforAccount();
                    break;
                case 2:
                    historyOrdered();
                    break;
                case 3:
                    changePassword();
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
    }

    private void inforAccount() {
        int choice = 0;
        while (choice != 5) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".--------------------------THÔNG TIN CÁ NHÂN---------------------------.");
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "      [1]    %-14s" + " : " + BLUE_BOLD_BRIGHT + "    %-35s " + YELLOW_BOLD_BRIGHT + "|\n", "Tên đăng nhập", userLogin.getUsername());
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "      [2]    %-14s" + " : " + BLUE_BOLD_BRIGHT + "    %-35s " + YELLOW_BOLD_BRIGHT + "|\n", "Họ và tên", userLogin.getFullName());
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "      [3]    %-14s" + " : " + BLUE_BOLD_BRIGHT + "    %-35s " + YELLOW_BOLD_BRIGHT + "|\n", "Email", userLogin.getEmail());
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "      [4]    %-14s" + " : " + BLUE_BOLD_BRIGHT + "    %-35s " + YELLOW_BOLD_BRIGHT + "|\n", "Điện thoại", userLogin.getPhone());
            System.out.println("|----------------------------------------------------------------------|");
            System.out.println("|" + RED_BOLD_BRIGHT + "                           [5] Quay lại                               " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("'----------------------------------------------------------------------'" + RESET);
            System.out.print(WHITE_BOLD_BRIGHT + "Để chỉnh sửa thông tin hoặc quay lại, nhấn phím (1/2/3/4/5) tương ứng, mời nhập lựa chọn : " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    System.out.print("Nhập tên đăng nhập : ");
                    String userName = Config.scanner().nextLine();
                    if (userName.isEmpty()) {
                        break;
                    } else {
                        System.out.print("Xác nhận mật khẩu : ");
                        String password = Validate.validateString();
                        if (password.equalsIgnoreCase(userLogin.getPassword())) {
                            userLogin.setUsername(userName);
                            config.writeFile(Config.URL_USER_LOGIN, userLogin);
                            User userEdit = userService.findById(userLogin.getUserId());
                            userService.save(userEdit);
                            System.out.println(GREEN_BOLD_BRIGHT + "Cập nhật thông tin thành công !" + RESET);
                        } else System.out.println(RED_BOLD_BRIGHT + "Sai mật khẩu !" + RESET);
                    }
                    break;
                case 2:
                    System.out.print("Nhập họ và tên : ");
                    String fullName = Config.scanner().nextLine();
                    if (fullName.isEmpty()) {
                        break;
                    } else {
                        System.out.print("Xác nhận mật khẩu : ");
                        String password = Validate.validateString();
                        if (password.equalsIgnoreCase(userLogin.getPassword())) {
                            userLogin.setFullName(fullName);
                            config.writeFile(Config.URL_USER_LOGIN, userLogin);
                            User userEdit = userService.findById(userLogin.getUserId());
                            userService.save(userEdit);
                            System.out.println(GREEN_BOLD_BRIGHT + "Cập nhật thông tin thành công !" + RESET);
                        } else System.out.println(RED_BOLD_BRIGHT + "Sai mật khẩu !" + RESET);
                    }
                    break;
                case 3:
                    System.out.print("Nhập email : ");
                    while (true) {
                        String email = Config.scanner().nextLine();
                        if (email.isEmpty()) {
                            break;
                        } else if (Validate.validateEmail(email)) {
                            System.out.print("Xác nhận mật khẩu : ");
                            String password = Validate.validateString();
                            if (password.equalsIgnoreCase(userLogin.getPassword())) {
                                userLogin.setEmail(email);
                                config.writeFile(Config.URL_USER_LOGIN, userLogin);
                                User userEdit = userService.findById(userLogin.getUserId());
                                userService.save(userEdit);
                                System.out.println(GREEN_BOLD_BRIGHT + "Cập nhật thông tin thành công !" + RESET);
                            } else System.out.println(RED_BOLD_BRIGHT + "Sai mật khẩu !" + RESET);
                            break;
                        } else {
                            System.out.print(RED_BRIGHT + "Email không đúng định dạng, vui lòng nhập lại : " + RESET);
                        }
                    }
                    break;
                case 4:
                    System.out.print("Nhập số điện thoại : ");
                    while (true) {
                        String phone = Config.scanner().nextLine();
                        if (phone.isEmpty()) {
                            break;
                        } else if (Validate.validatePhoneNumber(phone)) {
                            System.out.print("Xác nhận mật khẩu : ");
                            String password = Validate.validateString();
                            if (password.equalsIgnoreCase(userLogin.getPassword())) {
                                userLogin.setPhone(phone);
                                config.writeFile(Config.URL_USER_LOGIN, userLogin);
                                User userEdit = userService.findById(userLogin.getUserId());
                                userService.save(userEdit);
                                System.out.println(GREEN_BOLD_BRIGHT + "Cập nhật thông tin thành công !" + RESET);
                            } else System.out.println(RED_BOLD_BRIGHT + "Sai mật khẩu !" + RESET);
                            break;
                        } else {
                            System.out.print(RED_BRIGHT + "Số điện thoại phải gồm 10 số và bắt đầu bằng số 0, vui lòng nhập lại : " + RESET);
                        }
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.println(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại !" + RESET);
                    break;
            }
        }
    }

    private void historyOrdered() {
        int choice;
        while (true) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".------------------------------------------------------------------------------------------------------------.");
            System.out.println("|                                          LỊCH SỬ MUA HÀNG                                  " + RED_BOLD_BRIGHT + "[0] Quay lại    " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|------------------------------------------------------------------------------------------------------------|");
            System.out.println("|  ID  |     Người đặt hàng     |   Tổng thành tiền   |   Thời gian khởi tạo đơn hàng  |      Trạng thái     |");
            System.out.println("|------------------------------------------------------------------------------------------------------------|");
            List<Order> orderList = orderService.findOrderByUserName(userLogin.getUsername());
            if (orderList.isEmpty()) {
                System.out.println("|                                        " + RED_BOLD_BRIGHT + "Danh sách trống !                                                   " + YELLOW_BOLD_BRIGHT + "|");
                System.out.println("'------------------------------------------------------------------------------------------------------------'");
                System.out.print(WHITE_BOLD_BRIGHT + "Nhập [0] để quay lại : " + RESET);
                choice = Validate.validateInt();
                while (choice != 0) {
                    System.out.print(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại : " + RESET);
                    choice = Validate.validateInt();
                }
                break;
            } else {
                for (Order order : orderList) {
                    System.out.printf(YELLOW_BOLD_BRIGHT + "|  " + WHITE_BOLD_BRIGHT + "%-2d  " + YELLOW_BOLD_BRIGHT + "|   " + WHITE_BOLD_BRIGHT + "%-19s  " + YELLOW_BOLD_BRIGHT + "|  " + WHITE_BOLD_BRIGHT + "%18s " + YELLOW_BOLD_BRIGHT + "|       " + WHITE_BOLD_BRIGHT + "%-23s  " + YELLOW_BOLD_BRIGHT + "|  " + WHITE_BOLD_BRIGHT + "%-28s  " + YELLOW_BOLD_BRIGHT + "|\n", order.getId(), userService.findById(order.getIdUser()).getUsername(), formatCurrency(order.getTotal()), Format.getCurrentYearMonth(order.getBuyDate()), Status.getStatusByCodeForUser(order.getStatus()));
                }
                System.out.println("'------------------------------------------------------------------------------------------------------------'" + RESET);
                System.out.print(WHITE_BOLD_BRIGHT + "Nhập ID đơn hàng để xem chi tiết (Nhập [0] để quay lại) : " + RESET);
                while (true) {
                    int choiceId = Validate.validateInt();
                    if (choiceId == 0) {
                        break;
                    } else if (orderService.findById(choiceId) != null) {
                        Order order = orderService.findById(choiceId);
                        User user = userService.findById(order.getIdUser());
                        System.out.println(YELLOW_BOLD_BRIGHT + ".------------------------------------------------------------------------------------------------------------------------------.");
                        System.out.println("|                                                    CHI TIẾT ĐƠN HÀNG                                                         |");
                        System.out.println("|------------------------------------------------------------------------------------------------------------------------------|");
                        System.out.printf("|     " + WHITE_BOLD_BRIGHT + "Người đặt hàng  :  " + BLUE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "|        " + WHITE_BOLD_BRIGHT + "Tên người nhận hàng         :  " + BLUE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "|\n", user.getFullName(), order.getReceiver());
                        System.out.printf("|     " + WHITE_BOLD_BRIGHT + "Email           :  " + BLUE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "|        " + WHITE_BOLD_BRIGHT + "Tổng thành tiền             :  " + BLUE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "|\n", user.getEmail(), formatCurrency(order.getTotal()));
                        System.out.printf("|     " + WHITE_BOLD_BRIGHT + "Địa chỉ         :  " + BLUE_BOLD_BRIGHT + "%-30s " + YELLOW_BOLD_BRIGHT + "|        " + WHITE_BOLD_BRIGHT + "Trạng thái đơn hàng         :  " + BLUE_BOLD_BRIGHT + "%-41s " + YELLOW_BOLD_BRIGHT + "|\n", order.getAddress(), Status.getStatusByCodeForUser(order.getStatus()));
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
                            System.out.println(YELLOW_BOLD_BRIGHT + "|                                                    " + RED_BOLD_BRIGHT + "[0] Quay lại                                                              " + YELLOW_BOLD_BRIGHT + "|");
                            System.out.println("'------------------------------------------------------------------------------------------------------------------------------'" + RESET);
                            int choiceChangeStatus = -1;
                            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn của bạn : " + RESET);
                            while (choiceChangeStatus != 0) {
                                choiceChangeStatus = Validate.validateInt();
                                if (choiceChangeStatus != 0) {
                                    System.out.print(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại : " + RESET);
                                }
                            }
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    private void changePassword() {
        System.out.println(YELLOW_BOLD_BRIGHT + ".----------------------------------------------------------------------.");
        System.out.println("|                         THAY ĐỔI MẬT KHẨU                            |");
        System.out.println("'----------------------------------------------------------------------'" + RESET);
        System.out.print("Nhập mật khẩu hiện tại : ");
        String pass = Config.scanner().nextLine();
        if (userLogin.equals(pass)) {
            System.out.print("Nhập mật khẩu mới : ");
            String newPass = Validate.validateString();
            System.out.print("Xác nhận mật khẩu : ");
            while (true) {
                String repeatPass = Validate.validateString();
                if (newPass.equals(repeatPass)) {
                    userLogin.setPassword(newPass);
                    config.writeFile(Config.URL_USER_LOGIN, userLogin);
                    User userEdit = userService.findById(userLogin.getUserId());
                    userService.save(userEdit);
                    break;
                } else {
                    System.out.print(RED_BRIGHT + "Mật khẩu không khớp, vui lòng nhập lại : " + RESET);
                }
            }
        } else {
            System.out.println(RED_BOLD_BRIGHT + "Sai mật khẩu !" + RESET);
        }
        System.out.println(YELLOW_BOLD_BRIGHT + "------------------------------------------------------------------------" + RESET);
    }

    private String formatCurrency(double money) {
        DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
        return formatter.format(money);
    }
}
