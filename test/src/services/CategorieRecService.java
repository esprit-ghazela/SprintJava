/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.CategorieRec;
import utils.DataSource;

/**
 *
 * @author ACER
 */
public class CategorieRecService implements IserviceR <CategorieRec>  {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pre;
   
    public CategorieRecService(){
    cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(CategorieRec e) {
          String req="INSERT INTO `velo`.`categorierec` (`type`, `nom`, `description`) VALUES ('"+e.getType()+"','"+e.getNom()+"','"+e.getDescription()+"');";
        try {
          
            st=cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(CategorieRecService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean update(CategorieRec e, int id) {
       if(chercher(id)){
            
         try {
             pre=cnx.prepareStatement("UPDATE categorierec SET type = ? ,nom = ?,description =? WHERE id=?");
             pre.setString(1,e.getType());
             pre.setString(2,e.getNom());
             pre.setString(3,e.getDescription());
             pre.setInt(4,id);
             pre.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(CategorieRecService.class.getName()).log(Level.SEVERE, null, ex);
         }
            System.out.println("update valide");
         return true;}
        System.out.println("update invalid: categorie nexiste pas");
        return false;
    }

    @Override
    public boolean delete(int id) {
         if(chercher(id)){
        try {
             
             pre=cnx.prepareStatement("delete from categorierec where id= ?;");
             pre.setInt(1, id);
             pre.execute();
         } catch (SQLException ex) {
             Logger.getLogger(CategorieRecService.class.getName()).log(Level.SEVERE, null, ex);
         }
             System.out.println("delete valide");
         return true;
         }
         System.out.println("categorie nexiste pas");
         return false;
    }

    @Override
    public List<CategorieRec> displayAll() {
        String req ="select * from categorierec";
        List<CategorieRec> list =new ArrayList<>();
         try {
             st=cnx.createStatement();
             ResultSet rs =st.executeQuery(req);
               while(rs.next())
               {      
        
                   CategorieRec pp=new CategorieRec(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                   
        list.add(pp);
               
               }
         } catch (SQLException ex) {
             Logger.getLogger(CategorieRecService.class.getName()).log(Level.SEVERE, null, ex);
         }
        return list;
    }

    
   public Boolean chercher(int id) {
        String req ="select * from categorierec";
        List<Integer> list =new ArrayList<>();
         try {
             st=cnx.createStatement();
             ResultSet rs =st.executeQuery(req);
               while(rs.next())
               {      
       
        list.add(rs.getInt(1));
               
               }
         } catch (SQLException ex) {
             Logger.getLogger(CategorieRecService.class.getName()).log(Level.SEVERE, null, ex);
         }
        return list.contains(id);
    } 
   public List<CategorieRec> trieParid() {
        String req ="select * from categorierec ORDER BY id";
        List<CategorieRec> list =new ArrayList<>();
         try {
             st=cnx.createStatement();
             ResultSet rs =st.executeQuery(req);
               while(rs.next())
               {       
        list.add(new CategorieRec(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
               
               }
         } catch (SQLException ex) {
             Logger.getLogger(CategorieRecService.class.getName()).log(Level.SEVERE, null, ex);
         }
        return list;
    }
  
}
