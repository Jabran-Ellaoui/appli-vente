package main.controller;

import main.businessLogic.SearchService;
import main.exception.SearchException;

import java.sql.Date;
import java.util.ArrayList;

public class SearchController {

    private final SearchService searchService;

    public SearchController() throws SearchException {
        this.searchService = new SearchService();
    }

    public ArrayList<Object[]> getProductsByModelProvenanceDate(String label, String provenance, Date minReceptionDate) throws SearchException {
        return searchService.getProductsByModelProvenanceDate(label, provenance, minReceptionDate);
    }

    public ArrayList<Object[]> getSoldProducts(int clientId, int employeeId, Date saleDate) throws SearchException {
        return searchService.getSoldProducts(clientId, employeeId, saleDate);
    }

    public ArrayList<Object[]> getSalesSummary(int clientId, Date startDate, Date endDate) throws SearchException {
        return searchService.getSalesSummary(clientId, startDate, endDate);
    }
}