package view.admin;

import config.Config;
import config.Validate;
import model.User;
import view.Home;

import static config.Color.*;

public class AdminManager {
    static Config<User> config = new Config<>();
    public static User userLogin;

    static {
        userLogin = config.readFile(Config.URL_USER_LOGIN);
    }
    public void menuAdmin() {
        int choice = 0;
        while (choice != 5) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".----------------------------------------------------------------------.");
            System.out.printf("|  BICYCLE SHOP ADMINISTATION                  Xin chào, " + BLUE_BOLD_BRIGHT + "%-13s " + YELLOW_BOLD_BRIGHT + "|\n" + RESET, userLogin.getUsername());
            System.out.println(YELLOW_BOLD_BRIGHT + "|----------------------------SHOP MANAGEMENT---------------------------|");
            System.out.println("|" + RESET + "                        " + WHITE_BOLD_BRIGHT + "[1] Quản lý sản phẩm                          " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + RESET + "                        " + WHITE_BOLD_BRIGHT + "[2] Quản lý danh mục                          " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + RESET + "                        " + WHITE_BOLD_BRIGHT + "[3] Quản lý người dùng                        " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + RESET + "                        " + WHITE_BOLD_BRIGHT + "[4] Quản lý đơn hàng                          " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + RESET + "                        " + WHITE_BOLD_BRIGHT + "[5] Đăng xuất                                 " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("'----------------------------------------------------------------------'" + RESET);
            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn : ");
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    new ProductManagement().productManagement();
                    break;
                case 2:
                    new CategoryManagement().categoryManagement();
                    break;
                case 3:
                    new UserManagement().userManagement();
                    break;
                case 4:
                    new OrderManagement().orderManagement();
                    break;
                case 5:
                    new Config<User>().writeFile(Config.URL_USER_LOGIN, null);
                    System.out.println(GREEN_BOLD_BRIGHT + "Đăng xuất thành công !" + RESET);
                    new Home().menuHome();
                    break;
                default:
                    System.out.println(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại." + RESET);
            }
        }
    }
}
