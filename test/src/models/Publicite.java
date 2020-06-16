/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.Objects;
import javafx.scene.image.ImageView;

/**
 *
 * @author walid
 */
public class Publicite {
    private int id_publicite;
    private String nom;
    private String image;
    private ImageView photo;
    private Date date_debut;
    private Date date_fin;
    private String description;
    private double note;

    public Publicite(int id_publicite, String nom,ImageView photo, Date date_debut, Date date_fin, String description, double note) {
        this.id_publicite = id_publicite;
        this.nom = nom;
        this.photo = photo;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
        this.note = note;
    }

    public Publicite() {
    }
   

    public Publicite(int id_publicite, String nom, String image, Date date_debut,String description, Date date_fin, double note) {
        this.id_publicite = id_publicite;
        this.nom = nom;
        this.image = image;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description=description;
        this.note = note;
        
    }
    public Publicite( String nom, String image, Date date_debut, Date date_fin,String description ,double note) {
        
        this.nom = nom;
        this.image = image;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description=description;
        this.note = note;
        
    }

    public int getId_publicite() {
        return id_publicite;
    }

    public String getNom() {
        return nom;
    }

    public String getImage() {
        return image;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public String getDescription() {
        return description;
    }

    public double getNote() {
        return note;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

   

    public void setId_publicite(int id_publicite) {
        this.id_publicite = id_publicite;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNom(double note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Publicite{" + "id_publicite=" + id_publicite + ", nom=" + nom + ", img=" + photo + ", date_debut=" + date_debut + ", date_fin=" + date_fin + ", description=" + description + ", note=" + note + '}';
    }

  

   

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id_publicite;
        hash = 47 * hash + Objects.hashCode(this.nom);
        hash = 47 * hash + Objects.hashCode(this.image);
        hash = 47 * hash + Objects.hashCode(this.date_debut);
        hash = 47 * hash + Objects.hashCode(this.date_fin);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.note) ^ (Double.doubleToLongBits(this.note) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Publicite other = (Publicite) obj;
        if (this.id_publicite != other.id_publicite) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.date_debut, other.date_debut)) {
            return false;
        }
        if (!Objects.equals(this.date_fin, other.date_fin)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (Double.doubleToLongBits(this.note) != Double.doubleToLongBits(other.note)) {
            return false;
        }
        return true;
    }

   
    
}
