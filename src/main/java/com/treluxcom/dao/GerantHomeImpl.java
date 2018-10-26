package com.treluxcom.dao;

import com.treluxcom.service.IGerantHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Gerant;
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


public class GerantHomeImpl extends UnicastRemoteObject implements IGerantHome ,Serializable {

    public GerantHomeImpl() throws RemoteException {
        super();
    }
    @Override
       public List<Gerant> gerantList(){
            Session session = HibernateUtil.getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Gerant> query = builder.createQuery(Gerant.class);
            Root<Gerant> root = query.from(Gerant.class);
            query.select(root);
            Query<Gerant> q = session.createQuery(query);
            List<Gerant> list = q.getResultList();
            return list;
        }
    @Override
	public boolean persist(Gerant gerant) throws RemoteException {
		
		try {
                    try (Session session = HibernateUtil.getSession()) {
                        Transaction tr =session.beginTransaction();
                        session.saveOrUpdate(gerant.getEmploye().getPersonne());
                        session.saveOrUpdate(gerant.getEmploye());
                        session.saveOrUpdate(gerant);
                        tr.commit();
                    }
                        return true;
		} catch (RuntimeException re) {
		
			throw re;
		}
	}

    @Override
	public void deleteByObject(Gerant gerant) throws RemoteException, RemoteException {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Gerant where codepersonne = :codepersonne")
                        .setParameter("codepersonne", gerant.getCodepersonne())
                        .executeUpdate();
                        session.createQuery("delete from Employe where codepersonne = :codepersonne")
                        .setParameter("codepersonne", gerant.getCodepersonne())
                        .executeUpdate();
                        session.createQuery("delete from Personne where codepersonne = :codepersonne")
                        .setParameter("codepersonne", gerant.getCodepersonne())
                        .executeUpdate();
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
			session.createQuery("delete from Gerant where codepersonne = :codepersonne")
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
	public void update(String toUpdate ,String toUpdateValue , String reference , String referenceValue ) {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("update Gerant set "+toUpdate+" = '"+ toUpdateValue+"' where "+ reference+" = :"+ reference)
                        .setParameter(reference, referenceValue)
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}


    @Override
	public Gerant findById(String codepersonne)throws RemoteException {
		
		try {
			Session session  = HibernateUtil.getSession();
                        Gerant c = null;
                        c = (Gerant) session.createQuery(
                            "select c from Gerant c where c.codepersonne = :codepersonne" )
                            .setParameter( "codepersonne", codepersonne )
                            .getSingleResult();
                        return c;
                    } catch (RuntimeException re) {
			
			throw re;
                    }
        }      
}
