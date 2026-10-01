package vn.edu.ute.dao;

import vn.edu.ute.configs.JPAConfig_24162013;
import vn.edu.ute.entity.Videos;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class VideoDaoImpl_24162013 implements IVideoDao_24162013 {
    @Override
    public void insert(Videos video) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(video);
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
    public void update(Videos video) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(video);
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
    public void delete(String videoId) throws Exception {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Videos video = enma.find(Videos.class, videoId);
            if (video != null) {
                enma.remove(video);
            } else {
                throw new Exception("Không tìm thấy");
            }
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
    public Videos findById(String videoId) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        Videos video = enma.find(Videos.class, videoId);
        enma.close();
        return video;
    }

    @Override
    public List<Videos> findAll() {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        TypedQuery<Videos> query = enma.createQuery("SELECT v FROM Videos v", Videos.class);
        List<Videos> list = query.getResultList();
        enma.close();
        return list;
    }

    @Override
    public List<Videos> findAll(int page, int pagesize) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        TypedQuery<Videos> query = enma.createQuery("SELECT v FROM Videos v", Videos.class);
        query.setFirstResult(page * pagesize);
        query.setMaxResults(pagesize);
        List<Videos> list = query.getResultList();
        enma.close();
        return list;
    }

    @Override
    public int count() {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        String jpql = "SELECT count(v) FROM Videos v";
        Long count = enma.createQuery(jpql, Long.class).getSingleResult();
        enma.close();
        return count.intValue();
    }

    @Override
    public List<Videos> findByCategory(int categoryId, int page, int pagesize) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        TypedQuery<Videos> query = enma.createQuery("SELECT v FROM Videos v WHERE v.category.categoryId = :catId", Videos.class);
        query.setParameter("catId", categoryId);
        query.setFirstResult(page * pagesize);
        query.setMaxResults(pagesize);
        List<Videos> list = query.getResultList();
        enma.close();
        return list;
    }

    @Override
    public int countByCategory(int categoryId) {
        EntityManager enma = JPAConfig_24162013.getEntityManager();
        String jpql = "SELECT count(v) FROM Videos v WHERE v.category.categoryId = :catId";
        Long count = enma.createQuery(jpql, Long.class).setParameter("catId", categoryId).getSingleResult();
        enma.close();
        return count.intValue();
    }
}
