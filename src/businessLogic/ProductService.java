package businessLogic;

import dataAccess.ProductDAO;
import dataAccess.ProductDAOInterface;
import exception.ConnectionException;
import exception.ProductException;
import model.Product;

import java.util.ArrayList;

public class ProductService
{
    private final ProductDAOInterface productDAO;

    public ProductService() throws ConnectionException
    {
        this.productDAO = new ProductDAO();
    }

    public ArrayList<Product> getAllProducts() throws ProductException
    {
        return (ArrayList<Product>)productDAO.readAll();
    }
}
