/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bouchardm.tp3.biblio;

import com.bouchardm.tp3.biblio.mapping.BiArticles;
import com.bouchardm.tp3.biblio.mapping.BiEmprunts;
import com.bouchardm.tp3.biblio.mapping.BiReservations;
import com.bouchardm.tp3.biblio.util.BibliUtil;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Francis Ouellet & Marc-Antoine Bouchard M
 */
@ManagedBean
@SessionScoped
public class sessionBean {
    
    // Type d'utilisateur
    private String userType;
    // Infos de login
    private String username;
    private String password;
    private Boolean logged;
    // Paramètres d'une recherche d'article
    private String rechTitre;
    private String rechAuteur;
    
    // Articles trouvés
    private List<BiArticles> articlesTrouves;
    // Message de retour pour l'utilisateur
    private String message;
    // Utilitaire pour la recherche
    BibliUtil utilitaire;
    // Paramètres d'une recherche de membre
    private String userMembre;

    

    
    //Amende trouvé
    private List<BiEmprunts> amendesTrouves;
    
    /**
     * Creates a new instance of sessionBean
     */
    public sessionBean() {
        this.username = null;
        this.password = null;
        this.logged = false;
        this.rechTitre = null;
        this.rechAuteur = null;
        this.articlesTrouves = null;
        this.message = null;
        this.userType = "invite";
        this.utilitaire = new BibliUtil();
    }
    
    public sessionBean(String userType){
        this.username = null;
        this.password = null;
        this.logged = false;
        this.rechTitre = null;
        this.rechAuteur = null;
        this.articlesTrouves = null;
        this.message = null;
        this.userType = userType;
        this.utilitaire = new BibliUtil();
    }
    
    // Méthodes
    
    public String RechercherArticles(){
        
        if(this.logged){
            articlesTrouves = utilitaire.RechercherArticles(rechTitre, rechAuteur,this.username);
        }else{
            articlesTrouves = utilitaire.RechercherArticles(rechTitre, rechAuteur,null);
        }
        
        return "index";
    }
    
    public void RechercherAmendes(){
       if (userMembre.length() > 0 && this.logged) {
            amendesTrouves = utilitaire.RechercheAmende(userMembre);
       } else
       {
           amendesTrouves = null;
       }
    }
    
    public String Connexion(){
        
        this.userType = utilitaire.Connexion(username, password);
        
        if (this.userType != null && this.userType != "invite"){
            this.setLogged((Boolean) true);
            return "index";
        }
        else{
            this.message = "Une erreur est survenue lors de la connexion";
            return "message";
        }
    }
    
    public String Deconnexion(){
        this.username = null;
        this.password = null;
        this.setLogged((Boolean) false);
        this.userType = "invite";
        return "index";
    }
    
    public String Reserver(String ISBN){
        
        if(this.utilitaire.ReserverArticle(ISBN, this.username, new Date() )){
            this.message = "L'article a été réservé avec succès!";
            return "message";
        }
        this.message = "L'article n'a pas pu être réservé.";
        return "message";
    }
    
    public void PayerAmende(int idEmprunt) {
        this.utilitaire.PayerAmende(idEmprunt);
    }
    
    public List<BiReservations> ObtenirReservations(){
        return this.utilitaire.ObtenirReservations(this.username);
    }
    
    public List<BiEmprunts> ObtenirEmprunts(){
        return this.utilitaire.GetEmpruntsByUser(this.username);
    }
    
    // Accesseurs
    
    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the rechTitre
     */
    public String getRechTitre() {
        return rechTitre;
    }

    /**
     * @param rechTitre the rechTitre to set
     */
    public void setRechTitre(String rechTitre) {
        this.rechTitre = rechTitre;
    }

    /**
     * @return the rechAuteur
     */
    public String getRechAuteur() {
        return rechAuteur;
    }

    /**
     * @param rechAuteur the rechAuteur to set
     */
    public void setRechAuteur(String rechAuteur) {
        this.rechAuteur = rechAuteur;
    }

    /**
     * @return the articlesTrouves
     */
    public List<BiArticles> getArticlesTrouves() {
        return articlesTrouves;
    }

    /**
     * @param articlesTrouves the articlesTrouves to set
     */
    public void setArticlesTrouves(List<BiArticles> articlesTrouves) {
        this.articlesTrouves = articlesTrouves;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the logged
     */
    public Boolean getLogged() {
        return logged;
    }

    /**
     * @param logged the logged to set
     */
    public void setLogged(Boolean logged) {
        this.logged = logged;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    
    public List<BiEmprunts> getAmendeTrouve() {
        return amendesTrouves;
    }

    public void setAmendeTrouve(List<BiEmprunts> amendeTrouve) {
        this.amendesTrouves = amendeTrouve;
    }
    
    public String getUserMembre() {
        return userMembre;
    }

    public void setUserMembre(String userMembre) {
        this.userMembre = userMembre;
    }
}
