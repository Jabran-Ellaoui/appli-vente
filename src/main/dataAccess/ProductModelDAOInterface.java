package main.dataAccess;

import main.exception.ProductModelException;
import main.model.ProductModel;

import java.util.ArrayList;

public interface ProductModelDAOInterface {
    void create(ProductModel productModel) throws ProductModelException;
    ProductModel read(int id) throws ProductModelException;
    void update(ProductModel productModel) throws ProductModelException;
    void delete(int id) throws ProductModelException;
    ArrayList<ProductModel> readAll() throws ProductModelException;
}

