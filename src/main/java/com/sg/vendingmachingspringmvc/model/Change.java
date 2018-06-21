/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachingspringmvc.model;

import java.math.BigDecimal;

/**
 *
 * @author mcmu0
 */
public class Change {
    //recieves money in pennies
    //able to return change as quarters, dimes, nickles and pennies
    
    //pennies will hold the total change, then update as we move value from pennies to other coins
    private static int pennies;
    private static int quarters;
    private static int dimes;
    private static int nickels;
    
//    public Change(BigDecimal change) {
//        this.pennies = change.multiply(new BigDecimal("100")).intValueExact();
//        //mult by 100 to get pennies, then convert to int
//        
//        calculateChange();
//    }
//    
    private static void calculateChange() {
        quarters = pennies / 25; //calc total quarters in all of pennies
        pennies = pennies % 25; //update pennies to remainder
        
        dimes = pennies / 10;
        pennies = pennies % 10;
        
        nickels = pennies / 5;
        pennies = pennies % 5;
    }
    
    //use as a static implementaion no need for getters and setters, just call Change.getChange to get the desired string
    
    public static String getChange(BigDecimal cash) {
        pennies = cash.multiply(new BigDecimal("100")).intValueExact();
        //mult by 100 to get pennies, then convert to int
        
        calculateChange();
        
        String output = "";
        
        if (quarters != 0) {
            if (quarters == 1) {
                output += "1 Quarter ";
            } else {
                output += quarters + " Quarters ";
            }
        }
        
        if (dimes != 0) {
            if (dimes == 1) {
                output += "1 Dime ";
            } else {
                output += dimes + " Dimes ";
            }
        }
        
        if (nickels != 0) {
            if (nickels == 1) {
                output += "1 Nickel ";
            } else {
                output += nickels + " Nickels ";
            }
        }
        
        if (pennies != 0) {
            if (pennies == 1) {
                output += "1 Penny";
            } else {
                output += pennies + " Pennies";
            }
        }
        
        if (output.equals("")) {
            output = "No Change";
        }
       
        return output;
    }
    
}