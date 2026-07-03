package com.mednet.dao;

import com.mednet.config.HibernateUtil;
import com.mednet.entity.Prefix;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PrefixDao {

    public void savePrefix(Prefix prefix) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.save(prefix);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    public List<Prefix> getAllPrefixes() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery("FROM Prefix", Prefix.class).list();

        } catch (Exception e) {

            e.printStackTrace();

            return new ArrayList<>();

        }

    }

    public void deletePrefix(int id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Prefix prefix = session.get(Prefix.class, id);

            if (prefix != null) {

                session.delete(prefix);

            }

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();

            }

            e.printStackTrace();

        }

    }

}