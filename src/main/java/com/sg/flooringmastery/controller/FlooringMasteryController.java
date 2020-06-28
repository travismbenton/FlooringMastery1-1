/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Orders;
import com.sg.flooringmastery.service.FloorMasteryValidateSubmitException;
import com.sg.flooringmastery.service.FlooringMasteryDataValidationException;
import com.sg.flooringmastery.service.FlooringMasteryDuplicateIdException;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import java.util.List;

/**
 *
 * @author travi
 */
public class FlooringMasteryController {    
     
    FlooringMasteryServiceLayer service; 
    FlooringMasteryView view; 
    
    // --  CONSTRUCTOR -- 
    public FlooringMasteryController (FlooringMasteryServiceLayer service,
            FlooringMasteryView view) {
        this.service = service;
        this.view = view;        
    }
    // -- "END" CONSTRUCTOR -- 
    
    public void run() throws FlooringMasteryPersistenceException, 
                             FlooringMasteryDuplicateIdException, 
                             FlooringMasteryDataValidationException,
                             FloorMasteryValidateSubmitException{
        
        int menuSelection = 0;
        boolean keepGoing = true;
        try {
        while (keepGoing){            
            
            menuSelection = getMenuSelection();
            
            switch (menuSelection){
                case 1:
                    displayOrders();
                    break;
                case 2:
                    createOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    saveCurrentWork();
                    break;
                case 6:
                    listAllOrders();
                    break;
                case 7:
                    searchByOrderNumber();
                case 8:
                    listAllOrderNumbers();
                case 9:
                    keepGoing = false;
                    break;
                case 10:
                    unknownCommand();
                    break;
     
            } // -- "END" SWITCH --            
           
            
        } // -- "END" WHILE LOOP --
        exitMessage();
        
        } catch (FlooringMasteryPersistenceException | NumberFormatException 
                | NullPointerException | ArrayIndexOutOfBoundsException e){
            run();
            //view.displayErrorMessage(e.getMessage());
        }        
                   
            
    } // -- "END" RUN --
        
    
    
    // -- CONTROLLER --
    //---------------------------------------------------------|
    
    // -- Menu Section --    
    public int getMenuSelection(){
        return view.printMenuGetUserSelection();
    }    
    private void unknownCommand(){
        view.displayUnknownMenuBanner();
    }        
    private void exitMessage(){
        view.displayExitMenuBanner();
    }
    
    // -- "END" Menu Section --
    
    // ---------------------------------------------|  
    
    // -- DISPLAY ORDERS  SECTION --    
    private void displayOrders() throws FlooringMasteryPersistenceException {
        view.displayOrdersListBanner();
        String newDate = view.getOrderDateChoice();
        Orders order = service.getTXTOrder(newDate);        
        view.displayOrdersListSuccessBanner();  
    }           
    // -- "END" DISPLAY ORDERS  SECTION --
    
    //---------------------------------------------------------|      
        
    // -- ADD ORDER  SECTION --    
    private void createOrder() throws FlooringMasteryPersistenceException, 
                                   FlooringMasteryDuplicateIdException, 
                                   FloorMasteryValidateSubmitException,
                                   FlooringMasteryDataValidationException {
        view.displayCreateOrderBanner();
        boolean hasErrors = false;
        do {    
            //Orders order = ;
                Orders newOrder = view.getNewOrderInfo();                
            try {
                view.displayVerifyOrderSummary(newOrder);
                service.createOrder(newOrder);                
                view.displayCreateOrderSuccessBanner();  
                hasErrors = false;
            } catch (FlooringMasteryDataValidationException | 
                     FlooringMasteryDuplicateIdException | 
                     FlooringMasteryPersistenceException e) {                      
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            } catch (FloorMasteryValidateSubmitException e) {
                hasErrors = true;
                run();
            }
                
        } while(hasErrors);    
    }         
    // -- "END" ADD ORDER  SECTION --
    
    //---------------------------------------------------------|    
        
    // -- EDIT ORDER  SECTION --    
    private void editOrder() throws FlooringMasteryPersistenceException,                                     
                                    FloorMasteryValidateSubmitException,
                                    FlooringMasteryDataValidationException,
                                    FlooringMasteryDuplicateIdException {
        view.displayEditOrderBanner();
        boolean hasErrors = false;
        do {
                //String existingOrderDate = view.getOrderDateChoice();
                String existingOrderNumber = view.getEditOrderNumberChoice();
                Orders order = service.getOrder(existingOrderNumber);
                Orders newOrder = view.editExistingOrderInfo(order);            
        try {
                view.displayVerifyOrderSummary(newOrder);
                service.editOrder(newOrder);
                view.displayEditOrderSuccessBanner();
                hasErrors = false;
        } catch (FlooringMasteryDataValidationException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
        } catch (FloorMasteryValidateSubmitException e) {
                hasErrors = true;
                run(); 
        }        
        
        } while (hasErrors);
        
        
    }
    // -- "END" EDIT ORDER  SECTION --
    
    //---------------------------------------------------------|
    
    // -- REMOVE ORDER  SECTION --    
    private void removeOrder() throws FlooringMasteryPersistenceException {
        view.displayRemoveOrderBanner();
        String removeOrderDate = view.getOrderDateChoice();
        String removeOrderNumber = view.getOrderNumberChoice();
        Orders order = service.getOrder(removeOrderNumber);
        
        view.displayOrderSummary(order);
        if (order != null){
        service.removeOrder(removeOrderDate, removeOrderNumber);
        view.displayRemoveOrderSuccessfulBanner();
        } else {
            // intentionally left blank
        }
    }        
    // -- "END" REMOVE ORDER  SECTION --
    
    //---------------------------------------------------------| 
        
    // -- SAVE CURRENT WORK  SECTION --    
    private void saveCurrentWork() throws FlooringMasteryPersistenceException, 
                                          FlooringMasteryDuplicateIdException,
                                          FlooringMasteryDataValidationException,
                                          FloorMasteryValidateSubmitException {
        view.displaySaveCurrentWorkBanner();
        run();
        view.displaySaveCurrentWorkSuccessBanner();
    }    
    // -- "END" SAVE CURRENT WORK  SECTION --
    
    //---------------------------------------------------------|   
    
    // -- LIST ALL ORDERS  SECTION --    
    private void listAllOrders() throws FlooringMasteryPersistenceException {
        view.displayListAllOrdersBanner();
        List<Orders> orderList = service.getAllOrders();
        view.displayListAllOrders(orderList);       
    }           
    // -- "END" LIST ALL ORDERS  SECTION --
    
    //---------------------------------------------------------|
    
    // -- LIST ALL ORDERS  SECTION --    
    private void listAllOrderNumbers() throws FlooringMasteryPersistenceException, 
                                              FlooringMasteryDuplicateIdException, 
                                              FloorMasteryValidateSubmitException, 
                                              FlooringMasteryDataValidationException {
        view.displayListAllOrdersBanner();
        List<Orders> orderList = service.getAllOrders();
        view.displayListAllOrderNumbers(orderList); 
        run();
    }           
    // -- "END" LIST ALL ORDERS  SECTION --
    
    //---------------------------------------------------------|
    
    // -- SEARCH BY ORDER NUMBERS  SECTION --    
    private void searchByOrderNumber()throws FlooringMasteryPersistenceException, 
                                             FlooringMasteryDuplicateIdException, 
                                             FloorMasteryValidateSubmitException, 
                                             FlooringMasteryDataValidationException {
        view.displayFindOrderNumberBanner();
        String selectOrderNumber = view.selectOrderNumberChoice();
        List<Orders> orderNumber = service.getOrdersByOrderNumber(selectOrderNumber);
        orderNumber.stream()
                .forEach(m -> System.out.println("Order Number: "+m.getOrderNumber()
                                                    +":: Customer Name: "+m.getCustomerName()
                                                    +":: State: "+m.getState()));
        System.out.println("=== END ORDER ===");
        run();
    }
    // -- "END" SEARCH BY ORDER NUMBERS  SECTION --
}
