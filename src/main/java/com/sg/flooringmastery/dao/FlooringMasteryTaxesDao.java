/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Taxes;
import java.util.List;

/**
 *
 * @author travi
 */
public interface FlooringMasteryTaxesDao {
    
    Taxes addState(String stateAbbreviation, Taxes state)
            throws FlooringMasteryPersistenceException;
                
    
    Taxes removeState(String stateAbbreviation)
            throws FlooringMasteryPersistenceException;
            
    
    Taxes getState(String stateAbbreviation)
            throws FlooringMasteryPersistenceException;
            
        
    List<Taxes> listAllStates()
            throws FlooringMasteryPersistenceException; 
    
        
}
