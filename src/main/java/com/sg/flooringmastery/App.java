/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import com.sg.flooringmastery.dao.FlooringMasteryAuditDao;
import com.sg.flooringmastery.dao.FlooringMasteryAuditDaoImpl;
import com.sg.flooringmastery.dao.FlooringMasteryDao;
import com.sg.flooringmastery.dao.FlooringMasteryDaoFileImpl;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.ProductLaborCost;
import com.sg.flooringmastery.dto.ProductPrice;
import com.sg.flooringmastery.dto.TaxRates;
import com.sg.flooringmastery.service.FloorMasteryValidateSubmitException;
import com.sg.flooringmastery.service.FlooringMasteryDataValidationException;
import com.sg.flooringmastery.service.FlooringMasteryDuplicateIdException;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayerImpl;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import com.sg.flooringmastery.ui.UserIO;
import com.sg.flooringmastery.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author travi
 */
public class App {
    public static void main(String[] args) throws FlooringMasteryPersistenceException, 
                                                  FlooringMasteryDuplicateIdException,
                                                  FlooringMasteryDataValidationException,
                                                  FloorMasteryValidateSubmitException {
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = ctx.getBean("controller", FlooringMasteryController.class);
        controller.run();

        /*
        UserIO myIO = new UserIOConsoleImpl();
        TaxRates myTaxRate = new TaxRates();
        ProductPrice myProductPrice = new ProductPrice();
        ProductLaborCost myProductLaborCost = new ProductLaborCost();        
        FlooringMasteryView myView = new FlooringMasteryView(myIO, myTaxRate, 
                                            myProductPrice, myProductLaborCost);
        FlooringMasteryDao myDao = new FlooringMasteryDaoFileImpl();
        FlooringMasteryAuditDao myAuditDao = new FlooringMasteryAuditDaoImpl();
        FlooringMasteryServiceLayer myService = new FlooringMasteryServiceLayerImpl(myDao, myAuditDao);
        FlooringMasteryController controller = new 
            FlooringMasteryController(myService, myView);
        controller.run();
        */
    }
}
