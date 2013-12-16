package com.bouchardm.tp3.biblio.mapping;
// Generated 2013-12-14 16:42:46 by Hibernate Tools 3.2.1.GA



/**
 * BiReservations generated by hbm2java
 */
public class BiReservations  implements java.io.Serializable {


     private BiReservationsId id;
     private BiArticles biArticles;
     private BiMembres biMembres;
     private boolean estActif;

    public BiReservations() {
    }

    public BiReservations(BiReservationsId id, BiArticles biArticles, BiMembres biMembres, boolean estActif) {
       this.id = id;
       this.biArticles = biArticles;
       this.biMembres = biMembres;
       this.estActif = estActif;
    }
   
    public BiReservationsId getId() {
        return this.id;
    }
    
    public void setId(BiReservationsId id) {
        this.id = id;
    }
    public BiArticles getBiArticles() {
        return this.biArticles;
    }
    
    public void setBiArticles(BiArticles biArticles) {
        this.biArticles = biArticles;
    }
    public BiMembres getBiMembres() {
        return this.biMembres;
    }
    
    public void setBiMembres(BiMembres biMembres) {
        this.biMembres = biMembres;
    }
    public boolean isEstActif() {
        return this.estActif;
    }
    
    public void setEstActif(boolean estActif) {
        this.estActif = estActif;
    }




}


