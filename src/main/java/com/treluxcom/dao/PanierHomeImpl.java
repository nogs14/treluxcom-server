package com.treluxcom.dao;

import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Panier;
import com.treluxcom.service.IPanierHome;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class PanierHomeImpl extends UnicastRemoteObject implements IPanierHome {

    public PanierHomeImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Panier> panierList() {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Panier> query = builder.createQuery(Panier.class);
        Root<Panier> root = query.from(Panier.class);
        query.select(root);
        Query<Panier> q = session.createQuery(query);
        List<Panier> list = q.getResultList();
        return list;
    }

    @Override
    public boolean persist(Panier panier) throws RemoteException {

        try {
            try (Session session = HibernateUtil.getSession()) {
                Transaction tr = session.beginTransaction();
                session.persist(panier);
                tr.commit();
            }
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteByObject(Panier panier) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Panier where codepanier = :codepanier")
                    .setParameter("codepanier", panier.getCodepanier())
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteById(String codepanier) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Panier where codepanier = :codepanier")
                    .setParameter("codepanier", codepanier)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void update(String toUpdate, String toUpdateValue, String reference, String referenceValue) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("update Panier set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Panier findById(String codepanier) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Panier c = null;
            c = (Panier) session.createQuery(
                    "select c from Panier c where c.codepanier = :codepanier")
                    .setParameter("codepanier", codepanier)
                    .getSingleResult();
            return c;
        } catch (RuntimeException re) {

            throw re;
        }
    }
}
