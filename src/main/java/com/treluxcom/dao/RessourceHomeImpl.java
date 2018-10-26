
package com.treluxcom.dao;

import com.treluxcom.service.IRessourcemediaHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Ressourcemedia;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class RessourceHomeImpl extends UnicastRemoteObject implements IRessourcemediaHome {

    public RessourceHomeImpl() throws RemoteException {
        super();
    }
    
    @Override
           public List<Ressourcemedia> ressourceMediaList(){
            Session session = HibernateUtil.getSession();
            List<Ressourcemedia> list = session.createQuery("Select f from Ressourcemedia f" , Ressourcemedia.class).getResultList();
            return list;
        }
    @Override
	public boolean persist(Ressourcemedia ressourceMedia) {
		
		try {
			Session session = HibernateUtil.getSession();
			Transaction tr =session.beginTransaction();
                        session.persist(ressourceMedia);
			tr.commit();
			session.close();
                        return true;
		} catch (RuntimeException re) {
		
			throw re;
		}
	}

    @Override
	public void deleteByObject(Ressourcemedia ressourceMedia) throws RemoteException {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Ressourcemedia where coderessource = :coderessource")
                        .setParameter("coderessource", ressourceMedia.getCoderessource())
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}
    @Override
	public void deleteById(String coderessource) throws RemoteException {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Ressourcemedia where coderessource = :coderessource")
                        .setParameter("coderessource", coderessource)
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
			session.createQuery("update Ressourcemedia set "+toUpdate+" = '"+ toUpdateValue+"' where "+ reference+" = :"+ reference)
                        .setParameter(reference, referenceValue)
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}


    @Override
	public Ressourcemedia findById(String coderessource) throws RemoteException{
		
		try {
			Session session  = HibernateUtil.getSession();
                        Ressourcemedia c = null;
                        c = (Ressourcemedia) session.createQuery(
                            "select c from Ressourcemedia c where c.coderessource = :coderessource" )
                            .setParameter( "coderessource", coderessource )
                            .getSingleResult();
                        return c;
                    } catch (RuntimeException re) {
			
			throw re;
                    }
        }
}
