package vn.edu.ute.dao;

import vn.edu.ute.configs.JPAConfig_24162013;
import vn.edu.ute.entity.Users;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class UserDaoImpl_24162013 implements IUserDao_24162013 {
    @Override
    public void insert(Users user) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(user);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public void update(Users user) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(user);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public Users findByUsername(String username) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        Users user = enma.find(Users.class, username);
        enma.close();
        return user;
    }

    @Override
    public Users findByEmail(String email) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        TypedQuery<Users> query = enma.createQuery("SELECT u FROM Users u WHERE u.email = :email", Users.class);
        query.setParameter("email", email);
        Users user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            // not found
        }
        enma.close();
        return user;
    }

    @Override
    public boolean login(String username, String password) {
        Users user = findByUsername(username);
        if (user != null && user.getPassword().equals(password) && Boolean.TRUE.equals(user.getActive())) {
            return true;
        }
        return false;
    }
}
