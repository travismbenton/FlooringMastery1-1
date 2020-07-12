/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Taxes;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author travi
 */
public class FlooringMasteryTaxesDaoFileImpl implements FlooringMasteryTaxesDao {
    
    private Map<String, Taxes> myTaxes = new HashMap<>();
    
    public static final String TAXES_FILE = "taxes.txt";
    public static final String DELIMITER = "::"; 

    @Override
    public Taxes addState(String state, Taxes taxes) 
            throws FlooringMasteryPersistenceException {
        Taxes newState = myTaxes.put(state, taxes);
        writeTaxes();
        return newState;
    }

    @Override
    public Taxes removeState(String state) 
            throws FlooringMasteryPersistenceException {
        Taxes removeState = myTaxes.remove(state);
        writeTaxes();
        return removeState;
    }

    @Override
    public Taxes getState(String state) 
            throws FlooringMasteryPersistenceException {
        loadTaxes();
        return myTaxes.get(state);
    }

    @Override
    public List<Taxes> listAllStates() 
            throws FlooringMasteryPersistenceException {
        loadTaxes();
        return new ArrayList<>(myTaxes.values());
    }
    
    
    
    // -- LOAD TAXES --
    private void loadTaxes() throws FlooringMasteryPersistenceException {
        Scanner scanner;
        
        try {            
            scanner = new Scanner(
                    new BufferedReader(
                        new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e){
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load vending data into memory", e);
        }
        
        String currentLine;
        String[] currentTokens; 
        
        
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);           
            
            Taxes currentState = new Taxes(currentTokens[0]);           
                currentState.setTaxRate(currentTokens[1]);            
            
           myTaxes.put(currentState.getState(), currentState);           
        }
        scanner.close(); 
        
    } // -- LOAD TAXES --  

    
    
    // -- WRITE TAXES --   
    private void writeTaxes() throws FlooringMasteryPersistenceException {
	    
	    PrintWriter out;
	    
	    try {                
	        out = new PrintWriter(new FileWriter(TAXES_FILE));
	    } catch (IOException e) {
	        throw new FlooringMasteryPersistenceException(
	                "Could not save vending data.", e);
	    }	    
	    
	    List<Taxes> inventoryList = this.listAllStates();
	    for (Taxes currentInventory : inventoryList) {
	        // write the Inventory object to the file
	        out.println(currentInventory.getState() + DELIMITER
	                + currentInventory.getTaxRate());
	        // force PrintWriter to write line to the file
	        out.flush();
	    } 
	    // Clean up
	    out.close();
            
    } // -- WRITE TAXES --
    
}
