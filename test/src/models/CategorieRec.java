/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Objects;

/**
 *
 * @author ACER
 */
public class CategorieRec {
    private int id_categorie;
    private String type;
    private String nom;
    private String description;

    public CategorieRec(int id_categorie, String type, String nom, String description) {
        this.id_categorie = id_categorie;
        this.type = type;
        this.nom = nom;
        this.description = description;
    }

    public CategorieRec(String type, String nom, String description) {
        this.type = type;
        this.nom = nom;
        this.description = description;
    }

    public CategorieRec() {
       
    }

    
    
    
    public int getIdcategorie()
    {
        return id_categorie;
    }
    public String getType()
    {
        return type;
    }
    public String getNom()
    {
        return nom;
    }
    public String getDescription()
    {
        return description;
    }
    public void setIdcategorie(int id_categorie)
    {
        this.id_categorie=id_categorie;
    }
    public void setType(String type)
    {
        this.type=type;
    }
    public void setNom(String nom)
    {
        this.nom=nom;         
    }
    public void setDescription(String description)
    {
        this.description=description;
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.id_categorie;
        hash = 47 * hash + Objects.hashCode(this.type);
        hash = 47 * hash + Objects.hashCode(this.nom);
        hash = 47 * hash + Objects.hashCode(this.description);
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
        final CategorieRec other = (CategorieRec) obj;
        if (this.id_categorie!= other.id_categorie) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Categorie{" + "id_categorie=" + id_categorie + ", type=" + type + ", nom=" + nom + ", description=" + description + '}';
    }
    
    
}
