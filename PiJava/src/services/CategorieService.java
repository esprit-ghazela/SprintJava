/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.Categorie ;
import utils.Connexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ASUS
 */
public class CategorieService implements IService<Categorie> {
    
    
   private Connexion connexion;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    

    public CategorieService() {
        connexion=Connexion.getInstance();
    }
    
     @Override
    public void insert(Categorie c) {
       
        try {
             String requete="insert into categorie (nom,description) values ('"+c.getNom()+"','"+c.getDescription()+"')";
            ste=connexion.getCnx().createStatement();
            ste.executeUpdate(requete);
            System.out.println("Categorie ajoutée !");
        } 
        catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List<Categorie> getAll() {
        String requete="select * from categorie";
        List<Categorie> list=new ArrayList<>();
        try {
            ste=connexion.getCnx().createStatement();
            rs=ste.executeQuery(requete);
            while(rs.next()){
                list.add(new Categorie(rs.getString("nom"), rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public void delete(Categorie c) {
        try {
            String requete = "DELETE FROM categorie WHERE id=" + c.getId();
            ste=connexion.getCnx().createStatement();
            ste.executeUpdate(requete);
            System.out.println("Catégorie supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void edit(Categorie c) {
        try {
            String requete = "UPDATE categorie SET nom='" + c.getNom() + "',description='" + c.getDescription() + "' WHERE id=" + c.getId();
            ste=connexion.getCnx().createStatement();
            ste.executeUpdate(requete);
            System.out.println("Catégorie modifiée !");
           
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
