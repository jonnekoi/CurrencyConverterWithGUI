package datasource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class dbconn {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static EntityManager getEntityManager(){
        if (em == null){
            emf = Persistence.createEntityManagerFactory("CompanyMariaDbUnit");
            em = emf.createEntityManager();
        }
        return em;
    }
}