/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pijava;


import models.Categorie;
import services.CategorieService ;
import models.Produit;
import services.ProduitService ;

/**
 *
 * @author ASUS
 */
public class PiJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    
       CategorieService sp = new CategorieService();
        //sp.insert(new Categorie("java", "test"));
       // Categorie dc = new Categorie(22,"Ahmed", "Mahmoud");
       // sp.delete(dc);
       // Categorie mc = new Categorie(23,"JavaMod", "TestMod");
       // sp.edit(mc); 
       sp.getAll().forEach(System.out::println);
        
        ProduitService sp1 = new ProduitService() ;
      //  sp.insertProduit(new Produit ("Javanom","Javadescription","Javamarque",3,15,666,66,"Javareference","Javeimage"));
        
        
        sp1.getAllProduit().forEach(System.out::println);
        
    }
    
}
