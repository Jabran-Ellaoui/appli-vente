package main.businessLogic;

import main.dataAccess.ProductModelDAO;
import main.dataAccess.ProductModelDAOInterface;
import main.exception.ConnectionException;
import main.exception.ProductModelException;
import main.model.ProductModel;

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


    public void addNewProduct(ProductModel productModel) throws ProductModelException {
        // Vérification de l'éco-score
        String ecoScore = productModel.getEcoScore();
        if (ecoScore != null && !ecoScore.matches("[A-E]")) {
            throw new ProductModelException("L'éco-score doit être une lettre entre A et E.");
        }

        // Vérification des points de fidélité
        if (productModel.getFidelityPointNb() < 0) {
            throw new ProductModelException("Le nombre de points de fidélité ne peut pas être négatif.");
        }

        // Vérification de l'âge requis
        if (productModel.getRequiredAge() != null && productModel.getRequiredAge() < 0) {
            throw new ProductModelException("L'âge requis ne peut pas être négatif.");
        }

        // Vérification du poids
        if (productModel.getWeight() <= 0) {
            throw new ProductModelException("Le poids doit être supérieur à 0.");
        }

        // Si tout est bon, on crée le produit
        productModelDAO.create(productModel);
    }


    public void updateProduct(ProductModel productModel) throws ProductModelException {
        if (productModel.getFidelityPointNb() < 0) {
            throw new ProductModelException("Le nombre de points de fidélité ne peut pas être négatif.");
        }

        if (productModel.getRequiredAge() != null && productModel.getRequiredAge() < 0) {
            throw new ProductModelException("L'âge requis ne peut pas être négatif.");
        }

        if (productModel.getWeight() <= 0) {
            throw new ProductModelException("Le poids doit être strictement supérieur à 0.");
        }

        if (productModel.getEcoScore() != null && !"ABCDE".contains(productModel.getEcoScore())) {
            throw new ProductModelException("L'éco-score doit être une lettre parmi : A, B, C, D, E.");
        }

        productModelDAO.update(productModel);
    }


    public void deleteProduct(int barcode) throws ProductModelException {
        productModelDAO.delete(barcode);
    }
}
