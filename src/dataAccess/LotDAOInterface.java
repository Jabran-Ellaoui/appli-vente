package dataAccess;

import exception.LotException;

import java.util.ArrayList;

import model.Lot;

public interface LotDAOInterface
{
    ArrayList<Lot> readAll() throws LotException;
}
