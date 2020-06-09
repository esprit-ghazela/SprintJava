/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class Panier extends Produit {

//   
    private int qt_panier  ; 

    public Panier( int qt_panier, int id, String reference, String nom, String image, double prix, int quantite) {
        super(id, reference, nom, image, prix, quantite);
     
        this.qt_panier = qt_panier;
    }

    public Panier() {
    }

    public int getQt_panier() {
        return qt_panier;
    }

    public void setQt_panier(int qt_panier) {
        this.qt_panier = qt_panier;
    }
    
    
    

   

   
   
 


 

   

    
    
    
    

}
