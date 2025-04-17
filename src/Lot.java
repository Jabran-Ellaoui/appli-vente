import java.time.LocalDate;

public class Lot {
    private int number;
    private String provenance;
    private LocalDate receptionDate;
    private Employee manager;

    public Lot(int number, String provenance, LocalDate receptionDate, Employee manager) {
        this.number = number;
        this.provenance = provenance;
        this.receptionDate = receptionDate;
        this.manager = manager;
    }
}
