package view.admin;

import config.Validate;
import model.User;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.List;

import static model.RoleName.ADMIN;
import static model.RoleName.USER;
import static config.Color.*;

public class UserManagement {
    IUserService userService = new UserServiceIMPL();

    public void userManagement() {
        int choice = 0;
        while (choice != 5) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".----------------------------USER MANAGEMENT----------------------------.");
            System.out.println("|                       " + WHITE_BOLD_BRIGHT + "[1] Danh sách người dùng                         " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|                       " + WHITE_BOLD_BRIGHT + "[2] Tìm kiếm người dùng                          " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|                       " + WHITE_BOLD_BRIGHT + "[3] Thay đổi trạng thái                          " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|                       " + WHITE_BOLD_BRIGHT + "[4] Thay đổi quyền truy cập                      " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|                       " + WHITE_BOLD_BRIGHT + "[5] Quay lại                                     " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("'-----------------------------------------------------------------------'" + RESET);
            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn của bạn : " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    System.out.println(YELLOW_BOLD_BRIGHT + ".----------------------------------------------------DANH SÁCH NGƯỜI DÙNG---------------------------------------------------------.");
                    System.out.printf("|  %-3s | %-22s | %-23s | %-19s | %-15s | %-13s | %-13s |\n", "ID", "Họ và tên", "Tên đăng nhập", "Email", "Số điện thoại", "Vai trò", "Trạng thái");
                    System.out.println("|------+------------------------+-------------------------+---------------------+-----------------+---------------+---------------|");
                    if (userService.findAll().isEmpty())
                        System.out.println(RED_BOLD_BRIGHT + "Danh sách rỗng !" + RESET);
                    List<User> userList = userService.findAll();
                    for (User user : userList) {
                        user.display();
                    }
                    System.out.println(YELLOW_BOLD_BRIGHT + "'---------------------------------------------------------------------------------------------------------------------------------'" + RESET);
                    break;
                case 2:
                    System.out.println("Nhập ID người dùng cần tìm kiếm : ");
                    int idFind = Validate.validateInt();
                    if (userService.findById(idFind) == null) {
                        System.out.println(RED_BOLD_BRIGHT + "Không tìm thấy người dùng !" + RESET);
                        return;
                    } else {
                        User user = userService.findById(idFind);
                        System.out.println(YELLOW_BOLD_BRIGHT + ".-ID----------HỌ VÀ TÊN-------------------USERNAME-----------------EMAIL------------ĐIỆN THOẠI------QUYỀN TRUY CẬP---TRẠNG THÁI---.");
                        user.display();
                        System.out.println("'---------------------------------------------------------------------------------------------------------------------------------'" + RESET);
                    }
                    break;
                case 3:
                    System.out.println("Nhập ID người dùng cần thay đổi trạng thái : ");
                    int idUpdateStatus = Validate.validateInt();
                    if (userService.findById(idUpdateStatus) == null) {
                        System.out.println(RED_BOLD_BRIGHT + "Không tìm thấy người dùng !" + RESET);
                        return;
                    } else {
                        if (idUpdateStatus == 0) {
                            System.out.println(RED_BOLD_BRIGHT + "Không thể thay đổi trạng thái của tài khoản này!" + RESET);
                        } else {
                            User user = userService.findById(idUpdateStatus);
                            System.out.println(YELLOW_BOLD_BRIGHT + ".-ID----------HỌ VÀ TÊN-------------------USERNAME-----------------EMAIL------------ĐIỆN THOẠI------QUYỀN TRUY CẬP---TRẠNG THÁI---.");
                            user.display();
                            System.out.println("'---------------------------------------------------------------------------------------------------------------------------------'" + RESET);
                            System.out.println(WHITE_BOLD_BRIGHT + "Bạn có muốn khóa / mở khóa tài khoản này (Y/N) ?" + RESET);
                            while (true) {
                                String ch = Validate.validateString();
                                if (ch.equalsIgnoreCase("y")) {
                                    user.setStatus(!user.isStatus());
                                    System.out.println("Tài khoản đã " + ((user.isStatus()) ? "được mở khóa" : "bị khóa"));
                                    userService.save(user);
                                    break;
                                } else if (ch.equalsIgnoreCase("n")) {
                                    return;
                                }
                                System.out.println(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại" + RESET);
                            }
                        }
                    }
                    break;
                case 4:
                    System.out.println("Nhập ID người dùng cần thay đổi quyền truy cập : ");
                    int idUpdateRole = Validate.validateInt();
                    if (userService.findById(idUpdateRole) == null) {
                        System.out.println(RED_BOLD_BRIGHT + "Không tìm thấy người dùng !" + RESET);
                        return;
                    } else {
                        if (idUpdateRole == 0) {
                            System.out.println(RED_BOLD_BRIGHT + "Không thể thay đổi quyền cập nhật của tài khoản này!" + RESET);
                        } else {
                            User user = userService.findById(idUpdateRole);
                            System.out.println(YELLOW_BOLD_BRIGHT + ".-ID----------HỌ VÀ TÊN-------------------USERNAME-----------------EMAIL------------ĐIỆN THOẠI------QUYỀN TRUY CẬP---TRẠNG THÁI---.");
                            user.display();
                            System.out.println("'---------------------------------------------------------------------------------------------------------------------------------'" + RESET);
                            System.out.print("Bạn có muốn thay đổi quyền truy cập của tài khoản này (Y/N) ? ");
                            while (true) {
                                String ch = Validate.validateString();
                                if (ch.equalsIgnoreCase("y")) {
                                    if (user.getRole() == ADMIN) {
                                        user.setRole(USER);
                                        userService.save(user);
                                    } else {
                                        user.setRole(ADMIN);
                                        userService.save(user);
                                    }
                                    System.out.println(GREEN_BOLD_BRIGHT + "Tài khoản đã được cập nhật quyền truy cập !" + RESET);
                                } else if (ch.equalsIgnoreCase("n")) {
                                    break;
                                }
                                System.out.print(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại : " + RESET);
                            }
                        }
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.print(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại : " + RESET);
            }
        }
    }
}
