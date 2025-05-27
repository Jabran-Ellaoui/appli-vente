package test;

import main.controller.ProductModelController;
import main.controller.SearchController;
import main.exception.ConnectionException;
import main.exception.ProductModelException;
import main.exception.SearchException;
import main.model.Lot;
import main.model.ProductModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ProductModelControllerTest {

    private ProductModelController controller;

    @BeforeEach
    void setUp() throws SearchException, ConnectionException {
        controller = new ProductModelController();
    }

    @Test
    void getProductsByModelProvenanceDate_shouldReturnAtLeastOneKnownProduct() throws SearchException, ProductModelException
    {
        ProductModel result = controller.getProductModelById(11099999);
        Lot provenance = new Lot(3);
        ProductModel productModel = new ProductModel(11099999, "Gouda", 9, null, false, true, LocalDate.of(2025,8,30), 0.2, 4, provenance, "B");
        assertEquals (productModel, result);
    }
}
