package model;

public class Product {
    private int id;
    private double unitPrice;
    private Integer promotionPercentage;
    private ProductModel model;
    private SalesDetails sale;

    public Product(int id, double unitPrice, Integer promotionPercentage, ProductModel model, SalesDetails sale) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.promotionPercentage = promotionPercentage;
        this.model = model;
        this.sale = sale;
    }

    public Product(int id, double unitPrice, ProductModel model, SalesDetails sale)
    {
        this.id = id;
        this.unitPrice = unitPrice;
        this.promotionPercentage = null;
        this.model = model;
        this.sale = sale;
    }

    public Integer getId() {
        return id;
    }
}