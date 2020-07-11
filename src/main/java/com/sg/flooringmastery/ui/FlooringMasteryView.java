/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Orders;
import com.sg.flooringmastery.dto.Products;
import com.sg.flooringmastery.dto.Taxes;
import com.sg.flooringmastery.service.FlooringMasteryDataValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author travi
 */
public class FlooringMasteryView {
   
    private UserIO io;        
    
    
    // -- CONSTRUCTOR -- 
    public FlooringMasteryView (UserIO io){
        this.io = io;        
    }
    // -- "END" CONSTRUCTOR -- 
    
    public int printMenuGetUserSelection(){
        
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders ");
        io.print("2. Add an Order ");
        io.print("3. Edit an Order ");
        io.print("4. Remove an Order ");                
        io.print("5. States *Menu ");
        io.print("6. Products *Menu ");
        io.print("7. Quit");
            
        return io.readInt("Please select from the menu above. ", 1, 7);
    }   
    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|
    
    public int printTaxesMenuGetUserSelection(){
        
        io.print("<<Taxes - States>>");
        io.print("1. Add State ");
        io.print("2. Remove State ");
        io.print("3. Find State ");               
        io.print("4. Edit State ");
        io.print("5. List All States ");
        io.print("6. Exit ");      
            
        return io.readInt("Please select from the menu above. ", 1, 6);
    }     
    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|
    
    public int printProductMenuGetUserSelection(){
        
        io.print("<<Taxes - States>>");
        io.print("1. Add Product ");
        io.print("2. Remove Product ");
        io.print("3. Find Product ");               
        io.print("4. Edit Product ");
        io.print("5. List All Products ");
        io.print("6. Exit ");      
            
        return io.readInt("Please select from the menu above. ", 1, 6);
    }   
    
    //---------------------------------------------------------|
    //---------------------------------------------------------|
    
    
    // -- ERROR MESSAGE SECTION --
    public void displayErrorMessage(String errorMsg) {
	io.print("=== ERROR ===");
	io.print(errorMsg);
    }  
    public void displayDateErrorMessage(String errorMsg) {
	    io.print("=== Date Format Error ===");
            io.print("Please Use Date Format: YYYY-MM-DD");
	    //io.print(errorMsg);
    }
    // -- "END" ERROR MESSAGE SECTION --
    
    //---------------------------------------------------------|
    
    // -- Menu Section --
    public void displayUnknownMenuBanner(){
        io.print("Please select from the menu above.");
    }        
    public void displayExitMenuBanner(){
        io.print("Thank you!");
    }
        
    // -- "END" Menu Section --
    
    // ---------------------------------------------| 
 
    // -- DISPLAY ORDERS  SECTION --         
    public void displayOrdersListBanner() {
	io.print("=== Customer's Orders ===");        
    }   
    public String getOrderDateChoice(){
        return io.readString("Please enter Order Date. Formmat \"MMDDYYYY\" ");
    }    
    /*public void displayDidNotFindDateTXT(){        
	io.print("No such Orders exist.");         
        io.readString("Press enter to continue.");
    } */
    public void displayOrdersListSuccessBanner() {
	io.print("\n=== Orders List Complete ===");        
    }
    // -- "END" DISPLAY ORDERS  SECTION --
    
    //---------------------------------------------------------|      
        
    // -- ADD ORDER  SECTION -- 
    public void displayCreateOrderBanner() {
	io.print("=== Create New Order ===");        
    }
    
    public Orders getNewOrderInfo(List<Products> productList, Taxes taxes, Products products){           
        
        boolean hasOtherChars=true;
        String area="";
        do {
        area = io.readString("Enter Projected Area.(100ft Min) "); 
        int myArea = area.length(); 
        for (int i = 0; i < myArea; i++) {
            if(Character.isDigit(area.charAt(i)) == false){
                hasOtherChars=true; } else {
                hasOtherChars=false; }
            }
        } while(hasOtherChars);io.print("");    
        
        
        String orderDateStub = io.readString("Please enter \"Future\" Order Fulfillment Date."+"\n"
                + " Formmat \"YYYY-MM-DD\" ");io.print("");
        String customerName = io.readString("Enter Customer Name. ");io.print("");  
        
        String orderNumber="";
        //String orderNumber = io.readString("\"Order Number - System Generated\". Please press enter. ");io.print("");

        //displayFindState(taxes);           
        //displayFindProduct(products);
        
        // -- CREATING AN OBJECT --        /
        Orders currentOrder = new Orders(orderNumber);// -- OrderNumber of the "New Order"        
        // -- CREATING AN OBJECT --
        
        LocalDate orderDate;
        try {            
        
        orderDate = LocalDate.parse(orderDateStub, DateTimeFormatter.ISO_DATE);
        currentOrder.setOrderDate(orderDate);
        
        } catch(DateTimeParseException e){           
                displayDateErrorMessage(e.getMessage());
        }
        
        currentOrder.setCustomerName(customerName);
        currentOrder.setState(taxes.getState());
        
        String convertTaxRate = taxes.getTaxRate().toString();        
        currentOrder.setTaxRate(convertTaxRate);  //Taxes
        
        currentOrder.setProductType(products.getProductType());  //Products
        currentOrder.setArea(area);
        
        
        
        String convertCPSF = products.getCostPerSquareFoot().toString();
        currentOrder.setCostPerSquareFoot(convertCPSF);  //Products
        String convertLCPSF = products.getLaborCostPerSquareFoot().toString();
        currentOrder.setLaborCostPerSquareFoot(convertLCPSF);  //Products
        
        System.out.println("Area: "+area);
        System.out.println("Tax Rate: "+taxes.getTaxRate());
        System.out.println("Product Cost: "+products.getCostPerSquareFoot());
        System.out.println("Labor Cost: "+products.getLaborCostPerSquareFoot());
        
        
        BigDecimal decimalArea = new BigDecimal(area);
        String materialCost = "0";       
        BigDecimal decimalMaterialCost = new BigDecimal(materialCost); 
        //materialCost = area * costPerSquareFoot
        decimalMaterialCost = decimalArea.multiply(products.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
        materialCost = decimalMaterialCost.toString();
        currentOrder.setMaterialCost(materialCost);
        //---------------------------------------------------------|
        String laborCost = "0";
        BigDecimal decimalLaborCost = new BigDecimal(laborCost);
        //laborCost = (area * laborCostPerSquareFoot)
        decimalLaborCost = decimalArea.multiply(products.getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
        laborCost = decimalLaborCost.toString();
        currentOrder.setLaborCost(laborCost);
        //---------------------------------------------------------|
        String tax = "0";
        BigDecimal decimalTax = new BigDecimal(tax);
        //Tax = ((MaterialCost + LaborCost) * (TaxRate/100))
        decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(taxes.getTaxRate()).setScale(2, RoundingMode.HALF_UP);
        tax = decimalTax.toString();
        currentOrder.setTax(tax);
        //---------------------------------------------------------|
        String total = "0";
        BigDecimal decimalTotal = new BigDecimal(total); 
        //Total = (MaterialCost + LaborCost + Tax)
        decimalTotal = decimalMaterialCost.add(decimalLaborCost).add(decimalTax).setScale(2, RoundingMode.HALF_UP);
        total = decimalTotal.toString(); 
        currentOrder.setTotal(total);        
        
      
       
        return currentOrder;         
               
    }    
    
    public void displayVerifyOrderSummary(Orders newOrder){        
       
            io.print("");
            io.print("  ===  Order Summary  ===  ");
            io.print("Order Number: "+newOrder.getOrderNumber());
            io.print("Order Date: "+newOrder.getOrderDate());
	    io.print("Customer Name: "+newOrder.getCustomerName());
	    io.print("Customer State: "+newOrder.getState());
            io.print("Tax Rate: $"+newOrder.getTaxRate());
	    io.print("Product Type: "+newOrder.getProductType());
            io.print("Projected Area: "+newOrder.getArea());            
	    io.print("Product Cost Per Square Foot: $"+newOrder.getCostPerSquareFoot());
	    io.print("Labor Cost Per Square Foot: $"+newOrder.getLaborCostPerSquareFoot());
	    io.print("Material Cost: $"+newOrder.getMaterialCost());
            io.print("Labor Cost: $"+newOrder.getLaborCost());
            io.print("Tax: $"+newOrder.getTax());
            io.print("Total: $"+newOrder.getTotal());    
            io.print("  ===  END:  Order Summary  ===  ");
            io.print("");  
            
            //io.readString("Press enter to continue.");
            String submitOrder = io.readString("Would you like to \"Submit\" this order? Y/N");          
            newOrder.setSubmitOrder(submitOrder);
            
    }    
    public void displayCreateOrderSuccessBanner(){
        io.readString("New Order successfully "
                + "Created.  Please hit enter to continue. ");
    }
             
    // -- "END" ADD ORDER  SECTION --
    
    //---------------------------------------------------------|    
        
    // -- EDIT ORDER  SECTION -- 
    public void displayEditOrderBanner() {
	io.print("=== Edit Order ===");
    } 
    public String getEditOrderDateChoice(){
        return io.readString("Please enter Order Date. Formmat \"MMDDYYYY\" ");
    }
    //public String getEditOrderNumberChoice(Orders selectDate){
    public String getEditOrderNumberChoice(){
        return io.readString("\n\"Which Order Number would you like to edit.");
    }
    public void displayFindOrder(Orders order) {
	if (order != null) {
            
            io.print("");
	    io.print("  ===  Order Summary  ===  ");
            io.print("Order Number: "+order.getOrderNumber());
	    io.print("Customer Name: "+order.getCustomerName());
	    io.print("Customer State: "+order.getState());
            io.print("Tax Rate: $"+order.getTaxRate());
	    io.print("Product Type: "+order.getProductType());
            io.print("Projected Area: "+order.getArea());            
	    io.print("Product Cost Per Square Foot: $"+order.getCostPerSquareFoot());
	    io.print("Labor Cost Per Square Foot: $"+order.getLaborCostPerSquareFoot());
	    io.print("Material Cost: $"+order.getMaterialCost());
            io.print("Labor Cost: $"+order.getLaborCost());
            io.print("Tax: $"+order.getTax());
            io.print("Total: $"+order.getTotal());    
            io.print("  ===  END:  Order Summary  ===  ");
            io.print("");
            
	} else {
	    io.print("No such \"Order\" exist.");
	}
	    io.readString("Press enter to continue.");
            
    }

    
    public Orders editExistingOrderInfo22(Orders order, List<Products> productList, Taxes taxes, Products products){ 
        
        boolean hasOtherChars=true;
        String area="";
        do {
        area = io.readString("Enter Projected Area. "+order.getArea()+"\n"+"or press enter. (100ft Min)"); 
        int myArea = area.length(); 
            for (int i = 0; i < myArea; i++) {
            if(Character.isDigit(area.charAt(i)) == false){
                hasOtherChars=true;  } else {
                hasOtherChars=false; }
            }
            if(area.trim().length() == 0){
            area = order.getArea(); 
            hasOtherChars=false; }  
        } while(hasOtherChars);io.print("");         
              
        
        String customerName = io.readString("Enter Customer Name. "+order.getCustomerName()+"\n"+
                "or press enter.");
        if(customerName.trim().length() == 0){
            customerName = order.getCustomerName(); }io.print("");
            
            
        LocalDate orderDate = order.getOrderDate(); io.print("");    
                
        
        String orderNumber = order.getOrderNumber(); io.print("");
            
        
        //displayFindState(taxes);           
        //displayFindProduct(products);
        
        // -- CREATING AN OBJECT --
        Orders currentOrder = new Orders(orderNumber);// -- OrderNumber of the "New Order"   
        // -- CREATING AN OBJECT --
        
                    
        currentOrder.setOrderDate(orderDate);               
               
        currentOrder.setCustomerName(customerName);       
        
        currentOrder.setState(taxes.getState());
        
        
        String convertTaxRate = taxes.getTaxRate().toString();        
        currentOrder.setTaxRate(convertTaxRate);  //Taxes
        
        currentOrder.setProductType(products.getProductType());  //Products
        currentOrder.setArea(area); 
        String convertCPSF = products.getCostPerSquareFoot().toString();
        currentOrder.setCostPerSquareFoot(convertCPSF);  //Products
        String convertLCPSF = products.getLaborCostPerSquareFoot().toString();
        currentOrder.setLaborCostPerSquareFoot(convertLCPSF);  //Products
        
        System.out.println("Area: "+area);
        System.out.println("Tax Rate: "+taxes.getTaxRate());
        System.out.println("Product Cost: "+products.getCostPerSquareFoot());
        System.out.println("Labor Cost: "+products.getLaborCostPerSquareFoot());
        
                
        //System.out.println("Area: "+area);
        //System.out.println("Tax Rate: "+taxes.getTaxRate());
        //System.out.println("Product Cost: "+products.getCostPerSquareFoot());
        //System.out.println("Labor Cost: "+products.getLaborCostPerSquareFoot());
        
        
        BigDecimal decimalArea = new BigDecimal(area);
        String materialCost = "0";       
        BigDecimal decimalMaterialCost = new BigDecimal(materialCost); 
        //materialCost = area * costPerSquareFoot
        decimalMaterialCost = decimalArea.multiply(products.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
        materialCost = decimalMaterialCost.toString();
        currentOrder.setMaterialCost(materialCost);
        //---------------------------------------------------------|
        String laborCost = "0";
        BigDecimal decimalLaborCost = new BigDecimal(laborCost);
        //laborCost = (area * laborCostPerSquareFoot)
        decimalLaborCost = decimalArea.multiply(products.getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
        laborCost = decimalLaborCost.toString();
        currentOrder.setLaborCost(laborCost);
        //---------------------------------------------------------|
        String tax = "0";
        BigDecimal decimalTax = new BigDecimal(tax);
        //Tax = ((MaterialCost + LaborCost) * (TaxRate/100))
        decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(taxes.getTaxRate()).setScale(2, RoundingMode.HALF_UP);
        tax = decimalTax.toString();
        currentOrder.setTax(tax);
        //---------------------------------------------------------|
        String total = "0";
        BigDecimal decimalTotal = new BigDecimal(total); 
        //Total = (MaterialCost + LaborCost + Tax)
        decimalTotal = decimalMaterialCost.add(decimalLaborCost).add(decimalTax).setScale(2, RoundingMode.HALF_UP);
        total = decimalTotal.toString(); 
        currentOrder.setTotal(total);                        
       
        return currentOrder;         
               
    }
    
    public void displayEditOrderSuccessBanner(){
        io.readString("Edit successful. "
                + "Please hit enter to continue. ");
    }

    public void displayVerifyEditedOrderSummary(Orders newEditedOrder){        
       
            io.print("");
            io.print("  ===  Order Summary  ===  ");
            io.print("Order Number: "+newEditedOrder.getOrderNumber());
            io.print("Order Date: "+newEditedOrder.getOrderDate());
	    io.print("Customer Name: "+newEditedOrder.getCustomerName());
	    io.print("Customer State: "+newEditedOrder.getState());
            io.print("Tax Rate: $"+newEditedOrder.getTaxRate());
	    io.print("Product Type: "+newEditedOrder.getProductType());
            io.print("Projected Area: "+newEditedOrder.getArea());            
	    io.print("Product Cost Per Square Foot: $"+newEditedOrder.getCostPerSquareFoot());
	    io.print("Labor Cost Per Square Foot: $"+newEditedOrder.getLaborCostPerSquareFoot());
	    io.print("Material Cost: $"+newEditedOrder.getMaterialCost());
            io.print("Labor Cost: $"+newEditedOrder.getLaborCost());
            io.print("Tax: $"+newEditedOrder.getTax());
            io.print("Total: $"+newEditedOrder.getTotal());    
            io.print("  ===  END:  Order Summary  ===  ");
            io.print("");  
            
            //io.readString("Press enter to continue.");
            String submitOrder = io.readString("Would you like to \"Submit\" this order? Y/N");          
            newEditedOrder.setSubmitOrder(submitOrder);
            
    }
    // -- "END" EDIT ORDER  SECTION --
    
    //---------------------------------------------------------|
    
    // -- REMOVE ORDER  SECTION --    
    public void displayRemoveOrderBanner() {
	io.print("=== Remove Order ===");
    } 
     public String getOrderNumberChoice(){
        return io.readString("Please enter Order Number.");
    }
    public void displayOrderSummary(Orders newOrder){        
       
            io.print("");
            io.print("  ===  Order Summary  ===  ");
            io.print("Order Number: "+newOrder.getOrderNumber());
            io.print("Order Date: "+newOrder.getOrderDate());
	    io.print("Customer Name: "+newOrder.getCustomerName());
	    io.print("Customer State: "+newOrder.getState());
            io.print("Tax Rate: $"+newOrder.getTaxRate());
	    io.print("Product Type: "+newOrder.getProductType());
            io.print("Projected Area: "+newOrder.getArea());            
	    io.print("Product Cost Per Square Foot: $"+newOrder.getCostPerSquareFoot());
	    io.print("Labor Cost Per Square Foot: $"+newOrder.getLaborCostPerSquareFoot());
	    io.print("Material Cost: $"+newOrder.getMaterialCost());
            io.print("Labor Cost: $"+newOrder.getLaborCost());
            io.print("Tax: $"+newOrder.getTax());
            io.print("Total: $"+newOrder.getTotal());    
            io.print("  ===  END:  Order Summary  ===  ");
            io.print(""); 
            
            String submitOrder = io.readString("Would you like to \"Remove\" this order? Y/N");          
            newOrder.setSubmitOrder(submitOrder);
        
   } 
    public void displayRemoveOrderSuccessfulBanner() {
	io.readString("Order successfully removed. "
                + "Press enter to continue.");
    }
    // -- "END" REMOVE ORDER  SECTION --
    
    //---------------------------------------------------------|

    // -- LIST ALL ORDERS  SECTION -- 
    public void displayListAllOrdersBanner() {
	io.print("=== List All Orders ===");
    }
    public void displayListAllOrders(List<Orders> orderList){        
        for (Orders existingOrders : orderList){
            io.print("");
	    io.print("  ===  Order Summary  ===  ");
            io.print("Order Number: "+existingOrders.getOrderNumber());
            io.print("Order Date: "+existingOrders.getOrderDate());
	    io.print("Customer Name: "+existingOrders.getCustomerName());
	    io.print("Customer State: "+existingOrders.getState());
            io.print("Tax Rate: $"+existingOrders.getTaxRate());
	    io.print("Product Type: "+existingOrders.getProductType());
            io.print("Projected Area: "+existingOrders.getArea());            
	    io.print("Product Cost Per Square Foot: $"+existingOrders.getCostPerSquareFoot());
	    io.print("Labor Cost Per Square Foot: $"+existingOrders.getLaborCostPerSquareFoot());
	    io.print("Material Cost: $"+existingOrders.getMaterialCost());
            io.print("Labor Cost: $"+existingOrders.getLaborCost());
            io.print("Tax: $"+existingOrders.getTax());
            io.print("Total: $"+existingOrders.getTotal());
            io.print("  ===  END:  Order Summary  ===  ");
            io.print("");           
        }      
    }
         
    // -- "END" LIST ALL ORDERS  SECTION --
    
    //---------------------------------------------------------|    
    
    // -- LIST ALL ORDERS  SECTION -- 
    public void displayListAllOrderNumberBanner() {
	io.print("=== List All Order Numbers ===");
    }
    public void displayListAllOrderNumbers(List<Orders> orderList){        
        for (Orders orderNumbers : orderList){            	    
            io.print(orderNumbers.getOrderNumber());                       
        }      
    }
         
    // -- "END" LIST ALL ORDERS  SECTION --
    
    //---------------------------------------------------------| 
    // -- NOT IN USE --
    // -- SEARCH BY ORDER NUMBERS  SECTION --
    public void displayFindOrderNumberBanner () {
	    io.print("=== Display Order Numbers ===");
    }
    public String selectOrderNumberChoice(){
            return io.readString("Select \"Order Number\".");
    }
    // -- "END" SEARCH BY ORDER NUMBERS  SECTION --   
    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|
    
    // -- ADD TAXES INFORMATION  SECTION --    
    // -- ADD  SECTION --
    public Taxes getNewStateInfo(){ // New "Info" from the User
        String state = io.readString("Enter State Name:");
        String taxRate = io.readString("Enter State Tax Rate:");  
        
    // -- Create a new "Taxes" object --
        Taxes currentState = new Taxes(state); 
        // -- Values of the "New Taxes" -- 
        //currentState.setTaxRate(taxRate);
        
        return currentState;    
    }
    public void displayAddStateBanner(){
        io.print("=== Add New State ===");
    }
    public void displayAddStateSuccessfulBanner(){
        io.readString("New State added successfully. "
                    + "Press \"Enter\" to continue.");
    }              
    // -- "END" ADD  SECTION --
    
     // -- REMOVE  SECTION --    
    public void displayRemoveStateBanner () {
	io.print("=== Remove State ===");
    }
    public void displayRemoveStateSuccessfulBanner () {
	io.readString("State successfully removed. "
                + "Press enter to continue.");
    }        
    // -- "END" REMOVE  SECTION --
    
    // -- FIND  SECTION --
    public void displayFindStateBanner () {
	io.print("=== Display State ===");
    }
    public String getStateChoice() {
	return io.readString("Enter Customer State.");        
    } 
    public void displayFindState(Taxes state) {
	if (state != null) {
	    io.print(state.getState());
	    io.print("$"+state.getTaxRate());
	                    
	} else {
	    io.print("No such State exist.");
	}
	    io.readString("Press enter to continue.");
    }     
    public void displayFindStateValidation(Taxes state) {
	if (state != null){            
            
        } else {	    
                io.print("INVALID STATE: [oh, mi, pa, in] "); 
        }
    }
             
    // -- "END" FIND  SECTION --
    
    // -- EDIT  SECTION --    
    public void displayEditStateBanner() {
	io.print("=== Edit State ===");
    }        
    // -- "END" EDIT  SECTION --
    
    // -- LIST  SECTION --         
    public void displayStateList(List<Taxes> stateList){
        // -- List title and value for each created object --
        for (Taxes currentState : stateList){ 
            io.print(currentState.getState() + " $"
                    + currentState.getTaxRate());
                    
        }
	io.readString("Press enter to continue.");        
    }    
    public void displayListAllStatesBanner() {
	io.print("=== Display All States ===");
    }         
    // -- "END" LIST  SECTION --
    
    
    
    // -- "END" ADD TAXES INFORMATION  SECTION --
    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|
    
    // -- ADD PRODUCTS INFORMATION  SECTION --    
    // -- ADD  SECTION --
    public Products getNewProductInfo(){ // New "Info" from the User
        String productType = io.readString("Enter Product: ");        
        String costPerSquareFoot = io.readString("Enter Cost Per Square Foot: ");
        String laborCostPerSquareFoot = io.readString("Enter Labor Cost Per Square Foot: ");

        // -- Create a new "Products" object --
        Products currentProduct = new Products(productType.toLowerCase()); 
        // -- Values of the "New Products" -- 
        currentProduct.setCostPerSquareFoot(costPerSquareFoot);
        currentProduct.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
        
        return currentProduct;    
    }
    public void displayAddProductBanner(){
        io.print("=== Add New Product ===");
    }
    public void displayAddProductSuccessfulBanner(){
        io.readString("New Product added successfully. "
                    + "Press \"Enter\" to continue.");
    }              
    // -- "END" ADD  SECTION --
    
     // -- REMOVE  SECTION --    
    public void displayRemoveProductBanner () {
	io.print("=== Remove Product ===");
    }
    public void displayRemoveProductSuccessfulBanner () {
	io.readString("Product successfully removed. "
                + "Press enter to continue.");
    }        
    // -- "END" REMOVE  SECTION --
    
    // -- FIND  SECTION --
    public void displayFindProductBanner () {
	io.print("=== Display Product ===");
    }
    public String getProductTypeChoice() {
	return io.readString("Enter Product Type.");
    } 
    public void displayFindProduct(Products product) {
	if (product != null) {
	    io.print(product.getProductType());
	    io.print("$"+product.getCostPerSquareFoot());
	    io.print("$"+product.getLaborCostPerSquareFoot());
	                    
	} else {
	    io.print("No such Product exist.");
	}
	    io.readString("Press enter to continue.");
    }
    public void displayFindProductValidation(Products product) {
	if (product != null) {
	    	                    
	} else {
	    io.print("INVALID PRODUCT TYPE: [wood, tile, carpet, laminate]");
	}	    
    }             
    // -- "END" FIND  SECTION --
    
    // -- EDIT  SECTION --    
    public void displayEditProductBanner() {
	io.print("=== Edit Product ===");
    }        
    // -- "END" EDIT  SECTION --
    
    // -- LIST  SECTION --         
    public void displayProductList(List<Products> productList){
        // -- List title and value for each created object --
        for (Products currentProduct : productList){ 
            io.print(currentProduct.getProductType() + ": $"
	            + currentProduct.getCostPerSquareFoot() + " | $"
                    + currentProduct.getLaborCostPerSquareFoot());                    
        }
	//io.readString("Press enter to continue.");        
    }    
    public void displayListAllProductsBanner() {
	io.print("=== Display All Products ===");
    }          
    // -- "END" LIST  SECTION --
    
    // -- "END" ADD PRODUCTS INFORMATION  SECTION --
    
    //---------------------------------------------------------|
    

}
