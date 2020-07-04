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
        
    private String stateAbbreviation;
    private String stateName;
    private BigDecimal taxRate;
    
    
    public Taxes(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;        
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = new BigDecimal(taxRate);
    }
   
    
    
}
