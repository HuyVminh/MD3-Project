package service;

import java.util.List;

public interface IGenericService<T> {
    List<T> findAll();
    void save(T t); // dùng cho cả add và update
    void delete(int id);
    T findById(int id);
    int getNewId();
    void updateData();
}
