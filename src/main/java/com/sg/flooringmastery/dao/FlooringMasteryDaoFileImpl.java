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
    
    public static final String DELIMITER = "::"; 
    
    
    public String theKeys(String orderNumber) throws FlooringMasteryPersistenceException{
        
        Set<String> keys = myOrders.keySet();    
        for(String currentKey : keys){            
            System.out.println("Current List of Keys: "+currentKey);
            Integer newMaxNumber = Integer.valueOf(currentKey);
            newMaxNumber += 1;
            System.out.println("New List of Keys Value: "+newMaxNumber);           
            orderNumber = String.valueOf(newMaxNumber);
            System.out.println("Next OrderNumber Assigned: "+orderNumber);
            
        try {        
            loadOrder();
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e);
        }
        try {
            writeOrder();
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e);
        }        
        }
        return orderNumber;
    }
    
    
    
    public String theTestKeys(String orderNumber) throws FlooringMasteryPersistenceException{
        
        if (myOrders.containsKey("1")){
            System.out.println("True: Contains at least 1 Order");    
        Set<String> keys = myOrders.keySet();    
        for(String currentKey : keys){            
            System.out.println("Current List of Keys: "+currentKey);
            Integer newMaxNumber = Integer.valueOf(currentKey);
            newMaxNumber += 1;
            System.out.println("New List of Keys Value: "+newMaxNumber);           
            orderNumber = String.valueOf(newMaxNumber);
            System.out.println("Next OrderNumber Assigned: "+orderNumber);
            
        try {        
            loadOrder();
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e);
        }
        try {
            writeOrder();
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e);
        }        
        }
        return orderNumber;
        
    } else {
        
            System.out.println("False: Does not contain at least 1 Order");
        orderNumber = "0";
        Integer newOrderNumber = 0;
        
            newOrderNumber = Integer.valueOf(orderNumber);            
            newOrderNumber = myOrders.size() + 1;             
            orderNumber = String.valueOf(newOrderNumber);
            
        System.out.println("DAO - Assigned OrderNumber: "+orderNumber);
        try {        
            loadOrder();
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e);
        }
        try {
            writeOrder();
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e);
        }
        return orderNumber;    
        }    
            
            
            
    }        
            
 
    
    @Override  // -- NOT IN USE --
    public String generateNextOrderNumber(String orderNumber) 
            throws FlooringMasteryPersistenceException{
        orderNumber = "0";
        Integer newOrderNumber = 0;
        
            newOrderNumber = Integer.valueOf(orderNumber);            
            newOrderNumber = myOrders.size() + 1;             
            orderNumber = String.valueOf(newOrderNumber);
            
        System.out.println("DAO - Assigned OrderNumber: "+orderNumber);
        try {        
            loadOrder();
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e);
        }
        try {
            writeOrder();
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e);
        }
        return orderNumber;
        //String orderNumber = "";                  
   }
    
    
    @Override
    public Orders addOrder(String orderNumber, Orders order) 
            throws FlooringMasteryPersistenceException, FlooringMasteryDuplicateIdException {
        Orders newOrder = myOrders.put(orderNumber, order);        
        try {        
            loadOrder();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writeOrder();
        } catch (IOException e) {
            e.printStackTrace();
        }      
       
        return newOrder;                
    }
        
    
    @Override
    public List<Orders> getAllOrders() 
            throws FlooringMasteryPersistenceException {
        try {        
            //Orders order = null;
            loadOrder();
        } catch (IOException e) {
            e.printStackTrace();
        }        
        return new ArrayList<>(myOrders.values());        
    }
    
   @Override
    public Orders getTXTOrder(String orderDate) 
            throws FlooringMasteryPersistenceException {
        try {        
            loadDateFile(orderDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myOrders.get(orderDate);        
    } 
    

    @Override
    public Orders getOrder(String orderNumber) 
            throws FlooringMasteryPersistenceException {
        try {        
            loadOrder();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myOrders.get(orderNumber);        
    }
    

    @Override
    public Orders removeOrder(String orderDate, String orderNumber) 
            throws FlooringMasteryPersistenceException {
        Orders removedOrder = myOrders.remove(orderNumber);
        try {        
            loadOrder();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writeOrder();
        } catch (IOException e) {
            e.printStackTrace();
        }        
        return removedOrder;        
    }
    
    @Override
    public List<Orders> getAssignedOrderNumbers(String orderNumber)
            throws FlooringMasteryPersistenceException {
    try {        
            loadOrder();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myOrders.values()
                       .stream()
                       .filter(o -> o.getOrderNumber().equals(orderNumber))
                       .collect(Collectors.toList());
    }      
   
    
    
    public String loadDateFile(String orderDate) throws FileNotFoundException, 
            IOException, FlooringMasteryPersistenceException{
      //orderDate = "";
      try {          
      FileReader fr =     
      new FileReader("C:\\Users\\travi\\OneDrive\\Desktop\\SG-Folder"
              + "\\online-java-2019-travismbenton\\Summatives\\m4-summative"
              + "\\M4\\FlooringMastery\\Order_"+orderDate+".txt");       
    int i; 
    while ((i=fr.read()) != -1) 
      System.out.print((char) i);
    } catch (FileNotFoundException e){
          System.out.println("-_- No such Orders exist.");
          throw new FlooringMasteryPersistenceException(
                    "-_- Could not load order data into memory", e);
      }
      return orderDate;
    }
    
    
       
    
    // -- LOAD - ROSTER --
    private void loadOrder() throws FlooringMasteryPersistenceException, IOException {
        //Scanner scanner;
        
        try {    
            File ORDERS_FILE = new File("Order_"+LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy"))+".txt");
            ORDERS_FILE.createNewFile(); // -- Create New File DAILY --
            if (ORDERS_FILE.createNewFile()) {
            System.out.println("CREATE-FILE MESSAGE:  File created: " + ORDERS_FILE.getName());
        //} else {
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
            currentTokens = currentLine.split(DELIMITER);            
            
            Orders currentOrder = new Orders(currentTokens[0]);            
            
            currentOrder.setOrderDate(currentTokens[1]);
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
        
    }  // -- loadRoster() --
        
             
    
    


    // -- WRITE - ROSTER --
    private void writeOrder() throws FlooringMasteryPersistenceException, 
            FileNotFoundException, IOException {
	    
	    PrintWriter out;
	    
	    try {                
	        out = new PrintWriter(new FileWriter("Order_"+LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy"))+".txt"));
	    } catch (IOException e) {
	        throw new FlooringMasteryPersistenceException(
	                "Could not save order data.", e);
	    }            
            
            
	    List<Orders> orderList = this.getAllOrders();
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

        
    
}
