package com.treluxcom.dao;

import com.treluxcom.service.IProduitHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Famille;
import com.treluxcom.metier.Produit;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProduitHomeImpl extends UnicastRemoteObject implements IProduitHome {

    public ProduitHomeImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Produit> produitList() {
        Session session = HibernateUtil.getSession();
        List<Produit> list = session.createQuery("Select c from Produit c", Produit.class).getResultList();
        return list;
    }

    @Override
    public List<Produit> retirerProduit(Famille famille, int nombre) {
        Session session = HibernateUtil.getSession();
        List<Produit> list = session.createQuery("Select c from Produit c where codefamille = :codefamille and codepanier is null order by codestock", Produit.class)
                .setParameter("codefamille", famille.getCodefamille())
                .setMaxResults(nombre)
                .getResultList();
        return list;
    }

    @Override
    public List<Produit> produitDispoList(Famille famille) {
        List<Produit> list = null;
        try {
            Session session = HibernateUtil.getSession();
            list = session.createQuery("Select c from Produit c where codefamille = :codefamille and codepanier is null", Produit.class)
                    .setParameter("codefamille", famille.getCodefamille())
                    .getResultList();

        } catch (Exception e) {
        }
        return list;
    }

    @Override
    public List<Produit> produitList(String codepersonne) {
        Session session = HibernateUtil.getSession();
        List<Produit> list = null;
        try {
            list = session.createQuery("Select c from Produit c "
                    + "where codefournisseur=:code", Produit.class)
                    .setParameter("code", codepersonne)
                    .getResultList();
        } catch (Exception e) {
        }
        return list;
    }

    @Override
    public List<Produit> produitEncoursList() {
        Session session = HibernateUtil.getSession();
        List<Produit> list = session.createQuery("Select c from Produit c where codepanier is null and dateexpiration>current_date", Produit.class).getResultList();
        return list;
    }

    @Override
    public List<Produit> produitValideList() {
        Session session = HibernateUtil.getSession();
        List<Produit> list = session.createQuery("Select c from Produit c where codepanier is not null", Produit.class).getResultList();
        return list;
    }

    @Override
    public List<Produit> produitAnnulerList() {
        Session session = HibernateUtil.getSession();
        List<Produit> list = session.createQuery("Select c from Produit c where codepanier is null and dateexpiration<current_date", Produit.class).getResultList();
        return list;
    }

    @Override
    public List<Produit> produitAttenteConfirmationList() {
        Session session = HibernateUtil.getSession();
        List<Produit> list = session.createQuery("Select c from Produit c where etat=3", Produit.class).getResultList();
        return list;
    }

    @Override
    public boolean persist(Produit produit) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.persist(produit);
            tr.commit();
            session.close();
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteByObject(Produit produit) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Produit where codeproduit = :codeproduit")
                    .setParameter("codeproduit", produit.getCodeproduit())
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteById(String codeproduit) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Produit where codeproduit = :codeproduit")
                    .setParameter("codeproduit", codeproduit)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void update(String toUpdate, String toUpdateValue, String reference, String referenceValue) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("update Produit set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Produit findById(String codeproduit) {

        try {
            Session session = HibernateUtil.getSession();
            Produit c = null;
            c = (Produit) session.createQuery(
                    "select c from Produit c where c.codeproduit = :codeproduit")
                    .setParameter("codeproduit", codeproduit)
                    .getSingleResult();
            return c;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void updateEtat(Produit com, int etat) throws RemoteException {
        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("update Produit set etat = :etat where codeproduit=:codeproduit")
                    .setParameter("etat", etat)
                    .setParameter("codeproduit", com.getCodeproduit())
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    public BigDecimal getPrice(Famille famille) {
        try {
            Session session = HibernateUtil.getSession();
            List<Produit> c =  session.createQuery(
                    "select c from Produit c where c.famille.codefamille = :codefamille and codepanier is null")
                    .setParameter("codefamille", famille.getCodefamille())
                    .getResultList();
            
            return c.get(0).getPrixgerant();
        } catch (RuntimeException re) {

            throw re;
        }
    }

}
