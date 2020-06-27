/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.util.Scanner;

/**
 *
 * @author travi
 */
public class UserIOConsoleImpl implements UserIO {
    
    public void print(String message){
        System.out.println(message); // 8
    }

    public double readDouble(String prompt){
        Scanner sc = new Scanner(System.in);
        System.out.println(prompt);
        String input = sc.nextLine();
        //-- Conversion from "String to Double" --
        double num = Double.parseDouble(input);
        return num; // 6         
    }

    public double readDouble(String prompt, double min, double max){
        double num = 0;
        Scanner sc = new Scanner(System.in);        
        while (true) {        
        System.out.println(prompt);
        String input = sc.nextLine();
        //-- Conversion from "String to Double" --
        num = Double.parseDouble(input);        
        if (num >= min && num <= max) {            
            break;            
        }        
        } // -- WHILE LOOP -- 
        return num; // 7
    }

    public float readFloat(String prompt){
        Scanner sc = new Scanner(System.in);
        System.out.println(prompt);
        String input = sc.nextLine();
        //-- Conversion from "String to Float" --
        float num = Float.parseFloat(input);
        return num; // 4
    }

    public float readFloat(String prompt, float min, float max){
        float num = 0;
        Scanner sc = new Scanner(System.in);        
        while (true) {        
        System.out.println(prompt);
        String input = sc.nextLine();
        //-- Conversion from "String to Float" --
        num = Float.parseFloat(input);        
        if (num >= min && num <= max) {            
            break;            
        }        
        } // -- WHILE LOOP --        
            
        return num; // 5
    }

    public int readInt(String prompt){        
        Scanner sc = new Scanner(System.in);
        System.out.println(prompt);
        String input = sc.nextLine();
        //-- Conversion from "String to Integer" --
        int num = Integer.parseInt(input);
        //System.out.println(num);        
        return num;        
    }

    public int readInt(String prompt, int min, int max){
        //throw setupstuffDaoException{               
        int num = 0;
        Scanner sc = new Scanner(System.in);
        //try {
        while (true) {        
        System.out.println(prompt);        
        String input = sc.nextLine();        
        //-- Conversion from "String to Integer" --
        num = Integer.parseInt(input);        
        if (num >= min && num <= max) {
            break;            
        }        
        } // -- WHILE LOOP --        
        return num;
        /*} catch (NumberFormatException e) {
            throw new setupstuffDaoException(""
                    + "-_- Select from the menu please.", e);
        } */
    }

    public long readLong(String prompt){
        Scanner sc = new Scanner(System.in);
        System.out.println(prompt);
        String input = sc.nextLine();
        //-- Conversion from "String to Long" --
        long num = Long.parseLong(input);
        System.out.println(num); 
        return num;
    }

    public long readLong(String prompt, long min, long max){
        long num = 0;
        Scanner sc = new Scanner(System.in);        
        while (true) {        
        System.out.println(prompt);
        String input = sc.nextLine();
        //-- Conversion from "String to Long" --
        num = Long.parseLong(input);        
        if (num >= min && num <= max) {            
            break;            
        }        
        } // -- WHILE LOOP -- 
        return num;
    }

    public String readString(String prompt){
        Scanner sc = new Scanner(System.in);
        System.out.println(prompt);
        String input = sc.nextLine();
        
        //System.out.println(input);        
        return input;
    }

    
}
