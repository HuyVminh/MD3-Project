package service.user;

import model.User;
import service.IGenericService;

public interface IUserService extends IGenericService<User> {
    boolean existUserName(String username);
    boolean existEmail(String email);

    User checkLogin(String username, String password);
}
