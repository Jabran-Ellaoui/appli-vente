package controller;

import businessLogic.SalesDetailsService;
import exception.SalesDetailsException;
import model.SalesDetails;

import java.util.ArrayList;

public class SalesDetailsController {
    private final SalesDetailsService salesDetailsService;

    public SalesDetailsController(SalesDetailsService salesDetailsService) {
        this.salesDetailsService = salesDetailsService;
    }

    public SalesDetails getSalesDetails(int id)
    {
        try
        {
            return salesDetailsService.getSalesDetails(id);
        }
        catch (SalesDetailsException exception)
        {
            System.err.println("Erreur lors de la récupération des détails de vente : " + exception.getMessage());
            return null;
        }
    }

    public ArrayList<SalesDetails> getAllSalesDetails()
    {
        try {
            return salesDetailsService.getAllSalesDetails();
        }
        catch (SalesDetailsException exception)
        {
            System.err.println("Erreur lors de la récupération de toutes les ventes : " + exception.getMessage());
            return new ArrayList<>();
        }
    }

    public void addNewSalesDetails(SalesDetails salesDetails) {
        try {
            salesDetailsService.addNewSalesDetails(salesDetails);
            System.out.println("Détail de vente ajouté avec succès.");
        } catch (SalesDetailsException exception)
        {
            System.err.println("Erreur lors de l'ajout : " + exception.getMessage());
        }
    }

    public void updateSalesDetails(SalesDetails salesDetails) {
        try
        {
            salesDetailsService.updateSalesDetails(salesDetails);
            System.out.println("Détail de vente mis à jour avec succès.");
        }
        catch (SalesDetailsException exception) {
            System.err.println("Erreur lors de la mise à jour : " + exception.getMessage());
        }
    }

    public void deleteSalesDetails(int id) {
        try
        {
            salesDetailsService.deleteSalesDetails(id);
            System.out.println("Détail de vente supprimé avec succès.");
        }
        catch (SalesDetailsException exception)
        {
            System.err.println("Erreur lors de la suppression : " + exception.getMessage());
        }
    }
}