package main.model;

import java.time.LocalDate;
import java.util.Objects;

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

    public SalesDetails(int id, int quantity, boolean fidelityPointUsed, LocalDate date, Employee seller)
    {
        this.id = id;
        this.quantity = quantity;
        this.fidelityPointUsed = fidelityPointUsed;
        this.paymentMethod = null;
        this.comment = null;
        this.date = date;
        this.buyer = null;
        this.seller = seller;
    }

    public SalesDetails(int id)
    {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isFidelityPointsUsed() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setFidelityPointUsed(boolean fidelityPointUsed) {
        this.fidelityPointUsed = fidelityPointUsed;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSeller(Employee seller) {
        this.seller = seller;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SalesDetails that = (SalesDetails) o;
        return id == that.id && quantity == that.quantity && fidelityPointUsed == that.fidelityPointUsed && Objects.equals(paymentMethod, that.paymentMethod) && Objects.equals(comment, that.comment) && Objects.equals(date, that.date) && Objects.equals(seller, that.seller) && Objects.equals(buyer, that.buyer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, fidelityPointUsed, paymentMethod, comment, date, seller, buyer);
    }

    public String toString() {
        return "ID de la vente : " + id + "\n" +
                "Quantité : " + quantity + "\n" +
                "Points fidélité utilisés : " + (fidelityPointUsed ? "Oui" : "Non") + "\n" +
                "Méthode de paiement : " + (paymentMethod != null ? paymentMethod : "Non spécifiée") + "\n" +
                "Commentaire : " + (comment != null ? comment : "Aucun") + "\n" +
                "Date : " + (date != null ? date.toString() : "Non spécifiée") + "\n" +
                "Acheteur : " + (buyer != null ? buyer.toString() : "Non spécifié") + "\n" +
                "Vendeur : " + (seller != null ? seller.toString() : "Non spécifié");
    }
}