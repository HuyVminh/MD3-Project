package service.order;

import config.Config;
import model.Category;
import model.Order;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderServiceIMPL implements IOrderService {
    static Config<List<Order>> config = new Config<>();
    static List<Order> orderList;

    static {
        orderList = config.readFile(Config.URL_ORDER);
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
    }

    @Override
    public List<Order> findAll() {
        return orderList;
    }

    @Override
    public void save(Order order) {
        if (findById(order.getId()) == null) {
            orderList.add(order);
            updateData();
        } else {
            orderList.set(orderList.indexOf(order), order);
            updateData();
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Order findById(int id) {
        for (Order order : orderList) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Order order : orderList) {
            if (order.getId() > idMax) {
                idMax = order.getId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_ORDER, orderList);
    }

    @Override
    public List<Order> findOrderByUserName(String userName) {
        List<Order> matchedList = new ArrayList<>();
        IUserService userService = new UserServiceIMPL();
        for (Order order : orderList) {
            if (userService.findById(order.getIdUser()).getUsername().contains(userName)) {
                matchedList.add(order);
            }
        }
        return matchedList;
    }

    @Override
    public void sortOrderByDate() {
        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getBuyDate().compareTo(o1.getBuyDate());
            }
        });
    }
}
