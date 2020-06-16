/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import models.Club;
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
import utils.DataSource;


/**
 *
 * @author Ahmed laifi
 */
public class ServiceClub implements IServiceC<Club> {
    
private Connection cnx;
    private Statement st;
    private PreparedStatement pre;
   
    public ServiceClub(){
    cnx = DataSource.getInstance().getCnx();
    }
    

         
    @Override
    public void insert(Club e){
        Date dd =new java.sql.Date(e.getDate_creation().getTime());

        String req="INSERT INTO `velo`.`Club` (`nom`,  `fondateur`,   `date_creation`,  `solde`) VALUES ('"+e.getNom()+"','"+e.getFondateur()+"','"+e.getDate_creation()+"','"+e.getSolde()+"');";
        try {
            st=cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
                  Logger.getLogger(ServiceClub.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

   @Override
    public boolean update(Club t,int id){
        if(chercher(id)){
               Date dd=new java.sql.Date(t.getDate_creation().getTime());
   
         try {
             pre=cnx.prepareStatement("UPDATE Club SET nom = ? ,date_creation =? ,fondateur =?,solde =? WHERE id=?");
             pre.setString(1,t.getNom());         
             pre.setDate(2,dd);
            pre.setString(3,t.getFondateur());
             pre.setInt(4, t.getSolde());
             pre.setInt(5,id);
             pre.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(ServiceClub.class.getName()).log(Level.SEVERE, null, ex);
         }
            System.out.println("update valide");
         return true;}
        System.out.println("update invalid: Club nexiste pas");
        return false;
    }

    @Override
    public boolean delete(int id) {
         if(chercher(id)){
        try {
             
             pre=cnx.prepareStatement("delete from Club where id= ?;");
             pre.setInt(1, id);
             pre.execute();
         } catch (SQLException ex) {
             Logger.getLogger(ServiceClub.class.getName()).log(Level.SEVERE, null, ex);
         }
             System.out.println("delete valide");
         return true;
         }
         System.out.println("Club n existe pas");
         return false;
    }

    @Override
    public List<Club> displayAll() {
        String req ="select * from Club";
        List<Club> list =new ArrayList<>();
         try {
             st=cnx.createStatement();
             ResultSet rs =st.executeQuery(req);
               while(rs.next())
               {       java.util.Date dd=new java.util.Date(rs.getDate(3).getTime());

                   Club pp=new Club(rs.getInt(1),rs.getString(2), rs.getString(7),dd,rs.getInt(6));

                 
        list.add(pp);
               
               }
         } catch (SQLException ex) {
             Logger.getLogger(ServiceClub.class.getName()).log(Level.SEVERE, null, ex);
         }
        return list;
    }
    
     public Boolean chercher(int id) {
        String req ="select * from Club";
        List<Integer> list =new ArrayList<>();
         try {
             st=cnx.createStatement();
             ResultSet rs =st.executeQuery(req);
               while(rs.next())
               {       java.util.Date dd =new java.util.Date(rs.getDate(3).getTime());
 
        list.add(rs.getInt(1));
               
               }
         } catch (SQLException ex) {
             Logger.getLogger(ServiceClub.class.getName()).log(Level.SEVERE, null, ex);
         }
        return list.contains(id);
    } 
     
      public List<Club> trieParsolde() {
        String req ="select * from Club ORDER BY solde";
        List<Club> list =new ArrayList<>();
         try {
             st=cnx.createStatement();
             ResultSet rs =st.executeQuery(req);
               while(rs.next())
               {      java.util.Date dd=new java.util.Date(rs.getDate(3).getTime());

        list.add(new Club(rs.getInt(1),rs.getString(2), rs.getString(7),dd,rs.getInt(6)));
               
               }
         } catch (SQLException ex) {
             Logger.getLogger(ServiceClub.class.getName()).log(Level.SEVERE, null, ex);
         }
        return list;
    }
    
    
}