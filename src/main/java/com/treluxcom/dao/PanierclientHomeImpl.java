package com.treluxcom.dao;

import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Panierclient;
import com.treluxcom.service.IPanierclientHome;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class PanierclientHomeImpl extends UnicastRemoteObject implements IPanierclientHome {

    public PanierclientHomeImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Panierclient> panierclientList() {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Panierclient> query = builder.createQuery(Panierclient.class);
        Root<Panierclient> root = query.from(Panierclient.class);
        query.select(root);
        Query<Panierclient> q = session.createQuery(query);
        List<Panierclient> list = q.getResultList();
        return list;
    }

    @Override
    public boolean persist(Panierclient panierclient) throws RemoteException {
        try {
            try (Session session = HibernateUtil.getSession()) {
                Transaction tr = session.beginTransaction();
                session.persist(panierclient.getPanier());
                session.persist(panierclient);
                tr.commit();
            }
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteByObject(Panierclient panierclient) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Panierclient where codepanier = :codepanier")
                    .setParameter("codepanier", panierclient.getCodepanier())
                    .executeUpdate();
            session.createQuery("delete from Panier where codepanier = :codepanier")
                    .setParameter("codepanier", panierclient.getCodepanier())
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
            session.createQuery("delete from Panierclient where codepanier = :codeclient")
                    .setParameter("codepanier", codepanier)
                    .executeUpdate();
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
            session.createQuery("update Panierclient set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Panierclient findById(String codepanier) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Panierclient c = null;
            c = (Panierclient) session.createQuery(
                    "select c from Panierclient c where c.codepanier = :codepanier")
                    .setParameter("codepanier", codepanier)
                    .getSingleResult();
            return c;
        } catch (RuntimeException re) {

            throw re;
        }

    }
}