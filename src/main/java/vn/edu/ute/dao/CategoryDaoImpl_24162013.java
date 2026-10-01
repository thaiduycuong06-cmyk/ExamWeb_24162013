package vn.edu.ute.dao;

import vn.edu.ute.configs.JPAConfig_24162013;
import vn.edu.ute.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryDaoImpl_24162013 implements ICategoryDao_24162013 {
    @Override
    public List<Category> findAll() {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        TypedQuery<Category> query = enma.createQuery("SELECT c FROM Category c", Category.class);
        List<Category> list = query.getResultList();
        enma.close();
        return list;
    }

    @Override
    public Category findById(int categoryId) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        Category cat = enma.find(Category.class, categoryId);
        enma.close();
        return cat;
    }
}
