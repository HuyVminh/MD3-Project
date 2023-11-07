package service.category;

import config.Config;
import model.Category;
import model.Product;
import service.product.IProductService;
import service.product.ProductServiceIMPL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CategoryServiceIMPL implements ICategoryService {
    static IProductService productService = new ProductServiceIMPL();
    static Config<List<Category>> config = new Config<>();
    static List<Category> categoryList;

    static {
        categoryList = config.readFile(Config.URL_CATEGORY);
        if (categoryList == null) {
            categoryList = new ArrayList<>();
        }
    }

    @Override
    public List<Category> findAll() {
        return categoryList;
    }

    @Override
    public void save(Category category) {
        if (findById(category.getCategoryId()) == null) {
            categoryList.add(category);
            updateData();
        } else {
            categoryList.set(categoryList.indexOf(category), category);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        categoryList.remove(findById(id));
        updateData();
    }

    @Override
    public Category findById(int id) {
        for (Category category : categoryList) {
            if (category.getCategoryId() == id) {
                return category;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Category category : categoryList) {
            if (category.getCategoryId() > idMax) {
                idMax = category.getCategoryId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_CATEGORY, categoryList);
    }

    @Override
    public boolean existCategoryName(String categoryName) {
        for (Category category : categoryList) {
            if (category.getCategoryName().equals(categoryName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Category> findByName(String catalogName) {
        List<Category> matchedList = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.getCategoryName().contains(catalogName)) {
                matchedList.add(category);
            }
        }
        return matchedList;
    }

    @Override
    public void sortCategory() {
        Collections.sort(categoryList, new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return o1.getCategoryName().compareTo(o2.getCategoryName());
            }
        });
    }
}
