/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ACER
 *  @param <T>
 */
public interface ReclamationIservice<T> {
    void insert(T t) throws SQLException;
     List<T> displayALL() throws SQLException;
}
