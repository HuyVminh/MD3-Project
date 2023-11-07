package view;

import config.Config;
import config.Validate;
import model.RoleName;
import model.User;
import service.user.IUserService;
import service.user.UserServiceIMPL;
import view.admin.AdminManager;
import view.user.UserManager;

import static config.Color.*;

public class Home {
    IUserService userService = new UserServiceIMPL();
    public static User userLogin;

    public void menuHome() {
        int choice = 0;
        while (choice != 3) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".-----------------------------------------------------.");
            System.out.println("|" + BLUE_BOLD_BRIGHT + "              WELCOME TO BICYCLE SHOP                " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|------------------------MENU-------------------------|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "               [1] Login                             " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "               [2] Register                          " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "               [3] Exit                              " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("'-----------------------------------------------------'" + RESET);
            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn của bạn (1/2/3): " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Đang thoát chương trình...");
                    break;
                default:
                    System.out.println("Không hợp lệ, vui lòng nhập lại.");
            }
        }
    }

    private void login() {

        System.out.println(YELLOW_BOLD_BRIGHT + "-----------------------FORM LOGIN----------------------" + RESET);
        System.out.print(WHITE_BOLD_BRIGHT + "       Nhập username : " + RESET);
        String username = Validate.validateString();
        System.out.print(WHITE_BOLD_BRIGHT + "       Nhập mật khẩu : " + RESET);
        String password = Validate.validateString();
        System.out.println(YELLOW_BOLD_BRIGHT + "-------------------------------------------------------" + RESET);
        User user = userService.checkLogin(username, password);
        if (user == null) { // check xem có tồn tại hay không
            System.out.println(RED_BOLD_BRIGHT + "Sai username hoặc mật khẩu !" + RESET);
        } else {
            checkRoleLogin(user);
        }
    }

    public void checkRoleLogin(User user) {
        if (user.getRole() == RoleName.ADMIN) {
            new Config<User>().writeFile(Config.URL_USER_LOGIN, user); // ghi vào file
            new AdminManager().menuAdmin();
        } else {
            if (user.isStatus()) {
                new Config<User>().writeFile(Config.URL_USER_LOGIN, user); // ghi vào file
                new UserManager().menuUser();
            } else {
                System.out.println("Tài khoản của bạn đang bị khóa.");
            }
        }
    }

    private void register() {
        System.out.println("======FORM REGISTER ======");
        User user = new User();
        user.setUserId(userService.getNewId());
        System.out.println("UserId : " + user.getUserId());
        System.out.println("Nhập họ tên : ");
        user.setFullName(Validate.validateString());
        System.out.println("Nhập tên tài khoản : ");
        while (true) {
            String username = Validate.validateString();
            if (userService.existUserName(username)) {
                System.out.println("Đã tồn tại tên đăng nhập, mời nhập lại");
            } else {
                user.setUsername(username);
                break;
            }
        }
        System.out.println("Nhập mật khẩu");
        user.setPassword(Validate.validateString());
        System.out.println("Nhập lại mật khẩu :");
        while (true) {
            String repeatPass = Validate.validateString();
            if (user.getPassword().equals(repeatPass)) {
                break;
            } else {
                System.out.println("Mật khẩu không khớp, mời nhập lại.");
            }
        }
        System.out.println("Nhập email : ");
        while (true) {
            String email = Validate.validateEmail();
            if (userService.existEmail(email)) {
                System.out.println("Đã tồn tại email, mời nhập lại");
            } else {
                user.setEmail(email);
                break;
            }
        }
        System.out.println("Nhập số điện thoại : ");
        while (true) {
            String phone = Validate.validateString();
            if (Validate.validatePhoneNumber(phone)) {
                user.setPhone(phone);
                break;
            } else {
                System.out.println("Số điện thoại phải gồm 10 số và bắt đầu bằng số 0, vui lòng nhập lại :");
            }
        }
        userService.save(user);
        System.out.println("Tạo tài khoản thành công");
    }
}
