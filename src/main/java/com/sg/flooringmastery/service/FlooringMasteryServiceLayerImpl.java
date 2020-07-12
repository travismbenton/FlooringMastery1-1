/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryAuditDao;
import com.sg.flooringmastery.dao.FlooringMasteryDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dao.FlooringMasteryProductsDao;
import com.sg.flooringmastery.dao.FlooringMasteryTaxesDao;
import com.sg.flooringmastery.dto.Orders;
import com.sg.flooringmastery.dto.Products;
import com.sg.flooringmastery.dto.Taxes;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author travi
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer{

    private FlooringMasteryDao dao;
    private FlooringMasteryAuditDao auditDao; 
    private FlooringMasteryTaxesDao taxesDao;
    private FlooringMasteryProductsDao productsDao;
    
    
    // -- Constructor --
    public FlooringMasteryServiceLayerImpl(FlooringMasteryDao dao, FlooringMasteryAuditDao auditDao,
            FlooringMasteryTaxesDao taxesDao, FlooringMasteryProductsDao productsDao){
        this.dao = dao;
        this.auditDao = auditDao;
        this.taxesDao = taxesDao;
        this.productsDao = productsDao;
    }
    // -- "END" Constructor --
    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|


    LocalDate ld;
    @Override
    public void createOrder(LocalDate ld, Orders order) throws FlooringMasteryPersistenceException,
                                                 FloorMasteryValidateSubmitException,
                                                 FlooringMasteryDataValidationException,
                                                 FlooringMasteryDuplicateIdException {
        String orderNumber = order.getOrderNumber();
        if(dao.getOrder(ld,order.getOrderNumber()) == null) {            
            order.setOrderNumber(dao.theKeys(ld,orderNumber));
        }
                           
        validateRequiredFields(order);
        validateDate(order);
        //VALIDATION IN CONTROLLER -- STATE & PRODUCT TYPE
        validateAreaSquareFeet(order);        
        validateSumitOrder(order);
        dao.addOrder(ld, order.getOrderNumber(), order);
        auditDao.writeAuditEntry("New Order: "+order.getOrderNumber()+" CREATED.");        
    }
    

    @Override
    public void editOrder(String date, Orders order) throws FlooringMasteryPersistenceException,
                                                            FloorMasteryValidateSubmitException, 
                                                            FlooringMasteryDataValidationException,
                                                            FlooringMasteryDuplicateIdException {                                 
        validateAreaSquareFeet(order);
        validateSumitOrder(order);
        dao.addEditOrder(date, order.getOrderNumber(), order);
        auditDao.writeAuditEntry("Existing Order: "+order.getOrderNumber()+" EDITED.");
    }
    

    @Override
    public List<Orders> getAllOrders(LocalDate ld) throws FlooringMasteryPersistenceException {
        return dao.getAllOrders(ld);
    }
    
    
    @Override
    public Orders getOrder(LocalDate ld, String orderNumber) throws FlooringMasteryPersistenceException {
        return dao.getOrder(ld,orderNumber);
    }
    

    @Override
    public Orders removeOrder(String date, String orderNumber, Orders order) throws
            FlooringMasteryPersistenceException, 
            FloorMasteryValidateSubmitException, 
            FlooringMasteryDataValidationException {
        validateRemoveOrder(order);
        Orders removedOrder = dao.removeOrder(date, orderNumber);
        auditDao.writeAuditEntry("Existing Order: "+orderNumber+" REMOVED.");
        return removedOrder;
    }
    
    
    @Override // -- USED FOR CREATE ORDER
    public Orders getTXTOrder(String orderDate) throws FlooringMasteryPersistenceException {
        return dao.getTXTOrder(orderDate);
    }
    
    
    @Override // -- USED FOR REMOVE & REMOVE --
    public Orders getEditTXTOrder(String date, String orderNumber) throws FlooringMasteryPersistenceException {
        return dao.getEditTXTOrder(date, orderNumber);
    } 
    
    
    private void validateRequiredFields(Orders order) throws 
            FlooringMasteryDataValidationException {        
        if (order.getCustomerName() == null || order.getCustomerName().trim().length() == 0
            || order.getOrderDate() == null ) {                        
            throw new FlooringMasteryDataValidationException (
                    "ERROR: [Customer Name, Order Date] required field."+"\n"
                            + "Date Format: YYYY-MM-DD");                                        
        }          
    }   
    
    
    private void validateDate(Orders order) throws 
            FlooringMasteryDataValidationException { 
        LocalDate date1 = LocalDate.now();
        Integer yesDate = order.getOrderDate().compareTo(date1);
        System.out.println("Date Stuff: "+yesDate);
        if (yesDate <= 0 ) {                        
            throw new FlooringMasteryDataValidationException (
                    "ERROR: [Order Date] has to be greater than today's date.");                                        
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
    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|
    
    // -- ADD TAXES AND PRODUCTS INFORMATION  SECTION -- 

    @Override
    public Taxes addState(String stateAbbreviation, Taxes state) 
            throws FlooringMasteryPersistenceException {
        return taxesDao.addState(stateAbbreviation, state);
    }

    @Override
    public Taxes removeState(String stateAbbreviation) 
            throws FlooringMasteryPersistenceException {
        return taxesDao.removeState(stateAbbreviation);
    }

    @Override
    public Taxes getState(String stateAbbreviation) 
            throws FlooringMasteryPersistenceException {
        return taxesDao.getState(stateAbbreviation);
    }    
    
    @Override
    public List<Taxes> listAllStates() 
            throws FlooringMasteryPersistenceException {
        return taxesDao.listAllStates();
    }      
            
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|

    @Override
    public Products addProduct(String productType, Products product) 
            throws FlooringMasteryPersistenceException {
        return productsDao.addProduct(productType, product);
    }

    @Override
    public Products removeProduct(String productType) 
            throws FlooringMasteryPersistenceException {
        return productsDao.removeProduct(productType);
    }

    @Override
    public Products getProduct(String productType) 
            throws FlooringMasteryPersistenceException {
        return productsDao.getProduct(productType);
    }
    
    @Override
    public List<Products> listAllProducts() 
            throws FlooringMasteryPersistenceException {
        return productsDao.listAllProducts();
    }

    

    
    
        
    
}
