package controller;

import businessLogic.ProductModelService;
import exception.ConnectionException;
import exception.ProductModelException;
import model.ProductModel;

import java.util.ArrayList;

public class ProductModelController {

    private final ProductModelService productModelService;

    public ProductModelController() throws ConnectionException {
        this.productModelService = new ProductModelService();
    }

    public ProductModel getProductModelById(int id) throws ProductModelException
    {

            return productModelService.getProductModel(id);

    }

    public ArrayList<ProductModel> getAllProductModels() throws ProductModelException
    {
        return productModelService.getAllProducts();
    }

    public void createProductModel(ProductModel productModel) throws ProductModelException {

            productModelService.addNewProduct(productModel);

    }

    public void updateProductModel(ProductModel productModel) throws ProductModelException {

            productModelService.updateProduct(productModel);

    }

    public void deleteProductModel(int barcode) throws ProductModelException {

            productModelService.deleteProduct(barcode);

    }
}