/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;

/**
 *
 * @author travi
 */
public class Taxes {    
        
    private String state;
    private BigDecimal taxRate;
    
    // -- CONSTRUCTOR -- 
    public Taxes(String state){
        this.state=state;
    }
    public String toString() { 
        return state + " " + taxRate; 
    } 
    // -- CONSTRUCTOR --

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = new BigDecimal(taxRate);
    }

    
}
