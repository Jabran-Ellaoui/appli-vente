package main.dataAccess;

import main.exception.ProductException;
import main.model.Product;

import java.util.ArrayList;

public interface ProductDAOInterface
{
    ArrayList<Product> readAll() throws ProductException;

    ArrayList<Product> readProductsBySalesID(int salesID);
}
