import java.util.ArrayList;
import java.util.Date;

/**
 * Représente un ticket de caisse
 */

public class SalesDetails
{
    private int id;
    private int quantity;
    private boolean fidelityPointUsed;
    private String paymentMethod;
    private String comment;
    private Date date;
    private Employee seller;
    private Customer buyer;
    private ArrayList<Product> products;


    public SalesDetails(int id, int quantity, boolean fidelityPointUsed, String paymentMethod, String comment, Date date, Customer buyer, Employee seller)
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
