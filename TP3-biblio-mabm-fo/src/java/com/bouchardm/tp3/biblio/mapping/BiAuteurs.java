package com.bouchardm.tp3.biblio.mapping;
// Generated 2013-12-16 12:24:15 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * BiAuteurs generated by hbm2java
 */
public class BiAuteurs  implements java.io.Serializable {


     private int auteurId;
     private String nom;
     private String prenom;
     private String pays;
     private String siteInternet;
     private String anneeNaissance;
     private String anneeDeces;
     private Set<BiArticles> biArticleses = new HashSet<BiArticles>(0);

    public BiAuteurs() {
    }

	
    public BiAuteurs(int auteurId, String nom, String prenom, String pays, String anneeNaissance) {
        this.auteurId = auteurId;
        this.nom = nom;
        this.prenom = prenom;
        this.pays = pays;
        this.anneeNaissance = anneeNaissance;
    }
    public BiAuteurs(int auteurId, String nom, String prenom, String pays, String siteInternet, String anneeNaissance, String anneeDeces, Set<BiArticles> biArticleses) {
       this.auteurId = auteurId;
       this.nom = nom;
       this.prenom = prenom;
       this.pays = pays;
       this.siteInternet = siteInternet;
       this.anneeNaissance = anneeNaissance;
       this.anneeDeces = anneeDeces;
       this.biArticleses = biArticleses;
    }
   
    public int getAuteurId() {
        return this.auteurId;
    }
    
    public void setAuteurId(int auteurId) {
        this.auteurId = auteurId;
    }
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return this.prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getPays() {
        return this.pays;
    }
    
    public void setPays(String pays) {
        this.pays = pays;
    }
    public String getSiteInternet() {
        return this.siteInternet;
    }
    
    public void setSiteInternet(String siteInternet) {
        this.siteInternet = siteInternet;
    }
    public String getAnneeNaissance() {
        return this.anneeNaissance;
    }
    
    public void setAnneeNaissance(String anneeNaissance) {
        this.anneeNaissance = anneeNaissance;
    }
    public String getAnneeDeces() {
        return this.anneeDeces;
    }
    
    public void setAnneeDeces(String anneeDeces) {
        this.anneeDeces = anneeDeces;
    }
    public Set<BiArticles> getBiArticleses() {
        return this.biArticleses;
    }
    
    public void setBiArticleses(Set<BiArticles> biArticleses) {
        this.biArticleses = biArticleses;
    }




}


