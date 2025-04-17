import java.time.LocalDate;

public class ProductModel {
    private int barcode;
    private String label;
    private int fidelityPointNb;
    private int requiredAge;
    private boolean keptWarm;
    private boolean keptCold;
    private LocalDate expirationDate;
    private double weight;
    private int storageTemperature;
    private Lot provenance;
    private String ecoScore;

    public ProductModel(int barcode, String label, int fidelityPointNb, int requiredAge, boolean keptWarm, boolean keptCold, LocalDate expirationDate, double weight, int storageTemperature, Lot provenance, String ecoScore) {
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
}
