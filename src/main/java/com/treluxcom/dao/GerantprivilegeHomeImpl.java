/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treluxcom.dao;

import com.treluxcom.service.IGerantprivilegeHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Gerantprivilege;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ADA-MALICK
 */
public class GerantprivilegeHomeImpl extends UnicastRemoteObject implements IGerantprivilegeHome , Serializable{

    public GerantprivilegeHomeImpl() throws RemoteException {
    }

    @Override
    public List<Gerantprivilege> gerantprivilegeList() {
        Session session = HibernateUtil.getSession();
        List<Gerantprivilege> list = session.createQuery("Select b from Gerantprivilege b", Gerantprivilege.class).getResultList();
        return list;
    }

    @Override
    public boolean persist(Gerantprivilege gerantprivilege) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
           // session.persist(gerantprivilege.getPrivilege());
            session.persist(gerantprivilege);
            tr.commit();
            session.close();
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

 /*   public void deleteByObject(Gerantprivilege gerantprivilege) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Gerantprivilege where codegerantprivilege = :codegerantprivilege")
                    .setParameter("codegerantprivilege", gerantprivilege.getCodegerantprivilege())
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    public void deleteById(String codegerantprivilege) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Gerantprivilege where codegerantprivilege = :codegerantprivilege")
                    .setParameter("codegerantprivilege", codegerantprivilege)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }*/

    @Override
    public void update(String toUpdate, String toUpdateValue, String reference, String referenceValue) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("update Gerantprivilege set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Gerantprivilege findById(String codegerantprivilege) {

        try {
            Session session = HibernateUtil.getSession();
            Gerantprivilege c = null;
            c = (Gerantprivilege) session.createQuery(
                    "select c from Gerantprivilege c where c.codegerantprivilege = :codegerantprivilege")
                    .setParameter("codegerantprivilege", codegerantprivilege)
                    .getSingleResult();
            return c;
        } catch (RuntimeException re) {

            throw re;
        }
    }
}
