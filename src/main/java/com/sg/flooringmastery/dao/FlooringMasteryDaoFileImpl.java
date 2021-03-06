/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Orders;
import com.sg.flooringmastery.service.FlooringMasteryDuplicateIdException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author travi
 */
public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao {
    
    private Map<String, Orders> myOrders = new HashMap<>();
    //public static final String ORDERS_FILE = "orders.txt";
    public static final String DELIMITER = "::"; 
    
    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|

    
    // -- Generate Order Number --
    public String theKeys(LocalDate ld, String orderNumber) throws FlooringMasteryPersistenceException{
        
        if (myOrders.containsKey("1")){
            //System.out.println("True: Contains at least 1 Order");    
        Set<String> keys = myOrders.keySet();    
        for(String currentKey : keys){            
            //System.out.println("Current List of Keys: "+currentKey);
            Integer newMaxNumber = Integer.valueOf(currentKey);
            newMaxNumber += 1;
            //System.out.println("New List of Keys Value: "+newMaxNumber);           
            orderNumber = String.valueOf(newMaxNumber);
            //System.out.println("Next OrderNumber Assigned: "+orderNumber);
           
        try {        
            loadOrder(ld);
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e); }        
        try {
            writeOrder(ld);
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e); }               
        }
        return orderNumber;  } else {    
        
            System.out.println("False: Does not contain. at least 1 Order");
        orderNumber = "0";
        Integer newOrderNumber = 0;
        
            newOrderNumber = Integer.valueOf(orderNumber);            
            newOrderNumber = myOrders.size() + 1;             
            orderNumber = String.valueOf(newOrderNumber);
            
        System.out.println("DAO - Assigned OrderNumber: "+orderNumber);
        
        try {        
            loadOrder(ld);
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e); }         
        try {
           writeOrder(ld);
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e); }         
        return orderNumber;    
        }             
    }        
    
    
    @Override
    public Orders addOrder(LocalDate ld, String orderNumber, Orders order) 
            throws FlooringMasteryPersistenceException, FlooringMasteryDuplicateIdException {
        Orders newOrder = myOrders.put(orderNumber, order);    
        
        try {
            
            writeOrder(ld);
        } catch (IOException e) {
            e.printStackTrace();
        }      
       
        return newOrder;                
    }  
    
    
    @Override
    public Orders addEditOrder(String date, String orderNumber, Orders order) 
            throws FlooringMasteryPersistenceException, FlooringMasteryDuplicateIdException {
        Orders newEditedOrder = myOrders.put(orderNumber, order);        
        //Orders newEditedOrder = myOrders.put(date, order); // ---- ADDS NEW ORDER ON THAT DATE ----     
        
        try {            
            writeDateEditFile(date);
        } catch (IOException e) {
            e.printStackTrace();
        }     
       
        return newEditedOrder;                
    }  
    
    
    @Override  // -- USED FOR -- writeOrder(LocalDate ld) --
    public List<Orders> getAllOrders(LocalDate ld) 
            throws FlooringMasteryPersistenceException {
        
        try {        
            
            loadOrder(ld);
        } catch (IOException e) {
            e.printStackTrace();
        }      
        return new ArrayList<>(myOrders.values());        
    }
    // -- USED FOR -- writeDateEditFile(String date) --
    public List<Orders> getAllOrders2(String date) 
            throws FlooringMasteryPersistenceException {
        
        try {        
            //Orders order = null;
            loadDateFile(date);
        } catch (IOException e) {
            e.printStackTrace();
        }      
        return new ArrayList<>(myOrders.values());        
    }
    
    
   @Override  // -- USED FOR CREATE ORDER
    public Orders getTXTOrder(String date) 
            throws FlooringMasteryPersistenceException {
        
        try {        
            //loadDateFile(orderDate);
            loadDateFile(date);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return myOrders.get(date);        
    }
    
    
    @Override // -- USED FOR EDIT & REMOVE --
    public Orders getEditTXTOrder(String date, String orderNumber) 
            throws FlooringMasteryPersistenceException {
        
        try {            
            loadDateEditFile(date);
            //loadOrder();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return myOrders.get(orderNumber);   
    }
    
    
    @Override
    public Orders getOrder(LocalDate ld, String orderNumber) 
            throws FlooringMasteryPersistenceException {
        
        try {        
            loadOrder(ld);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return myOrders.get(orderNumber);        
    }
    

    @Override
    public Orders removeOrder(String date, String orderNumber) 
            throws FlooringMasteryPersistenceException {
        Orders removedOrder = myOrders.remove(orderNumber);
        
        try {
            writeDateEditFile(date);
        } catch (IOException e) {
            e.printStackTrace();
        }        
        return removedOrder;        
    }
    
    
   // -- LOAD -- displayOrders() --
    private String loadDateFile(String date) throws FileNotFoundException, 
            IOException, FlooringMasteryPersistenceException {
      Scanner scanner;
      
      try { 
    
    File ORDERS_FILE = new File("Order_"+date+".txt");    
              
      scanner = new Scanner(
                  new BufferedReader(
                  new FileReader(ORDERS_FILE)));
        
        String currentLine;        
        String[] currentTokens;
        
        
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            //System.out.println(currentLine);
            currentTokens = currentLine.split(DELIMITER);              
            
            
            Orders currentOrder = new Orders(currentTokens[0]);
            
            currentOrder.setOrderDate(LocalDate.parse(currentTokens[1]));
            System.out.println(currentTokens[0]+" | "+currentTokens[1]);
            currentOrder.setCustomerName(currentTokens[2]);
            System.out.println(currentTokens[2]);
	    currentOrder.setState(currentTokens[3]);
            System.out.println(currentTokens[3]);
	    currentOrder.setTaxRate(currentTokens[4]);            
            currentOrder.setProductType(currentTokens[5]);
            System.out.println("Product: "+currentTokens[5]);
	    currentOrder.setArea(currentTokens[6]);
	    currentOrder.setCostPerSquareFoot(currentTokens[7]);
            currentOrder.setLaborCostPerSquareFoot(currentTokens[8]);            
	    currentOrder.setMaterialCost(currentTokens[9]);
            System.out.println("Materials: "+currentTokens[9]);
	    currentOrder.setLaborCost(currentTokens[10]);
            System.out.println("Labor: "+currentTokens[10]);
            currentOrder.setTax(currentTokens[11]);
            System.out.println("Tax: "+currentTokens[11]);
	    currentOrder.setTotal(currentTokens[12]);
            System.out.println("Total: "+currentTokens[12]);
            System.out.println("");
           
            myOrders.put(currentOrder.getOrderNumber(), currentOrder);            
        
        }            
        
        scanner.close();
    
    
    } catch (FileNotFoundException e){
          System.out.println("-_- No such Orders exist.");
          throw new FlooringMasteryPersistenceException(
                    "-_- Could not load order data into memory", e);
      }
      return date;
    }
    
    
    // -- LOAD -- createOrder() & listAllOrderNumbers() --
    private void loadOrder(LocalDate ld) throws FlooringMasteryPersistenceException, IOException {
        //Scanner scanner;
        
        try {    
            File ORDERS_FILE = new File("Order_"+LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy"))+".txt");
            ORDERS_FILE.createNewFile(); // -- Create New File DAILY --
            if (ORDERS_FILE.createNewFile()) {
            System.out.println("CREATE-FILE MESSAGE:  File created: " + ORDERS_FILE.getName());
        } else {
                
            //System.out.println("CREATE-FILE MESSAGE:  File already exists. ");
        }
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not create new order file", e);
        }
        
        Scanner scanner;
        
        
        try {
            
            // Create Scanner for "READING" the file            
            File ORDERS_FILE = new File("Order_"+LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy"))+".txt");                 
            
            //scanner = new Scanner(ORDERS_FILE);
              scanner = new Scanner(
                        new BufferedReader(
                        new FileReader(ORDERS_FILE)));
        } catch (FileNotFoundException e){
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load order data into memory", e);           
        }       
        
              
        // currentLine holds the most recent line read from the file
        String currentLine;        
        String[] currentTokens;

        
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            //System.out.println(currentLine);
            currentTokens = currentLine.split(DELIMITER);              
            
                        
            Orders currentOrder = new Orders(currentTokens[0]);             
            
            currentOrder.setOrderDate(LocalDate.parse(currentTokens[1]));
            currentOrder.setCustomerName(currentTokens[2]);
	    currentOrder.setState(currentTokens[3]);
	    currentOrder.setTaxRate(currentTokens[4]);
            currentOrder.setProductType(currentTokens[5]);
	    currentOrder.setArea(currentTokens[6]);
	    currentOrder.setCostPerSquareFoot(currentTokens[7]);
            currentOrder.setLaborCostPerSquareFoot(currentTokens[8]);
	    currentOrder.setMaterialCost(currentTokens[9]);
	    currentOrder.setLaborCost(currentTokens[10]);
            currentOrder.setTax(currentTokens[11]);
	    currentOrder.setTotal(currentTokens[12]);
           
            myOrders.put(currentOrder.getOrderNumber(), currentOrder);            
        
        }            
        
        scanner.close();    
        //myOrders.clear();
    }  // -- loadRoster() --
        

    // -- WRITE -- createOrder() --
    private void writeOrder(LocalDate ld) throws FlooringMasteryPersistenceException, 
            FileNotFoundException, IOException {
	    
	    PrintWriter out;
	    
	    try {                
	        out = new PrintWriter(new FileWriter("Order_"+LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy"))+".txt"));
	    } catch (IOException e) {
	        throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e);
	    }            
            
            
	    List<Orders> orderList = this.getAllOrders(ld);
	    for (Orders currentOrder : orderList) {
	        // write the Orders object to the file
                
                 
	        out.println(currentOrder.getOrderNumber()+ DELIMITER
                        + currentOrder.getOrderDate()+ DELIMITER
	                + currentOrder.getCustomerName()+ DELIMITER
	                + currentOrder.getState()+ DELIMITER
                        + currentOrder.getTaxRate()+ DELIMITER 
	                + currentOrder.getProductType()+ DELIMITER
	                + currentOrder.getArea()+ DELIMITER
                        + currentOrder.getCostPerSquareFoot()+ DELIMITER 
	                + currentOrder.getLaborCostPerSquareFoot()+ DELIMITER
	                + currentOrder.getMaterialCost()+ DELIMITER
                        + currentOrder.getLaborCost()+ DELIMITER 
	                + currentOrder.getTax()+ DELIMITER 
                        + currentOrder.getTotal());         
            
	    out.flush();
                
	    } 
            
	    // Clean up
	    out.close();
              
            //System.out.println("WRITE-FILE MESSAGE:  Successfully wrote to the file.");
            
    } // -- writeRoster() --    
    
    
    // -- LOAD 2 -- editOrder() & removeOrder() --
    private String loadDateEditFile(String date) throws FileNotFoundException, 
            FlooringMasteryPersistenceException {
      Scanner scanner;        
      myOrders = new HashMap<>(); 
      
      try {
         
      File ORDERS_FILE = new File("Order_"+date+".txt");    
              
      scanner = new Scanner(
                  new BufferedReader(
                  new FileReader(ORDERS_FILE)));
        
        String currentLine;        
        String[] currentTokens;

        
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            
            currentTokens = currentLine.split(DELIMITER);              
            
           Orders currentOrder = new Orders(currentTokens[0]);            
            
            System.out.println("Assigned Order Number: "+currentTokens[0]);
            
            currentOrder.setOrderDate(LocalDate.parse(currentTokens[1]));            
            currentOrder.setCustomerName(currentTokens[2]);
	    currentOrder.setState(currentTokens[3]);
	    currentOrder.setTaxRate(currentTokens[4]);
            currentOrder.setProductType(currentTokens[5]);
	    currentOrder.setArea(currentTokens[6]);
	    currentOrder.setCostPerSquareFoot(currentTokens[7]);
            currentOrder.setLaborCostPerSquareFoot(currentTokens[8]);
	    currentOrder.setMaterialCost(currentTokens[9]);
	    currentOrder.setLaborCost(currentTokens[10]);
            currentOrder.setTax(currentTokens[11]);
	    currentOrder.setTotal(currentTokens[12]);
           
            myOrders.put(currentOrder.getOrderNumber(), currentOrder);            
        
        }            
        
        scanner.close();           
         
    
    } catch (FileNotFoundException e){
          System.out.println("-_- No such Orders exist.");
          throw new FlooringMasteryPersistenceException(
                    "-_- Could not load order data into memory", e);
      }
      return date;
    } // -- loadRoster() 2 --

    
    // -- WRITE -- editOrder() & removeOrder() --
    private void writeDateEditFile(String date) throws FlooringMasteryPersistenceException, 
            FileNotFoundException, IOException {
	    
	    PrintWriter out;
	    
	    try { 
                out = new PrintWriter(new FileWriter("Order_"+date+".txt"));
                
	       
	    } catch (IOException e) {
	        throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e);
	    }          
	    
                
                List<Orders> orderList = this.getAllOrders2(date);
	        for (Orders currentOrder : orderList) {
	        // write the Orders object to the file
                
	        out.println(currentOrder.getOrderNumber()+ DELIMITER
                        + currentOrder.getOrderDate()+ DELIMITER
	                + currentOrder.getCustomerName()+ DELIMITER
	                + currentOrder.getState()+ DELIMITER
                        + currentOrder.getTaxRate()+ DELIMITER 
	                + currentOrder.getProductType()+ DELIMITER
	                + currentOrder.getArea()+ DELIMITER
                        + currentOrder.getCostPerSquareFoot()+ DELIMITER 
	                + currentOrder.getLaborCostPerSquareFoot()+ DELIMITER
	                + currentOrder.getMaterialCost()+ DELIMITER
                        + currentOrder.getLaborCost()+ DELIMITER 
	                + currentOrder.getTax()+ DELIMITER 
                        + currentOrder.getTotal());         
                
                
	    out.flush();
                
	    }             
	    // Clean up
            
	    out.close();
            
            //System.out.println("WRITE-FILE MESSAGE:  Successfully wrote to the file.");
            
    } // -- writeRoster() 2 --  

    
    //---------------------------------------------------------|
    
    //---------------------------------------------------------|
    
}   