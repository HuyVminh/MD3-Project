package view.admin;

import config.Config;
import config.Validate;
import model.Category;
import model.Product;
import service.category.CategoryServiceIMPL;
import service.category.ICategoryService;
import service.product.IProductService;
import service.product.ProductServiceIMPL;

import java.util.ArrayList;
import java.util.List;

import static config.Color.*;

public class ProductManagement {
    IProductService productService = new ProductServiceIMPL();
    ICategoryService categoryService = new CategoryServiceIMPL();

    public void productManagement() {
        int choice = 0;
        while (choice != 5) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".----------------------------PRODUCT MANAGEMENT----------------------------.");
            System.out.println("|                      " + WHITE_BOLD_BRIGHT + "[1] Danh sách sản phẩm                              " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|                      " + WHITE_BOLD_BRIGHT + "[2] Tìm kiếm sản phẩm theo ID                       " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|                      " + WHITE_BOLD_BRIGHT + "[3] Thêm sản phẩm mới                               " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|                      " + WHITE_BOLD_BRIGHT + "[4] Cập nhật sản phẩm                               " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|                      " + WHITE_BOLD_BRIGHT + "[5] Quay lại                                        " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("'--------------------------------------------------------------------------'" + RESET);
            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn của bạn : " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    System.out.println(YELLOW_BOLD_BRIGHT + ".-----------------------------------------------------------DANH SÁCH SẢN PHẨM---------------------------------------------------------------.");
                    System.out.printf("| %-4s |     %-18s |             %-18s |      %-15s |     %-11s |    %-12s|   %-13s |\n", "ID", "Tên sản phẩm", "Mô tả", "Danh mục", "Đơn giá", "Số lượng", "Trạng thái");
                    System.out.println("|------+------------------------+--------------------------------+----------------------+-----------------+----------------+-----------------|");
                    if (productService.findAll().isEmpty()) {
                        System.out.println(YELLOW_BOLD_BRIGHT + "|" + RED_BOLD_BRIGHT + "   Danh sách rỗng                                                                                                                           " + YELLOW_BOLD_BRIGHT + "|");

                    }
                    List<Product> productList = productService.findAll();
                    for (Product product : productList) {
                        product.display();
                    }
                    System.out.println(YELLOW_BOLD_BRIGHT + "'--------------------------------------------------------------------------------------------------------------------------------------------'" + RESET);
                    break;
                case 2:
                    System.out.println("Nhập ID sản phẩm cần tìm kiếm :");
                    int idFind = Validate.validateInt();
                    if (productService.findById(idFind) == null) {
                        System.out.println(RED_BOLD_BRIGHT + "Không tìm thấy sản phẩm !" + RESET);
                    } else {
                        Product product = productService.findById(idFind);
                        System.out.println(YELLOW_BOLD_BRIGHT + "|--ID--+-----Tên sản phẩm-------+-------------Mô tả--------------+------Danh mục--------+-----Đơn giá-----+----Số lượng----+---Trạng thái----|");
                        product.display();
                        System.out.println("|------+------------------------+--------------------------------+----------------------+-----------------+----------------+-----------------|" + RESET);
                    }
                    break;
                case 3:
                    System.out.println("Nhập số lượng sản phẩm muốn thêm mới : ");
                    int numToAdd = Validate.validateInt();
                    for (int i = 0; i < numToAdd; i++) {
                        Product product = new Product();
                        product.setProductId(productService.getNewId());
                        System.out.println("productId : " + product.getProductId());
                        System.out.println("Nhập tên sản phẩm : ");
                        while (true) {
                            String productName = Validate.validateString();
                            if (productService.existProductName(productName)) {
                                System.out.println(RED_BOLD_BRIGHT + "Sản phẩm đã tồn tại, mời nhập lại" + RESET);
                            } else {
                                product.setProductName(productName);
                                break;
                            }
                        }
                        System.out.println("Nhập mô tả sản phẩm : ");
                        product.setDescription(Validate.validateString());
                        System.out.println("Danh mục sản phẩm :");
                        List<Category> activeCatalogs = new ArrayList<>();
                        for (int j = 0; j < categoryService.findAll().size(); j++) {
                            Category category = categoryService.findAll().get(j);
                            if (category.isStatus()) { // Kiểm tra xem danh mục có đang hoạt động không
                                activeCatalogs.add(category);
                                System.out.println((activeCatalogs.size()) + "." + category.getCategoryName());
                            }
                        }

                        if (activeCatalogs.isEmpty()) {
                            System.out.println(RED_BOLD_BRIGHT + "Không có danh mục sản phẩm, bạn có muốn tạo danh mục mới? (Y/N)" + RESET);
                            while (true) {
                                String createCategory = Validate.validateString();
                                if (createCategory.equalsIgnoreCase("n")) {
                                    break;
                                } else if (createCategory.equalsIgnoreCase("y")) {
                                    Category category = new Category();
                                    category.setCategoryId(categoryService.getNewId());
                                    System.out.println("categoryId : " + category.getCategoryId());
                                    System.out.println("Nhập tên danh mục : ");
                                    category.setCategoryName(Validate.validateString());
                                    System.out.println("Nhập mô tả danh mục : ");
                                    category.setDescription(Validate.validateString());
                                    category.setStatus(true);
                                    categoryService.save(category);
                                    System.out.println(GREEN_BOLD_BRIGHT + "Tạo danh mục thành công !" + RESET);
                                    product.setCategory(category);
                                    break;
                                }
                                System.out.print(RED_BOLD_BRIGHT + "Lựa chọn không hợp lệ, vui lòng chọn lại : " + RESET);
                            }
                        } else {
                            while (true) {
                                int choiceCate = Validate.validateInt();
                                if (choiceCate >= 1 && choiceCate <= activeCatalogs.size()) {
                                    product.setCategory(activeCatalogs.get(choiceCate - 1));
                                    break;
                                } else {
                                    System.out.println(RED_BOLD_BRIGHT + "Lựa chọn không hợp lệ. Mời bạn lựa chọn lại." + RESET);
                                }
                            }
                        }
                        System.out.println("Nhập giá sản phẩm:");
                        product.setUnitPrice(Double.parseDouble(Config.scanner().nextLine()));
                        System.out.println("Nhập số lượng sản phẩm");
                        product.setStock(Validate.validateInt());
                        System.out.println("Nhập trạng thái sản phẩm");
                        product.setStatus(Boolean.parseBoolean(Config.scanner().nextLine()));
                        productService.save(product);
                    }
                    System.out.println(GREEN_BOLD_BRIGHT + "Thêm mới sản phẩm thành công" + RESET);
                    break;
                case 4:
                    System.out.println("Mời nhập ID sản phẩm cần sửa :");
                    int idEdit = Validate.validateInt();
                    Product productEdit = productService.findById(idEdit);
                    if (productEdit == null) {
                        System.out.println(RED_BOLD_BRIGHT + "Sản phẩm không tồn tại" + RESET);
                    } else {
                        int choiceUpdate = 0;
                        while (choiceUpdate != 7) {
                            System.out.println(YELLOW_BOLD_BRIGHT + ".--ID--------TÊN SẢN PHẨM--------------------MÔ TẢ------------------DANH MỤC SẢN PHẨM--------ĐƠN GIÁ-----------SỐ LƯỢNG--------TRẠNG THÁI----." + RESET);
                            productEdit.display();
                            System.out.println(YELLOW_BOLD_BRIGHT + "'--------------------------------------------------------------------------------------------------------------------------------------------'" + RESET);
                            System.out.println(BLUE + ".---------------------------------------.");
                            System.out.println("|    [1] Sửa tên sản phẩm               |");
                            System.out.println("|    [2] Sửa tên danh mục sản phẩm      |");
                            System.out.println("|    [3] Sửa mô tả sản phẩm             |");
                            System.out.println("|    [4] Sửa giá tiền sản phẩm          |");
                            System.out.println("|    [5] Sửa số lượng sản phẩm          |");
                            System.out.println("|    [6] Sửa trạng thái của sản phẩm    |");
                            System.out.println("|    [7] Quay lại                       |");
                            System.out.println("'---------------------------------------'" + RESET);
                            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn : " + RESET);
                            choiceUpdate = Validate.validateInt();
                            switch (choiceUpdate) {
                                case 1:
                                    System.out.println("Mời bạn nhập tên sản phẩm mới:");
                                    productEdit.setProductName(Validate.validateString());
                                    productService.save(productEdit);
                                    System.out.println(GREEN_BOLD_BRIGHT + "Sửa thành công tên sản phẩm" + RESET);
                                    break;
                                case 2:
                                    System.out.println("Lựa chọn danh mục sản phẩm");
                                    List<Category> activeCatalogs = new ArrayList<>();
                                    List<Category> allCatalogs = categoryService.findAll();
                                    for (int j = 0; j < allCatalogs.size(); j++) {
                                        if (allCatalogs.get(j).isStatus()) { // Check if the catalog is active
                                            activeCatalogs.add(allCatalogs.get(j));
                                            System.out.println((activeCatalogs.size()) + "." + activeCatalogs.get(activeCatalogs.size() - 1).getCategoryName());
                                        }
                                    }

                                    while (true) {
                                        int choiceP = Validate.validateInt();
                                        if (choiceP >= 1 && choiceP <= activeCatalogs.size()) {
                                            productEdit.setCategory(activeCatalogs.get(choiceP - 1));
                                            break;
                                        } else {
                                            System.out.println(RED_BOLD_BRIGHT + "Không có danh mục mời bạn lựa chọn lại" + RESET);
                                        }
                                    }
                                    productService.save(productEdit);
                                    System.out.println(GREEN_BOLD_BRIGHT + "Sửa thành công danh mục sản phẩm" + RESET);

                                    break;
                                case 3:
                                    System.out.println("Mời bạn nhập mô tả sản phẩm mới:");
                                    productEdit.setDescription(Validate.validateString());
                                    productService.save(productEdit);
                                    System.out.println(GREEN_BOLD_BRIGHT + "Sửa thành công mô tả sản phẩm" + RESET);
                                    break;
                                case 4:
                                    System.out.println("Mời bạn nhập giá tiền mới:");
                                    productEdit.setUnitPrice(Double.parseDouble(Config.scanner().nextLine()));
                                    productService.save(productEdit);
                                    System.out.println(GREEN_BOLD_BRIGHT + "Sửa thành công giá tiền" + RESET);
                                    break;
                                case 5:
                                    System.out.println("Mời bạn nhập số lượng sản phẩm mới:");
                                    productEdit.setStock(Validate.validateInt());
                                    productService.save(productEdit);
                                    System.out.println(GREEN_BOLD_BRIGHT + "Sửa thành công số lượng sản phẩm" + RESET);
                                    break;
                                case 6:
                                    System.out.println(WHITE_BOLD_BRIGHT + "Bạn có chắc chắn muốn " + (productEdit.isStatus() ? "khóa sản phẩm này" : "mở khóa sản phẩm mày") + " (Y/N) ?" + RESET);
                                    String strP = Validate.validateString();
                                    while (true) {
                                        if (strP.equalsIgnoreCase("y")) {
                                            productEdit.setStatus(!productEdit.isStatus());
                                            productService.save(productEdit);
                                            System.out.println(GREEN_BOLD_BRIGHT + "Sửa trạng thái sản phẩm thành công" + RESET);
                                            break;
                                        } else if (strP.equalsIgnoreCase("n")) {
                                            break;
                                        } else {
                                            System.out.println(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại :" + RESET);
                                            strP = Validate.validateString();
                                        }
                                    }
                                    break;
                                case 7:
                                    break;
                                default:
                                    System.out.println(RED_BOLD_BRIGHT + "Lựa chọn không hợp lệ. Vui lòng chọn lại : " + RESET);
                            }
                        }
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.println(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại." + RESET);
            }
        }
    }
}
