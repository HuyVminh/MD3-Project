package service.order;

import model.Order;
import service.IGenericService;

import java.util.List;

public interface IOrderService extends IGenericService<Order> {
    List<Order> findOrderByUserName(String userName);
    void sortOrderByDate();
}
