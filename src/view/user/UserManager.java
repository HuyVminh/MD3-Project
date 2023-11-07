package view.user;

import config.Config;
import config.Validate;
import model.User;
import view.Home;

import static config.Color.*;

public class UserManager {
    static Config<User> config = new Config<>();
    public static User userLogin;

    static {
        userLogin = config.readFile(Config.URL_USER_LOGIN);
    }

    public void menuUser() {
        int choice = 0;
        while (choice != 4) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".----------------------------------------------------------------------.");
            System.out.printf("|     BICYCLE SHOP                             Xin chào, " + BLUE_BOLD_BRIGHT + "%-13s " + YELLOW_BOLD_BRIGHT + "|\n" + RESET, userLogin.getUsername());
            System.out.println(YELLOW_BOLD_BRIGHT + "|--------------------------BICYCLE STORE-------------------------------|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "                  [1] Tài khoản                                       " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "                  [2] Danh sách sản phẩm                              " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "                  [3] Giỏ hàng                                        " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "                  [4] Đăng xuất                                       " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("'----------------------------------------------------------------------'" + RESET);
            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn của bạn (1/2/3/4): " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    new PersonInfor().personInfor();
                    break;
                case 2:
                    new ListProducts().listProducts();
                    break;
                case 3:
                    new MyCart().cart();
                    break;
                case 4:
                    new Config<User>().writeFile(Config.URL_USER_LOGIN, null);
                    new Home().menuHome();
                    break;
                default:
                    System.out.println("Không hợp lệ, vui lòng nhập lại.");

            }
        }
    }
}
