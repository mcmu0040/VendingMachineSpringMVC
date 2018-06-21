/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachingspringmvc.dao;

import com.sg.vendingmachingspringmvc.model.Item;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author mcmu0
 */
@Component
public class MachineDaoImpl implements MachineDao {
    
    private List<Item> inventory = new ArrayList<>();
    private static final String INV_FILE = "inventory.txt";
    private static final String DELIMITER = "::";

    @Override
    public List<Item> getInventory() throws VendingMachingPersistenceException {
        loadInventory();
        return new ArrayList<Item>(inventory);
    }

    @Override
    public void soldItem(int id) throws VendingMachingPersistenceException {
        inventory.get(findItemIndex(id)).itemSold();
        //after item sold, update memory
        writeInventory();
    }
    
    @Override
    public BigDecimal getPrice(int id) throws VendingMachingPersistenceException {
        return inventory.get(findItemIndex(id)).getPrice();
    }
    
    private int findItemIndex(int id) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
    
    private void loadInventory() throws VendingMachingPersistenceException {
        inventory.clear();
        Scanner scanner;

        File f = new File(getClass().getClassLoader().getResource(INV_FILE).getFile());

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            throw new VendingMachingPersistenceException("-_- Could not load inventory data into memory.", e);
        }
        String currentLine;
        
        String[] currentTokens;
        
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Item currentItem = new Item(Integer.parseInt(currentTokens[0]), 
                    currentTokens[1], 
                    new BigDecimal(currentTokens[2]));
            currentItem.setQuantity(Integer.parseInt(currentTokens[3]));
            
            inventory.add(currentItem);
        }
        scanner.close();
    }
    
    private void writeInventory() throws VendingMachingPersistenceException {
        PrintWriter out;
        File f = new File(getClass().getClassLoader().getResource(INV_FILE).getFile());
        
        try {
            FileWriter fw = new FileWriter(f);
            out = new PrintWriter(fw, true);
        } catch (IOException e) {
            throw new VendingMachingPersistenceException("-_- Could not load inventory data into memory.", e);
        }

        for (Item item : inventory) {
            out.println(item.getId() + DELIMITER
                    + item.getName() + DELIMITER
                    + item.getPrice() + DELIMITER
                    + item.getQuantity());
            out.flush();
        }
        out.close();
    }
}
