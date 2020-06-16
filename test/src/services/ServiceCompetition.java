/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.Competition;
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
public class ServiceCompetition implements IServiceC<Competition> {
    
private Connection cnx;
    private Statement st;
    private PreparedStatement pre;
   
    public ServiceCompetition(){
    cnx = DataSource.getInstance().getCnx();
    }
    


   
    @Override
    public void insert(Competition e){
        Date dd =new java.sql.Date(e.getDebut().getTime());
Date df =new java.sql.Date(e.getDfinal().getTime());
        String req="INSERT INTO `velo`.`Competition` (`region`,`debut`,  `final`,  `prime`,`nomcomp`) VALUES ('"+e.getRegion()+"','"+e.getDebut()+"','"+e.getDfinal()+"','"+e.getPrime()+"','"+e.getNomcomp()+"');";
        try {
            st=cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
                  Logger.getLogger(ServiceCompetition.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

   @Override
    public boolean update(Competition c,int id){
        if(chercher(id)){
               Date dd=new java.sql.Date(c.getDebut().getTime());
   Date df=new java.sql.Date(c.getDfinal().getTime());
         try {
             pre=cnx.prepareStatement("UPDATE Competition SET region = ? ,debut = ? ,final =? ,prime =?,nomcomp =? WHERE id=?");
            pre.setString(1,c.getRegion());
             pre.setDate(2,dd);     
             pre.setDate(3,df);
             pre.setInt(4, c.getPrime()); 
             pre.setString(5,c.getNomcomp());
             pre.setInt(6,id);
             pre.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(ServiceCompetition.class.getName()).log(Level.SEVERE, null, ex);
         }
            System.out.println("update valide");
         return true;}
        System.out.println("update invalid: Competition nexiste pas");
        return false;
    }

    @Override
    public boolean delete(int id) {
         if(chercher(id)){
        try {
             
             pre=cnx.prepareStatement("delete from Competition where id= ?;");
             pre.setInt(1, id);
             pre.execute();
         } catch (SQLException ex) {
             Logger.getLogger(ServiceCompetition.class.getName()).log(Level.SEVERE, null, ex);
         }
             System.out.println("delete valide");
         return true;
         }
         System.out.println("Competition n existe pas");
         return false;
    }

    @Override
    public List<Competition> displayAll() {
        String req ="select * from Competition";
        List<Competition> list =new ArrayList<>();
         try {
             st=cnx.createStatement();
             ResultSet rs =st.executeQuery(req);
               while(rs.next())
               {       java.util.Date dd=new java.util.Date(rs.getDate(3).getTime());
 java.util.Date df=new java.util.Date(rs.getDate(4).getTime());
                   Competition cc=new Competition(rs.getInt(1),rs.getString(2), dd, df,rs.getInt(5),rs.getString(6));

                 
        list.add(cc);
               
               }
         } catch (SQLException ex) {
             Logger.getLogger(ServiceCompetition.class.getName()).log(Level.SEVERE, null, ex);
         }
        return list;
    }
    
     public Boolean chercher(int id) {
        String req ="select * from Competition";
        List<Integer> list =new ArrayList<>();
         try {
             st=cnx.createStatement();
             ResultSet rs =st.executeQuery(req);
               while(rs.next())
               {       java.util.Date dd =new java.util.Date(rs.getDate(3).getTime());
   java.util.Date df =new java.util.Date(rs.getDate(4).getTime());
        list.add(rs.getInt(1));
               
               }
         } catch (SQLException ex) {
             Logger.getLogger(ServiceCompetition.class.getName()).log(Level.SEVERE, null, ex);
         }
        return list.contains(id);
    } 
    
    
}