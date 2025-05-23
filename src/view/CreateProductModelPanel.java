package view;

import controller.LotController;
import model.ProductModel;
import controller.ProductModelController;
import javax.swing.*;
import java.awt.*;
import java.text.Normalizer;

public class CreateProductModelPanel extends JPanel {
    private final ProductModelController productModelController;
    private final LotController lotController;
    private JPanel formPanel;
    private JPanel buttonsPanel;
    private JPanel datePanel;
    private JLabel barcodeLabel,labelLabel,ecoScoreLabel,fidelityPointNbLabel,requiredAgeLabel,keptWarmLabel, keptColdLabel,expirationDateLabel,weightLabel,storageTemperatureLabel, provenanceLabel;
    private JTextField barcode, label, ecoScore, fidelityPointNb, requiredAge, weight, storageTemperature;
    private JCheckBox keptCold, keptWarm;
    private JComboBox expirationDay, expirationMonth, expirationYear, provenance;
    private JButton reset, backToWelcomePanel, submit;


    public CreateProductModelPanel(ProductModelController productModelController, LotController lotController) {
        this.productModelController = productModelController;
        this.lotController = lotController;
        formPanel = new JPanel();
        buttonsPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8,2,5,5));

        barcodeLabel = new JLabel("Code-barres");
        barcodeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(barcodeLabel);
        barcode = new JTextField(10);
        formPanel.add(barcode);

        labelLabel = new JLabel("Intitulé");
        labelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(labelLabel);
        label = new JTextField(30);
        formPanel.add(label);

        ecoScoreLabel = new JLabel("Eco-score");
        ecoScoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(ecoScoreLabel);
        ecoScore = new JTextField(1);
        formPanel.add(ecoScore);

        fidelityPointNbLabel = new JLabel("Nombre de points de fidelité");
        fidelityPointNbLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(fidelityPointNbLabel);
        fidelityPointNb =  new JTextField(3);
        formPanel.add(fidelityPointNb);

        requiredAgeLabel = new JLabel("Age requis");
        requiredAgeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(requiredAgeLabel);
        requiredAge = new JTextField(3);
        formPanel.add(requiredAge);

        weightLabel = new JLabel("Poids ");
        weightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(weightLabel);
        weight = new JTextField(3);
        formPanel.add(weight);

        storageTemperatureLabel = new JLabel("Température de stockage");
        storageTemperatureLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(storageTemperatureLabel);
        storageTemperature = new JTextField(3);
        formPanel.add(storageTemperature);

        provenanceLabel = new JLabel("Provenance");
        provenanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(provenanceLabel);



    }
}
