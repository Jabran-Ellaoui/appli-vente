import java.util.ArrayList;

public class Employee
{
    private int id;
    private String lastname;
    private String firstname;
    private int phoneNumber;
    private ArrayList<Lot> lots;
    private ArrayList<SalesDetails> salesDetails;


    public Employee(int id, String lastname, String firstname, int phoneNumber)
    {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.phoneNumber = phoneNumber;
        this.lots = new ArrayList<>();
        this.salesDetails = new ArrayList<>();
    }
}
