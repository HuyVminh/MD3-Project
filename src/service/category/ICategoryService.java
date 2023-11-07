package service.category;

import model.Category;
import service.IGenericService;

import java.util.List;

public interface ICategoryService extends IGenericService<Category> {
    boolean existCategoryName(String categoryName);
    List<Category> findByName(String catalogName);
    void sortCategory();
}
