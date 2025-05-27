package test;

import main.controller.ProductModelController;
import main.controller.SalesDetailsController;
import main.controller.SearchController;
import main.exception.ConnectionException;
import main.exception.ProductModelException;
import main.exception.SearchException;
import main.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SalesDetailsControllerTest {

    private SalesDetailsController controller;

    @BeforeEach
    void setUp() throws SearchException, ConnectionException
    {
        controller = new SalesDetailsController();
    }

    @Test
    void getProductsByModelProvenanceDate_shouldReturnAtLeastOneKnownProduct() throws SearchException, ProductModelException
    {
        SalesDetails result = controller.getSalesDetails(2);
        Employee seller = new Employee(2);
        Customer buyer = new Customer(15);
        SalesDetails salesDetail = new SalesDetails(2, 5, false, "Cash", "Achat groupé", LocalDate.of(2025,5,22), buyer, seller);
        assertEquals (salesDetail, result);
    }
}
