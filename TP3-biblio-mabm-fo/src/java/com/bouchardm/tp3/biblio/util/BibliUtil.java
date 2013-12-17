/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bouchardm.tp3.biblio.util;

import com.bouchardm.tp3.biblio.mapping.BiArticles;
import com.bouchardm.tp3.biblio.mapping.BiAuteurs;
import com.bouchardm.tp3.biblio.mapping.BiCopiesarticles;
import com.bouchardm.tp3.biblio.mapping.BiEmprunts;
import com.bouchardm.tp3.biblio.mapping.BiEtatarticle;
import com.bouchardm.tp3.biblio.mapping.BiMembres;
import com.bouchardm.tp3.biblio.mapping.BiReservations;
import com.bouchardm.tp3.biblio.mapping.BiReservationsId;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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
            Query req = session.createQuery("FROM BiEmprunts emprunt WHERE emprunt.dateRetour IS NOT NULL AND emprunt.totalAmende > 0 AND emprunt.biMembres.login = '" + userMembre + "'");
            lesEmprunts = (List<BiEmprunts>)req.list();
            
            for (Iterator<BiEmprunts> unEmprunt = lesEmprunts.iterator(); unEmprunt.hasNext();){
                BiEmprunts emprunt = unEmprunt.next();
                
               if(emprunt.getTotalAmende() == null) {
                   emprunt.setTotalAmende(BigDecimal.ZERO);
                   emprunt.setAmende(false);
               } else {
                   emprunt.setAmende(true);
               }
                
            }
            
            
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
        
        // Chercher toutes les copies d'articles pour trouver une copie à réserver
        BiCopiesarticles copieAReserver = this.FindCopieAReserver(ISBN);
        
        BiEtatarticle sdf = new BiEtatarticle();
        sdf.setId(3);
        if(copieAReserver != null ){
            copieAReserver.setBiEtatarticle(sdf);
        }
        
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
        tx = null;
        try{
            tx = session.beginTransaction();
            session.update(copieAReserver);
            tx.commit();
        }catch(Exception e){
            tx.rollback();
            return false;
        }
        
        return true;
    }
    
    public Boolean PayerAmende(int id) {
        BiEmprunts emprunt = this.getEmpruntById(id);
        
        BigDecimal zero = new BigDecimal(0);
        
        emprunt.setTotalAmende(zero);
        
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(emprunt);
            tx.commit();
        }
        catch(Exception e){
            tx.rollback();
            return false;
        }
        
        
        return true;
    }
    
    public Boolean Retourner(int idEmprunt) {
        BiEmprunts emprunt = this.getEmpruntById(idEmprunt);
        
        emprunt.setDateRetour(new Date());
        
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(emprunt);
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
            
        }catch(Exception e){}
        
        return reservations;
    }
    
    public List<BiEmprunts> GetEmpruntsByUser(String username){
        List<BiEmprunts> emprunts = null;
        
        try{
            session.beginTransaction();
            Query req = session.createQuery("FROM BiEmprunts emprunt WHERE emprunt.biMembres.login = '" + username + "'");
            emprunts = (List<BiEmprunts>)req.list();
            
        }catch(Exception e){}
        
        return emprunts;
    }
    
    public Boolean EntrerLocation(String username, String ISBN, Date date){
        
        BiMembres membre = this.GetMembreByUsername(username);
        BiArticles article = this.GetArticleByID(ISBN);
        
        BiCopiesarticles copieArticle = this.FindCopieArticleForUser(membre,article);
        
        if(copieArticle != null){
            // Nouvel emprunt à écrire dans la BD
            BiEmprunts emprunt = new BiEmprunts();
        
            emprunt.setBiMembres(membre);
            emprunt.setBiCopiesarticles(copieArticle);
            emprunt.setDateEmprunt(date);
            emprunt.setDateRetourPrevue(this.addDays(date, membre.getBiTypesmembres().getNbJoursSurEmprunt()));
            emprunt.setAmendeParJour(article.getBiTypearticles().getAmendeParJour());
            emprunt.setIndicateurPerte('0');
            emprunt.setPrixUnitaire(article.getPrixUnitaire());
            emprunt.setEmpruntId(this.GetNextEmpruntID());

            Transaction tx = null;
            
            try{
                tx = session.beginTransaction();
                session.save(emprunt);
                tx.commit();
                
                return true;
                
            }catch(Exception e){
                tx.rollback();
                String test = e.getMessage();
                test = test;
                return false;
                
            }
            
        }
        
        return false;
    }
    
    
    /*
    
    * Fonctions Utilitaires Privées
     
    */
    
    private int GetNextEmpruntID(){
        int nombre = -1;
        
        try{
            session.beginTransaction();
            Query req = session.createQuery("SELECT emprunt.empruntId FROM BiEmprunts emprunt");
            List<Integer> nombres = (List<Integer>)req.list();
            
            nombre = nombres.size();
        
        }catch(Exception e){}
        
        return nombre + 1;
    }
    
    private BiCopiesarticles FindCopieAReserver(String ISBN){
        BiCopiesarticles copie = null;
        
        try{
            session.beginTransaction();
            Query req = session.createQuery("FROM BiCopiesarticles copie WHERE copie.biArticles.isbn = '"
                    + ISBN + "' AND copie.biEtatarticle.valeur = 'DISPONIBLE'");
            copie = (BiCopiesarticles)req.uniqueResult();
        }catch(Exception e){}
        
        return copie;
    }
    
    
    
    private BiCopiesarticles FindCopieArticleForUser(BiMembres membre, BiArticles article){

        BiCopiesarticles articleALouer = null;
        String s_req = null;
        
        // Vérifier si le membre a réservé l'article
        BiReservations reservationMembre = null;
        Boolean reserved = false;
        Iterator<BiReservations> uneReservation = membre.getBiReservationses().iterator();
        while ( uneReservation.hasNext() && !reserved){
            reservationMembre = uneReservation.next();
            
            if (reservationMembre.getBiArticles().getIsbn().equals(article.getIsbn())){
                reserved = true;
            }
            
        }
        
        // Si le membre a réservé l'article, vérifier la position du membre dans la file d'attente et déterminer s'il peut louer l'article
        if(reserved){
            int nbResPrioritaires = 0;
            BiReservations autreReservation;
            // Pour chacune des autres réservations
            for (uneReservation = article.getBiReservationses().iterator(); uneReservation.hasNext();){
                autreReservation = uneReservation.next();

                if(reservationMembre.getId().getDateReservation().after(autreReservation.getId().getDateReservation())){
                    nbResPrioritaires++;
                }
            }
            
            if (nbResPrioritaires < article.getBiReservationses().size()){
                s_req = "FROM BiCopiesarticles copie WHERE copie.biArticles.isbn = '"
                        + article.getIsbn() + "' AND copie.biEtatarticle.valeur = 'RÉSERVÉ'";
            }
        }
        // Si le membre n'a pas réservé l'article, chercher un article parmi les disponibles
        else{
            s_req = "FROM BiCopiesarticles copie WHERE copie.biArticles.isbn = '"
                    + article.getIsbn() + "' AND copie.biEtatarticle.valeur = 'DISPONIBLE'";
        }
        
        if (s_req != null){
            try{
                List<BiCopiesarticles> lesArticlesDispo;
                
                session.beginTransaction();
                Query req = session.createQuery(s_req);
                lesArticlesDispo = (List<BiCopiesarticles>)req.list();
                
                articleALouer = lesArticlesDispo.iterator().next();
                
            }catch(Exception e){}
        }
        
        return articleALouer;
    }
    
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
    
    private BiEmprunts getEmpruntById (int id) {
        BiEmprunts emprunt = null;
        
        try{
            Transaction tx = session.beginTransaction();
            Query req = session.createQuery("FROM BiEmprunts emprunt WHERE emprunt.empruntId = " + id);
            emprunt = (BiEmprunts)req.uniqueResult();
            
        }catch(Exception e){}
        
        return emprunt;
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

    
    
    private static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
