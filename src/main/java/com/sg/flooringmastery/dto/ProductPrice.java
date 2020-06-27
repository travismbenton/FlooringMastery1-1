/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

/**
 *
 * @author travi
 */
public class ProductPrice {
    
    private String carpet = "2.25";    
    private String laminate = "1.75";   
    private String tile = "3.50";    
    private String wood = "5.15";
    

    public String getCarpet() {
        return carpet;
    }

    public void setCarpet(String carpet) {
        this.carpet = carpet;
    }

    public String getLaminate() {
        return laminate;
    }

    public void setLaminate(String laminate) {
        this.laminate = laminate;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getWood() {
        return wood;
    }

    public void setWood(String wood) {
        this.wood = wood;
    }

    
    

}
