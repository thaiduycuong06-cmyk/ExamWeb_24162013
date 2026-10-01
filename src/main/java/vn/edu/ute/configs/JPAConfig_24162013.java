package vn.edu.ute.configs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAConfig_24162013 {
    private static EntityManagerFactory factory;
    
    static {
        factory = Persistence.createEntityManagerFactory("ExamWebPU");
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
