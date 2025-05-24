package controller;

import businessLogic.ProductService;
import exception.ConnectionException;
import exception.ProductException;
import model.Product;

import java.util.ArrayList;

public class ProductController
{
    private final ProductService productService;

    public ProductController() throws ConnectionException
    {
        this.productService = new ProductService();
    }

    public ArrayList<Product> getAllProducts()
    {

            return productService.getAllProducts();
    }
}
