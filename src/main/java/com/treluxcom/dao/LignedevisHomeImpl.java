package com.treluxcom.dao;

import com.treluxcom.service.ILignedevisHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Lignedevis;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LignedevisHomeImpl extends UnicastRemoteObject implements ILignedevisHome {

    public LignedevisHomeImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Lignedevis> lignedevisList() throws RemoteException {
        Session session = HibernateUtil.getSession();
        List<Lignedevis> list = session.createQuery("Select b from Lignedevis b", Lignedevis.class).getResultList();
        return list;
    }

    @Override
    public boolean persistLigneCommande(Lignedevis lignedevis) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.persist(lignedevis);
            tr.commit();
            session.close();
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public boolean persist(Lignedevis lignedevis) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.persist(lignedevis);
            tr.commit();
            session.close();
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteById(String codelignedevis) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Lignedevis where codelignedevis = :codelignedevis")
                    .setParameter("codelignedevis", codelignedevis)
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
            session.createQuery("update Lignedevis set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Lignedevis findById(String codelignedevis) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Lignedevis c = null;
            c = (Lignedevis) session.createQuery(
                    "select c from Lignedevis c where c.codelignedevis = :codelignedevis")
                    .setParameter("codelignedevis", codelignedevis)
                    .getSingleResult();
            return c;
        } catch (RuntimeException re) {

            throw re;
        }
    }

}
