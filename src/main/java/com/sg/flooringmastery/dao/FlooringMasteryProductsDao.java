/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Products;
import java.util.List;

/**
 *
 * @author travi
 */
public interface FlooringMasteryProductsDao {
    
    Products addProduct(String productType, Products product)
            throws FlooringMasteryPersistenceException;
                
    
    Products removeProduct(String productType)
            throws FlooringMasteryPersistenceException;
            
    
    Products getProduct(String productType)
            throws FlooringMasteryPersistenceException;
            
        
    List<Products> listAllProducts()
            throws FlooringMasteryPersistenceException;
}
