package dao;
import entity.currency;
import entity.transaction;
import jakarta.persistence.*;
public class TransactionDao {
    private EntityManager entityManager;

    public TransactionDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void AddTransaction(String fromCur, String toCur, double amount, double result) {
        EntityTransaction trans = null;
        try {
            trans = entityManager.getTransaction();
            trans.begin();
            transaction transaction = new transaction();
            transaction.setFromCur(fromCur);
            transaction.setToCur(toCur);
            transaction.setAmount(amount);
            transaction.setResult(result);
            entityManager.persist(transaction);
            trans.commit();
        } catch (RuntimeException e) {
            if (trans != null && trans.isActive()) {
                trans.rollback();
            }
            throw e;
        }
    }
}
