package view;

import controller.LotController;
import controller.ProductModelController;
import model.Lot;
import model.ProductModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class UpdateProductModelPanel extends JPanel {
    private final ProductModelController productModelController;
    private final LotController lotController;
    private JPanel searchPanel, resultsPanel, buttonsPanel;
    private JLabel barcodeLabel,labelLabel,ecoScoreLabel,fidelityPointNbLabel,requiredAgeLabel,expirationDateLabel,weightLabel,storageTemperatureLabel, provenanceLabel;
    private JTextField barcode, label, ecoScore, fidelityPointNb, requiredAge, weight, storageTemperature;
    private JRadioButton keptCold, keptWarm;
    private ButtonGroup keptGroup;
    private JComboBox provenance;
    private JSpinner expirationDate;

    JLabel barcodeSearchLabel, info;
    JTextField barcodeSearchField;

    JButton reset, backToWelcomePanel, submit, clear;
    public UpdateProductModelPanel(ProductModelController productModelController, LotController lotController, MainWindow mainWindow) {
        this.productModelController = productModelController;
        this.lotController = lotController;
        this.setLayout(new BorderLayout());

        //search Panel
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 2));

        barcodeSearchLabel = new JLabel("Code-barres");
        barcodeSearchField = new JTextField(9);
        BarcodeTextListener barcodeTextListener = new BarcodeTextListener();
        barcodeSearchField.addActionListener(barcodeTextListener);
        info = new JLabel("Appuyez sur enter après la saisie");

        searchPanel.add(barcodeSearchLabel);
        searchPanel.add(barcodeSearchField);
        searchPanel.add(info);
        this.add(searchPanel, BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(11, 2, 5, 5));
        this.add(resultsPanel, BorderLayout.CENTER);

        // button panel
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        backToWelcomePanel = new JButton("Retour");
        backToWelcomePanel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainWindow.homePage();
            }
        });
        buttonsPanel.add(backToWelcomePanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void createResultsPanel(ProductModel productModel) {
        resultsPanel.removeAll();

        barcodeLabel = new JLabel("Code-barres");
        barcodeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultsPanel.add(barcodeLabel);
        barcode = new JTextField(9);
        barcode.setEditable(false);
        barcode.setText(String.valueOf(productModel.getBarcode()));
        resultsPanel.add(barcode);

        labelLabel = new JLabel("Intitulé");
        labelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultsPanel.add(labelLabel);
        label = new JTextField(30);
        label.setText(productModel.getLabel());
        resultsPanel.add(label);

        fidelityPointNbLabel = new JLabel("Nombre de points de fidelité");
        fidelityPointNbLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultsPanel.add(fidelityPointNbLabel);
        fidelityPointNb =  new JTextField(3);
        fidelityPointNb.setText(String.valueOf(productModel.getFidelityPointNb()));
        resultsPanel.add(fidelityPointNb);

        requiredAgeLabel = new JLabel("Age requis");
        requiredAgeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultsPanel.add(requiredAgeLabel);
        requiredAge = new JTextField(3);
        requiredAge.setText(productModel.getRequiredAge() != null ? String.valueOf(productModel.getRequiredAge()) : "");
        resultsPanel.add(requiredAge);

        weightLabel = new JLabel("Poids ");
        weightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultsPanel.add(weightLabel);
        weight = new JTextField(3);
        weight.setText(String.valueOf(productModel.getWeight()));
        resultsPanel.add(weight);

        storageTemperatureLabel = new JLabel("Température de stockage");
        storageTemperatureLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultsPanel.add(storageTemperatureLabel);
        storageTemperature = new JTextField(3);
        storageTemperature.setText(productModel.getStorageTemperature() != null ? String.valueOf(productModel.getStorageTemperature()) : "");
        resultsPanel.add(storageTemperature);

        provenanceLabel = new JLabel("Provenance");
        provenanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultsPanel.add(provenanceLabel);
        provenance = new JComboBox<>();
        ArrayList<Lot> lots = lotController.getAllLot();
        for (Lot lot : lots) {
            provenance.addItem(lot);
        }
        provenance.setSelectedItem(productModel.getProvenance().toString());
        resultsPanel.add(provenance);

        expirationDateLabel = new JLabel("Date d'expiration");
        expirationDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultsPanel.add(expirationDateLabel);
        SpinnerDateModel dateModel = new SpinnerDateModel();
        expirationDate = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(expirationDate, "yyyy-MM-dd");
        expirationDate.setEditor(editor);
        expirationDate.setValue(Date.from(productModel.getExpirationDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        resultsPanel.add(expirationDate);

        keptGroup = new ButtonGroup();
        keptCold = new JRadioButton("Garder au froid");
        keptCold.setHorizontalAlignment(SwingConstants.RIGHT);
        keptWarm = new JRadioButton("Garder au chaud");
        keptGroup.add(keptWarm);
        keptGroup.add(keptCold);
        keptCold.setSelected(productModel.isKeptCold());
        keptWarm.setSelected(productModel.isKeptWarm());
        resultsPanel.add(keptCold);
        resultsPanel.add(keptWarm);

        ecoScoreLabel = new JLabel("Eco-score");
        ecoScoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultsPanel.add(ecoScoreLabel);
        ecoScore = new JTextField(1);
        ecoScore.setText(productModel.getEcoScore() != null ? String.valueOf(productModel.getEcoScore()) : "/");
        resultsPanel.add(ecoScore);

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private class BarcodeTextListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                int barcode = Integer.parseInt(barcodeSearchField.getText());
                ProductModel productModel = searchProductModel(barcode);
                createResultsPanel(productModel);
                addButtonAfterSearch();
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Le code-barres doit être un entier.");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la recherche : " + exception.getMessage());
            }
        }
    }

    private ProductModel searchProductModel(int barcode) {
        try {
            return productModelController.getProductModelById(barcode);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche : " + exception.getMessage());
            return null;
        }
    }

    private void addButtonAfterSearch() {
        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int barcode = Integer.parseInt(barcodeSearchField.getText());
                    ProductModel productModel = searchProductModel(barcode);
                    createResultsPanel(productModel);
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Le code-barres doit être un entier.");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la recherche : " + exception.getMessage());
                }
            }
        });
        buttonsPanel.add(reset);

        clear = new JButton("Reset");
        clear.addActionListener(new ActionListener() {
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
        buttonsPanel.add(clear);

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
                    productModelController.updateProductModel(productModel);
                    JOptionPane.showMessageDialog(null, productModel,"Fiche Produit", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur de validation : \n" + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonsPanel.add(submit);
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
                errorMessages += "- Le poids doit être un nombre.\n";
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
