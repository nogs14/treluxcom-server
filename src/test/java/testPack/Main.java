package testPack;

import com.treluxcom.dao.BoutiqueHomeImpl;
import com.treluxcom.dao.FamilleHomeImpl;
import com.treluxcom.dao.ProduitHomeImpl;
import com.treluxcom.dao.StatistiquesImpl;
import com.treluxcom.dao.StockHomeImpl;
import com.treluxcom.metier.Famille;
import com.treluxcom.service.Statistiques;
import java.rmi.RemoteException;
import java.util.HashMap;

public class Main {

    public static void main(String args[]) throws RemoteException {
        /*        StockHomeImpl stoHome = new StockHomeImpl();
        Stock sto= new Stock ("samastock");
        stoHome.persist(sto);
        sto =stoHome.findById("samastock");*/
       
        Famille famille=new FamilleHomeImpl().findById("FAM4");
        /*
        System.out.println(sto.getProduits().size());
        Produit prod =new Produit ("PRO", famille, sto);
        
        ProduitHomeImpl prodHome = new ProduitHomeImpl();
        boolean persist = prodHome.persist(prod);
        System.out.println(sto.getProduits().size());*/
        
        HashMap<String,Integer>stat=new StatistiquesImpl().getDashBoard();
        System.out.println(stat);
    }
}
