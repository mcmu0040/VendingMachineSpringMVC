/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachingspringmvc.service;

import com.sg.vendingmachingspringmvc.dao.MachineDao;
import com.sg.vendingmachingspringmvc.dao.VendingMachingPersistenceException;
import com.sg.vendingmachingspringmvc.model.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

/**
 *
 * @author mcmu0
 */
@Component
public class MachineService {

    @Inject
    private MachineDao dao;

    public MachineService(MachineDao dao) {
        this.dao = dao;
    }

    public List<Item> getInventory() throws VendingMachingPersistenceException {
        return dao.getInventory();
    }

    public boolean validateSelection(int id) throws VendingMachingPersistenceException, 
                                                                NoItemInventoryException {
        boolean valid = false;
        List<Item> items = getInventory();
        for (Item item : items) {
            if (item.getId() == id) {
                if (item.getQuantity() > 0) {
                    valid = true;
                } else {
                    throw new NoItemInventoryException("SOLD OUT!!!");
                }
            }
        }
        return valid;
    }

    public BigDecimal soldItem(int id, BigDecimal cash) throws VendingMachingPersistenceException, 
                                                                    InsufficientFundsException {
        //check if cash available is greater than or equal to the items price

        if (cash.compareTo(dao.getPrice(id)) >= 0) {
            dao.soldItem(id);
            return dao.getPrice(id);
        } else {
            BigDecimal temp = dao.getPrice(id).subtract(cash);
            throw new InsufficientFundsException("Please Deposit: $" + temp.setScale(2, RoundingMode.HALF_UP));
        }
    }

    public boolean validateMoney(String money) {
        try {
            Double.parseDouble(money);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
