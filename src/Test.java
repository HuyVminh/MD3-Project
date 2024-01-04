import model.Category;
import model.Product;
import service.product.IProductService;

public class Test {
    public static void main(String[] args) {
        Product product = new Product(10,"productname", new Category(12,"categoryName"," String description",true), "String description", 120000, 12, true);
        System.out.println(product.getProductName());
        System.out.println(product.getCategory().getCategoryName());
    }
}
