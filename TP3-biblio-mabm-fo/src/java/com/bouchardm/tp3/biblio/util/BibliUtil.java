/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bouchardm.tp3.biblio.util;

import com.bouchardm.tp3.biblio.mapping.BiArticles;
import com.bouchardm.tp3.biblio.mapping.BiAuteurs;
import com.bouchardm.tp3.biblio.mapping.BiEmprunts;
import com.bouchardm.tp3.biblio.mapping.BiMembres;
import com.bouchardm.tp3.biblio.mapping.BiReservations;
import com.bouchardm.tp3.biblio.mapping.BiReservationsId;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Francis Ouellet & Marc-Antoine Bouchard M
 */
public class BibliUtil {
    
    Session session = null;
    
    public BibliUtil(){
        this.session = HibernateUtil.getSessionFactory().openSession();
    }
    
    public List<BiArticles> RechercherArticles(String s_titre, String s_auteur, String username){
    
        List<BiArticles> lstArticles = new ArrayList<BiArticles>(), lstArticlesTous = null;
        BiArticles article;
        BiAuteurs auteur;
        
        String excludeReserved = "";
        
        if(username != null){
            excludeReserved = "WHERE article.isbn NOT IN "
                    + "(SELECT res.biArticles.isbn FROM BiReservations res WHERE res.biMembres.login = '"
                    + username + "')";
        }
        try{
            // Récupère tous les articles non réservés
            session.beginTransaction();
            Query req = session.createQuery("FROM BiArticles article " + excludeReserved + " ORDER BY article.titre ");
            lstArticlesTous = (List<BiArticles>)req.list();
            
            for (Iterator<BiArticles> unArticle = lstArticlesTous.iterator(); unArticle.hasNext();){
                article = unArticle.next();
                
                if(s_titre != null && !s_titre.equals("") && article.getTitre().toLowerCase().contains(s_titre.toLowerCase())){
                    lstArticles.add(article);
                    
                }
                else{
                    if (s_auteur != null && !s_auteur.equals("")){
                        Iterator<BiAuteurs> unAuteur = article.getBiAuteurses().iterator();
                        Boolean found = false;
                        while( unAuteur.hasNext() && !found ){
                            auteur = unAuteur.next();
                            if (auteur.getNom().toLowerCase().contains(s_auteur.toLowerCase()) 
                                    || auteur.getPrenom().toLowerCase().contains(s_auteur.toLowerCase())){
                                lstArticles.add(article);
                                found = true;
                            }
                        }
                    }
                }
            }
            
            return lstArticles;
            
        }
        catch(Exception e){
            return null;
        }
    }
    
    public List<BiEmprunts> RechercheAmende(String userMembre){
           
        List<BiEmprunts> lesEmprunts = null;
            
        try {
            // Récupère tous les articles non réservés
            Transaction tx = session.beginTransaction();
            Query req = session.createQuery("FROM BiEmprunts emprunt WHERE emprunt.biMembres.login = '" + userMembre + "'");
            lesEmprunts = (List<BiEmprunts>)req.list();
            
            return lesEmprunts;
        } catch (Exception e) {
            return null;
        }
        
    }
    
    public String Connexion(String username, String password){
        
        try{
            session.beginTransaction();
            Query req = session.createQuery("SELECT membre.motPasse, type.typeDescFr FROM BiMembres membre, BiTypesmembres type WHERE membre.login = '"
                    + username + "' AND type.typeMembre =  membre.biTypesmembres.typeMembre");
            Object[] resSQL = (Object[])req.uniqueResult();
            
            String userPassword = null;
            String userType = "invite";
            
            userPassword = (String)resSQL[0];
             
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5 = new byte[64];
            md.update(password.getBytes("UTF-8"),0,password.length());        
            md5 = md.digest();
            
            if(convertedToHex(md5).equals(userPassword)){
            
                userType = (String)resSQL[1];
            
                if(userType.equals("Employé")){
                    userType = "employe";
                }
                else
                {
                    userType = "membre";
                }
    
                return userType;
            }
            
            return userType;
            
        }catch(Exception e){
            return null;
        }
    }

    public Boolean ReserverArticle(String ISBN, String membreLogin, Date dateReservation){
        
        // Trouver le membre et l'article
        BiArticles article = this.GetArticleByID(ISBN);
        BiMembres membre = this.GetMembreByUsername(membreLogin);
        
        BiReservationsId resId = new BiReservationsId(membre.getNoMembre(),article.getIsbn(),dateReservation);
        
        BiReservations reservation = new BiReservations();
        reservation.setBiArticles(article);
        reservation.setBiMembres(membre);
        reservation.setId(resId);
        reservation.setEstActif(true);
        
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(reservation);
            tx.commit();
        }
        catch(Exception e){
            tx.rollback();
            return false;
        }
        
        return true;
    }
    
    public List<BiReservations> ObtenirReservations(String username){
        
        List<BiReservations> reservations = null;
        
        try{
            // Trouve les réservations du membre
            session.beginTransaction();
            Query req = session.createQuery("FROM BiReservations res WHERE res.biMembres.login = '" + username + "'");
            reservations = (List<BiReservations>)req.list();
            
            BiReservations reservation;
            List<BiReservations> autresReservations;
            // Pour chaque réservation du membre
            for(Iterator<BiReservations> resMembre = reservations.iterator(); resMembre.hasNext();){
                reservation = resMembre.next();
                
                // Trouve les autres réservations pour cet article
                session.beginTransaction();
                req = session.createQuery("FROM BiReservations res WHERE res.biArticles.isbn = '" 
                        + reservation.getBiArticles().getIsbn() + "'");
                autresReservations = (List<BiReservations>)req.list();
                
                int nbResPrioritaires = 1;
                
                BiReservations autreReservation;
                // Pour chacune des autres réservations de cet article
                for(Iterator<BiReservations> autresRes = autresReservations.iterator(); autresRes.hasNext(); ){
                    autreReservation = autresRes.next();
                    
                    if(reservation.getId().getDateReservation().after(autreReservation.getId().getDateReservation())){
                        nbResPrioritaires++;
                    }
                    
                }
                
                reservations.get(reservations.indexOf(reservation)).setPosition(nbResPrioritaires);
                
            }
            
        }catch(Exception e){
            e.getMessage();
        }
        
        return reservations;
    }
    
    
    /*
    
    * Fonctions Utilitaires Privées
     
    */
    
    private BiArticles GetArticleByID (String ISBN) {
        BiArticles article = null;
        
        try{
            Transaction tx = session.beginTransaction();
            Query req = session.createQuery("FROM BiArticles article WHERE article.isbn = '" + ISBN + "'");
            article = (BiArticles)req.uniqueResult();
            
        }catch(Exception e){}
        
        return article;
    }
    
    private BiMembres GetMembreByUsername (String username) {
        BiMembres membre = null;
        
        try{
            Transaction tx = session.beginTransaction();
            Query req = session.createQuery("FROM BiMembres membre WHERE membre.login = '" + username + "'");
            membre = (BiMembres)req.uniqueResult();
            
        }catch(Exception e){}
        
        return membre;
    }
    
    private String convertedToHex(byte[] data) 
    { 
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < data.length; i++) 
        { 
            int halfOfByte = (data[i] >>> 4) & 0x0F;
            int twoHalfBytes = 0;
            
            do 
            { 
                if ((0 <= halfOfByte) && (halfOfByte <= 9)) 
                {
                    buf.append( (char) ('0' + halfOfByte) );
                }
                
                else 
                {
                    buf.append( (char) ('a' + (halfOfByte - 10)) );
                }

                halfOfByte = data[i] & 0x0F;

            } while(twoHalfBytes++ < 1);
        } 
        return buf.toString();
    } 
    
    
}
