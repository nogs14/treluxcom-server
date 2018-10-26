package com.treluxcom.dao;

import com.treluxcom.service.ILivreurHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Livreur;
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

public class LivreurHomeImpl extends UnicastRemoteObject implements ILivreurHome, Serializable {

    public LivreurHomeImpl() throws RemoteException {
        super();
    }
    
    @Override
    public List<Livreur> livreurList()throws RemoteException{
            Session session = HibernateUtil.getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Livreur> query = builder.createQuery(Livreur.class);
            Root<Livreur> root = query.from(Livreur.class);
            query.select(root);
            Query<Livreur> q = session.createQuery(query);
            List<Livreur> list = q.getResultList();
            return list;
        }
    @Override
	public boolean persist(Livreur livreur)throws RemoteException {
		
		try {
			Session session = HibernateUtil.getSession();
			Transaction tr =session.beginTransaction();
			session.saveOrUpdate(livreur.getEmploye().getPersonne());
                        session.saveOrUpdate(livreur.getEmploye());
                        session.saveOrUpdate(livreur);
			tr.commit();
			session.close();
                        return true;
		} catch (RuntimeException re) {
		
			throw re;
		}
	}

    @Override
	public void deleteByObject(Livreur livreur)throws RemoteException {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Livreur where codepersonne = :codepersonne")
                        .setParameter("codepersonne", livreur.getCodepersonne())
                        .executeUpdate();
                        session.createQuery("delete from Employe where codepersonne = :codepersonne")
                        .setParameter("codepersonne", livreur.getCodepersonne())
                        .executeUpdate();
                        session.createQuery("delete from Personne where codepersonne = :codepersonne")
                        .setParameter("codepersonne", livreur.getCodepersonne())
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}
    @Override
	public void deleteById(String codepersonne)throws RemoteException {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Livreur where codepersonne = :codepersonne")
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
	public void update(String toUpdate ,String toUpdateValue , String reference , String referenceValue ) throws RemoteException{
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("update Livreur set "+toUpdate+" = '"+ toUpdateValue+"' where "+ reference+" = :"+ reference)
                        .setParameter(reference, referenceValue)
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}


    @Override
	public Livreur findById(String codepersonne) throws RemoteException{
		
		try {
			Session session  = HibernateUtil.getSession();
                        Livreur c = null;
                        c = (Livreur) session.createQuery(
                            "select c from Livreur c where c.codepersonne = :codepersonne" )
                            .setParameter( "codepersonne", codepersonne )
                            .getSingleResult();
                        return c;
                    } catch (RuntimeException re) {
			
			throw re;
                    }
        }        
}
