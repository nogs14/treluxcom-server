/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treluxcom.dao;

import com.treluxcom.service.ICalendrierservicehome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Calendrierservice;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CalendrierserviceHomeImpl extends UnicastRemoteObject implements ICalendrierservicehome, Serializable {

    public CalendrierserviceHomeImpl() throws RemoteException {
        super();
    }
    
    @Override
            public List<Calendrierservice> calendrierserviceList(){
            Session session = HibernateUtil.getSession();
            List<Calendrierservice> list = session.createQuery("Select b from Calendrierservice b" , Calendrierservice.class).getResultList();
            return list;
        }
    @Override
	public boolean persist(Calendrierservice calendrierservice) {
		
		try {
                    try (Session session = HibernateUtil.getSession()) {
                        Transaction tr =session.beginTransaction();
                        session.persist(calendrierservice);
                        tr.commit();
                    }
                        return true;
		} catch (RuntimeException re) {
		
			throw re;
		}
	}

    @Override
	public void deleteById(String codecalendrierservice) {
	
		try {
			Session session  = HibernateUtil.getSession();
                        Transaction tr = session.beginTransaction();
			session.createQuery("delete from Calendrierservice where codecalendrierservice = :codecalendrierservice")
                        .setParameter("codecalendrierservice", codecalendrierservice)
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
			session.createQuery("update Calendrierservice set "+toUpdate+" = '"+ toUpdateValue+"' where "+ reference+" = :"+ reference)
                        .setParameter(reference, referenceValue)
                        .executeUpdate();
                        tr.commit();
		
		} catch (RuntimeException re) {
			
			throw re;
		}
	}


    @Override
	public Calendrierservice findById(String codecalendrierservice) {
		
		try {
			Session session  = HibernateUtil.getSession();
                        Calendrierservice c = null;
                        c = (Calendrierservice) session.createQuery(
                            "select c from Calendrierservice c where c.codecalendrierservice = :codecalendrierservice" )
                            .setParameter( "codecalendrierservice", codecalendrierservice )
                            .getSingleResult();
                        return c;
                    } catch (RuntimeException re) {
			
			throw re;
                    }
        }
}
