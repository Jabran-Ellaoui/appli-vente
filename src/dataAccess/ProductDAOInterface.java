package dataAccess;

import exception.ProductException;
import model.Product;

import java.util.ArrayList;

public interface ProductDAOInterface
{
    ArrayList<Product> readAll() throws ProductException;

    ArrayList<Product> readProductsBySalesID(int salesID);
}
