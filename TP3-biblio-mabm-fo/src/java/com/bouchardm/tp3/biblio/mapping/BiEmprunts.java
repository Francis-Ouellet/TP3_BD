package com.bouchardm.tp3.biblio.mapping;
// Generated 2013-12-16 12:24:15 by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * BiEmprunts generated by hbm2java
 */
public class BiEmprunts  implements java.io.Serializable {


     private int empruntId;
     private BiCopiesarticles biCopiesarticles;
     private BiModespaiements biModespaiements;
     private BiMembres biMembres;
     private Date dateEmprunt;
     private Date dateRetourPrevue;
     private Date dateRetour;
     private Integer nbJoursDeRetard;
     private BigDecimal amendeParJour;
     private char indicateurPerte;
     private BigDecimal prixUnitaire;
     private BigDecimal totalAmende;

   
     private Boolean amende;
     private Boolean retourner;
     private Set<BiCommentaires> biCommentaireses = new HashSet<BiCommentaires>(0);

    public BiEmprunts() {
    }

	
    public BiEmprunts(int empruntId, BiCopiesarticles biCopiesarticles, BiMembres biMembres, Date dateEmprunt, Date dateRetourPrevue, BigDecimal amendeParJour, char indicateurPerte, BigDecimal prixUnitaire) {
        this.empruntId = empruntId;
        this.biCopiesarticles = biCopiesarticles;
        this.biMembres = biMembres;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.amendeParJour = amendeParJour;
        
        this.indicateurPerte = indicateurPerte;
        this.prixUnitaire = prixUnitaire;
        
        
    }
    public BiEmprunts(int empruntId, BiCopiesarticles biCopiesarticles, BiModespaiements biModespaiements, BiMembres biMembres, Date dateEmprunt, Date dateRetourPrevue, Date dateRetour, Integer nbJoursDeRetard, BigDecimal amendeParJour, char indicateurPerte, BigDecimal prixUnitaire, BigDecimal totalAmende, Set<BiCommentaires> biCommentaireses) {
       this.empruntId = empruntId;
       this.biCopiesarticles = biCopiesarticles;
       this.biModespaiements = biModespaiements;
       this.biMembres = biMembres;
       this.dateEmprunt = dateEmprunt;
       this.dateRetourPrevue = dateRetourPrevue;
       this.dateRetour = dateRetour;
       this.nbJoursDeRetard = nbJoursDeRetard;
       this.amendeParJour = amendeParJour;
       this.indicateurPerte = indicateurPerte;
       this.prixUnitaire = prixUnitaire;
       this.totalAmende = totalAmende;
       this.biCommentaireses = biCommentaireses;
    }
    
     public Boolean getAmende() {
        return amende;
    }

    public void setAmende(Boolean amende) {
        this.amende = amende;
    }

    public Boolean getRetourner() {
        return retourner;
    }

    public void setRetourner(Boolean retourner) {
        this.retourner = retourner;
    }
   
    public int getEmpruntId() {
        return this.empruntId;
    }
    
    public void setEmpruntId(int empruntId) {
        this.empruntId = empruntId;
    }
    public BiCopiesarticles getBiCopiesarticles() {
        return this.biCopiesarticles;
    }
    
    public void setBiCopiesarticles(BiCopiesarticles biCopiesarticles) {
        this.biCopiesarticles = biCopiesarticles;
    }
    public BiModespaiements getBiModespaiements() {
        return this.biModespaiements;
    }
    
    public void setBiModespaiements(BiModespaiements biModespaiements) {
        this.biModespaiements = biModespaiements;
    }
    public BiMembres getBiMembres() {
        return this.biMembres;
    }
    
    public void setBiMembres(BiMembres biMembres) {
        this.biMembres = biMembres;
    }
    public Date getDateEmprunt() {
        return this.dateEmprunt;
    }
    
    public void setDateEmprunt(Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }
    public Date getDateRetourPrevue() {
        return this.dateRetourPrevue;
    }
    
    public void setDateRetourPrevue(Date dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }
    public Date getDateRetour() {
        return this.dateRetour;
    }
    
    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }
    public Integer getNbJoursDeRetard() {
        return this.nbJoursDeRetard;
    }
    
    public void setNbJoursDeRetard(Integer nbJoursDeRetard) {
        this.nbJoursDeRetard = nbJoursDeRetard;
    }
    public BigDecimal getAmendeParJour() {
        return this.amendeParJour;
    }
    
    public void setAmendeParJour(BigDecimal amendeParJour) {
        this.amendeParJour = amendeParJour;
    }
    public char getIndicateurPerte() {
        return this.indicateurPerte;
    }
    
    public void setIndicateurPerte(char indicateurPerte) {
        this.indicateurPerte = indicateurPerte;
    }
    public BigDecimal getPrixUnitaire() {
        return this.prixUnitaire;
    }
    
    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    public BigDecimal getTotalAmende() {
        return this.totalAmende;
    }
    
    public void setTotalAmende(BigDecimal totalAmende) {
        this.totalAmende = totalAmende;
    }
    public Set<BiCommentaires> getBiCommentaireses() {
        return this.biCommentaireses;
    }
    
    public void setBiCommentaireses(Set<BiCommentaires> biCommentaireses) {
        this.biCommentaireses = biCommentaireses;
    }




}


