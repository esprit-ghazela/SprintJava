/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services
;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author aissa
 * @param <T>
 */
public interface IServiceC<T> {
    void insert(T e);
    boolean update(T e,int id);
    boolean delete(int id);
    List<T> displayAll();
}
