package vn.edu.ute.dao;

import vn.edu.ute.entity.Category;
import java.util.List;

public interface ICategoryDao_24162013 {
    List<Category> findAll();
    Category findById(int categoryId);
}
