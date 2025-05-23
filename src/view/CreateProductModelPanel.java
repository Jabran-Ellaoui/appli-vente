package view;

import model.ProductModel;
import javax.swing.*;

public class CreateProductModelPanel extends JPanel {
    JLabel barcodeLabel,labelLabel,ecoScoreLabel,fidelityPointNbLabel,requiredAgeLabel,keptWarmLabel, keptColdLabel,expirationDateLabel,weightLabel,storageTemperatureLabel, provenanceLabel;
    JTextField barcode, label, ecoScore, fidelityPointNb, requiredAge, weight, storageTemperature, provenance;
    JCheckBox keptCold, keptWarm;
    JComboBox expirationDay, expirationMonth, expirationYear;

    JButton reset, backToWelcomePanel, submit;


    public CreateProductModelPanel() {

    }
}
