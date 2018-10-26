/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treluxcom.dao;

import com.treluxcom.service.ICaissierHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Caissier;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author inspectrice Amy Sene
 */
public class CaissierHomeImpl extends UnicastRemoteObject implements ICaissierHome , Serializable{

    public CaissierHomeImpl() throws RemoteException {
        super();
    }
    
    @Override
       public List<Caissier> caissierList() throws RemoteException{
            Session session = HibernateUtil.getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Caissier> query = builder.createQuery(Caissier.class);
            Root<Caissier> root = query.from(Caissier.class);
            query.select(root);
            Query<Caissier> q = session.createQuery(query);
            List<Caissier> list = q.getResultList();
            return list;
        }
    @Override
	public boolean persist(Caissier caissier) throws RemoteException{
		
		try {
			Session session = HibernateUtil.getSession();
			Transaction tr =session.beginTransaction();
			session.saveOrUpdate(caissier.getEmploye().getPersonne());
                        session.saveOrUpdate(caissier.getEmploye());
                        session.saveOrUpdate(caissier);
			tr.commit();
			session.close();
                        return true;
		} catch (RuntimeException re) {
		
			throw re;
		}
	}

    @Override
	public void deleteByObject(Caissier caissier) throws RemoteException {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Caissier where codepersonne = :codepersonne")
                        .setParameter("codepersonne", caissier.getCodepersonne())
                        .executeUpdate();
                        session.createQuery("delete from Employe where codepersonne = :codepersonne")
                        .setParameter("codepersonne", caissier.getCodepersonne())
                        .executeUpdate();
                        session.createQuery("delete from Personne where codepersonne = :codepersonne")
                        .setParameter("codepersonne", caissier.getCodepersonne())
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}
    @Override
	public void deleteById(String codepersonne) throws RemoteException {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Caissier where codepersonne = :codepersonne")
                        .setParameter("codepersonne", codepersonne)
                        .executeUpdate();
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
	public void update(String toUpdate ,String toUpdateValue , String reference , String referenceValue ) throws RemoteException {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("update Caissier set "+toUpdate+" = '"+ toUpdateValue+"' where "+ reference+" = :"+ reference)
                        .setParameter(reference, referenceValue)
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}


    @Override
	public Caissier findById(String codepersonne)  throws RemoteException {
		
		try {
			Session session  = HibernateUtil.getSession();
                        Caissier c = null;
                        c = (Caissier) session.createQuery(
                            "select c from Caissier c where c.codepersonne = :codepersonne" )
                            .setParameter( "codepersonne", codepersonne )
                            .getSingleResult();
                        return c;
                    } catch (RuntimeException re) {
			
			throw re;
                    }
        }      
}
