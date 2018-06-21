/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachingspringmvc.controller;

import com.sg.vendingmachingspringmvc.dao.VendingMachingPersistenceException;
import com.sg.vendingmachingspringmvc.model.Change;
import com.sg.vendingmachingspringmvc.service.InsufficientFundsException;
import com.sg.vendingmachingspringmvc.service.MachineService;
import com.sg.vendingmachingspringmvc.service.NoItemInventoryException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author mcmu0
 */
@Controller
public class VendingController {

    int id = 0;
    BigDecimal cash = BigDecimal.ZERO;
    String message = "";
    String change = "";

    @Inject
    MachineService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        try {
            model.addAttribute("items", service.getInventory());

            if (id == 0) {
                model.addAttribute("selected", "");
            } else {
                model.addAttribute("selected", id);
            }

            if (cash.equals(BigDecimal.ZERO)) {
                model.addAttribute("cash", "");
            } else {
                model.addAttribute("cash", cash.setScale(2, RoundingMode.HALF_UP));
            }

            model.addAttribute("message", message);
            model.addAttribute("change", change);

            return "index";
        } catch (VendingMachingPersistenceException ex) {
            return "error";
        }
    }

    @RequestMapping(value = "/selectItem/{id}", method = RequestMethod.POST)
    public String selectItem(@PathVariable Integer id) {
        this.id = id;

        return "redirect:/";
    }

    @RequestMapping(value = "/addMoney/{money}", method = RequestMethod.POST)
    public String selectItem(@PathVariable String money) {
        BigDecimal temp = new BigDecimal(money);
        temp = temp.divide(new BigDecimal("100"));
        cash = cash.add(temp);

        return "redirect:/";
    }

    @RequestMapping(value = "/makePurchase/{id}", method = RequestMethod.POST)
    public String makePurchase(@PathVariable Integer id) throws VendingMachingPersistenceException {
        //first check item has enough inventory
        //then check we have the money
        try {
            if (service.validateSelection(id)) {
                try {
                    cash = cash.subtract(service.soldItem(id, cash));
                    //here, no errors, so set message
                    message = "Thank you";
                    change = Change.getChange(cash);
                    cash = BigDecimal.ZERO;
                } catch (InsufficientFundsException ex) {
                    message = ex.getMessage();
                }
            }
        } catch (NoItemInventoryException ex) {
            message = ex.getMessage();
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/makePurchase/", method = RequestMethod.POST)
    public String makeEmptyPurchase() throws VendingMachingPersistenceException {

        return "redirect:/";
    }

    @RequestMapping(value = "/resetAll", method = RequestMethod.POST)
    public String resetAll() {
        //if change is empty, check for cash
        //if cash != 0, then make change
        //if change is not empty, clear it
        if (change.equals("")) {
            //no purchase
            if (cash.compareTo(BigDecimal.ZERO) != 0) {
                change = Change.getChange(cash);
            }
        } else {
            change = "";
        }

        cash = BigDecimal.ZERO;
        message = "";
        id = 0;

        return "redirect:/";
    }
}
