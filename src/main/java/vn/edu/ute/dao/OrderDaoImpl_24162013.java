package vn.edu.ute.dao;

import vn.edu.ute.configs.JPAConfig_24162013;
import vn.edu.ute.entity.Orders;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderDaoImpl_24162013 implements IOrderDao_24162013 {

    @Override
    public void insert(Orders order) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(order);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public void update(Orders order) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(order);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public Orders findById(Long id) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        try {
            return enma.find(Orders.class, id);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Orders> findAll() {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        try {
            TypedQuery<Orders> query = enma.createQuery("SELECT o FROM Orders o ORDER BY o.orderDate DESC", Orders.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Orders> findByUser(String username) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        try {
            TypedQuery<Orders> query = enma.createQuery("SELECT o FROM Orders o WHERE o.user.username = :username ORDER BY o.orderDate DESC", Orders.class);
            query.setParameter("username", username);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Orders> findByUserAndStatus(String username, String status) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        try {
            if (status == null || status.trim().isEmpty() || "all".equalsIgnoreCase(status.trim())) {
                return findByUser(username);
            }
            TypedQuery<Orders> query = enma.createQuery("SELECT o FROM Orders o WHERE o.user.username = :username AND o.status = :status ORDER BY o.orderDate DESC", Orders.class);
            query.setParameter("username", username);
            query.setParameter("status", status.trim());
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public int countByUserAndStatus(String username, String status) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        try {
            if (status == null || status.trim().isEmpty() || "all".equalsIgnoreCase(status.trim())) {
                TypedQuery<Long> query = enma.createQuery("SELECT COUNT(o) FROM Orders o WHERE o.user.username = :username", Long.class);
                query.setParameter("username", username);
                return query.getSingleResult().intValue();
            } else {
                TypedQuery<Long> query = enma.createQuery("SELECT COUNT(o) FROM Orders o WHERE o.user.username = :username AND o.status = :status", Long.class);
                query.setParameter("username", username);
                query.setParameter("status", status.trim());
                return query.getSingleResult().intValue();
            }
        } finally {
            enma.close();
        }
    }
}
