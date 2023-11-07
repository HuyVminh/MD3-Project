package service.user;

import config.Config;
import model.RoleName;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceIMPL implements IUserService {
    static Config<List<User>> config = new Config<>();
    public static List<User> userList;

    static {
        userList = config.readFile(Config.URL_USERS);
        if (userList == null) {
            userList = new ArrayList<>();
            userList.add(new User(0, "admin", "admin@gmail.com", "admin", "abc123", RoleName.ADMIN, true, "0123456789"));
            new UserServiceIMPL().updateData();
        }
    }

    @Override
    public List<User> findAll() {
        return userList;
    }

    @Override
    public void save(User user) {
        if (findById(user.getUserId()) == null) { // kiểm tra user đã tồn tại trong list chưa
            userList.add(user);
            updateData();// nếu chưa thì thêm user mới
        } else {
            userList.set(userList.indexOf(user), user);  // nếu tồn tại thì set lại user vào vị trí user trong list
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        userList.remove(findById(id));
        updateData();
    }

    @Override
    public User findById(int id) {
        for (User user : userList) {
            if (user.getUserId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (User user : userList) {
            if (user.getUserId() > idMax) {
                idMax = user.getUserId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_USERS, userList);
    }

    @Override
    public boolean existUserName(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existEmail(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User checkLogin(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
