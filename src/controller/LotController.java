package controller;
import exception.ConnectionException;
import exception.ProductModelException;
import model.Lot;
import exception.LotException;
import businessLogic.LotService;
import model.ProductModel;

import java.util.ArrayList;

public class LotController
{
    private final LotService lotService;

    public LotController() throws ConnectionException {
        this.lotService = new LotService();
    }

    public ArrayList<Lot> getAllLot()
    {
        try {
            return lotService.getAllLot();
        } catch (LotException exception)
        {
            System.err.println("Erreur lors de la récupération de tous les produits : " + exception.getMessage());
            return new ArrayList<>();
        }
    }
}
