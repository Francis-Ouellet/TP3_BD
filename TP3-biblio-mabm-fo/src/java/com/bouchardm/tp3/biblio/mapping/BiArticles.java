package com.bouchardm.tp3.biblio.mapping;
// Generated 2013-12-16 12:24:15 by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * BiArticles generated by hbm2java
 */
public class BiArticles  implements java.io.Serializable {


     private String isbn;
     private BiTypearticles biTypearticles;
     private String titre;
     private String resume;
     private BigDecimal prixUnitaire;
     private char indicateurEnCommande;
     private short quantiteEnCommande;
     private Date dateParution;
     private int maisonEditionId;
     private String langue;
     private byte ageMin;
     private Set<BiCopiesarticles> biCopiesarticleses = new HashSet<BiCopiesarticles>(0);
     private Set<BiAuteurs> biAuteurses = new HashSet<BiAuteurs>(0);
     private Set<BiReservations> biReservationses = new HashSet<BiReservations>(0);

    public BiArticles() {
    }

	
    public BiArticles(String isbn, BiTypearticles biTypearticles, String titre, String resume, BigDecimal prixUnitaire, char indicateurEnCommande, short quantiteEnCommande, Date dateParution, int maisonEditionId, String langue, byte ageMin) {
        this.isbn = isbn;
        this.biTypearticles = biTypearticles;
        this.titre = titre;
        this.resume = resume;
        this.prixUnitaire = prixUnitaire;
        this.indicateurEnCommande = indicateurEnCommande;
        this.quantiteEnCommande = quantiteEnCommande;
        this.dateParution = dateParution;
        this.maisonEditionId = maisonEditionId;
        this.langue = langue;
        this.ageMin = ageMin;
    }
    public BiArticles(String isbn, BiTypearticles biTypearticles, String titre, String resume, BigDecimal prixUnitaire, char indicateurEnCommande, short quantiteEnCommande, Date dateParution, int maisonEditionId, String langue, byte ageMin, Set<BiCopiesarticles> biCopiesarticleses, Set<BiAuteurs> biAuteurses, Set<BiReservations> biReservationses) {
       this.isbn = isbn;
       this.biTypearticles = biTypearticles;
       this.titre = titre;
       this.resume = resume;
       this.prixUnitaire = prixUnitaire;
       this.indicateurEnCommande = indicateurEnCommande;
       this.quantiteEnCommande = quantiteEnCommande;
       this.dateParution = dateParution;
       this.maisonEditionId = maisonEditionId;
       this.langue = langue;
       this.ageMin = ageMin;
       this.biCopiesarticleses = biCopiesarticleses;
       this.biAuteurses = biAuteurses;
       this.biReservationses = biReservationses;
    }
   
    public String getIsbn() {
        return this.isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public BiTypearticles getBiTypearticles() {
        return this.biTypearticles;
    }
    
    public void setBiTypearticles(BiTypearticles biTypearticles) {
        this.biTypearticles = biTypearticles;
    }
    public String getTitre() {
        return this.titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getResume() {
        return this.resume;
    }
    
    public void setResume(String resume) {
        this.resume = resume;
    }
    public BigDecimal getPrixUnitaire() {
        return this.prixUnitaire;
    }
    
    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    public char getIndicateurEnCommande() {
        return this.indicateurEnCommande;
    }
    
    public void setIndicateurEnCommande(char indicateurEnCommande) {
        this.indicateurEnCommande = indicateurEnCommande;
    }
    public short getQuantiteEnCommande() {
        return this.quantiteEnCommande;
    }
    
    public void setQuantiteEnCommande(short quantiteEnCommande) {
        this.quantiteEnCommande = quantiteEnCommande;
    }
    public Date getDateParution() {
        return this.dateParution;
    }
    
    public void setDateParution(Date dateParution) {
        this.dateParution = dateParution;
    }
    public int getMaisonEditionId() {
        return this.maisonEditionId;
    }
    
    public void setMaisonEditionId(int maisonEditionId) {
        this.maisonEditionId = maisonEditionId;
    }
    public String getLangue() {
        return this.langue;
    }
    
    public void setLangue(String langue) {
        this.langue = langue;
    }
    public byte getAgeMin() {
        return this.ageMin;
    }
    
    public void setAgeMin(byte ageMin) {
        this.ageMin = ageMin;
    }
    public Set<BiCopiesarticles> getBiCopiesarticleses() {
        return this.biCopiesarticleses;
    }
    
    public void setBiCopiesarticleses(Set<BiCopiesarticles> biCopiesarticleses) {
        this.biCopiesarticleses = biCopiesarticleses;
    }
    public Set<BiAuteurs> getBiAuteurses() {
        return this.biAuteurses;
    }
    
    public void setBiAuteurses(Set<BiAuteurs> biAuteurses) {
        this.biAuteurses = biAuteurses;
    }
    public Set<BiReservations> getBiReservationses() {
        return this.biReservationses;
    }
    
    public void setBiReservationses(Set<BiReservations> biReservationses) {
        this.biReservationses = biReservationses;
    }




}


