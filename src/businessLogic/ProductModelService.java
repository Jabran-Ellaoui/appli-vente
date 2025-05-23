package businessLogic;

import dataAccess.ProductModelDAO;
import dataAccess.ProductModelDAOInterface;
import exception.ConnectionException;
import exception.ProductModelException;
import model.ProductModel;

import java.util.ArrayList;

public class ProductModelService {
    private final ProductModelDAOInterface productModelDAO;

    public ProductModelService() throws ConnectionException {
        this.productModelDAO = new ProductModelDAO();
    }

    public ProductModel getProductModel(int id) throws ProductModelException {
        return productModelDAO.read(id);
    }

    public ArrayList<ProductModel> getAllProducts() throws ProductModelException {
        return (ArrayList<ProductModel>)productModelDAO.readAll();
    }

    // à finir
    public void addNewProduct(ProductModel productModel) throws ProductModelException {
        productModelDAO.create(productModel);
    }

    public void updateProduct(ProductModel productModel) throws ProductModelException {
        productModelDAO.update(productModel);
    }

    public void deleteProduct(int barcode) throws ProductModelException {
        productModelDAO.delete(barcode);
    }
}
