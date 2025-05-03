package model;

import java.util.ArrayList;

public class Employee
{
    private Integer id;
    private String lastname;
    private String firstname;
    private Integer phoneNumber;
    private ArrayList<Lot> lots;
    private ArrayList<SalesDetails> salesDetails;


    public Employee(Integer id, String lastname, String firstname, Integer phoneNumber)
    {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.phoneNumber = phoneNumber;
        this.lots = new ArrayList<>();
        this.salesDetails = new ArrayList<>();
    }
}
