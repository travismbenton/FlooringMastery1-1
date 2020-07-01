/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Orders;
import com.sg.flooringmastery.service.FlooringMasteryDuplicateIdException;
import java.util.List;

/**
 *
 * @author travi
 */
public interface FlooringMasteryDao {
    
    public String theKeys(String orderNumber)
            throws FlooringMasteryPersistenceException;
    
    public String theTestKeys(String orderNumber)
            throws FlooringMasteryPersistenceException;
    
    public String generateNextOrderNumber(String orderNumber) 
            throws FlooringMasteryPersistenceException;
    
    Orders addOrder(String orderNumber, Orders order)
            throws FlooringMasteryPersistenceException, FlooringMasteryDuplicateIdException;
    
    Orders addEditOrder(String date, String orderNumber, Orders order)
            throws FlooringMasteryPersistenceException, FlooringMasteryDuplicateIdException;

    
    List<Orders> getAllOrders()
            throws FlooringMasteryPersistenceException;
    
    Orders getTXTOrder (String orderDate)
            throws FlooringMasteryPersistenceException;
    
    Orders getEditTXTOrder (String date, String orderNumber)
            throws FlooringMasteryPersistenceException;
    
    Orders getOrder(String orderNumber)
            throws FlooringMasteryPersistenceException;       
    
    Orders removeOrder(String orderDate, String orderNumber)
            throws FlooringMasteryPersistenceException;   
  
    // -- Lambdas, Streams, and Aggregate Operations Section --

    public List<Orders> getAssignedOrderNumbers (String orderNumber)
            throws FlooringMasteryPersistenceException;
    
    
    
}
