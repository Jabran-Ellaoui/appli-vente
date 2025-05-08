package model;

import java.util.ArrayList;

public class Locality {
    private int postalCode;
    private String locality;
    private ArrayList<Customer> customers;

    public Locality(int postalCode, String locality)
    {
        this.postalCode = postalCode;
        this.locality = locality;
        this.customers = new ArrayList<>();
    }
}