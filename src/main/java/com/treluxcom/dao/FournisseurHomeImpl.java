package com.treluxcom.dao;

import com.treluxcom.service.IFournisseurHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Fournisseur;
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

public class FournisseurHomeImpl extends UnicastRemoteObject implements IFournisseurHome, Serializable {

    public FournisseurHomeImpl() throws RemoteException {
        super();
    }
    @Override
    public List<Fournisseur> fournisseurList(){
            Session session = HibernateUtil.getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Fournisseur> query = builder.createQuery(Fournisseur.class);
            Root<Fournisseur> root = query.from(Fournisseur.class);
            query.select(root);
            Query<Fournisseur> q = session.createQuery(query);
            List<Fournisseur> list = q.getResultList();
            return list;
        }
    @Override
	public boolean persist(Fournisseur fournisseur) {
		
		try {
			Session session = HibernateUtil.getSession();
			Transaction tr =session.beginTransaction();
			session.saveOrUpdate(fournisseur.getPersonne());
                        session.saveOrUpdate(fournisseur);
			tr.commit();
			session.close();
                        return true;
		} catch (RuntimeException re) {
		
			throw re;
		}
	}

    @Override
	public void deleteByObject(Fournisseur fournisseur) throws RemoteException {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Fournisseur where codepersonne = :codepersonne")
                        .setParameter("codepersonne", fournisseur.getPersonne().getCodepersonne())
                        .executeUpdate();
                        session.delete(fournisseur.getPersonne());
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}
    @Override
	public void deleteById(String codepersonne) {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Fournisseur where codepersonne = :codepersonne")
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
	public void update(String toUpdate ,String toUpdateValue , String reference , String referenceValue ) {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("update Fournisseur set "+toUpdate+" = '"+ toUpdateValue+"' where "+ reference+" = :"+ reference)
                        .setParameter(reference, referenceValue)
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}


    @Override
	public Fournisseur findById(String codepersonne) {
		
		try {
			Session session  = HibernateUtil.getSession();
                        Fournisseur c = null;
                        c = (Fournisseur) session.createQuery(
                            "select c from Fournisseur c where c.codepersonne = :codepersonne" )
                            .setParameter( "codepersonne", codepersonne )
                            .getSingleResult();
                        return c;
                    } catch (RuntimeException re) {
			
			throw re;
                    }
        }        
}
