package config;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Config<T> {
    public static Scanner scanner() {
        return new Scanner(System.in);
    }

    public static final String URL_USERS = "src/config/data/users.txt";
    public static final String URL_USER_LOGIN = "src/config/data/userLogin.txt";
    public static final String URL_PRODUCT = "src/config/data/product.txt";
    public static final String URL_CATEGORY = "src/config/data/category.txt";
    public static final String URL_CART = "src/config/data/cart.txt";
    public static final String URL_ORDER = "src/config/data/order.txt";


    public void writeFile(String PATH_FILE, T t) {
        File file = new File(PATH_FILE);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            fos.close();
            oos.close();
        } catch (Exception e) {
            System.out.println("Write File error");
        }
    }

    public T readFile(String PATH_FILE) {
        File file = new File(PATH_FILE);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        T t = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            t = (T) ois.readObject();
            if (fis != null) {
                fis.close();
            }
            if (ois != null) {
                ois.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không có file !");
        } catch (IOException e) {
            System.out.println("File rỗng !");
        } catch (ClassNotFoundException e) {
            System.out.println("Lỗi file");
        }
        return t;
    }
}
