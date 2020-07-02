/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryAuditDao;
import com.sg.flooringmastery.dao.FlooringMasteryDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Orders;
import java.util.List;

/**
 *
 * @author travi
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer{

    private FlooringMasteryDao dao;
    private FlooringMasteryAuditDao auditDao; 
    
    
    // -- Constructor --
    public FlooringMasteryServiceLayerImpl(FlooringMasteryDao dao, FlooringMasteryAuditDao auditDao){
        this.dao = dao;
        this.auditDao = auditDao;
    }
    // -- "END" Constructor --

    @Override
    public void createOrder(Orders order) throws FlooringMasteryPersistenceException,
                                                 FloorMasteryValidateSubmitException,
                                                 FlooringMasteryDataValidationException,
                                                 FlooringMasteryDuplicateIdException {
        String orderNumber = order.getOrderNumber();
        if(dao.getOrder(order.getOrderNumber()) == null) {            
            order.setOrderNumber(dao.theTestKeys(orderNumber));
        }
                           
        validateRequiredFields(order);
        validateAreaSquareFeet(order);        
        validateSumitOrder(order);
        dao.addOrder(order.getOrderNumber(), order);
        //auditDao.writeAuditEntry("New Order: "+order.getOrderNumber()+" CREATED.");        
    }

    @Override
    public void editOrder(String date, Orders order) throws FlooringMasteryPersistenceException,
                                                            FloorMasteryValidateSubmitException, 
                                                            FlooringMasteryDataValidationException,
                                                            FlooringMasteryDuplicateIdException {                                     
        validateAreaSquareFeet(order);
        validateSumitOrder(order);
        dao.addEditOrder(date, order.getOrderNumber(), order);
        //auditDao.writeAuditEntry("Existing Order: "+order.getOrderNumber()+" EDITED.");
    }

    @Override
    public List<Orders> getAllOrders() throws FlooringMasteryPersistenceException {
        return dao.getAllOrders();
    }
    
    @Override
    public Orders getTXTOrder(String orderDate) throws FlooringMasteryPersistenceException {
        return dao.getTXTOrder(orderDate);
    }
    
    @Override
    public Orders getEditTXTOrder(String date, String orderNumber) throws FlooringMasteryPersistenceException {
        return dao.getEditTXTOrder(date, orderNumber);
    }

    @Override
    public Orders getOrder(String orderNumber) throws FlooringMasteryPersistenceException {
        return dao.getOrder(orderNumber);
    }

    @Override
    public Orders removeOrder(String date, String orderNumber, Orders order) throws
            FlooringMasteryPersistenceException, 
            FloorMasteryValidateSubmitException, 
            FlooringMasteryDataValidationException {
        validateRemoveOrder(order);
        Orders removedOrder = dao.removeOrder(date, orderNumber);
        //auditDao.writeAuditEntry("Existing Order: "+orderNumber+" REMOVED.");
        return removedOrder;
    }
    
    
    private void validateRequiredFields(Orders order) throws 
            FlooringMasteryDataValidationException {        
        if (order.getCustomerName() == null || order.getCustomerName().trim().length() == 0) {                        
            throw new FlooringMasteryDataValidationException (
                    "ERROR: [Customer Name] is a required field.");                                        
        }         
    }    
    
    
    private void validateAreaSquareFeet(Orders order) throws 
            FlooringMasteryDataValidationException {            
        
        if (Double.valueOf(order.getArea()) < 100) {            
            throw new FlooringMasteryDataValidationException (
                    "ERROR: [Area: Minimum 100sqft] is required.");                                        
        }                
    }
    
    
    private void validateSumitOrder(Orders order) throws FloorMasteryValidateSubmitException, 
                                                         FlooringMasteryDataValidationException {
        if (order.getSubmitOrder() == null || order.getSubmitOrder().trim().length() == 0) {                        
            throw new FlooringMasteryDataValidationException (
                    "ERROR: [Submit Order \"Y/N\"] is a required field.");
        }
        if (order.getSubmitOrder().equalsIgnoreCase("n")){ 
            //order = null;
            throw new FloorMasteryValidateSubmitException (
                    "Order Removed Successfully!");         
        }         
    }
    
    
    private void validateRemoveOrder(Orders order) throws FloorMasteryValidateSubmitException, 
                                                        FlooringMasteryDataValidationException {
        if (order.getSubmitOrder() == null || order.getSubmitOrder().trim().length() == 0) {                        
            throw new FlooringMasteryDataValidationException (
                    "ERROR: [Remove Order \"Y/N\"] is a required field.");
        }
        if (order.getSubmitOrder().equalsIgnoreCase("n")){ 
            //order = null;
            throw new FloorMasteryValidateSubmitException (
                    "Remove Order Cancelled!");         
        }         
    }
    
    // -- NOT IN USE --
    public void systemGeneratedOrderNumber(Orders order) 
            throws FlooringMasteryPersistenceException{
        System.out.println();
        String orderNumber = order.getOrderNumber();
        if(dao.getOrder(order.getOrderNumber()) == null) {              
           dao.generateNextOrderNumber(orderNumber);   
           order.setOrderNumber(orderNumber);
            System.out.println("Service Generated: "+orderNumber);
        }   
    }    
            
                  
   public List<Orders> getOrdersByOrderNumber(String orderNumber) 
           throws FlooringMasteryPersistenceException {
        return dao.getAssignedOrderNumbers(orderNumber);
    }     

    

    
        
    
}
