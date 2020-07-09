/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.time.LocalDate;

/**
 *
 * @author travi
 */
public class Orders {
    
    public Taxes taxes;
    public Products products;   
    
    private String orderNumber;
    private LocalDate orderDate;
    private String customerName;
    private String state;
    private String taxRate;
    private String productType;    
    private String costPerSquareFoot;
    private String laborCostPerSquareFoot; 
    private String area;
    private String materialCost;
    private String laborCost;
    private String tax;
    private String total;
    private String submitOrder;
    
    
    public Orders(String orderNumber){
        this.orderNumber = orderNumber;
    }
            
    public String getOrderNumber() {
        return orderNumber;
    } 
    
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }   

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSubmitOrder() {
        return submitOrder;
    }

    public void setSubmitOrder(String submitOrder) {
        this.submitOrder = submitOrder;
    }

    public Taxes getTaxes() {
        return taxes;
    }

    public void setTaxes(Taxes taxes) {
        this.taxes = taxes;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    
    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(String costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public String getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(String laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }
    
     public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }      

    
    public String getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(String materialCost) {
        this.materialCost = materialCost;
    }

    public String getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(String laborCost) {
        this.laborCost = laborCost;
    }
    
    

    
    
    
}
