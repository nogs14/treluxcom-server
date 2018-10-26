package com.treluxcom.dao;

import com.treluxcom.service.IPersonneHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Personne;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PersonneHomeImpl extends UnicastRemoteObject implements IPersonneHome {

    public PersonneHomeImpl() throws RemoteException {

    }

    Session session = HibernateUtil.getSession();

    @Override
    public List<Personne> personneList() {
        List<Personne> list = session.createQuery("Select p from Personne p", Personne.class).getResultList();
        return list;
    }

    @Override
    public boolean inserer(Personne personne) {

        try {
            //Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.saveOrUpdate(personne);
            tr.commit();
            session.close();
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void delete(Personne persistentInstance) {

        try {
            //Session session  = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.delete(persistentInstance);
            tr.commit();
            session.close();
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Personne findById(String codepersonne) {

        try {
            //Session session  = HibernateUtil.getSession();
            Personne p = null;
            p = (Personne) session.createQuery(
                    "select p from Personne p where p.codepersonne = :codepersonne")
                    .setParameter("codepersonne", codepersonne)
                    .getSingleResult();
            return p;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    public Personne findByEmail(String email) {

        try {
            //Session session  = HibernateUtil.getSession();
            Personne p = null;
            p = (Personne) session.createQuery(
                    "select p from Personne p where p.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
            return p;
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
        public Personne findByLogin(String login) {

        try {
            Session session  = HibernateUtil.getSession();
            Personne p = null;
            p = (Personne) session.createQuery(
                    "select p from Personne p where p.login = :login")
                    .setParameter("login", login)
                    .getSingleResult();
            return p;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public void update(String toUpdate, String toUpdateValue, String reference, String referenceValue) {

        try {
            Session session  = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("update Personne set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Personne connection(String login, String motpasse) {
        try {
            //Session session  = HibernateUtil.getSession();
            Personne p = null;
            p = (Personne) session.createQuery(
                    "select p from Personne p where p.login = :login and p.motpasse = :motpasse")
                    .setParameter("login", login)
                    .setParameter("motpasse", motpasse)
                    .uniqueResult()
                    ;
            return p;
        } catch (Exception re) {

            throw re;
        }

    }

}
