package model;

import java.util.ArrayList;

public class Customer
{
    private int id;
    private String lastname;
    private String firstname;
    private String address;
    private String email;
    private Integer phoneNumber;
    private Integer fidelityPointNb;
    private Locality locality;

    public Customer(int id, String lastname, String firstname, String address, String email, Integer phoneNumber, Integer fidelityPointNb, Locality locality)
    {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fidelityPointNb = fidelityPointNb;
        this.locality = locality;
    }

    public Customer(int id)
    {
        this.id = id;
    }
}