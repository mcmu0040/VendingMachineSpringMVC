/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachingspringmvc.dao;

import com.sg.vendingmachingspringmvc.model.Item;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author mcmu0
 */

public interface MachineDao {
    
    List<Item> getInventory() throws VendingMachingPersistenceException ;
    
    //updates the inventory of name but removing 1 from quantity
    //needed in service layer
    void soldItem(int id)throws VendingMachingPersistenceException;

    BigDecimal getPrice(int id) throws VendingMachingPersistenceException;
}
