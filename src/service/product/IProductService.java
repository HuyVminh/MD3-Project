package service.product;

import model.Product;
import service.IGenericService;

import java.util.List;

public interface IProductService extends IGenericService<Product> {
    boolean existProductName(String productName);
    void softProductByPrice();
    List<Product>findProductByName(String productName);
}
