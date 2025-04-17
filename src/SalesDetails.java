import java.util.Date;

public class SalesDetails
{
    private int id;
    private int quantity;
    private boolean fidelityPointUsed;
    private String paymentMethod;
    private String comment;
    private Date date;

    public SalesDetails(int id, int quantity, boolean fidelityPointUsed, String paymentMethod, String comment, Date date) {
        this.id = id;
        this.quantity = quantity;
        this.fidelityPointUsed = fidelityPointUsed;
        this.paymentMethod = paymentMethod;
        this.comment = comment;
        this.date = date;
    }


}
