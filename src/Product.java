

public class Product {
    private int id;
    private double unitPrice;
    private int promotionPercentage;
    private ProductModel model;
    private SalesDetails sale;

    public Product(int id, double unitPrice, int promotionPercentage, ProductModel model, SalesDetails sale) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.promotionPercentage = promotionPercentage;
        this.model = model;
        this.sale = sale;
    }
}