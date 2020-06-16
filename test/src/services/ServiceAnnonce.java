/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.annonce;
import utils.DataSource;
/**
 *
 * @author anest
 */
public class ServiceAnnonce   {
     private Connection con;
    private Statement ste;
    private PreparedStatement pre;
   
    
    public ServiceAnnonce(){
    con = DataSource.getInstance().getCnx();
    }

  
    public void ajouter(annonce t) throws SQLException {
     if(controlede_saisie(t)&&(chercher_ajout(t)||!chercher(t.getId_annonce())))
     
    pre=con.prepareStatement("INSERT INTO `velo`.`annonce` ( `nom`, `description`, `date`,  `image`) VALUES ( ?, ?, ?, ?);");
    pre.setString(1, t.getNom());
    pre.setString(2, t.getDescription());
    Date sDate = new java.sql.Date(t.getDate().getTime());
    System.out.println(sDate);
    pre.setDate(3, sDate);
    pre.setString(4, t.getImage());
    pre.executeUpdate();
    
    }

 
    public boolean delete(int id) throws SQLException {
        
        if(chercher(id)){
            System.out.println("exist");
        pre=con.prepareStatement("delete from `velo`.`annonce` where id  = (?);");
        pre.setInt(1,id);
            System.out.println(pre.execute());
       return true;}
        else 
        {System.out.println("nexiste pas");
            return false;}
        
    }


    public int update(annonce t,int id) throws SQLException {
        if(chercher(id)){
        Date sDate = new java.sql.Date(t.getDate().getTime());

        pre=con.prepareStatement("UPDATE annonce SET nom=?, image = ? , date = ?  , description = ?  WHERE id = ?");
         
    pre.setString(1, t.getNom());
    pre.setString(2,t.getImage());
    pre.setDate(3,sDate);
    pre.setString(4, t.getDescription());
    pre.setInt(5,id);
    pre.executeUpdate();
    return 1;}
        return 0;
    }


    public List<annonce> readAll() throws SQLException {
        String req="select * from annonce  ";
        List<annonce> list = new ArrayList<>();
        
        try {
            ste=con.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while(rs.next()){
                
               java.util.Date d1 = new java.util.Date(rs.getDate(4).getTime());
       
                list.add(new annonce(rs.getInt(1),rs.getString(2),d1, rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePublicite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list  ;
    }
        public List<annonce> readAll_withImage() throws SQLException {
        String req="select * from annonce  ";
        List<annonce> list = new ArrayList<>();
        
        try {
            ste=con.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while(rs.next()){
                
               java.util.Date d1 = new java.util.Date(rs.getDate(4).getTime());
           
               
             
                ImageView v = new ImageView();
               
                v.setImage(new Image(rs.getString(5)));
                v.setFitWidth(100);
                v.setFitHeight(100);
               
                //ystem.out.println(v.getImage().toString());
                annonce p=new annonce(rs.getInt(1), rs.getString(2),null, d1,rs.getString(3));
                annonce p1=new annonce(rs.getInt(1), rs.getString(2),null, d1,rs.getString(3));
                p.setPhoto(v);
                p.setImage(rs.getString(5));
                list.add(p);
             
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePublicite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list  ;
    }
    
    public List<annonce>trieParId() throws SQLException{
            String req="select * from annonce  ORDER BY id ";
        List<annonce> list = new ArrayList<>();
        
        try {
            ste=con.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while(rs.next()){
                
                java.util.Date d1 = new java.util.Date(rs.getDate(4).getTime());
           
               
             
                ImageView v = new ImageView();
               
                v.setImage(new Image(rs.getString(5)));
                v.setFitWidth(100);
                v.setFitHeight(100);
               
                //ystem.out.println(v.getImage().toString());
                annonce p=new annonce(rs.getInt(1), rs.getString(2),null, d1,rs.getString(3));
                annonce p1=new annonce(rs.getInt(1), rs.getString(2),null, d1,rs.getString(3));
                p.setPhoto(v);
                p.setImage(rs.getString(5));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePublicite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list  ;
    
    }
     public List<annonce>trieParNom() throws SQLException{
            String req="select * from annonce  ORDER BY nom ";
        List<annonce> list = new ArrayList<>();
        
        try {
            ste=con.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while(rs.next()){
                
                   java.util.Date d1 = new java.util.Date(rs.getDate(4).getTime());
           
               
             
                ImageView v = new ImageView();
               
                v.setImage(new Image(rs.getString(5)));
                v.setFitWidth(100);
                v.setFitHeight(100);
               
                //ystem.out.println(v.getImage().toString());
                annonce p=new annonce(rs.getInt(1), rs.getString(2),null, d1,rs.getString(3));
                annonce p1=new annonce(rs.getInt(1), rs.getString(2),null, d1,rs.getString(3));
                p.setPhoto(v);
                p.setImage(rs.getString(5));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePublicite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list  ;
    
    }

        public List<annonce>trieParDate() throws SQLException{
            String req="select * from annonce  ORDER BY date DESC ";
        List<annonce> list = new ArrayList<>();
        
        try {
            ste=con.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while(rs.next()){
                  java.util.Date d1 = new java.util.Date(rs.getDate(4).getTime());
           
               
             
                ImageView v = new ImageView();
               
                v.setImage(new Image(rs.getString(5)));
                v.setFitWidth(100);
                v.setFitHeight(100);
               
                //ystem.out.println(v.getImage().toString());
                annonce p=new annonce(rs.getInt(1), rs.getString(2),null, d1,rs.getString(3));
                annonce p1=new annonce(rs.getInt(1), rs.getString(2),null, d1,rs.getString(3));
                p.setPhoto(v);
                p.setImage(rs.getString(5));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceAnnonce.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list  ;
    
    }
    

 
    public boolean chercher(int id) throws SQLException {
        String req="select * from annonce";
        List<Integer> list = new ArrayList<>();
        
        try {
            ste=con.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while(rs.next()){
                list.add(rs.getInt(1));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceAnnonce.class.getName()).log(Level.SEVERE, null, ex);
        }
        list.forEach(System.out::println);
        return list.contains(id);
    }


    public boolean chercher_ajout(annonce t) throws SQLException {
        System.out.println(t.getId_annonce());
        return (t.getId_annonce()==0);
    }
    
    public boolean controlede_saisie(annonce t) throws SQLException{
        System.out.println(t.getNom());
         System.out.println(t.getImage());
         System.out.println(t.getDescription());
         System.out.println(t.getDate());
    if(t.getNom().length()==0 || t.getImage().length()==0 || t.getDescription().length()==0 ||  t.getDate()==null)
        return false;
    else if(t.getNom().length()>255 && t.getImage().length()>255 && t.getDescription().length()>255) return false;
        System.out.println("valide");
    return true;
    
    }  


    
}
