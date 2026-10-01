package vn.edu.ute.services;

import vn.edu.ute.dao.CategoryDaoImpl_24162013;
import vn.edu.ute.dao.ICategoryDao_24162013;
import vn.edu.ute.entity.Category;

import java.util.List;

public class CategoryServiceImpl_24162013 implements ICategoryService_24162013 {
    ICategoryDao_24162013 categoryDao = new CategoryDaoImpl_24162013();

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(int categoryId) {
        return categoryDao.findById(categoryId);
    }
}
