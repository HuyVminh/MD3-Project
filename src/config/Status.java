package config;

import static config.Color.*;

public class Status {
    public static int WAITING = 0;
    public static int ACCEPT = 1;
    public static int CANCEL = 2;

    public static String getStatusByCodeForUser(int code) {

        switch (code) {
            case 0:
                return YELLOW_BOLD_BRIGHT + "Đang chờ xác nhận" + RESET;
            case 1:
                return GREEN_BOLD_BRIGHT + "Đã được chấp nhận" + RESET;
            case 2:
                return RED_BOLD_BRIGHT + "Đã bị hủy" + RESET;
            default:
                return "Không hợp lệ";
        }
    }
    public static String getStatusByCodeForAdmin(int code) {

        switch (code) {
            case 0:
                return YELLOW_BOLD_BRIGHT + "Đang chờ xác nhận" + RESET;
            case 1:
                return GREEN_BOLD_BRIGHT + "Đã chấp nhận" + RESET;
            case 2:
                return RED_BOLD_BRIGHT + "Đã hủy" + RESET;
            default:
                return "Không hợp lệ";
        }
    }
}
