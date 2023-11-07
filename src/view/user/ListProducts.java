package view.user;

import config.Validate;
import model.Category;
import model.Product;

import service.category.CategoryServiceIMPL;
import service.category.ICategoryService;
import service.product.IProductService;
import service.product.ProductServiceIMPL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static config.Color.*;

public class ListProducts {
    static IProductService productService = new ProductServiceIMPL();
    static ICategoryService categoryService = new CategoryServiceIMPL();

    public void listProducts() {
        int choice = 0;
        while (choice != 5) {
            System.out.println(YELLOW_BOLD_BRIGHT + ".-----------------------------SẢN PHẨM---------------------------------.");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "              [1] Danh sách các sản phẩm                              " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "              [2] Sắp xếp sản phẩm theo giá tiền tăng dần             " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "              [3] Tìm kiếm sản phẩm theo danh mục                     " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "              [4] Tìm kiếm sản phẩm theo tên                          " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "              [5] Quay lại                                            " + YELLOW_BOLD_BRIGHT + "|");
            System.out.println("'----------------------------------------------------------------------'" + RESET);
            System.out.print(WHITE_BOLD_BRIGHT + "Nhập lựa chọn của bạn (1/2/3/4/5): " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    tableProduct();
                    for (Product p : productService.findAll()) {
                        if (p.isStatus() && p.getCategory().isStatus()) {
                            System.out.printf(YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " [ %-2s] " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " %-31s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "    %-22s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "      %20s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "    %-11s " + YELLOW_BOLD_BRIGHT + "|\n", p.getProductId(), p.getProductName(), p.getCategory().getCategoryName(), formatCurrency(p.getUnitPrice()), (p.getStock() > 0 ? "Còn hàng" : p.getStock() == 1 ? "Còn 1 sp" : "Hết hàng"));
                        }
                    }
                    System.out.println(YELLOW_BOLD_BRIGHT + "'------------------------------------------------------------------------------------------------------------------'" + RESET);
                    whileInput();
                    break;
                case 2:
                    tableProduct();
                    productService.softProductByPrice();
                    for (Product p : productService.findAll()) {
                        if (p.isStatus() && p.getCategory().isStatus()) {
                            System.out.printf(YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " [ %-2s] " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " %-31s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "    %-22s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "      %20s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "    %-11s " + YELLOW_BOLD_BRIGHT + "|\n", p.getProductId(), p.getProductName(), p.getCategory().getCategoryName(), formatCurrency(p.getUnitPrice()), (p.getStock() > 0 ? "Còn hàng" : p.getStock() == 1 ? "Còn 1 sp" : "Hết hàng"));
                        }
                    }
                    System.out.println(YELLOW_BOLD_BRIGHT + "'------------------------------------------------------------------------------------------------------------------'" + RESET);
                    whileInput();
                    break;
                case 3:
                    System.out.println(YELLOW_BOLD_BRIGHT + ".-------------------------------------------------------.");
                    System.out.println("|                 DANH SÁCH DANH MỤC                    |");
                    System.out.println("|-------------------------------------------------------|" + RESET);
                    for (Category cate : categoryService.findAll()) {
                        if (cate.isStatus()) {
                            System.out.printf(YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "   [ %-2d]      %-40s " + YELLOW_BOLD_BRIGHT + "|\n", cate.getCategoryId(), cate.getCategoryName());
                        }
                    }
                    System.out.println("|-------------------------------------------------------|");
                    System.out.println("|" + WHITE_BOLD_BRIGHT + "                   [0] Quay lại                        " + YELLOW_BOLD_BRIGHT + "|");
                    System.out.println("'-------------------------------------------------------'" + RESET);
                    System.out.print("Nhập lựa chọn của bạn :");
                    while (true) {
                        int choiceCate = Validate.validateInt();
                        if (choiceCate == 0) {
                            break;
                        } else if (categoryService.findById(choiceCate) != null) {
                            List<Product> productsFoundList = findProductsByCategory(choiceCate);
                            tableProduct();
                            if (productsFoundList.isEmpty()) {
                                System.out.println(YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "                                           DANH SÁCH RỖNG                                                         " + YELLOW_BOLD_BRIGHT + "|");
                                System.out.println("'------------------------------------------------------------------------------------------------------------------'" + RESET);
                            } else {
                                for (Product p : productsFoundList) {
                                    if (p.isStatus()) {
                                        System.out.printf(YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " [ %-2s] " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " %-31s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "    %-22s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "      %20s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "    %-11s " + YELLOW_BOLD_BRIGHT + "|\n", p.getProductId(), p.getProductName(), p.getCategory().getCategoryName(), formatCurrency(p.getUnitPrice()), (p.getStock() > 0 ? "Còn hàng" : p.getStock() == 1 ? "Còn 1 sp" : "Hết hàng"));
                                    }
                                }
                                System.out.println(YELLOW_BOLD_BRIGHT + "'------------------------------------------------------------------------------------------------------------------'" + RESET);
                                whileInput();
                            }
                            break;
                        } else {
                            System.out.print(RED_BOLD_BRIGHT + "Lựa chọn không phù hợp, mời nhập lại : " + RESET);
                        }
                    }
                    break;
                case 4:
                    System.out.print(WHITE_BOLD_BRIGHT + "Nhập tên sản phẩm cần tìm kiếm : " + RESET);
                    String productName = Validate.validateString();
                    List<Product> productsFoundList = productService.findProductByName(productName);
                    if (productsFoundList.isEmpty()) {
                        System.out.println(RED_BRIGHT + "Không tìm thấy sản phẩm !" + RESET);
                    } else {
                        tableProduct();
                        for (Product p : productsFoundList) {
                            if (p.isStatus()) {
                                System.out.printf(YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " [ %-2s] " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + " %-31s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "    %-22s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "      %20s " + YELLOW_BOLD_BRIGHT + "|" + WHITE_BOLD_BRIGHT + "    %-11s " + YELLOW_BOLD_BRIGHT + "|\n", p.getProductId(), p.getProductName(), p.getCategory().getCategoryName(), formatCurrency(p.getUnitPrice()), (p.getStock() > 0 ? "Còn hàng" : p.getStock() == 1 ? "Còn 1 sp" : "Hết hàng"));
                            }
                        }
                        System.out.println(YELLOW_BOLD_BRIGHT + "'------------------------------------------------------------------------------------------------------------------'" + RESET);
                        whileInput();
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.print(RED_BOLD_BRIGHT + "Không hợp lệ, vui lòng nhập lại : " + RESET);
            }
        }
    }

    private String formatCurrency(double money) {
        DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
        return formatter.format(money);
    }

    private List<Product> findProductsByCategory(int id) {
        List<Product> productsFoundList = new ArrayList<>();
        for (Product product : productService.findAll()) {
            if (product.getCategory().getCategoryId() == id) {
                productsFoundList.add(product);
            }
        }
        return productsFoundList;
    }

    private void whileInput() {
        System.out.print(WHITE_BOLD_BRIGHT + "Nhập ID sản phẩm để xem chi tiết (Nhập [0] để quay lại) : " + RESET);
        while (true) {
            int choiceId = Validate.validateInt();
            if (choiceId == 0) {
                break;
            } else if (productService.findById(choiceId) != null) {
                new ProductDetail().productDetail(choiceId);
                break;
            } else {
                System.out.print(RED_BOLD_BRIGHT + "Lựa chọn không phù hợp, mời nhập lại : " + RESET);
            }
        }
    }

    private void tableProduct() {
        System.out.println(YELLOW_BOLD_BRIGHT + ".------------------------------------------------------------------------------------------------------------------.");
        System.out.println("|                                              DANH SÁCH SẢN PHẨM                                    " + RED_BOLD_BRIGHT + "[0] Quay lại  " + YELLOW_BOLD_BRIGHT + "|");
        System.out.println("|------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|  ID   |          Tên sản phẩm           |         Danh mục          |          Đơn giá          |   Trạng thái   |");
        System.out.println("|-------+---------------------------------+---------------------------+---------------------------+----------------|" + RESET);
    }
}
