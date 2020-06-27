/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

/**
 *
 * @author travi
 */
public class FlooringMasteryDuplicateIdException extends Exception{
    
    public FlooringMasteryDuplicateIdException(String message){
        super(message);
    }
    
    public FlooringMasteryDuplicateIdException(String message, Throwable cause){
        super(message, cause);
    }
}
