package businessLogic;

import dataAccess.LotDAO;
import dataAccess.LotDAOInterface;
import exception.ConnectionException;
import exception.LotException;
import model.Lot;

import java.util.ArrayList;

public class LotService
{
    private LotDAOInterface lotDAO;

    public LotService() throws ConnectionException {
        this.lotDAO = new LotDAO();
    }

    public ArrayList<Lot> getAllLot() throws LotException {
        return (ArrayList<Lot>)lotDAO.readAll();
    }
}
