package com.treluxcom.dao;

import com.treluxcom.service.ICommandeHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Commande;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CommandeHomeImpl extends UnicastRemoteObject implements ICommandeHome, Serializable {

    public CommandeHomeImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Commande> commandeList() {
        Session session = HibernateUtil.getSession();
        List<Commande> list = session.createQuery("Select c from Commande c", Commande.class).getResultList();
        return list;
    }

    @Override
    public List<Commande> commandeList(String codepersonne) {
        Session session = HibernateUtil.getSession();
        List<Commande> list = session.createQuery("Select c from Commande c "
                + "where codefournisseur=:code", Commande.class)
                .setParameter("code", codepersonne)
                .getResultList();
        return list;
    }

    @Override
    public List<Commande> commandeEncoursList() {
        Session session = HibernateUtil.getSession();
        List<Commande> list = session.createQuery("Select c from Commande c where etat=0", Commande.class).getResultList();
        return list;
    }

    @Override
    public List<Commande> commandeValideList() {
        Session session = HibernateUtil.getSession();
        List<Commande> list = session.createQuery("Select c from Commande c where etat=1", Commande.class).getResultList();
        return list;
    }

    @Override
    public List<Commande> commandeAnnulerList() {
        Session session = HibernateUtil.getSession();
        List<Commande> list = session.createQuery("Select c from Commande c where etat=2", Commande.class).getResultList();
        return list;
    }
      @Override 
    public List<Commande> commandeAttenteConfirmationList() {
        Session session = HibernateUtil.getSession();
        List<Commande> list = session.createQuery("Select c from Commande c where etat=3", Commande.class).getResultList();
        return list;
    }

    @Override
    public boolean persist(Commande commande) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.persist(commande);
            tr.commit();
            session.close();
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteByObject(Commande commande) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Commande where codecommande = :codecommande")
                    .setParameter("codecommande", commande.getCodecommande())
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteById(String codecommande) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Commande where codecommande = :codecommande")
                    .setParameter("codecommande", codecommande)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void update(String toUpdate, String toUpdateValue, String reference, String referenceValue) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("update Commande set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Commande findById(String codecommande) {

        try {
            Session session = HibernateUtil.getSession();
            Commande c = null;
            c = (Commande) session.createQuery(
                    "select c from Commande c where c.codecommande = :codecommande")
                    .setParameter("codecommande", codecommande)
                    .getSingleResult();
            return c;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void updateEtat(Commande com , int etat) throws RemoteException {
            try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("update Commande set etat = :etat where codecommande=:codecommande")
                    .setParameter("etat", etat)
                    .setParameter("codecommande", com.getCodecommande())
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }    
    }
}
