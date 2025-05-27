package main.businessLogic;

import main.dataAccess.ProductDAO;
import main.dataAccess.ProductDAOInterface;
import main.exception.ConnectionException;
import main.exception.ProductException;
import main.model.Product;

import java.util.ArrayList;

public class ProductService {
    private final ProductDAOInterface productDAO;

    public ProductService() throws ConnectionException {
        this.productDAO = new ProductDAO();
    }

    public ArrayList<Product> getAllProducts() throws ProductException {
        return (ArrayList<Product>)productDAO.readAll();
    }

    public ArrayList<Product> getAllProductsBySalesID(int salesID) throws ProductException {
        return (ArrayList<Product>)productDAO.readProductsBySalesID(salesID);
    }

    public ArrayList<Product> readAllUnsoldProduct() throws ProductException {
        return (ArrayList<Product>)productDAO.readAllUnsoldProduct();
    }

    public void updateProduct(int productID, int salesID) throws ProductException {
        productDAO.updateProduct(productID, salesID);
    }

    public void releaseProduct(int saleId) throws ProductException {
        productDAO.releaseProduct(saleId);
    }
}