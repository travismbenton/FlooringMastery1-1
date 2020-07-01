/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Orders;
import com.sg.flooringmastery.dto.ProductLaborCost;
import com.sg.flooringmastery.dto.ProductPrice;
import com.sg.flooringmastery.dto.TaxRates;
import java.io.File;
import static java.lang.System.out;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import org.eclipse.core.internal.runtime.Product;

/**
 *
 * @author travi
 */
public class FlooringMasteryView {
   
    private UserIO io; 
    TaxRates taxRate;
    ProductPrice productPrice;
    ProductLaborCost productLaborCost; 
    
    // -- CONSTRUCTOR -- 
    public FlooringMasteryView (UserIO io, TaxRates taxRate, 
            ProductPrice productPrice, ProductLaborCost productLaborCost){
        this.io = io;
        this.taxRate = taxRate;
        this.productPrice = productPrice;
        this.productLaborCost = productLaborCost;        
    }
    // -- "END" CONSTRUCTOR -- 
    
    public int printMenuGetUserSelection(){
        
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders ");
        io.print("2. Add an Order ");
        io.print("3. Edit an Order ");
        io.print("4. Remove an Order ");
        io.print("5. Save Current Work ");
        io.print("6. List all Orders ");
        io.print("7. Seach by Order Number");
        io.print("8. List All Order Numbers");
        io.print("9. Quit");
            
        return io.readInt("Please select from the menu above. ", 1, 8);
    }   
    
    //---------------------------------------------------------|
    
    // -- ERROR MESSAGE SECTION --
    public void displayErrorMessage(String errorMsg) {
	io.print("=== ERROR ===");
	io.print(errorMsg);
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
    public void displayDidNotFindDateTXT(){        
	io.print("No such Orders exist.");         
        io.readString("Press enter to continue.");
    }
    public void displayOrdersListSuccessBanner() {
	io.print("\n=== Orders List Complete ===");        
    }
    // -- "END" DISPLAY ORDERS  SECTION --
    
    //---------------------------------------------------------|      
        
    // -- ADD ORDER  SECTION -- 
    public void displayCreateOrderBanner() {
	io.print("=== Create New Order ===");        
    }
    public Orders getNewOrderInfo(){
        
        //LocalDate orderDate;// = LocalDate.now().format(DateTimeFormatter.ofPattern("MMDDYYYY"));
        String orderNumber = io.readString("\"System Generated\". Please press enter. ");
        String orderDate = io.readString("Please enter Order Date. Formmat \"MMDDYYYY\" ");
        String customerName = io.readString("Enter Customer Name. ");
        String state = io.readString("Enter Customer State. ");
        String productType = io.readString("Enter Product Type. ");
        String area = io.readString("Enter Projected Area.(100ft Min) "); 
        
        // -- Create a new "Order" object --
        Orders currentOrder = new Orders(orderNumber);// -- OrderNumber of the "New Order"
        
        currentOrder.setOrderDate(orderDate);
        currentOrder.setCustomerName(customerName);
        currentOrder.setState(state);
        // -- TAXE RATES --
        switch (state.toUpperCase()){
            case "OH":
                currentOrder.setTaxRate(taxRate.getOH());
                break;
            case "PA":
                currentOrder.setTaxRate(taxRate.getPA());                
                break;
            case "MI":
                currentOrder.setTaxRate(taxRate.getMI());                
                break;
            case "IN":
                currentOrder.setTaxRate(taxRate.getIN());                
                break;
            default:
                break;
        }        
        currentOrder.setProductType(productType);
        
        currentOrder.setArea(area);
        // -- COST PER SQUARE FOOT --
        switch (productType.toLowerCase()){
            case "carpet":
                currentOrder.setCostPerSquareFoot(productPrice.getCarpet());
                break;
            case "laminate":
                currentOrder.setCostPerSquareFoot(productPrice.getLaminate());                
                break;
            case "tile":
                currentOrder.setCostPerSquareFoot(productPrice.getTile());               
                break;
            case "wood":    
                currentOrder.setCostPerSquareFoot(productPrice.getWood());
            default:
                break;     
        }
        // -- LABOR COST PER SQUARE FOOT --
        switch (productType.toLowerCase()){
            case "carpet":
                currentOrder.setLaborCostPerSquareFoot(productLaborCost.getCarpet());
                break;
            case "laminate":
                currentOrder.setLaborCostPerSquareFoot(productLaborCost.getLaminate());                
                break;
            case "tile":
                currentOrder.setLaborCostPerSquareFoot(productLaborCost.getTile());               
                break;
            case "wood":
                currentOrder.setLaborCostPerSquareFoot(productLaborCost.getWood());                
                break;
            default:
                break;    
        }
        // -- CALCULATIONS --
        
        //materialCost = area * costPerSquareFoot
        String materialCost = "0";       
        BigDecimal decimalMaterialCost = new BigDecimal(materialCost);        
        BigDecimal decimalArea = new BigDecimal(area);        
        
        BigDecimal decimalCarpetCostPerSquareFoot = new BigDecimal(productPrice.getCarpet());
        BigDecimal decimalLaminateCostPerSquareFoot = new BigDecimal(productPrice.getLaminate());
        BigDecimal decimalTileCostPerSquareFoot = new BigDecimal(productPrice.getTile());
        BigDecimal decimalWoodCostPerSquareFoot = new BigDecimal(productPrice.getWood());
        //laborCost = (area * laborCostPerSquareFoot)
        String laborCost = "0";
        BigDecimal decimalLaborCost = new BigDecimal(laborCost);
        BigDecimal decimalCarpetLaborCostPerSquareFoot = new BigDecimal(productLaborCost.getCarpet());
        //System.out.println("Carpet Labor Cost Test: "+decimalCarpetLaborCostPerSquareFoot);
        BigDecimal decimalLaminateLaborCostPerSquareFoot = new BigDecimal(productLaborCost.getLaminate());
        BigDecimal decimalTileLaborCostPerSquareFoot = new BigDecimal(productLaborCost.getTile());
        BigDecimal decimalWoodLaborCostPerSquareFoot = new BigDecimal(productLaborCost.getWood());
        String tax = "0";
        BigDecimal decimalTax = new BigDecimal(tax);
        BigDecimal decimalOHTaxRate = new BigDecimal(taxRate.getOH());
        //System.out.println("Tax Test: "+decimalOHTaxRate);
        BigDecimal decimalPATaxRate = new BigDecimal(taxRate.getPA());
        BigDecimal decimalMITaxRate = new BigDecimal(taxRate.getMI());
        BigDecimal decimalINTaxRate = new BigDecimal(taxRate.getIN());
        //Total = (MaterialCost + LaborCost + Tax)
        String total = "0";
        BigDecimal decimalTotal = new BigDecimal(total);        
        
        // -- BIG DECIMAL  COST PER SQUARE FOOT --
        switch (productType.toLowerCase()){
            case "carpet":
                decimalMaterialCost = decimalArea.multiply(decimalCarpetCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                decimalLaborCost = decimalArea.multiply(decimalCarpetLaborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                break;
            case "laminate":
                decimalMaterialCost = decimalArea.multiply(decimalLaminateCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP); 
                decimalLaborCost = decimalArea.multiply(decimalLaminateLaborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP); 
                break;
            case "tile":
                decimalMaterialCost = decimalArea.multiply(decimalTileCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);   
                decimalLaborCost = decimalArea.multiply(decimalTileLaborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                break;
            case "wood":
                decimalMaterialCost = decimalArea.multiply(decimalWoodCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);  
                decimalLaborCost = decimalArea.multiply(decimalWoodLaborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                break; 
            default:
                break;     
        }        
        
        // -- BIG DECIMAL  TAX RATES --
        switch (state.toUpperCase()){
            case "OH":
                decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(decimalOHTaxRate).setScale(2, RoundingMode.HALF_UP);
                break;
            case "PA":
                 decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(decimalPATaxRate).setScale(2, RoundingMode.HALF_UP);               
                break;
            case "MI":
                 decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(decimalMITaxRate).setScale(2, RoundingMode.HALF_UP);              
                break;
            case "IN":
                 decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(decimalINTaxRate).setScale(2, RoundingMode.HALF_UP);               
                break;
            default:
                break;
        } 
        
        //Total = (MaterialCost + LaborCost + Tax)
        decimalTotal = decimalMaterialCost.add(decimalLaborCost).add(decimalTax).setScale(2, RoundingMode.HALF_UP);
        
        // -- Big Decimal  Conversion back to String --
        materialCost = decimalMaterialCost.toString();
        currentOrder.setMaterialCost(materialCost);
        laborCost = decimalLaborCost.toString();
        currentOrder.setLaborCost(laborCost);        
        tax = decimalTax.toString();
        currentOrder.setTax(tax);
        total = decimalTotal.toString();        
        currentOrder.setTotal(total);   
        
                          
       
        return currentOrder;         
               
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
    public Orders editExistingOrderInfo(Orders order){                
        
        String orderNumber = io.readString("Editing Order Number: "+order.getOrderNumber());
        String orderDate = io.readString("Enter Order Fullfillment Date. "+order.getOrderDate());
        String customerName = io.readString("Enter Customer Name. "+order.getCustomerName());   
        String state = io.readString("Enter State. "+order.getState());        
        String productType = io.readString("Enter Product Type. "+order.getProductType());
        String area = io.readString("Enter Projected Area. "+order.getArea());        
        
        // -- Create a new "Order" object --
        Orders currentOrder = new Orders(orderNumber);// -- OrderNumber of the "New Order" 
        
        if(orderNumber.trim().length() == 0){
            currentOrder.setOrderNumber(order.getOrderNumber());
        } else {
            currentOrder.setOrderNumber(orderNumber);
        }
        
        if(orderDate.trim().length() == 0){
            currentOrder.setOrderDate(order.getOrderDate());
        } else {       
            currentOrder.setOrderDate(orderDate);
        }
        
        if(customerName.trim().length() == 0){
            currentOrder.setCustomerName(order.getCustomerName());
        } else {
            currentOrder.setCustomerName(customerName);
        }
                   
        if(state.trim().length() == 0){
            currentOrder.setState(order.getState());
        } else {
            currentOrder.setState(state);
        }
        
        // -- TAXE RATES --
        switch (state.toUpperCase()){
            case "OH":
                currentOrder.setTaxRate(taxRate.getOH());
                break;
            case "PA":
                currentOrder.setTaxRate(taxRate.getPA());                
                break;
            case "MI":
                currentOrder.setTaxRate(taxRate.getMI());                
                break;
            case "IN":
                currentOrder.setTaxRate(taxRate.getIN());                
                break;
            default:
                break;
        }        
        
        if(productType.trim().length() == 0){
            currentOrder.setProductType(order.getProductType());
        } else {
            currentOrder.setProductType(productType);
        }
        
        if(area.trim().length() == 0){
            currentOrder.setArea(order.getArea());
        } else {
            currentOrder.setArea(area);
        }
        
        // -- COST PER SQUARE FOOT --
        switch (productType.toLowerCase()){
            case "carpet":
                currentOrder.setCostPerSquareFoot(productPrice.getCarpet());
                break;
            case "laminate":
                currentOrder.setCostPerSquareFoot(productPrice.getLaminate());                
                break;
            case "tile":
                currentOrder.setCostPerSquareFoot(productPrice.getTile());               
                break;
            case "wood":    
                currentOrder.setCostPerSquareFoot(productPrice.getWood());
            default:
                break;     
        }
        // -- LABOR COST PER SQUARE FOOT --
        switch (productType.toLowerCase()){
            case "carpet":
                currentOrder.setLaborCostPerSquareFoot(productLaborCost.getCarpet());
                break;
            case "laminate":
                currentOrder.setLaborCostPerSquareFoot(productLaborCost.getLaminate());                
                break;
            case "tile":
                currentOrder.setLaborCostPerSquareFoot(productLaborCost.getTile());               
                break;
            case "wood":
                currentOrder.setLaborCostPerSquareFoot(productLaborCost.getWood());                
                break;
            default:
                break;    
        }
        // -- CALCULATIONS --
        
        //materialCost = area * costPerSquareFoot
        String materialCost = "0";       
        BigDecimal decimalMaterialCost = new BigDecimal(materialCost);
        BigDecimal decimalArea = new BigDecimal(area);  
        BigDecimal decimalCarpetCostPerSquareFoot = new BigDecimal(productPrice.getCarpet());
        BigDecimal decimalLaminateCostPerSquareFoot = new BigDecimal(productPrice.getLaminate());
        BigDecimal decimalTileCostPerSquareFoot = new BigDecimal(productPrice.getTile());
        BigDecimal decimalWoodCostPerSquareFoot = new BigDecimal(productPrice.getWood());
        //laborCost = (area * laborCostPerSquareFoot)
        String laborCost = "0";
        BigDecimal decimalLaborCost = new BigDecimal(laborCost);
        BigDecimal decimalCarpetLaborCostPerSquareFoot = new BigDecimal(productLaborCost.getCarpet());
        //System.out.println("Carpet Labor Cost Test: "+decimalCarpetLaborCostPerSquareFoot);
        BigDecimal decimalLaminateLaborCostPerSquareFoot = new BigDecimal(productLaborCost.getLaminate());
        BigDecimal decimalTileLaborCostPerSquareFoot = new BigDecimal(productLaborCost.getTile());
        BigDecimal decimalWoodLaborCostPerSquareFoot = new BigDecimal(productLaborCost.getWood());
        String tax = "0";
        BigDecimal decimalTax = new BigDecimal(tax);
        BigDecimal decimalOHTaxRate = new BigDecimal(taxRate.getOH());
        //System.out.println("Tax Test: "+decimalOHTaxRate);
        BigDecimal decimalPATaxRate = new BigDecimal(taxRate.getPA());
        BigDecimal decimalMITaxRate = new BigDecimal(taxRate.getMI());
        BigDecimal decimalINTaxRate = new BigDecimal(taxRate.getIN());
        //Total = (MaterialCost + LaborCost + Tax)
        String total = "0";
        BigDecimal decimalTotal = new BigDecimal(total);        
        
        // -- BIG DECIMAL  COST PER SQUARE FOOT --
        switch (productType.toLowerCase()){
            case "carpet":
                decimalMaterialCost = decimalArea.multiply(decimalCarpetCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                decimalLaborCost = decimalArea.multiply(decimalCarpetLaborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                break;
            case "laminate":
                decimalMaterialCost = decimalArea.multiply(decimalLaminateCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP); 
                decimalLaborCost = decimalArea.multiply(decimalLaminateLaborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP); 
                break;
            case "tile":
                decimalMaterialCost = decimalArea.multiply(decimalTileCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);   
                decimalLaborCost = decimalArea.multiply(decimalTileLaborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                break;
            case "wood":
                decimalMaterialCost = decimalArea.multiply(decimalWoodCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);  
                decimalLaborCost = decimalArea.multiply(decimalWoodLaborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                break; 
            default:
                break;     
        }        
        
        // -- BIG DECIMAL  TAX RATES --
        switch (state.toUpperCase()){
            case "OH":
                decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(decimalOHTaxRate).setScale(2, RoundingMode.HALF_UP);
                break;
            case "PA":
                 decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(decimalPATaxRate).setScale(2, RoundingMode.HALF_UP);               
                break;
            case "MI":
                 decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(decimalMITaxRate).setScale(2, RoundingMode.HALF_UP);              
                break;
            case "IN":
                 decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(decimalINTaxRate).setScale(2, RoundingMode.HALF_UP);               
                break;
            default:
                break;
        } 
        
        //Total = (MaterialCost + LaborCost + Tax)
        decimalTotal = decimalMaterialCost.add(decimalLaborCost).add(decimalTax).setScale(2, RoundingMode.HALF_UP);
        
        // -- Big Decimal  Conversion back to String --
        materialCost = decimalMaterialCost.toString();
        currentOrder.setMaterialCost(materialCost);
        laborCost = decimalLaborCost.toString();
        currentOrder.setLaborCost(laborCost);        
        tax = decimalTax.toString();
        currentOrder.setTax(tax);
        total = decimalTotal.toString();        
        currentOrder.setTotal(total);       
                        
       
        return currentOrder;         
               
    }    
    public void displayEditOrderSuccessBanner(){
        io.readString("Edit successful. "
                + "Please hit enter to continue. ");
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
    public void displayRemoveOrderSuccessfulBanner() {
	io.readString("Order successfully removed. "
                + "Press enter to continue.");
    }
    // -- "END" REMOVE ORDER  SECTION --
    
    //---------------------------------------------------------| 
        
    // -- SAVE CURRENT WORK  SECTION --         
    public String displaySaveCurrentWorkBanner(){
        return io.readString("Saving your selections. "
                + "Please hit the enter key to continue.");
    }
    public void displaySaveCurrentWorkSuccessBanner() {
	io.print("=== Selections Saved Successful. ===");        
    }
    // -- "END" SAVE CURRENT WORK  SECTION --
    
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
    
    // -- SEARCH BY ORDER NUMBERS  SECTION --
    public void displayFindOrderNumberBanner () {
	    io.print("=== Display Order Numbers ===");
    }
    public String selectOrderNumberChoice(){
            return io.readString("Select \"Order Number\".");
    }
    // -- "END" SEARCH BY ORDER NUMBERS  SECTION --
    
    
    public Orders testExistingOrderInfo(Orders order){                
        
        String date = io.readString("Enter Date: ");
        String orderNumber = io.readString("Order Number: "+order.getOrderNumber());
        String orderDate = io.readString("Enter Order Fullfillment Date. "+order.getOrderDate());
        String customerName = io.readString("Enter Customer Name. "+order.getCustomerName());   
        String state = io.readString("Enter State. "+order.getState());        
        String productType = io.readString("Enter Product Type. "+order.getProductType());
        String area = io.readString("Enter Projected Area. "+order.getArea());        
        
        // -- Create a new "Order" object --
        Orders currentOrder = new Orders(orderNumber);// -- OrderNumber of the "New Order" 
        
        if(orderNumber.trim().length() == 0){
            currentOrder.setOrderNumber(order.getOrderNumber());
        } else {
            currentOrder.setOrderNumber(orderNumber);
        }
        
        if(orderDate.trim().length() == 0){
            currentOrder.setOrderDate(order.getOrderDate());
        } else {       
            currentOrder.setOrderDate(orderDate);
        }
        
        if(customerName.trim().length() == 0){
            currentOrder.setCustomerName(order.getCustomerName());
        } else {
            currentOrder.setCustomerName(customerName);
        }
                   
        if(state.trim().length() == 0){
            currentOrder.setState(order.getState());
        } else {
            currentOrder.setState(state);
        }
        
        if(productType.trim().length() == 0){
            currentOrder.setProductType(order.getProductType());
        } else {
            currentOrder.setProductType(productType);
        } 
        
        if(area.trim().length() == 0){
            currentOrder.setArea(order.getArea());
        } else {
            currentOrder.setArea(area);
        }
        
        // -- TAXE RATES --
        switch (state.toUpperCase()){
            case "OH":
                currentOrder.setTaxRate(taxRate.getOH());
                break;
            case "PA":
                currentOrder.setTaxRate(taxRate.getPA());                
                break;
            case "MI":
                currentOrder.setTaxRate(taxRate.getMI());                
                break;
            case "IN":
                currentOrder.setTaxRate(taxRate.getIN());                
                break;
            default:
                break;
        }    
        
        /*
        if(productType.trim().length() == 0){
            currentOrder.setProductType(order.getProductType());
        } else {
            currentOrder.setProductType(productType);
        }  
        
        if(area.trim().length() == 0){
            currentOrder.setArea(order.getArea());
        } else {
            currentOrder.setArea(area);
        }
        */
        
        // -- COST PER SQUARE FOOT --
        switch (productType.toLowerCase()){
            case "carpet":
                currentOrder.setCostPerSquareFoot(productPrice.getCarpet());
                break;
            case "laminate":
                currentOrder.setCostPerSquareFoot(productPrice.getLaminate());                
                break;
            case "tile":
                currentOrder.setCostPerSquareFoot(productPrice.getTile());               
                break;
            case "wood":    
                currentOrder.setCostPerSquareFoot(productPrice.getWood());
            default:
                break;     
        }
        // -- LABOR COST PER SQUARE FOOT --
        switch (productType.toLowerCase()){
            case "carpet":
                currentOrder.setLaborCostPerSquareFoot(productLaborCost.getCarpet());
                break;
            case "laminate":
                currentOrder.setLaborCostPerSquareFoot(productLaborCost.getLaminate());                
                break;
            case "tile":
                currentOrder.setLaborCostPerSquareFoot(productLaborCost.getTile());               
                break;
            case "wood":
                currentOrder.setLaborCostPerSquareFoot(productLaborCost.getWood());                
                break;
            default:
                break;    
        }
        
        if(area.trim().length() == 0){
            currentOrder.setArea(order.getArea());
        } else {
            currentOrder.setArea(area);
        }
        // -- CALCULATIONS --
        
        //materialCost = area * costPerSquareFoot
        String materialCost = "0";       
        BigDecimal decimalMaterialCost = new BigDecimal(materialCost);
        BigDecimal decimalArea = new BigDecimal(area);  
        BigDecimal decimalCarpetCostPerSquareFoot = new BigDecimal(productPrice.getCarpet());
        BigDecimal decimalLaminateCostPerSquareFoot = new BigDecimal(productPrice.getLaminate());
        BigDecimal decimalTileCostPerSquareFoot = new BigDecimal(productPrice.getTile());
        BigDecimal decimalWoodCostPerSquareFoot = new BigDecimal(productPrice.getWood());
        //laborCost = (area * laborCostPerSquareFoot)
        String laborCost = "0";
        BigDecimal decimalLaborCost = new BigDecimal(laborCost);
        BigDecimal decimalCarpetLaborCostPerSquareFoot = new BigDecimal(productLaborCost.getCarpet());
        //System.out.println("Carpet Labor Cost Test: "+decimalCarpetLaborCostPerSquareFoot);
        BigDecimal decimalLaminateLaborCostPerSquareFoot = new BigDecimal(productLaborCost.getLaminate());
        BigDecimal decimalTileLaborCostPerSquareFoot = new BigDecimal(productLaborCost.getTile());
        BigDecimal decimalWoodLaborCostPerSquareFoot = new BigDecimal(productLaborCost.getWood());
        String tax = "0";
        BigDecimal decimalTax = new BigDecimal(tax);
        BigDecimal decimalOHTaxRate = new BigDecimal(taxRate.getOH());
        //System.out.println("Tax Test: "+decimalOHTaxRate);
        BigDecimal decimalPATaxRate = new BigDecimal(taxRate.getPA());
        BigDecimal decimalMITaxRate = new BigDecimal(taxRate.getMI());
        BigDecimal decimalINTaxRate = new BigDecimal(taxRate.getIN());
        //Total = (MaterialCost + LaborCost + Tax)
        String total = "0";
        BigDecimal decimalTotal = new BigDecimal(total);        
        
        // -- BIG DECIMAL  COST PER SQUARE FOOT --
        switch (productType.toLowerCase()){
            case "carpet":
                decimalMaterialCost = decimalArea.multiply(decimalCarpetCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                decimalLaborCost = decimalArea.multiply(decimalCarpetLaborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                break;
            case "laminate":
                decimalMaterialCost = decimalArea.multiply(decimalLaminateCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP); 
                decimalLaborCost = decimalArea.multiply(decimalLaminateLaborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP); 
                break;
            case "tile":
                decimalMaterialCost = decimalArea.multiply(decimalTileCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);   
                decimalLaborCost = decimalArea.multiply(decimalTileLaborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                break;
            case "wood":
                decimalMaterialCost = decimalArea.multiply(decimalWoodCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);  
                decimalLaborCost = decimalArea.multiply(decimalWoodLaborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                break; 
            default:
                break;     
        }        
        
        // -- BIG DECIMAL  TAX RATES --
        switch (state.toUpperCase()){
            case "OH":
                decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(decimalOHTaxRate).setScale(2, RoundingMode.HALF_UP);
                break;
            case "PA":
                 decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(decimalPATaxRate).setScale(2, RoundingMode.HALF_UP);               
                break;
            case "MI":
                 decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(decimalMITaxRate).setScale(2, RoundingMode.HALF_UP);              
                break;
            case "IN":
                 decimalTax = decimalMaterialCost.add(decimalLaborCost).multiply(decimalINTaxRate).setScale(2, RoundingMode.HALF_UP);               
                break;
            default:
                break;
        } 
        
        //Total = (MaterialCost + LaborCost + Tax)
        decimalTotal = decimalMaterialCost.add(decimalLaborCost).add(decimalTax).setScale(2, RoundingMode.HALF_UP);
        
        // -- Big Decimal  Conversion back to String --
        materialCost = decimalMaterialCost.toString();
        currentOrder.setMaterialCost(materialCost);
        laborCost = decimalLaborCost.toString();
        currentOrder.setLaborCost(laborCost);        
        tax = decimalTax.toString();
        currentOrder.setTax(tax);
        total = decimalTotal.toString();        
        currentOrder.setTotal(total);       
                        
       
        return currentOrder;         
               
    }
    

}
