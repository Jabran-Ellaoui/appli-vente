package dataAccess;

import exception.ConnectionException;
import model.ProductModel;

import java.util.List;

public interface DataAccessInterface<T>
{
    void create(T object) throws Exception;
    T read(int id) throws Exception;
    void update(T object) throws Exception;
    void delete(int id) throws Exception;
    List<T> readAll() throws Exception;
}