package com.treluxcom.dao;

import com.treluxcom.service.IAdministrateurHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Administrateur;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AdministrateurHomeImpl extends UnicastRemoteObject implements IAdministrateurHome{

   static Session session = HibernateUtil.getSession();

    public AdministrateurHomeImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Administrateur> administrateurList() throws RemoteException {
        List<Administrateur> list = session.createQuery("Select a from Administrateur a", Administrateur.class)
                                           .getResultList();
        
        Administrateur adm =list.get(0);
        adm.setPersonne(list.get(0).getPersonne());
        adm.setBoutiques(list.get(0).getBoutiques());
        adm.setSupercode(list.get(0).getSupercode());
        
        List<Administrateur> ls =new ArrayList();
        ls.add(adm);
        return ls;
    }

    @Override
    public boolean persist(Administrateur administrateur) throws RemoteException {

        try {
            //Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.saveOrUpdate(administrateur.getPersonne());
            session.saveOrUpdate(administrateur);
            tr.commit();
            session.close();
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteByObject(Administrateur administrateur) throws RemoteException {

        try {
            //Session session  = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Administrateur where codeadministrateur = :codeadministrateur")
                    .setParameter("codeadministrateur", administrateur.getCodepersonne())
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteById(String codeadministrateur) throws RemoteException {

        try {
            //Session session  = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Administrateur where codeadministrateur = :codeadministrateur")
                    .setParameter("codeadministrateur", codeadministrateur)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void update(String toUpdate, String toUpdateValue, String reference, String referenceValue) throws RemoteException {

        try {
            Transaction tr = session.beginTransaction();
            session.createQuery("update Administrateur set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Administrateur findById(String codeadministrateur) throws RemoteException {

        try {
            Administrateur c= null;
            c = (Administrateur) session.createQuery(
                    "select c from Administrateur c where c.codepersonne = :codeadministrateur")
                    .setParameter("codeadministrateur", codeadministrateur)
                    .getSingleResult();          
            return c;
        } catch (RuntimeException re) {

            throw re;
        }
    }
}
