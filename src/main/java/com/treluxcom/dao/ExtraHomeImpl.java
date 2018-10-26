package com.treluxcom.dao;

import com.treluxcom.database.HibernateUtil;
import com.treluxcom.service.IExtraHome;
import com.treluxcom.metier.Boutique;
import com.treluxcom.metier.Employe;
import com.treluxcom.metier.Personne;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;

public class ExtraHomeImpl extends UnicastRemoteObject implements IExtraHome,Serializable {

    public ExtraHomeImpl() throws RemoteException {
    }

    @Override
    public Set<Boutique> boutiqueList(Personne personne) throws RemoteException {
        return new PersonneHomeImpl().findById(personne.getCodepersonne()).getAdministrateur().getBoutiques();
    }
    @Override
    public List<Personne> EmployeList(String codeboutique) throws RemoteException {
                    
        Session session = HibernateUtil.getSession();
        List<Personne> list = session.createQuery(
                    "Select p from Personne p , Employe b"+
                    "where p.codepersonne  = b.codepersonne"
                    +"and codeboutique=:codeboutique")
                .setParameter("codeboutique", codeboutique)
                .getResultList();
        return list;
        
    }
}
