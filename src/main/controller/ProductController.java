package main.controller;

import main.businessLogic.ProductService;
import main.exception.ConnectionException;
import main.model.Product;

import java.util.ArrayList;

public class ProductController {
    private final ProductService productService;

    public ProductController() throws ConnectionException {
        this.productService = new ProductService();
    }

    public ArrayList<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public ArrayList<Product> getAllProductsBySalesID(int saleID)
    {
        return productService.getAllProductsBySalesID(saleID);
    }

    public void updateProduct(int productID, int salesID)
    {
        productService.updateProduct(productID, salesID);
    }

    public ArrayList<Product> readAllUnsoldProduct()
    {
        return productService.readAllUnsoldProduct();
    }

    public void releaseProduct(int saleId)
    {
        productService.releaseProduct(saleId);
    }

}
