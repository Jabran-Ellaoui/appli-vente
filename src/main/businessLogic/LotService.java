package main.businessLogic;

import main.dataAccess.LotDAO;
import main.dataAccess.LotDAOInterface;
import main.exception.ConnectionException;
import main.exception.LotException;
import main.model.Lot;

import java.util.ArrayList;

public class LotService {
    private final LotDAOInterface lotDAO;

    public LotService() throws ConnectionException {

        this.lotDAO = new LotDAO();
    }

    public ArrayList<Lot> getAllLot() throws LotException {
        return (ArrayList<Lot>)lotDAO.readAll();
    }
}