/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author walid
 */
public class PubliciteAimer {
    private int id_pub_aimer;
    private int id_publicite;
    private int id_user;
    private Date date;

    public PubliciteAimer(int id_pub_aimer, int id_publicite, int id_user, Date date) {
        this.id_pub_aimer = id_pub_aimer;
        this.id_publicite = id_publicite;
        this.id_user = id_user;
        this.date = date;
    }
      public PubliciteAimer(int id_publicite, int id_user, Date date) {
        
        this.id_publicite = id_publicite;
        this.id_user = id_user;
        this.date = date;
    }

    public int getId_pub_aimer() {
        return id_pub_aimer;
    }

    public int getId_publicite() {
        return id_publicite;
    }

    public int getId_user() {
        return id_user;
    }

    public Date getDate() {
        return date;
    }

    public void setId_pub_aimer(int id_pub_aimer) {
        this.id_pub_aimer = id_pub_aimer;
    }

    public void setId_publicite(int id_publicite) {
        this.id_publicite = id_publicite;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PubliciteAimer{" + "id_pub_aimer=" + id_pub_aimer + ", id_publicite=" + id_publicite + ", id_user=" + id_user + ", date=" + date + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id_pub_aimer;
        hash = 47 * hash + this.id_publicite;
        hash = 47 * hash + this.id_user;
        hash = 47 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PubliciteAimer other = (PubliciteAimer) obj;
        if (this.id_pub_aimer != other.id_pub_aimer) {
            return false;
        }
        if (this.id_publicite != other.id_publicite) {
            return false;
        }
        if (this.id_user != other.id_user) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }
    
}
