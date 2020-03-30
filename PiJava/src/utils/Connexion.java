/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ASUS
 */
public class Connexion {
    
   private String url="jdbc:mysql://localhost:3306/velo";
   private String login="root";
   private String pwd="";
   private Connection cnx;
   private static Connexion conn;

    private Connexion() {
        try {
           // TODO code application logic here
           cnx=DriverManager.getConnection(url, login, pwd);
           System.out.println("connexion établie");
       } catch (SQLException ex) {
           Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    public static Connexion getInstance(){
        if(conn==null)
            conn=new Connexion();
        return conn;
    }

    public Connection getCnx() {
        return cnx;
    }
}
