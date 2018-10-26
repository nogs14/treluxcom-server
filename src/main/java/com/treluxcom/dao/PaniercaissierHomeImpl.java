package com.treluxcom.dao;

import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Paniercaissier;
import com.treluxcom.metier.Produit;
import com.treluxcom.service.IPaniercaissierHome;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class PaniercaissierHomeImpl extends UnicastRemoteObject implements IPaniercaissierHome {

    public PaniercaissierHomeImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Paniercaissier> paniercaissierList() {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Paniercaissier> query = builder.createQuery(Paniercaissier.class);
        Root<Paniercaissier> root = query.from(Paniercaissier.class);
        query.select(root);
        Query<Paniercaissier> q = session.createQuery(query);
        List<Paniercaissier> list = q.getResultList();
        return list;
    }

    @Override
    public boolean persist(Paniercaissier paniercaissier) throws RemoteException {
        try {
            try (Session session = HibernateUtil.getSession()) {
                Transaction tr = session.beginTransaction();
                session.persist(paniercaissier.getPanier());
                session.persist(paniercaissier);
                tr.commit();
                ProduitHomeImpl ph=new ProduitHomeImpl();
                List<Produit> lp=new ArrayList(paniercaissier.getPanier().getProduits());
                for(int i=0;i<lp.size();i++){
                ph.update("codepanier", paniercaissier.getPanier().getCodepanier(), "codeproduit", lp.get(i).getCodeproduit());    
                }
            }
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteByObject(Paniercaissier paniercaissier) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Paniercaissier where codepanier = :codepanier")
                    .setParameter("codepanier", paniercaissier.getCodepanier())
                    .executeUpdate();
            session.createQuery("delete from Panier where codepanier = :codepanier")
                    .setParameter("codepanier", paniercaissier.getCodepanier())
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteById(String codepanier) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Paniercaissier where codepanier = :codecaissier")
                    .setParameter("codepanier", codepanier)
                    .executeUpdate();
            session.createQuery("delete from Panier where codepanier = :codepanier")
                    .setParameter("codepanier", codepanier)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void update(String toUpdate, String toUpdateValue, String reference, String referenceValue) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("update Paniercaissier set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Paniercaissier findById(String codepanier) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Paniercaissier c = null;
            c = (Paniercaissier) session.createQuery(
                    "select c from Paniercaissier c where c.codepanier = :codepanier")
                    .setParameter("codepanier", codepanier)
                    .getSingleResult();
            return c;
        } catch (RuntimeException re) {

            throw re;
        }

    }
}