/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Products;
import java.io.BufferedReader;
import java.io.File;
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

/**
 *
 * @author travi
 */
public class FlooringMasteryProductsDaoFileImpl implements FlooringMasteryProductsDao {
    
    private Map<String, Products> myProducts = new HashMap<>();
    
    public static final String PRODUCTS_FILE = "products.txt";
    public static final String DELIMITER = "::";

    @Override
    public Products addProduct(String productType, Products product) 
            throws FlooringMasteryPersistenceException {
        Products newProduct = myProducts.put(productType, product);
        writeProducts();
        return newProduct;
    }

    @Override
    public Products removeProduct(String productType) 
            throws FlooringMasteryPersistenceException {
        Products removeProduct = myProducts.remove(productType);
        writeProducts();
        return removeProduct;

    }

    @Override
    public Products getProduct(String productType) 
            throws FlooringMasteryPersistenceException {
        loadProducts();
        return myProducts.get(productType);
    }
    
/*    @Override
    public Products getProductFile(String products) 
            throws FlooringMasteryPersistenceException {
        try { 
        loadProductsFile(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myProducts.get(products);
    }  */
    

    @Override
    public List<Products> listAllProducts() 
            throws FlooringMasteryPersistenceException {
        loadProducts();
        return new ArrayList<>(myProducts.values());
    }
    
    
    /*
    public String loadProductsFile(String products) throws FileNotFoundException, 
            IOException, FlooringMasteryPersistenceException {
      Scanner scanner;
      
      try { 
    
    File ORDERS_FILE = new File("products.txt");    
              
      scanner = new Scanner(
                  new BufferedReader(
                  new FileReader(PRODUCTS_FILE)));
      } catch (FileNotFoundException e){
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load product data into memory", e);
        }
        
        String currentLine;        
        String[] currentTokens;

        
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);           
            
            Products currentProducts = new Products(currentTokens[0]);            
	    currentProducts.setCostPerSquareFoot(currentTokens[1]);
	    currentProducts.setLaborCostPerSquareFoot(currentTokens[2]);            
            
            myProducts.put(currentProducts.getProductType(), currentProducts);            
        
        }            
        
      scanner.close();       
    
      return products;
    } */

    
    
    
    
    // -- LOAD PRODUCTS --
    private void loadProducts() throws FlooringMasteryPersistenceException {
        Scanner scanner;
        
        try {            
            scanner = new Scanner(
                    new BufferedReader(
                        new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e){
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load vending data into memory", e);
        }
        
        String currentLine;
        String[] currentTokens; 
         
        
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);           
            
            Products currentProducts = new Products(currentTokens[0]);
            
	    currentProducts.setCostPerSquareFoot(currentTokens[1]);
	    currentProducts.setLaborCostPerSquareFoot(currentTokens[2]);
            
            
            myProducts.put(currentProducts.getProductType(), currentProducts);           
        }
        scanner.close(); 
        
    } // -- LOAD PRODUCTS --  

    
    
    // -- WRITE PRODUCTS --   
    private void writeProducts() throws FlooringMasteryPersistenceException {
	    
	    PrintWriter out;
	    
	    try {                
	        out = new PrintWriter(new FileWriter(PRODUCTS_FILE));
	    } catch (IOException e) {
	        throw new FlooringMasteryPersistenceException(
	                "Could not save vending data.", e);
	    }	    
	    
	   List<Products> productList = this.listAllProducts();
	    for (Products currentProducts : productList) {
	        // write the Inventory object to the file
	        out.println(currentProducts.getProductType() + DELIMITER
	                + currentProducts.getCostPerSquareFoot() + DELIMITER 
	                + currentProducts.getLaborCostPerSquareFoot());
	        // force PrintWriter to write line to the file
	        out.flush();
	    } 
	    // Clean up
	    out.close();
            
    } // -- WRITE PRODUCTS --
}
