/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author Ahmed laifi
 */
public class Competition {
    
    
    private int id;
    private String  region;
    private Date debut;
    private Date dfinal ;
    private int prime;
    private String  nomcomp;

    public Competition(int id,String region, Date debut, Date dfinal, int prime, String nomcomp) {
        this.id = id;
        this.region=region;
        this.debut = debut;
        this.dfinal = dfinal;
         this.prime = prime;
        this.nomcomp = nomcomp;
    }

      public Competition( String region,Date debut, Date dfinal, int prime, String nomcomp) {
          this.region=region;
        this.debut = debut;
        this.dfinal = dfinal;
         this.prime = prime;
        this.nomcomp = nomcomp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getDfinal() {
        return dfinal;
    }

    public void setDfinal(Date dfinal) {
        this.dfinal = dfinal;
    }

    public int getPrime() {
        return prime;
    }

    public void setPrime(int prime) {
        this.prime = prime;
    }

    public String getNomcomp() {
        return nomcomp;
    }

    public void setNomcomp(String nomcomp) {
        this.nomcomp = nomcomp;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Competition{" + "id=" + id + ", region=" + region + ", debut=" + debut + ", dfinal=" + dfinal + ", prime=" + prime + ", nomcomp=" + nomcomp + '}';
    }

    
    
}
