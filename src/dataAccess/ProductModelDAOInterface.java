package dataAccess;

import exception.ProductModelException;
import model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public interface ProductModelDAOInterface {
    void create(ProductModel productModel) throws ProductModelException;
    ProductModel read(int id) throws ProductModelException;
    void update(ProductModel productModel) throws ProductModelException;
    void delete(int id) throws ProductModelException;
    ArrayList<ProductModel> readAll() throws ProductModelException;
}

