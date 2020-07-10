/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Orders;
import com.sg.flooringmastery.dto.Products;
import com.sg.flooringmastery.dto.Taxes;
import java.util.List;

/**
 *
 * @author travi
 */
public interface FlooringMasteryServiceLayer {
    
    public void createOrder(String date, Orders order) throws 
            FlooringMasteryPersistenceException, 
            FlooringMasteryDataValidationException,
            FloorMasteryValidateSubmitException,
            FlooringMasteryDuplicateIdException;
    
    public void editOrder(String date, Orders order) throws 
            FlooringMasteryPersistenceException, 
            FlooringMasteryDataValidationException,
            FlooringMasteryDuplicateIdException,
            FloorMasteryValidateSubmitException;                       
 
    public List<Orders> getAllOrders() throws
            FlooringMasteryPersistenceException;
    
    public Orders getTXTOrder(String orderDate) throws
            FlooringMasteryPersistenceException;
    
    public Orders getEditTXTOrder(String date, String orderNumber) throws
            FlooringMasteryPersistenceException;
    
    public Orders getEditTXTOrder2(String date, String orderNumber) throws
            FlooringMasteryPersistenceException;
 
    public Orders getOrder(String orderNumber) throws
            FlooringMasteryPersistenceException;
 
    public Orders removeOrder(String date, String OrderNumber, Orders order) throws
            FlooringMasteryPersistenceException,
            FlooringMasteryDataValidationException,
            FloorMasteryValidateSubmitException;
    
    public List<Orders> getOrdersByOrderNumber(String orderNumber) 
           throws FlooringMasteryPersistenceException;
    
    
    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|
    
    // -- ADD TAXES AND PRODUCTS INFORMATION  SECTION --     
    
    
    Taxes addState(String stateAbbreviation, Taxes state)
            throws FlooringMasteryPersistenceException;
                
    
    Taxes removeState(String stateAbbreviation)
            throws FlooringMasteryPersistenceException;
            
    
    Taxes getState(String stateAbbreviation)
            throws FlooringMasteryPersistenceException;
            
        
    List<Taxes> listAllStates()
            throws FlooringMasteryPersistenceException;   
    
        
     //---------------------------------------------------------|
    
    //---------------------------------------------------------|
    
    
    Products addProduct(String productType, Products product)
            throws FlooringMasteryPersistenceException;
                
    
    Products removeProduct(String productType)
            throws FlooringMasteryPersistenceException;
            
    
    Products getProduct(String productType)
            throws FlooringMasteryPersistenceException;
            
        
    List<Products> listAllProducts()
            throws FlooringMasteryPersistenceException;
    
    
    
    
}
