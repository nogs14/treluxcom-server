package com.treluxcom.dao;

import com.treluxcom.metier.Boutique;
import com.treluxcom.metier.Commande;
import com.treluxcom.metier.Devis;
import com.treluxcom.metier.Employe;
import com.treluxcom.metier.Gerant;
import com.treluxcom.metier.Lignedevis;
import com.treluxcom.metier.Produit;
import com.treluxcom.metier.Stock;
import com.treluxcom.service.Statistiques;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatistiquesImpl extends UnicastRemoteObject implements Statistiques {

    public StatistiquesImpl() throws RemoteException {
    }

    @Override
    public HashMap<String, List<Produit>> stock(Stock stock) throws RemoteException {
        HashMap<String, List<Produit>> stats = new HashMap<String, List<Produit>>();
        List<Produit> produitEnstock = new ArrayList();
        List<Produit> produitVendu = new ArrayList();
        List<Produit> produitExpire = new ArrayList();
        List<Produit> produitVenduEnligne = new ArrayList();
        List<Produit> produitVenduenBoutique = new ArrayList();

        stats.put("produitEnstock", produitEnstock);
        stats.put("produitVendu", produitVendu);
        stats.put("produitVenduEnligne", produitVenduEnligne);
        stats.put("produitVenduenBoutique", produitVenduenBoutique);
        stats.put("produitExpire", produitExpire);

        try {
            List<Produit> produits = new ArrayList(stock.getProduits());
            Iterator prods = produits.iterator();
            while (prods.hasNext()) {
                Produit produit = (Produit) prods.next();
                try {
                    if (produit.getDateexpiration().compareTo(new Date()) < 0) {
                        stats.get("produitExpire").add(produit);
                    } else if (produit.getPanier() == null) {
                        stats.get("produitEnstock").add(produit);
                    } else {
                        if (produit.getPanier().getPanierclient() != null) {
                            stats.get("produitVenduEnligne").add(produit);
                        } else {
                            stats.get("produitVenduenBoutique").add(produit);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    @Override
    public HashMap<String, List<Commande>> commande(Boutique boutique) throws RemoteException {
        boutique = new BoutiqueHomeImpl().findById(boutique.getCodeboutique());
        HashMap<String, List<Commande>> stats = new HashMap<String, List<Commande>>();
        List<Commande> tout = new ArrayList();
        List<Commande> valide = new ArrayList();
        List<Commande> annule = new ArrayList();
        List<Commande> encours = new ArrayList();
        List<Commande> sansconfirmation = new ArrayList();

        stats.put("tout", tout);
        stats.put("encours", encours);
        stats.put("sansconfirmation", sansconfirmation);
        stats.put("valide", valide);
        stats.put("annule", annule);

        try {
            List<Employe> emps = new ArrayList(boutique.getEmployes());
            Iterator codegerants = emps.iterator();
            while (codegerants.hasNext()) {
                Gerant gerant = new Gerant();
                gerant = ((Employe) codegerants.next()).getGerant();
                Iterator commandes = null;
                try {
                    commandes = gerant.getCommandes().iterator();
                } catch (Exception e) {
                }
                try {
                    while (commandes.hasNext()) {
                        Commande commande = (Commande) commandes.next();
                        if (commande.getEtat() == 0) {
                            stats.get("sansconfirmation").add(commande);
                        } else if (commande.getEtat() == 1) {
                            stats.get("valide").add(commande);
                        } else if (commande.getEtat() == 2) {
                            stats.get("annule").add(commande);
                        } else if (commande.getEtat() == 3) {
                            stats.get("encours").add(commande);
                        }
                        stats.get("tout").add(commande);
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    @Override
    public HashMap<String, Integer> statProduit(Commande commande) {
        int nombreProduit = commande.getLignecommandes().size();
        int nombreProduitdispo = 0;
        List<Devis> lesdevis = new ArrayList(commande.getDevises());
        try {
            Devis devis = new Devis();
            try {
                devis = lesdevis.get(0);
            } catch (Exception e) {
            }
            Iterator ligneproduitsdispo = devis.getLignedevises().iterator();
            while (ligneproduitsdispo.hasNext()) {
                Lignedevis ld = (Lignedevis) ligneproduitsdispo.next();
                if (ld.getQuantite() != 0) {
                    nombreProduitdispo = nombreProduitdispo + 1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, Integer> stat = new HashMap<String, Integer>();
        stat.put("nombreProduit", nombreProduit);
        stat.put("nombreProduitdispo", nombreProduitdispo);
        return stat;

    }
    
    public HashMap<String, Integer> getDashBoard(){
        HashMap<String, Integer> stat = new HashMap<String, Integer>();
        
        try {
            ProduitHomeImpl proh=new ProduitHomeImpl();
            
            int nbreBout=new BoutiqueHomeImpl().boutiqueList().size();
            int nbreEmp=new EmployeHomeImpl().employeList().size();    
            int nbreFour=new FournisseurHomeImpl().fournisseurList().size();
            int nbreClient=new ClientHomeImpl().clientList().size();
            int nbreProdEx = proh.produitAnnulerList().size();
            int nbreProdEnStock = proh.produitEncoursList().size();
            int nbreProdVendu = proh.produitValideList().size();
            
            stat.put("client", nbreClient);
            stat.put("employe",nbreEmp );
            stat.put("fournisseur", nbreFour);
            stat.put("boutique",nbreBout );
            
            stat.put("produitVendu",nbreProdVendu );
            stat.put("produitExpire", nbreProdEx);
            stat.put("produitEnStock",nbreProdEnStock );
                    
        } catch (Exception ex) {
            Logger.getLogger(StatistiquesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stat;
    }

}
