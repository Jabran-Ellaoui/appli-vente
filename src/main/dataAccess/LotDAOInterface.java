package main.dataAccess;

import main.exception.LotException;

import java.util.ArrayList;

import main.model.Lot;

public interface LotDAOInterface
{
    ArrayList<Lot> readAll() throws LotException;
}
