package com.treluxcom.dao;

import com.treluxcom.service.IDevisHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Devis;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DevisHomeImpl extends UnicastRemoteObject implements IDevisHome {

    public DevisHomeImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Devis> devisList() {
        Session session = HibernateUtil.getSession();
        List<Devis> list = session.createQuery("Select c from Devis c", Devis.class).getResultList();
        return list;
    }

    @Override
    public List<Devis> devisList(String codepersonne) {
        Session session = HibernateUtil.getSession();
        List<Devis> list = session.createQuery("Select c from Devis c "
                + "where codefournisseur=:code", Devis.class)
                .setParameter("code", codepersonne)
                .getResultList();
        return list;
    }

    @Override
    public List<Devis> devisEncoursList() {
        Session session = HibernateUtil.getSession();
        List<Devis> list = session.createQuery("Select c from Devis c where etat=0", Devis.class).getResultList();
        return list;
    }

    @Override
    public List<Devis> devisValideList() {
        Session session = HibernateUtil.getSession();
        List<Devis> list = session.createQuery("Select c from Devis c where etat=1", Devis.class).getResultList();
        return list;
    }

    @Override
    public List<Devis> devisAnnulerList() {
        Session session = HibernateUtil.getSession();
        List<Devis> list = session.createQuery("Select c from Devis c where etat=2", Devis.class).getResultList();
        return list;
    }

    @Override
    public boolean persist(Devis devis) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.persist(devis);
            tr.commit();
            session.close();
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteByObject(Devis devis) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Devis where codedevis = :codedevis")
                    .setParameter("codedevis", devis.getCodedevis())
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteById(String codedevis) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Devis where codedevis = :codedevis")
                    .setParameter("codedevis", codedevis)
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
            session.createQuery("update Devis set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Devis findById(String codedevis) {

        try {
            Session session = HibernateUtil.getSession();
            Devis c = null;
            c = (Devis) session.createQuery(
                    "select c from Devis c where c.codedevis = :codedevis")
                    .setParameter("codedevis", codedevis)
                    .getSingleResult();
            return c;
        } catch (RuntimeException re) {

            throw re;
        }
    }
}
