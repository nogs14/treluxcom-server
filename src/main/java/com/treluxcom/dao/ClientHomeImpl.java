package com.treluxcom.dao;

import com.treluxcom.service.IClientHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Client;
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


public class ClientHomeImpl extends UnicastRemoteObject  implements IClientHome ,Serializable {

    public ClientHomeImpl() throws RemoteException {
        super();
    }
    
    @Override
    public List<Client> clientList(){
            Session session = HibernateUtil.getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Client> query = builder.createQuery(Client.class);
            Root<Client> root = query.from(Client.class);
            query.select(root);
            Query<Client> q = session.createQuery(query);
            List<Client> list = q.getResultList();
            return list;
        }
    @Override
	public boolean persist(Client client) {
		
		try {
			Session session = HibernateUtil.getSession();
			Transaction tr =session.beginTransaction();
			session.saveOrUpdate(client.getPersonne());
                        session.saveOrUpdate(client);
			tr.commit();
			session.close();
                        return true;
		} catch (RuntimeException re) {
		
			throw re;
		}
	}

    @Override
	public void deleteByObject(Client client) {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Client where codepersonne = :codepersonne")
                        .setParameter("codepersonne", client.getCodepersonne())
                        .executeUpdate();
                        session.delete(client.getPersonne());
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
			session.createQuery("delete from Client where codepersonne = :codepersonne")
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
			session.createQuery("update Client set "+toUpdate+" = '"+ toUpdateValue+"' where "+ reference+" = :"+ reference)
                        .setParameter(reference, referenceValue)
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}


    @Override
	public Client findById(String codepersonne) {
		
		try {
			Session session  = HibernateUtil.getSession();
                        Client c = null;
                        c = (Client) session.createQuery(
                            "select c from Client c where c.codepersonne = :codepersonne" )
                            .setParameter( "codepersonne", codepersonne )
                            .getSingleResult();
                        return c;
                    } catch (RuntimeException re) {
			
			throw re;
                    }
        }   

}
