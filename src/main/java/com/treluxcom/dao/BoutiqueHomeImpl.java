package com.treluxcom.dao;

import com.treluxcom.service.IBoutiqueHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Boutique;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class BoutiqueHomeImpl extends UnicastRemoteObject implements IBoutiqueHome, Serializable {

    public BoutiqueHomeImpl() throws RemoteException {
        super();
    }
    
    @Override
    public List<Boutique> boutiqueList() throws RemoteException{
        Session session = HibernateUtil.getSession();
        List<Boutique> list = session.createQuery("Select b from Boutique b", Boutique.class).getResultList();
        return list;
    }


    @Override
    public boolean persist(Boutique boutique) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.persist(boutique);
            tr.commit();
            session.close();
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteByObject(Boutique boutique) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Boutique where codeboutique = :codeboutique")
                    .setParameter("codeboutique", boutique.getCodeboutique())
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteById(String codeboutique) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Boutique where codeboutique = :codeboutique")
                    .setParameter("codeboutique", codeboutique)
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
            session.createQuery("update Boutique set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Boutique findById(String codeboutique) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Boutique c = null;
            c = (Boutique) session.createQuery(
                    "select c from Boutique c where c.codeboutique = :codeboutique")
                    .setParameter("codeboutique", codeboutique)
                    .getSingleResult();
            return c;
        } catch (RuntimeException re) {

            throw re;
        }
    }
}
