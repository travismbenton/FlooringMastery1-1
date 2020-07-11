/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Orders;
import com.sg.flooringmastery.dto.Products;
import com.sg.flooringmastery.dto.Taxes;
import com.sg.flooringmastery.service.FloorMasteryValidateSubmitException;
import com.sg.flooringmastery.service.FlooringMasteryDataValidationException;
import com.sg.flooringmastery.service.FlooringMasteryDuplicateIdException;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
                    //listAllOrders();
                    break; 
                case 6:
                    taxes();
                    break;                
                case 7:
                    products();
                    break;    
                case 8:
                    keepGoing = false;
                    break;
                case 9:
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
    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|
    
    public void taxes() throws FlooringMasteryPersistenceException, 
                             FlooringMasteryDuplicateIdException, 
                             FlooringMasteryDataValidationException,
                             FloorMasteryValidateSubmitException{
        
        int menuSelection = 0;
        boolean keepGoing = true;
        try {
        while (keepGoing){            
            
            menuSelection = getTaxesMenuSelection();
            

            switch (menuSelection){
                case 1:
                    addState();
                    break;
                case 2:
                    removeState();
                    break;
                case 3:
                    findState();
                    break;
                case 4:
                    editState();
                    break;
                case 5:
                    listAllStates();
                    break;    
                case 6:
                    keepGoing = false;
                    break;
                case 7:
                    unknownCommand();
                    break;
     
            } // -- "END" SWITCH --            
           
            
        } // -- "END" WHILE LOOP --
        exitMessage();
        
        } catch (FlooringMasteryPersistenceException | NumberFormatException 
                | NullPointerException | ArrayIndexOutOfBoundsException e){

            taxes();
            //view.displayErrorMessage(e.getMessage());
        }        
                   
            
    } // -- "END" RUN --
    
    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|
    
    public void products() throws FlooringMasteryPersistenceException, 
                             FlooringMasteryDuplicateIdException, 
                             FlooringMasteryDataValidationException,
                             FloorMasteryValidateSubmitException{
        
        int menuSelection = 0;
        boolean keepGoing = true;
        try {
        while (keepGoing){            
            
            menuSelection = getProductsMenuSelection();
            

            switch (menuSelection){
                case 1:
                    addProduct();
                    break;
                case 2:
                    removeProduct();
                    break;
                case 3:
                    findProduct();
                    break;
                case 4:
                    editProduct();
                    break;
                case 5:
                    listAllProducts();
                    break;                               
                case 6:
                    keepGoing = false;
                    break;
                case 7:
                    unknownCommand();
                    break;
     
            } // -- "END" SWITCH --            
           
            
        } // -- "END" WHILE LOOP --
        exitMessage();
        
        } catch (FlooringMasteryPersistenceException | NumberFormatException 
                | NullPointerException | ArrayIndexOutOfBoundsException e){

            products();
            //view.displayErrorMessage(e.getMessage());
        }        
                   
            
    } // -- "END" RUN --
    
    //---------------------------------------------------------|
    
    // -- Menu Section --    
    public int getMenuSelection(){
        return view.printMenuGetUserSelection();
    }    
    public int getTaxesMenuSelection(){
        return view.printTaxesMenuGetUserSelection();
    } 
    public int getProductsMenuSelection(){
        return view.printProductMenuGetUserSelection();
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
        String date = view.getOrderDateChoice();
        service.getTXTOrder(date);         
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
        LocalDate ld = null; 
        
        do {
            
            //String date = view.getOrderDateChoice();
            //Orders order = service.getTXTOrder(date);            
        
            String state="";            
            do{
            state= view.getStateChoice().toLowerCase();
            } while(!state.equalsIgnoreCase("oh")&&!state.equalsIgnoreCase("mi")&&
                    !state.equalsIgnoreCase("pa")&&!state.equalsIgnoreCase("in"));
            Taxes taxes = service.getState(state); System.out.println("");                  
            
        
            System.out.println("Item  | Cost  | Labor Cost");
            List<Products> productList;
            productList = service.listAllProducts();
            view.displayProductList(productList);System.out.println("");        
        
            String productType="";
            do{
            productType = view.getProductTypeChoice().toLowerCase();
            }while(!productType.equalsIgnoreCase("wood")&&!productType.equalsIgnoreCase("tile")&&
                    !productType.equalsIgnoreCase("carpet")&&!productType.equalsIgnoreCase("laminate"));
            Products products = service.getProduct(productType); System.out.println("");           
               
           
            
                Orders newOrder = view.getNewOrderInfo(productList, taxes, products);
                
           
            
                try {
                   
                view.displayVerifyOrderSummary(newOrder);
                service.createOrder(ld, newOrder);
                    
                view.displayCreateOrderSuccessBanner();  
                hasErrors = false;
            } catch(DateTimeParseException e){           
                view.displayDateErrorMessage(e.getMessage());
                hasErrors = true; 
            } catch (FlooringMasteryDataValidationException | 
                     FlooringMasteryDuplicateIdException | 
                     FlooringMasteryPersistenceException e) {                      
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            } catch (FloorMasteryValidateSubmitException e) {
                hasErrors = true;
                run();
            }
          //} // -- FOR LOOP --    
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
                String date = view.getEditOrderDateChoice();
                String existingOrderNumber = view.getEditOrderNumberChoice();
                Orders order = service.getEditTXTOrder(date, existingOrderNumber);                
                if (order == null){
                    System.out.println("");
                    System.out.println("No such \"Order Number\" exist.");
                    System.out.println(""); }                
                
                System.out.println("");
                System.out.println("State: "+order.getState()+" \"Select\" new state or press enter."); 
                String state = view.getStateChoice().toLowerCase();
                if(state.trim().length() == 0){                    
                 state = order.getState(); }
                Taxes taxes = service.getState(state); System.out.println(""); 
                System.out.println("");                 
        
                System.out.println("Item  | Cost  | Labor Cost");
                List<Products> productList;
                productList = service.listAllProducts();
                view.displayProductList(productList); System.out.println("");                
                
                System.out.println("Product: "+order.getProductType()+" \"Select\" new product or press enter.");
                String productType = view.getProductTypeChoice().toLowerCase();
                if(productType.trim().length() == 0){
                 productType = order.getProductType(); }
                Products products = service.getProduct(productType); System.out.println("");                                  
        
                
                Orders newEditedOrder = view.editExistingOrderInfo22(order ,productList, taxes, products);            
        try {
                view.displayVerifyEditedOrderSummary(newEditedOrder);
                service.editOrder(date, newEditedOrder);
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
    private void removeOrder() throws FlooringMasteryPersistenceException,                                     
                                    FloorMasteryValidateSubmitException,
                                    FlooringMasteryDataValidationException,
                                    FlooringMasteryDuplicateIdException {
        
        view.displayRemoveOrderBanner();
        boolean hasErrors = false;
        do{
                String date = view.getOrderDateChoice();        
                String existingOrderNumber = view.getOrderNumberChoice();
                Orders order = service.getEditTXTOrder(date, existingOrderNumber);        
        try {
                view.displayOrderSummary(order);        
                service.removeOrder(date, existingOrderNumber, order);
                view.displayRemoveOrderSuccessfulBanner();
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
    // -- "END" REMOVE ORDER  SECTION --
    
    //---------------------------------------------------------| 
        
    // -- LIST ALL ORDERS  SECTION --    
    private void listAllOrders(LocalDate ld) throws FlooringMasteryPersistenceException {
        view.displayListAllOrdersBanner();
        List<Orders> orderList = service.getAllOrders(ld);
        view.displayListAllOrders(orderList);       
    }           
    // -- "END" LIST ALL ORDERS  SECTION --
    
    //---------------------------------------------------------|
    
    // -- LIST ALL ORDERS  SECTION --    
    private void listAllOrderNumbers(LocalDate ld) throws FlooringMasteryPersistenceException, 
                                              FlooringMasteryDuplicateIdException, 
                                              FloorMasteryValidateSubmitException, 
                                              FlooringMasteryDataValidationException {
        view.displayListAllOrdersBanner();
        List<Orders> orderList = service.getAllOrders(ld);
        view.displayListAllOrderNumbers(orderList);         
    }           
    // -- "END" LIST ALL ORDERS  SECTION --
    
    //---------------------------------------------------------|
    
    /*/ -- SEARCH BY ORDER NUMBERS  SECTION --   NOT IN USE*** 
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
    */ // -- "END" SEARCH BY ORDER NUMBERS  SECTION --
    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|
    
    // -- ADD TAXES INFORMATION  SECTION --    
    
    // -- ADD  SECTION --    
    private void addState() throws FlooringMasteryPersistenceException{
        view.displayAddStateBanner();
        Taxes newState = view.getNewStateInfo();
        service.addState(newState.getState(), newState);
        view.displayAddStateSuccessfulBanner();
    }           
    // -- "END" ADD  SECTION --
    
    //---------------------------------------------------------| 
    
    // -- REMOVE  SECTION --    
    private void removeState() throws FlooringMasteryPersistenceException{
        view.displayRemoveStateBanner();
        String stateAbbreviation = view.getStateChoice().toLowerCase();
        Taxes removeState = service.removeState(stateAbbreviation);
        
        if (stateAbbreviation != null){
        view.displayRemoveStateSuccessfulBanner();
        } else {
           listAllStates(); 
        }
    }        
    // -- "END" REMOVE  SECTION --
    
    //---------------------------------------------------------|
        
    // -- FIND  SECTION --    
    private void findState() throws FlooringMasteryPersistenceException{
        view.displayFindStateBanner();
        String state = view.getStateChoice().toLowerCase();
        Taxes taxes = service.getState(state);
        view.displayFindState(taxes);        
    }         
    // -- "END" FIND  SECTION --
    
    //---------------------------------------------------------|            
    
    // -- EDIT  SECTION --
    private void editState() throws FlooringMasteryPersistenceException{
        view.displayEditStateBanner();
        Taxes newState = view.getNewStateInfo();
        service.addState(newState.getState(), newState);
            }           
    // -- "END" EDIT  SECTION --
    
    //---------------------------------------------------------| 
        
    // -- LIST  SECTION --         
    private void listAllStates() throws FlooringMasteryPersistenceException{
        view.displayListAllStatesBanner();
        List<Taxes> stateList = service.listAllStates();
        view.displayStateList(stateList);
    }    
    // -- "END" LIST  SECTION --
    
    
    // -- "END" ADD TAXES INFORMATION  SECTION --
    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|
    
    // -- ADD PRODUCTS INFORMATION  SECTION --    
    
    // -- ADD  SECTION --    
    private void addProduct() throws FlooringMasteryPersistenceException{
        view.displayAddProductBanner();
        Products newProduct = view.getNewProductInfo();
        service.addProduct(newProduct.getProductType(), newProduct);
        view.displayAddProductSuccessfulBanner();
    }           
    // -- "END" ADD  SECTION --
    
    //---------------------------------------------------------| 
    
    // -- REMOVE  SECTION --    
    private void removeProduct() throws FlooringMasteryPersistenceException{
        view.displayRemoveProductBanner();
        String removeProductType = view.getProductTypeChoice().toLowerCase();
        Products removeProduct = service.removeProduct(removeProductType);
        
        if (removeProductType != null){
        view.displayRemoveProductSuccessfulBanner();
        } else {
           listAllProducts(); 
        }
    }        
    // -- "END" REMOVE  SECTION --
    
    //---------------------------------------------------------|
        
    // -- FIND  SECTION --    
    private void findProduct() throws FlooringMasteryPersistenceException{
        view.displayFindProductBanner();
        String productType = view.getProductTypeChoice().toLowerCase();
        Products product = service.getProduct(productType);
        view.displayFindProduct(product);        
    }         
    // -- "END" FIND  SECTION --
    
    //---------------------------------------------------------|            
    
    // -- EDIT  SECTION --
    private void editProduct() throws FlooringMasteryPersistenceException{
        view.displayEditProductBanner();
        Products newProduct = view.getNewProductInfo();
        service.addProduct(newProduct.getProductType(), newProduct);
        }           
    // -- "END" EDIT  SECTION --
    
    //---------------------------------------------------------| 
        
    // -- LIST  SECTION --         
    private void listAllProducts() throws FlooringMasteryPersistenceException{
        view.displayListAllProductsBanner();
        List<Products> productList = service.listAllProducts();
        view.displayProductList(productList);
    }    
    // -- "END" LIST  SECTION --
    
    
    // -- "END" ADD PRODUCTS INFORMATION  SECTION --
    
    //---------------------------------------------------------|
}
