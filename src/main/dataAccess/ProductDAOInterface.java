package main.dataAccess;

import main.exception.ProductException;
import main.model.Product;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface ProductDAOInterface
{
    ArrayList<Product> readAll() throws ProductException;

    ArrayList<Product> readProductsBySalesID(int salesID);
    void updateProduct(int productID, int salesID);
    ArrayList<Product> readAllUnsoldProduct();
}
