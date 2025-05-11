package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProductModel {
    private int barcode;
    private String label;
    private int fidelityPointNb;
    private Integer requiredAge;
    private boolean keptWarm;
    private boolean keptCold;
    private LocalDate expirationDate;
    private Double weight;
    private Integer storageTemperature;
    private Lot provenance;
    private String ecoScore;

    public ProductModel(int barcode, String label, int fidelityPointNb, Integer requiredAge, boolean keptWarm, boolean keptCold, LocalDate expirationDate, double weight, Integer storageTemperature, Lot provenance, String ecoScore) {
        this.barcode = barcode;
        this.label = label;
        this.fidelityPointNb = fidelityPointNb;
        this.requiredAge = requiredAge;
        this.keptWarm = keptWarm;
        this.keptCold = keptCold;
        this.expirationDate = expirationDate;
        this.weight = weight;
        this.storageTemperature = storageTemperature;
        this.provenance = provenance;
        this.ecoScore = ecoScore;
    }

    public int getBarcode() {
        return barcode;
    }

    public String getLabel() {
        return label;
    }

    public int getFidelityPointNb() {
        return fidelityPointNb;
    }

    public int getRequiredAge() {
        return requiredAge;
    }

    public boolean isKeptWarm() {
        return keptWarm;
    }

    public boolean isKeptCold() {
        return keptCold;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public double getWeight() {
        return weight;
    }

    public int getStorageTemperature() {
        return storageTemperature;
    }

    public Lot getProvenance() {
        return provenance;
    }

    public String getEcoScore() {
        return ecoScore;
    }

}