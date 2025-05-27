package main.view;

import main.controller.LotController;
import main.model.Lot;
import main.model.ProductModel;
import main.controller.ProductModelController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class CreateProductModelPanel extends JPanel {
    private final ProductModelController productModelController;
    private final LotController lotController;
    private JPanel formPanel;
    private JPanel buttonsPanel;
    private JLabel barcodeLabel,labelLabel,ecoScoreLabel,fidelityPointNbLabel,requiredAgeLabel,expirationDateLabel,weightLabel,storageTemperatureLabel, provenanceLabel;
    private JTextField barcode, label, ecoScore, fidelityPointNb, requiredAge, weight, storageTemperature;
    private JRadioButton keptCold, keptWarm;
    private ButtonGroup keptGroup;
    private JComboBox provenance;
    private JSpinner expirationDate;
    private JButton reset, backToWelcomePanel, submit;


    public CreateProductModelPanel(ProductModelController productModelController, LotController lotController, MainWindow mainWindow) {
        this.productModelController = productModelController;
        this.lotController = lotController;
        // form panel
        formPanel = new JPanel();
        buttonsPanel = new JPanel();
        formPanel.setLayout(new GridLayout(11,2,5,5));

        barcodeLabel = new JLabel("Code-barres");
        barcodeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(barcodeLabel);
        barcode = new JTextField(9);
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
        provenance = new JComboBox<>();
        ArrayList<Lot> lots = lotController.getAllLot();
        for (Lot lot : lots) {
            provenance.addItem(lot);
        }
        formPanel.add(provenance);
        // rajouter un try catch ?

        expirationDateLabel = new JLabel("Date d'expiration");
        expirationDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(expirationDateLabel);
        SpinnerDateModel dateModel = new SpinnerDateModel();
        expirationDate = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(expirationDate, "yyyy-MM-dd");
        expirationDate.setEditor(editor);
        formPanel.add(expirationDate);

        keptGroup = new ButtonGroup();
        keptCold = new JRadioButton("Garder au froid");
        keptCold.setHorizontalAlignment(SwingConstants.RIGHT);
        keptWarm = new JRadioButton("Garder au chaud");
        keptGroup.add(keptWarm);
        keptGroup.add(keptCold);
        formPanel.add(keptCold);
        formPanel.add(keptWarm);


        // Button panel
        backToWelcomePanel = new JButton("Retour");
        backToWelcomePanel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainWindow.homePage();
            }
        });
        buttonsPanel.add(backToWelcomePanel);

        submit = new JButton("Validation");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (!validateCreateProductModelForm()) {
                    return;
                }
                try {
                ProductModel productModel;
                Date selected = (Date) expirationDate.getValue();
                LocalDate expirationDateFormat = selected.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                productModel = new ProductModel(Integer.parseInt(barcode.getText()), label.getText(), Integer.parseInt(fidelityPointNb.getText()), (!requiredAge.getText().equals("") ? Integer.valueOf(requiredAge.getText()) : null), keptWarm.isSelected(), keptCold.isSelected(), expirationDateFormat, Double.valueOf(weight.getText()), (!storageTemperature.getText().equals("") ? Integer.valueOf(storageTemperature.getText()) : null), (Lot) provenance.getSelectedItem(), (!ecoScore.getText().equals("") ? ecoScore.getText() : null));
                productModelController.createProductModel(productModel);
                JOptionPane.showMessageDialog(null, productModel,"Fiche Produit", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur de validation : \n" + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonsPanel.add(submit);

        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                barcode.setText("");
                label.setText("");
                fidelityPointNb.setText("");
                requiredAge.setText("");
                weight.setText("");
                storageTemperature.setText("");
                ecoScore.setText("");
                if (provenance.getItemCount() > 0) {
                    provenance.setSelectedIndex(0);
                }
                keptGroup.clearSelection();
                expirationDate.setValue(new Date());
            }
        });
        buttonsPanel.add(reset);

        // ajout et setlayout
        this.setLayout(new BorderLayout());
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }
    private boolean validateCreateProductModelForm() {
        String errorMessages = "";

        if (barcode.getText().trim().isEmpty()) {
            errorMessages += "- Le code-barres est requis.\n";
        } else {
            try {
                Integer.parseInt(barcode.getText());
            } catch (NumberFormatException e) {
                errorMessages += "- Le code-barres doit être un nombre entier.\n";
            }
        }

        if (label.getText().trim().isEmpty()) {
            errorMessages += "- L'intitulé est requis.\n";
        }

        if (!fidelityPointNb.getText().trim().isEmpty()) {
            try {
                Integer.parseInt(fidelityPointNb.getText());
            } catch (NumberFormatException e) {
                errorMessages += "- Le nombre de points de fidélité doit être un nombre entier.\n";
            }
        }

        if (!requiredAge.getText().trim().isEmpty()) {
            try {
                Integer.parseInt(requiredAge.getText());
            } catch (NumberFormatException e) {
                errorMessages += "- L'âge requis doit être un nombre entier.\n";
            }
        }

        if (weight.getText().trim().isEmpty()) {
            errorMessages += "- Le poids est requis.\n";
        } else {
            try {
                Double.parseDouble(weight.getText());
            } catch (NumberFormatException e) {
                errorMessages += "- Le poids doit être un nombre entier.\n";
            }
        }

        if (!storageTemperature.getText().trim().isEmpty()) {
            try {
                Integer.parseInt(storageTemperature.getText());
            } catch (NumberFormatException e) {
                errorMessages += "- La température de stockage doit être un nombre entier.\n";
            }
        }

        if (provenance.getSelectedItem() == null) {
            errorMessages += "- Un lot de provenance doit être sélectionné.\n";
        }

        if (!errorMessages.isEmpty()) {
            JOptionPane.showMessageDialog(this, errorMessages, "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}