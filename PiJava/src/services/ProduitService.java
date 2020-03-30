/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Categorie;
import models.Produit;
import utils.Connexion;

/**
 *
 * @author ASUS
 */
public class ProduitService implements IServiceProduit<Produit> {
    
    private Connexion connexion;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
   
    public ProduitService() {
        connexion=Connexion.getInstance();
    }
    
     @Override
    public void insertProduit(Produit p) { 
      
        try {
             String requete="insert into produit (nom,description,reference,image,prix,quantite,id_categorie,marque,partenaire) "
                     + "values ('"+p.getNom()+"','"+p.getDescription()+"','"+p.getReference()+"','"+p.getImage()+"','"+p.getPrix()+
                     "','"+p.getQuantite()+"','"+p.getId_categorie()+"','"+p.getMarque()+"','"+p.getPartenaire()+"')" ;
             
            ste=connexion.getCnx().createStatement();
            ste.executeUpdate(requete);
            System.out.println("Produit ajoutée !");
        } 
        catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
   
     public List<Produit> getAllProduit() {
        String requete="select * from produit";
        List<Produit> list=new ArrayList<>();
        try {
            ste=connexion.getCnx().createStatement();
            rs=ste.executeQuery(requete);
            while(rs.next()){
                list.add(new Produit(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("marque"),
                        rs.getInt("id_categorie") ,
                        rs.getInt("partenaire") ,
                        rs.getDouble("prix"),
                        rs.getInt("quantite"),
                        rs.getString("reference"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

   

 
   
    
    
}
