/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Orders;
import com.sg.flooringmastery.dto.Products;
import com.sg.flooringmastery.dto.Taxes;
import com.sg.flooringmastery.service.FlooringMasteryDuplicateIdException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author travi
 */
public interface FlooringMasteryDao {
    
    public String theKeys(LocalDate ld, String orderNumber)
            throws FlooringMasteryPersistenceException;
    
    public String theTestKeys(String orderNumber)
            throws FlooringMasteryPersistenceException;
    
    public String generateNextOrderNumber(String orderNumber) 
            throws FlooringMasteryPersistenceException;
    
    Orders addOrder(LocalDate ld, String orderNumber, Orders order)
            throws FlooringMasteryPersistenceException, FlooringMasteryDuplicateIdException;
    
    Orders addEditOrder(String date, String orderNumber, Orders order)
            throws FlooringMasteryPersistenceException, FlooringMasteryDuplicateIdException;

    
    List<Orders> getAllOrders(LocalDate ld)
            throws FlooringMasteryPersistenceException;
    
    List<Orders> getAllOrders2(String date)
            throws FlooringMasteryPersistenceException;
    
    Orders getTXTOrder (String date)
            throws FlooringMasteryPersistenceException;
    
    Orders getEditTXTOrder (String date, String orderNumber)
            throws FlooringMasteryPersistenceException;
    
    Orders getOrder(LocalDate ld, String orderNumber)
            throws FlooringMasteryPersistenceException;       
    
    Orders removeOrder(String date, String orderNumber)
            throws FlooringMasteryPersistenceException;   
  
    // -- Lambdas, Streams, and Aggregate Operations Section --

   // public List<Orders> getAssignedOrderNumbers (String orderNumber)
   //         throws FlooringMasteryPersistenceException;
    
        
    
    
}
