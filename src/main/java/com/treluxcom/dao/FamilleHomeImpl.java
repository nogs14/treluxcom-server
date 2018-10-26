
package com.treluxcom.dao;

import com.treluxcom.service.IFamilleHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Famille;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class FamilleHomeImpl extends UnicastRemoteObject implements IFamilleHome {

    public FamilleHomeImpl() throws RemoteException {
        super();
    }
    
    @Override
           public List<Famille> familleList(){
            Session session = HibernateUtil.getSession();
            List<Famille> list = session.createQuery("Select f from Famille f" , Famille.class).getResultList();
            return list;
        }
    @Override
	public boolean persist(Famille famille) {
		
		try {
			Session session = HibernateUtil.getSession();
			Transaction tr =session.beginTransaction();
                        session.persist(famille);
			tr.commit();
			session.close();
                        return true;
		} catch (RuntimeException re) {
		
			throw re;
		}
	}

    @Override
	public void deleteByObject(Famille famille) throws RemoteException {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Famille where codefamille = :codefamille")
                        .setParameter("codefamille", famille.getCodefamille())
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}
    @Override
	public void deleteById(String codefamille) throws RemoteException {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Famille where codefamille = :codefamille")
                        .setParameter("codefamille", codefamille)
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}
    @Override
	public void update(String toUpdate ,String toUpdateValue , String reference , String referenceValue )throws RemoteException {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("update Famille set "+toUpdate+" = '"+ toUpdateValue+"' where "+ reference+" = :"+ reference)
                        .setParameter(reference, referenceValue)
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}


    @Override
	public Famille findById(String codefamille) throws RemoteException{
		
		try {
			Session session  = HibernateUtil.getSession();
                        Famille c = null;
                        c = (Famille) session.createQuery(
                            "select c from Famille c where c.codefamille = :codefamille" )
                            .setParameter( "codefamille", codefamille )
                            .getSingleResult();
                        return c;
                    } catch (RuntimeException re) {
			
			throw re;
                    }
        }
}
