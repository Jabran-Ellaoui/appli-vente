
/**
 * Représente le produit physique
 */

public class Product {
    private Integer id;
    private Double unitPrice;
    private Integer promotionPercentage;
    private ProductModel model;
    private SalesDetails sale;

    public Product(Integer id, Double unitPrice, Integer promotionPercentage, ProductModel model, SalesDetails sale) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.promotionPercentage = promotionPercentage;
        this.model = model;
        this.sale = sale;
    }
}