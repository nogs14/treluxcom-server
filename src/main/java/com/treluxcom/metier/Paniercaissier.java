package com.treluxcom.metier;
// Generated 9 juin 2018 12:35:35 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Paniercaissier generated by hbm2java
 */
@Entity
@Table(name="paniercaissier"
    ,schema="public"
)
public class Paniercaissier  implements java.io.Serializable {


     private String codepanier;
     private Caissier caissier;
     private Panier panier;
     private String facture;

    public Paniercaissier() {
    }

	
    public Paniercaissier(Caissier caissier, Panier panier) {
        this.caissier = caissier;
        this.panier = panier;
    }
    public Paniercaissier(Caissier caissier, Panier panier, String facture) {
       this.caissier = caissier;
       this.panier = panier;
       this.facture = facture;
    }
   
     @GenericGenerator(name="generator", strategy="foreign", parameters=@Parameter(name="property", value="panier"))@Id @GeneratedValue(generator="generator")

    
    @Column(name="codepanier", nullable=false, length=50)
    public String getCodepanier() {
        return this.codepanier;
    }
    
    public void setCodepanier(String codepanier) {
        this.codepanier = codepanier;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="codecaissier", nullable=false)
    public Caissier getCaissier() {
        return this.caissier;
    }
    
    public void setCaissier(Caissier caissier) {
        this.caissier = caissier;
    }

@OneToOne(fetch=FetchType.LAZY)@PrimaryKeyJoinColumn
    public Panier getPanier() {
        return this.panier;
    }
    
    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    
    @Column(name="facture", length=50)
    public String getFacture() {
        return this.facture;
    }
    
    public void setFacture(String facture) {
        this.facture = facture;
    }




}

