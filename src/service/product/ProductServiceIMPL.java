package service.product;

import config.Config;
import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductServiceIMPL implements IProductService {
    static Config<List<Product>> config = new Config<>();
    public static List<Product> productList;

    static {
        productList = config.readFile(Config.URL_PRODUCT);
        if (productList == null) {
            productList = new ArrayList<>();
        }
    }

    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public void save(Product product) {
        if (findById(product.getProductId()) == null) {
            productList.add(product);
            updateData();
        } else {
            productList.set(productList.indexOf(product), product);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        productList.remove(findById(id));
        updateData();
    }

    @Override
    public Product findById(int id) {
        for (Product p : productList) {
            if (p.getProductId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Product product : productList) {
            if (product.getProductId() > idMax) {
                idMax = product.getProductId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_PRODUCT, productList);
    }

    @Override
    public boolean existProductName(String productName) {
        for (Product p : productList) {
            if (p.getProductName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void softProductByPrice() {
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return (int) (o1.getUnitPrice() - o2.getUnitPrice());
            }
        });
    }

    @Override
    public List<Product> findProductByName(String productName) {
        List<Product> matchedList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getProductName().contains(productName)) {
                matchedList.add(product);
            }
        }
        return matchedList;
    }
}
