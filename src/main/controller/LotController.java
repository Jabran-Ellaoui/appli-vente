package main.controller;

import main.exception.ConnectionException;
import main.exception.LotException;
import main.model.Lot;
import main.businessLogic.LotService;

import java.util.ArrayList;

public class LotController
{
    private final LotService lotService;

    public LotController() throws ConnectionException {
        this.lotService = new LotService();
    }

    public ArrayList<Lot> getAllLot() throws LotException
    {

            return lotService.getAllLot();

    }
}
