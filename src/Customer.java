import java.util.ArrayList;

/**
 * Représente un client
 */

public class Customer
{
    private Integer id;
    private String lastname;
    private String firstname;
    private String address;
    private String email;
    private Integer phoneNumber;
    private Integer fidelityPointNb;
    private Locality locality;
    private ArrayList<SalesDetails> salesDetails;

    public Customer(Integer id, String lastname, String firstname, String address, String email, Integer phoneNumber, Integer fidelityPointNb, Locality locality)
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
