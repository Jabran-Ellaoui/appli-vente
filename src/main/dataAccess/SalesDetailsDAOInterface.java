package main.dataAccess;

import main.exception.SalesDetailsException;
import main.model.SalesDetails;

import java.util.ArrayList;

public interface SalesDetailsDAOInterface {
    void create(SalesDetails object) throws SalesDetailsException;
    SalesDetails read(int id) throws SalesDetailsException;
    void update(SalesDetails object) throws SalesDetailsException;
    void delete(int id) throws SalesDetailsException;
    ArrayList<SalesDetails> readAll() throws SalesDetailsException;
}
