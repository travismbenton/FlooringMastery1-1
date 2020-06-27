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
public class FloorMasteryValidateSubmitException extends Exception {
    
    public FloorMasteryValidateSubmitException(String message){
        super(message);
    }
    
    public FloorMasteryValidateSubmitException(String message, Throwable cause){
        super(message, cause);
    }
}
