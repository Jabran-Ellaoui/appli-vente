package model;

import java.util.ArrayList;

public class Locality {
    private Integer postalCode;
    private String locality;
    private ArrayList<Customer> customers;

    public Locality(Integer postalCode, String locality)
    {
        this.postalCode = postalCode;
        this.locality = locality;
        this.customers = new ArrayList<>();
    }
}