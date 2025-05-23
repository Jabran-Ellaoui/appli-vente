package businessLogic;

import dataAccess.LotDAOInterface;
import exception.LotException;
import exception.SalesDetailsException;
import model.Lot;
import model.SalesDetails;

import java.util.ArrayList;

public class LotService
{
    private final LotDAOInterface lotDAO;

    public LotService(LotDAOInterface lotDAO)
    {
        this.lotDAO = lotDAO;
    }

    public ArrayList<Lot> getAllLot() throws LotException {
        return (ArrayList<Lot>)lotDAO.readAll();
    }
}
