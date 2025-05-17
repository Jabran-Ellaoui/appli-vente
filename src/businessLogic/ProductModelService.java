package businessLogic;

import dataAccess.ProductModelDAO;
import exception.ConnectionException;
import exception.ProductModelException;
import model.ProductModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductModelService {
    private final ProductModelDAO productModelDAO;

    public ProductModelService(ProductModelDAO productModelDAO) {
        this.productModelDAO = productModelDAO;
    }

    public ProductModel getProductModel(int id) throws SQLException, ProductModelException {
        return productModelDAO.read(id);
    }

    public ArrayList<ProductModel> getAllProducts() throws ConnectionException, ProductModelException {
        return (ArrayList<ProductModel>)productModelDAO.readAll();
    }

    // à finir
    public void addNewProduct(ProductModel productModel) throws ConnectionException, ProductModelException {
        // règle métier ex: pas de produit avec un poids <= 0
        if (productModel.getWeight() <= 0) {
            throw new IllegalArgumentException("Le poids du produit doit être positif.");
        }
        productModelDAO.create(productModel);
    }

    public void updateProduct(ProductModel productModel) throws ConnectionException, ProductModelException {
        productModelDAO.update(productModel);
    }

    public void deleteProduct(int barcode) throws ConnectionException, ProductModelException {
        productModelDAO.delete(barcode);
    }
}
