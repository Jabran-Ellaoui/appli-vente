package model;

import java.util.ArrayList;
import java.util.Date;

public class SalesDetails
{
    private Integer id;
    private Integer quantity;
    private boolean fidelityPointUsed;
    private String paymentMethod;
    private String comment;
    private Date date;
    private Employee seller;
    private Customer buyer;
    private ArrayList<Product> products;


    public SalesDetails(Integer id, Integer quantity, boolean fidelityPointUsed, String paymentMethod, String comment, Date date, Customer buyer, Employee seller)
    {
        this.id = id;
        this.quantity = quantity;
        this.fidelityPointUsed = fidelityPointUsed;
        this.paymentMethod = paymentMethod;
        this.comment = comment;
        this.date = date;
        this.buyer = buyer;
        this.seller = seller;
        this.products = new ArrayList<>();
    }

}
