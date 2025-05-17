package model;

import java.time.LocalDate;

public class SalesDetails
{
    private int id;
    private int quantity;
    private boolean fidelityPointUsed;
    private String paymentMethod;
    private String comment;
    private LocalDate date;
    private Employee seller;
    private Customer buyer;


    public SalesDetails(int id, int quantity, boolean fidelityPointUsed, String paymentMethod, String comment, LocalDate date, Customer buyer, Employee seller)
    {
        this.id = id;
        this.quantity = quantity;
        this.fidelityPointUsed = fidelityPointUsed;
        this.paymentMethod = paymentMethod;
        this.comment = comment;
        this.date = date;
        this.buyer = buyer;
        this.seller = seller;
    }


    public int getQuantity() {
        return quantity;
    }

    public boolean isFidelityPointUsed() {
        return fidelityPointUsed;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public Employee getSeller() {
        return seller;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public int getId() {
        return id;
    }
}