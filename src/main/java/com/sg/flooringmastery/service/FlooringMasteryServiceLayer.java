/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Orders;
import java.util.List;

/**
 *
 * @author travi
 */
public interface FlooringMasteryServiceLayer {
    
    public void createOrder(Orders order) throws 
            FlooringMasteryPersistenceException, 
            FlooringMasteryDataValidationException,
            FloorMasteryValidateSubmitException,
            FlooringMasteryDuplicateIdException;
    
    public void editOrder(Orders order) throws 
            FlooringMasteryPersistenceException, 
            FlooringMasteryDataValidationException,
            FlooringMasteryDuplicateIdException,
            FloorMasteryValidateSubmitException;                       
 
    public List<Orders> getAllOrders() throws
            FlooringMasteryPersistenceException;
    
    public Orders getTXTOrder(String orderDate) throws
            FlooringMasteryPersistenceException;
 
    public Orders getOrder(String orderNumber) throws
            FlooringMasteryPersistenceException;
 
    public Orders removeOrder(String orderDate, String orderNumber) throws
            FlooringMasteryPersistenceException;
    
    public List<Orders> getOrdersByOrderNumber(String orderNumber) 
           throws FlooringMasteryPersistenceException;
}
