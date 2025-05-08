package dataAccess;

import model.ProductModel;

import java.util.List;

public interface DataAccessInterface
{
    void create(ProductModel product) throws Exception;
    ProductModel read(int id);
    void update(ProductModel product);
    void delete(int id);
    List<ProductModel> readAll();
}