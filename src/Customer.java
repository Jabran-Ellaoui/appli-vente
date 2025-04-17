import java.util.ArrayList;

public class Customer
{
    private int id;
    private String lastname;
    private String firstname;
    private String address;
    private String email;
    private int phoneNumber;
    private int fidelityPointNb;
    private Locality locality;
    private ArrayList<SalesDetails> salesDetails;

    public Customer(int id, String lastname, String firstname, String address, String email, int phoneNumber, int fidelityPointNb, Locality locality)
    {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fidelityPointNb = fidelityPointNb;
        this.locality = locality;
        this.salesDetails = new ArrayList<>();
    }
}
