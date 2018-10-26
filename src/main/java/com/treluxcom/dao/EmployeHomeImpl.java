package com.treluxcom.dao;

import com.treluxcom.service.IEmployeHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Boutique;
import com.treluxcom.metier.Employe;
import com.treluxcom.metier.Personne;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class EmployeHomeImpl extends UnicastRemoteObject implements IEmployeHome, Serializable {

    public EmployeHomeImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Employe> employeList() {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employe> query = builder.createQuery(Employe.class);
        Root<Employe> root = query.from(Employe.class);
        query.select(root);
        Query<Employe> q = session.createQuery(query);
        List<Employe> list = q.getResultList();
        return list;
    }

    @Override
    public boolean persist(Employe employe) throws RemoteException {

        try {
            try (Session session = HibernateUtil.getSession()) {
                Transaction tr = session.beginTransaction();
                session.persist(employe.getPersonne());
                session.persist(employe);
                tr.commit();
            }
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteByObject(Employe employe) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Employe where codepersonne = :codepersonne")
                    .setParameter("codepersonne", employe.getPersonne().getCodepersonne())
                    .executeUpdate();
            session.delete(employe.getPersonne());
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteById(String codepersonne) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Employe where codepersonne = :codepersonne")
                    .setParameter("codepersonne", codepersonne)
                    .executeUpdate();
            session.createQuery("delete from Personne where codepersonne = :codepersonne")
                    .setParameter("codepersonne", codepersonne)
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
            session.createQuery("update Employe set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Employe findById(String codepersonne) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Employe c = null;
            c = (Employe) session.createQuery(
                    "select c from Employe c where c.codepersonne = :codepersonne")
                    .setParameter("codepersonne", codepersonne)
                    .getSingleResult();
            return c;
        } catch (RuntimeException re) {

            throw re;
        }
    }

     @Override
    public List<Employe> ListEmployes(Set boutiques) {
        Session session = HibernateUtil.getSession();
        List<Employe> empl = new ArrayList();
        List<Boutique> bouts = new ArrayList(boutiques);
        Iterator boutIt = bouts.iterator();
        while (boutIt.hasNext()) {
            Boutique bout=(Boutique)boutIt.next();
            List<Employe> c = null;
            c =new ArrayList(bout.getEmployes());
                    
            if(c.size()>0){
                empl.addAll(c);
            }
   
        }
        return empl;
     }
}