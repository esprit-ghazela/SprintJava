/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author ASUS
 */
public class Produit {

    private int id;
    private String nom;
    private String description;
    private String marque;
    private int id_categorie;
    private int partenaire;
    private double prix;
    private int quantite;
    private String reference;
    private String image;


    public Produit(int id, String nom, String description, String marque, int id_categorie, int partenaire, double prix, int quantite, String reference, String image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.marque = marque;
        this.id_categorie = id_categorie;
        this.partenaire = partenaire;
        this.prix = prix;
        this.quantite = quantite;
        this.reference = reference;
        this.image = image;
    }

    public Produit(String nom, String description, String marque, int id_categorie, int partenaire, double prix, int quantite, String reference, String image) {
        this.nom = nom;
        this.description = description;
        this.marque = marque;
        this.id_categorie = id_categorie;
        this.partenaire = partenaire;
        this.prix = prix;
        this.quantite = quantite;
        this.reference = reference;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public int getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(int partenaire) {
        this.partenaire = partenaire;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", marque=" + marque + ", id_categorie=" + id_categorie + ", partenaire=" + partenaire + ", prix=" + prix + ", quantite=" + quantite + ", reference=" + reference + ", image=" + image + '}';
    }

}
