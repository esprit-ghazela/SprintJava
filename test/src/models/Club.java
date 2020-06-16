/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author Ahmed laifi
 */
public class Club {

    private int id;
    private String nom;
    private String fondateur;
    private Date date_creation;
    private int solde;

    public Club(int id, String nom, String fondateur, Date date_creation, int solde) {
        this.id = id;
        this.nom = nom;
        this.fondateur = fondateur;
         this.date_creation = date_creation;
        this.solde = solde;
    }

    public Club(String nom, String fondateur, Date date_creation, int solde) {
        this.nom = nom;
        this.fondateur = fondateur;
         this.date_creation = date_creation;
         this.solde = solde;
        
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

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public void setNomC(String nom) {
        this.nom = nom;
    }

    public String getFondateur() {
        return fondateur;
    }

    public void setFondateur(String fondateur) {
        this.fondateur = fondateur;
    }

    @Override
    public String toString() {
        return "club{" + "id=" + id + ", nom=" + nom + "date_creation=" + date_creation + "solde=" + solde + ", fondateur=" + fondateur + '}';
    }
}