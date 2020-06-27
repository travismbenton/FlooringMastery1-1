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
public class TaxRates {
    
    private String OH = ".0625";
    private String PA = ".0675";
    private String MI = ".0575";
    private String IN = ".06";

    public String getOH() {
        return OH;
    }

    public void setOH(String OH) {
        this.OH = OH;
    }

    public String getPA() {
        return PA;
    }

    public void setPA(String PA) {
        this.PA = PA;
    }

    public String getMI() {
        return MI;
    }

    public void setMI(String MI) {
        this.MI = MI;
    }

    public String getIN() {
        return IN;
    }

    public void setIN(String IN) {
        this.IN = IN;
    }

    
    
}
