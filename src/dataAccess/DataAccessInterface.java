package dataAccess;

import exception.ConnectionException;
import model.ProductModel;

import java.util.List;

public interface DataAccessInterface<T>
{
    void create(T object) throws ConnectionException;
    T read(int id);
    void update(T object) throws ConnectionException;
    void delete(int id) throws ConnectionException;
    List<T> readAll() throws ConnectionException;
}