package controller;

import businessLogic.ProductModelService;
import exception.ProductModelException;
import model.ProductModel;

import java.util.ArrayList;

public class ProductModelController {

    private final ProductModelService productModelService;

    public ProductModelController(ProductModelService productModelService)
    {
        this.productModelService = productModelService;
    }

    public ProductModel getProductModelById(int id)
    {
        try
        {
            return productModelService.getProductModel(id);
        }
        catch (ProductModelException exception)
        {
            System.err.println("Erreur lors de la récupération du produit : " + exception.getMessage());
            return null;
        }
    }

    public ArrayList<ProductModel> getAllProductModels()
    {
        try {
            return productModelService.getAllProducts();
        } catch (ProductModelException exception)
        {
            System.err.println("Erreur lors de la récupération de tous les produits : " + exception.getMessage());
            return new ArrayList<>();
        }
    }

    public void createProductModel(ProductModel productModel)
    {
        try
        {
            productModelService.addNewProduct(productModel);
        }
        catch (ProductModelException exception)
        {
            System.err.println("Erreur lors de la création du produit : " + exception.getMessage());
        }
    }

    public void updateProductModel(ProductModel productModel)
    {
        try {
            productModelService.updateProduct(productModel);
        }
        catch (ProductModelException exception)
        {
            System.err.println("Erreur lors de la mise à jour du produit : " + exception.getMessage());
        }
    }

    public void deleteProductModel(int barcode)
    {
        try
        {
            productModelService.deleteProduct(barcode);
        }
        catch (ProductModelException exception)
        {
            System.err.println("Erreur lors de la suppression du produit : " + exception.getMessage());
        }
    }
}