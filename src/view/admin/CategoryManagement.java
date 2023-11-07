package view.admin;

import config.Validate;
import model.Category;
import model.Product;
import service.category.CategoryServiceIMPL;
import service.category.ICategoryService;
import service.product.IProductService;
import service.product.ProductServiceIMPL;

import java.util.List;

import static config.Color.*;

public class CategoryManagement {
    ICategoryService categoryService = new CategoryServiceIMPL();
    IProductService productService = new ProductServiceIMPL();

    public void categoryManagement() {
        int choice = 0;
        while (choice != 6) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".-----------------------------QUẢN LÝ DANH MỤC------------------------------.");
            System.out.println("|" + RESET + "                    " + WHITE_BOLD_BRIGHT + "[1] Danh sách danh mục sản phẩm                        " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + RESET + "                    " + WHITE_BOLD_BRIGHT + "[2] Thêm danh mục mới                                  " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + RESET + "                    " + WHITE_BOLD_BRIGHT + "[3] Tìm kiếm danh mục sản phẩm                         " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + RESET + "                    " + WHITE_BOLD_BRIGHT + "[4] Cập nhật danh mục sản phẩm                         " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + RESET + "                    " + WHITE_BOLD_BRIGHT + "[5] Xóa danh mục sản phẩm                              " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + RESET + "                    " + WHITE_BOLD_BRIGHT + "[6] Quay lại                                           " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("'---------------------------------------------------------------------------'" + RESET);
            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn : " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    System.out.println(YELLOW_BOLD_BRIGHT + ".----------------------------DANH SÁCH DANH MỤC SẢN PHẨM--------------------------------.");
                    System.out.printf("|  %-3s |      %-17s |           %-20s |      %-15s |\n", "ID", "Tên danh mục", "Mô tả", "Trạng thái");
                    System.out.println("|------+------------------------+--------------------------------+----------------------|");
                    if (categoryService.findAll().isEmpty()) {
                        System.out.printf(YELLOW_BOLD_BRIGHT + "|" + RED_BOLD_BRIGHT + " %-73s " + YELLOW_BOLD_BRIGHT + "|\n", "Danh sách rỗng !");
                    }
                    List<Category> categoryList = categoryService.findAll();
                    for (Category category : categoryList) {
                        category.display();
                    }
                    System.out.println("'---------------------------------------------------------------------------------------'" + RESET);
                    break;
                case 2:
                    System.out.println("Nhập số lượng danh mục muốn thêm mới : ");
                    int numToAdd = Validate.validateInt();
                    for (int i = 0; i < numToAdd; i++) {
                        Category category = new Category();
                        category.setCategoryId(categoryService.getNewId());
                        System.out.println("categoryId : " + category.getCategoryId());
                        System.out.println("Nhập tên danh mục : ");
                        while (true) {
                            String categoryName = Validate.validateString();
                            if (categoryService.existCategoryName(categoryName)) {
                                System.out.println(RED_BOLD_BRIGHT + "Danh mục đã tồn tại, mời nhập lại : " + RESET);
                            } else {
                                category.setCategoryName(categoryName);
                                break;
                            }
                        }
                        System.out.println("Nhập mô tả danh mục : ");
                        category.setDescription(Validate.validateString());
                        System.out.println("Nhập trạng thái danh mục :");
                        category.setStatus(Boolean.parseBoolean(Validate.validateString()));
                        categoryService.save(category);
                    }
                    System.out.println(GREEN_BOLD_BRIGHT + "Thêm mới danh mục thành công." + RESET);
                    break;
                case 3:
                    System.out.println("Nhập tên danh mục bạn cần tìm kiếm :");
                    String search = Validate.validateString();
                    List<Category> searchedList = categoryService.findByName(search);
                    if (searchedList.isEmpty()) {
                        System.out.println(RED_BOLD_BRIGHT + "Không tìm thấy danh mục nào phù hợp với tìm kiếm của bạn." + RESET);
                    } else {
                        System.out.println("Tìm thấy danh mục :");
                        System.out.println(YELLOW_BOLD_BRIGHT + "|--ID--+-----TÊN DANH MỤC-------+-------------MÔ TẢ--------------+------TRẠNG THÁI------|");
                        for (Category category : searchedList) {
                            category.display();
                        }
                        System.out.println("'---------------------------------------------------------------------------------------'" + RESET);
                    }
                    break;
                case 4:
                    System.out.println("Nhập ID danh mục cần sửa :");
                    int idEdit = Validate.validateInt();
                    Category catalogsEdit = categoryService.findById(idEdit);
                    if (catalogsEdit == null) {
                        System.out.println(RED_BOLD_BRIGHT + "Danh mục không tồn tại" + RESET);
                    } else {
                        System.out.println(YELLOW_BOLD_BRIGHT + ".---------------------------------------------------------------------------------------.");
                        catalogsEdit.display();
                        System.out.println("'---------------------------------------------------------------------------------------'" + RESET);
                        System.out.println(BLUE_BOLD_BRIGHT + ".---------------------------------.");
                        System.out.println("|   [1] Sửa tên danh mục          |");
                        System.out.println("|   [2] Sửa mô tả danh mục        |");
                        System.out.println("|   [3] Sửa trạng thái danh mục   |");
                        System.out.println("|   [4] Quay lại                  |");
                        System.out.println("'---------------------------------'");
                        System.out.print(WHITE_BOLD_BRIGHT + "Nhập tùy chọn (1/2/3/4): " + RESET);
                        int editChoice = Validate.validateInt();

                        switch (editChoice) {
                            case 1:
                                System.out.println("Nhập tên danh mục mới:");
                                catalogsEdit.setCategoryName(Validate.validateString());
                                categoryService.save(catalogsEdit);
                                updateProduct(catalogsEdit);
                                System.out.println(GREEN_BOLD_BRIGHT + "Sửa thành công tên danh mục" + RESET);
                                break;
                            case 2:
                                System.out.println("Nhập tên mô tả danh mục cần sửa:");
                                catalogsEdit.setDescription(Validate.validateString());
                                categoryService.save(catalogsEdit);
                                updateProduct(catalogsEdit);
                                System.out.println(GREEN_BOLD_BRIGHT + "Sửa thành công mô tả danh mục" + RESET);
                                break;
                            case 3:
                                catalogsEdit.setStatus(!catalogsEdit.isStatus());
                                categoryService.save(catalogsEdit);
                                updateProduct(catalogsEdit);
                                System.out.println(GREEN_BOLD_BRIGHT + "Sửa trạng thái danh mục sản phẩm thành công" + RESET);
                                break;
                            case 4:
                                return;
                            default:
                                System.out.println(RED_BOLD_BRIGHT + "Lựa chọn không hợp lệ. Vui lòng chọn lại" + RESET);
                        }
                    }
                    break;
                case 5:
                    System.out.println("Nhập ID danh mục cần xóa:");
                    int idDelete = Validate.validateInt();
                    Category catalogsDelete = categoryService.findById(idDelete);
                    if (catalogsDelete == null) {
                        System.out.println(RED_BOLD_BRIGHT + "Danh mục không tồn tại" + RESET);
                        return;
                    }

                    List<Product> productsList = productService.findAll();

                    boolean check = false;
                    for (Product product : productsList) {
                        if (product.getCategory().getCategoryId() == idDelete) {
                            check = true;
                            break;
                        }
                    }

                    if (check) {
                        System.out.println(RED_BOLD_BRIGHT + "Danh mục đã tồn tại sản phẩm. Không thể xóa." + RESET);
                    } else {
                        categoryService.delete(idDelete);
                        System.out.println(GREEN_BOLD_BRIGHT + "Xóa danh mục thành công" + RESET);
                    }
                    break;
                case 6:
                    break;
                default:
                    System.out.print(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại : " + RESET);
                    break;
            }
        }
    }

    private void updateProduct(Category category) {
        for (Product product : productService.findAll()) {
            if (product.getCategory().getCategoryId() == category.getCategoryId()) {
                product.setCategory(category);
                productService.save(product);
            }
        }
    }
}
