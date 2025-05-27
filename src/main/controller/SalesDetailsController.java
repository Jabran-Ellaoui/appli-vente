package main.controller;

import main.businessLogic.SalesDetailsService;
import main.exception.ConnectionException;
import main.exception.SalesDetailsException;
import main.model.SalesDetails;

import java.util.ArrayList;

public class SalesDetailsController {
    private final SalesDetailsService salesDetailsService;

    public SalesDetailsController() throws ConnectionException
    {
        this.salesDetailsService = new SalesDetailsService();
    }

    public SalesDetails getSalesDetails(int id) throws SalesDetailsException
    {
            return salesDetailsService.getSalesDetails(id);
    }

    public ArrayList<SalesDetails> getAllSalesDetails() throws SalesDetailsException
    {
            return salesDetailsService.getAllSalesDetails();
    }

    public void addNewSalesDetails(SalesDetails salesDetails) throws SalesDetailsException
    {
            salesDetailsService.addNewSalesDetails(salesDetails);
    }

    public void updateSalesDetails(SalesDetails salesDetails) throws SalesDetailsException
    {
        salesDetailsService.updateSalesDetails(salesDetails);
    }

    public void deleteSalesDetails(int id) throws SalesDetailsException
    {
            salesDetailsService.deleteSalesDetails(id);
    }
}