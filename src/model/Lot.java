package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Lot {
    private int number;
    private String provenance;
    private LocalDate receptionDate;
    private Employee manager;
    private ArrayList<ProductModel> productsModel;

    public Lot(int number, String provenance, LocalDate receptionDate, Employee manager)
    {
        this.number = number;
        this.provenance = provenance;
        this.receptionDate = receptionDate;
        this.manager = manager;
        this.productsModel = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

}