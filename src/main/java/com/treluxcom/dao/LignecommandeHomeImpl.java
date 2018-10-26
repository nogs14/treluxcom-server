package com.treluxcom.dao;

import com.treluxcom.service.ILignecommandeHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Lignecommande;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LignecommandeHomeImpl extends UnicastRemoteObject implements ILignecommandeHome {

    public LignecommandeHomeImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Lignecommande> lignecommandeList() throws RemoteException {
        Session session = HibernateUtil.getSession();
        List<Lignecommande> list = session.createQuery("Select b from Lignecommande b", Lignecommande.class).getResultList();
        return list;
    }

    @Override
    public boolean persistLigneCommande(Lignecommande lignecommande) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.persist(lignecommande);
            tr.commit();
            session.close();
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public boolean persist(Lignecommande lignecommande) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.persist(lignecommande);
            tr.commit();
            session.close();
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteById(String codelignecommande) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Lignecommande where codelignecommande = :codelignecommande")
                    .setParameter("codelignecommande", codelignecommande)
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
            session.createQuery("update Lignecommande set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Lignecommande findById(String codelignecommande) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Lignecommande c = null;
            c = (Lignecommande) session.createQuery(
                    "select c from Lignecommande c where c.codelignecommande = :codelignecommande")
                    .setParameter("codelignecommande", codelignecommande)
                    .getSingleResult();
            return c;
        } catch (RuntimeException re) {

            throw re;
        }
    }
}
