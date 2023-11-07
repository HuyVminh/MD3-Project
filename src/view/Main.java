package view;

import config.Config;
import config.Validate;
import model.User;

import javax.jws.soap.SOAPBinding;

public class Main {
    public static void main(String[] args) {
        User userLogin = new Config<User>().readFile(Config.URL_USER_LOGIN);
        if (userLogin != null) {
            new Home().checkRoleLogin(userLogin);
        } else {
            new Home().menuHome();
        }
    }
}
