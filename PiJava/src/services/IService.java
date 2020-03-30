/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;

/**
 *
 * @author ASUS
 * @param <C>
 */
public interface IService<C> {
    void insert(C c);
    List<C> getAll();
    void delete(C c);
    void edit (C c) ;
    
   
    
    
}
