package dao;

import entity.currency;
import jakarta.persistence.*;
import java.util.List;

public class CurrencyDao {

    private EntityManager entityManager;

    public CurrencyDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CurrencyDao() {
    }

    public double getExchangeRate(String Currency){
        TypedQuery<Double> query = entityManager.createQuery(
                "SELECT c.conversion_rate FROM currency c WHERE c.abbreviation = :abbreviation", Double.class);
        query.setParameter("abbreviation", Currency);
        return query.getSingleResult();
    }

    public List<String> getCurrencyNames() {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT c.abbreviation FROM currency c", String.class);
        return query.getResultList();
    }
    public void addCurrency(String name, String abbreviation, double rate) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            currency currency = new currency();
            currency.setName(name);
            currency.setAbbreviation(abbreviation);
            currency.setConversion_rate(rate);

            entityManager.persist(currency);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    public boolean currencyExists(String name, String abbreviation) {
        Query query = entityManager.createQuery("SELECT COUNT(c) FROM currency c WHERE c.name = :name OR c.abbreviation = :abbreviation");
        query.setParameter("name", name);
        query.setParameter("abbreviation", abbreviation);
        long count = (long) query.getSingleResult();
        return count > 0;
    }
}