package controller;
import exception.ProductModelException;
import model.Lot;
import exception.LotException;
import businessLogic.LotService;
import model.ProductModel;

import java.util.ArrayList;

public class LotController
{
    private final LotService lotService;

    public LotController(LotService lotService)
    {
        this.lotService = lotService;
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
