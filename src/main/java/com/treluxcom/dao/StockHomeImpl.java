package com.treluxcom.dao;

import com.treluxcom.service.IStockHome;
import com.treluxcom.database.HibernateUtil;
import com.treluxcom.metier.Commande;
import com.treluxcom.metier.Devis;
import com.treluxcom.metier.Famille;
import com.treluxcom.metier.Lignedevis;
import com.treluxcom.metier.Produit;
import com.treluxcom.metier.Stock;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StockHomeImpl extends UnicastRemoteObject implements IStockHome {

    public StockHomeImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Stock> stockList() {
        Session session = HibernateUtil.getSession();
        List<Stock> list = session.createQuery("Select f from Stock f", Stock.class).getResultList();
        return list;
    }

    @Override
    public List<Stock> stockPublieList() {
        Session session = HibernateUtil.getSession();
        List<Stock> list = session.createQuery("Select f from Stock f where publier=:publie", Stock.class)
                .setParameter("publie", true)
                .getResultList();
        return list;
    }

    @Override
    public List<Stock> stockNonPublieList() {
        Session session = HibernateUtil.getSession();
        List<Stock> list = session.createQuery("Select f from Stock f where publier=:publie", Stock.class)
                .setParameter("publie", false)
                .getResultList();
        return list;
    }

    @Override
    public List<Stock> stockPublieNonVideList() {
        Session session = HibernateUtil.getSession();
        List<Stock> list = new ArrayList();
        Iterator sto = stockPublieList().iterator();
        while (sto.hasNext()) {
            Stock st = (Stock) sto.next();
            if (!isEmpty(st)) {
                list.add(st);
            }
        }
        return list;
    }

    @Override
    public boolean isEmpty(Stock stock) {
        List<Produit> produits = new ArrayList(stock.getProduits());
        Iterator prods = produits.iterator();
        while (prods.hasNext()) {
            Produit produit = (Produit) prods.next();
            if ((produit.getPanier() != null)
                    && (produit.getDateexpiration().compareTo(new Date()) > 0)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int quantiteDispoEnStock(Stock stock, Famille famille) {
        int quantite = 0;
        List<Produit> produits = new ArrayList(stock.getProduits());
        Iterator prods = produits.iterator();
        while (prods.hasNext()) {
            Produit produit = (Produit) prods.next();
            if (produit.getPanier() == null && produit.getFamille().equals(famille) && produit.getDateexpiration().compareTo(new Date()) > 0) {
                quantite++;
            }
        }
        return quantite;
    }

    @Override
    public HashMap<String, Integer> famillePublie(List<Stock> stocks) {
        HashMap<String, Integer> stats = new HashMap<String, Integer>();
        try {
            Iterator stockIt = stocks.iterator();
            while (stockIt.hasNext()) {
                Stock sto = (Stock) stockIt.next();
                List<Famille> familles = familleList(sto);
                Iterator famillesIt = familles.iterator();
                while (famillesIt.hasNext()) {
                    Famille fam = (Famille) famillesIt.next();
                    if (stats.containsKey(fam.getCodefamille())) {
                        stats.put(fam.getCodefamille(), stats.get(fam.getCodefamille()) + quantiteDispoEnStock(sto, fam));
                    } else {
                        stats.put(fam.getCodefamille(), quantiteDispoEnStock(sto, fam));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    @Override
    public List<Famille> familleList(Stock stock) throws RemoteException {
        List<Famille> familles = new ArrayList();
        try {
            Commande commande = (Commande) (new ArrayList(stock.getCommandes()).get(new ArrayList(stock.getCommandes()).size() - 1));
            Devis devis = (Devis) (new ArrayList(commande.getDevises()).get(new ArrayList(commande.getDevises()).size() - 1));

            List<Lignedevis> ligneDevis = new ArrayList(devis.getLignedevises());
            Iterator ligneDevisIt = ligneDevis.iterator();
            while (ligneDevisIt.hasNext()) {
                Lignedevis ld = (Lignedevis) ligneDevisIt.next();
                familles.add(ld.getFamille());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return familles;
    }

    @Override
    public boolean persist(Stock stock) {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.persist(stock);
            tr.commit();
            session.close();
            return true;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteByObject(Stock stock) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Stock where codestock = :codestock")
                    .setParameter("codestock", stock.getCodestock())
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public void deleteById(String codestock) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from Stock where codestock = :codestock")
                    .setParameter("codestock", codestock)
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
            session.createQuery("update Stock set " + toUpdate + " = '" + toUpdateValue + "' where " + reference + " = :" + reference)
                    .setParameter(reference, referenceValue)
                    .executeUpdate();
            tr.commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    @Override
    public Stock findById(String codestock) throws RemoteException {

        try {
            Session session = HibernateUtil.getSession();
            Stock c = null;
            c = (Stock) session.createQuery(
                    "select c from Stock c where c.codestock = :codestock")
                    .setParameter("codestock", codestock)
                    .getSingleResult();
            return c;
        } catch (RuntimeException re) {

            throw re;
        }
    }

}
