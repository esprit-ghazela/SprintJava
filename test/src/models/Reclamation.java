/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ACER
 */
public class Reclamation {
    private int id_reclamation;
    private String reclamation;
    private int categorie;
    private String nom;
    private String prenom;
    private Date date;
    private int etat;

    public Reclamation(int id_reclamation, String reclamation, int categorie, String nom, String prenom, Date date, int etat) {
        this.id_reclamation = id_reclamation;
        this.reclamation = reclamation;
        this.categorie = categorie;
        this.nom = nom;
        this.prenom = prenom;
        this.date = date;
        this.etat = etat;
    }

    public Reclamation(int id_reclamation, String reclamation, int categorie, String nom, String prenom, Date date) {
        this.id_reclamation = id_reclamation;
        this.reclamation = reclamation;
        this.categorie = categorie;
        this.nom = nom;
        this.prenom = prenom;
        this.date = date;
    }

  

    public Reclamation(String reclamation, int categorie, String nom, String prenom, Date date) {
        this.reclamation = reclamation;
        this.categorie = categorie;
        this.nom = nom;
        this.prenom = prenom;
        this.date = date;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public String getReclamation() {
        return reclamation;
    }

    public void setReclamation(String reclamation) {
        this.reclamation = reclamation;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id_reclamation;
        hash = 37 * hash + Objects.hashCode(this.reclamation);
        hash = 37 * hash + this.categorie;
        hash = 37 * hash + Objects.hashCode(this.nom);
        hash = 37 * hash + Objects.hashCode(this.prenom);
        hash = 37 * hash + Objects.hashCode(this.date);
        hash = 37 * hash + this.etat;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reclamation other = (Reclamation) obj;
        if (this.id_reclamation != other.id_reclamation) {
            return false;
        }
        if (this.etat != other.etat) {
            return false;
        }
        if (!Objects.equals(this.reclamation, other.reclamation)) {
            return false;
        }
        if (!Objects.equals(this.categorie, other.categorie)) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id_reclamation=" + id_reclamation + ", reclamation=" + reclamation + ", categorie=" + categorie + ", nom=" + nom + ", prenom=" + prenom + ", date=" + date + ", etat=" + etat + '}';
    }

    
       
    
}
