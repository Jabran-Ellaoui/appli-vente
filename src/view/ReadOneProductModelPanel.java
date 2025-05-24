package view;

import model.ProductModel;
import controller.ProductModelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReadOneProductModelPanel extends JPanel {
    ProductModelController productModelController;
    MainWindow mainWindow;
    JPanel searchPanel, resultsPanel, buttonsPanel;
    JLabel barcodeLabel, info;
    JTextField barcodeField;
    JLabel barcodeLabelInfo, barcodeInfo, labelLabelInfo, labelInfo, fidelityPointNbLabelInfo, fidelityPointNbInfo, requiredAgeLabelInfo, requiredAgeInfo, keptWarmLabelInfo, keptWarmInfo, keptColdLabelInfo, keptColdInfo, expirationDateLabelInfo, expirationDateInfo, weightLabelInfo, weightInfo, storageTemperatureLabelInfo, storageTemperatureInfo, provenanceLabelInfo, provenanceInfo, ecoScoreLabelInfo, ecoScoreInfo;
    JButton backToWelcomePanel;

    public ReadOneProductModelPanel(ProductModelController productModelController, MainWindow mainWindow) {
        this.productModelController = productModelController;
        this.mainWindow = mainWindow;
        this.setLayout(new BorderLayout());

        searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 2));

        barcodeLabel = new JLabel("Code-barres");
        barcodeField = new JTextField(9);
        BarcodeTextListener barcodeTextListener = new BarcodeTextListener();
        barcodeField.addActionListener(barcodeTextListener);
        info = new JLabel("Appuyez sur enter après la saisie");

        searchPanel.add(barcodeLabel);
        searchPanel.add(barcodeField);
        searchPanel.add(info);
        this.add(searchPanel, BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(11, 2, 5, 5));
        this.add(resultsPanel, BorderLayout.CENTER);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
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

        barcodeLabelInfo = new JLabel("Code-barres :");
        barcodeInfo = new JLabel(String.valueOf(productModel.getBarcode()));
        resultsPanel.add(barcodeLabelInfo);
        resultsPanel.add(barcodeInfo);

        labelLabelInfo = new JLabel("Libellé :");
        labelInfo = new JLabel(productModel.getLabel());
        resultsPanel.add(labelLabelInfo);
        resultsPanel.add(labelInfo);

        fidelityPointNbLabelInfo = new JLabel("Points de fidélité :");
        fidelityPointNbInfo = new JLabel(String.valueOf(productModel.getFidelityPointNb()));
        resultsPanel.add(fidelityPointNbLabelInfo);
        resultsPanel.add(fidelityPointNbInfo);

        requiredAgeLabelInfo = new JLabel("Age requis :");
        requiredAgeInfo = new JLabel(String.valueOf(productModel.getRequiredAge()));
        resultsPanel.add(requiredAgeLabelInfo);
        resultsPanel.add(requiredAgeInfo);

        keptWarmLabelInfo = new JLabel("Conservé au chaud :");
        keptWarmInfo = new JLabel(productModel.isKeptWarm() ? "Oui" : "Non");
        resultsPanel.add(keptWarmLabelInfo);
        resultsPanel.add(keptWarmInfo);

        keptColdLabelInfo = new JLabel("Conservé au froid :");
        keptColdInfo = new JLabel(productModel.isKeptCold() ? "Oui" : "Non");
        resultsPanel.add(keptColdLabelInfo);
        resultsPanel.add(keptColdInfo);

        expirationDateLabelInfo = new JLabel("Date d'expiration :");
        expirationDateInfo = new JLabel(productModel.getExpirationDate().toString());
        resultsPanel.add(expirationDateLabelInfo);
        resultsPanel.add(expirationDateInfo);

        weightLabelInfo = new JLabel("Poids :");
        weightInfo = new JLabel(String.valueOf(productModel.getWeight()) + "Kg");
        resultsPanel.add(weightLabelInfo);
        resultsPanel.add(weightInfo);

        storageTemperatureLabelInfo = new JLabel("Température de stockage:");
        storageTemperatureInfo = new JLabel(String.valueOf(productModel.getStorageTemperature()));
        resultsPanel.add(storageTemperatureLabelInfo);
        resultsPanel.add(storageTemperatureInfo);

        provenanceLabelInfo = new JLabel("Provenance :");
        provenanceInfo = new JLabel(productModel.getProvenance().toString());
        resultsPanel.add(provenanceLabelInfo);
        resultsPanel.add(provenanceInfo);

        ecoScoreLabelInfo = new JLabel("Eco-score :");
        ecoScoreInfo = new JLabel(String.valueOf(productModel.getEcoScore()));
        resultsPanel.add(ecoScoreLabelInfo);
        resultsPanel.add(ecoScoreInfo);

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private class BarcodeTextListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                int barcode = Integer.parseInt(barcodeField.getText());
                ProductModel productModel = productModelController.getProductModelById(barcode);
                createResultsPanel(productModel);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(ReadOneProductModelPanel.this, "Le code-barres doit être un entier.");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(ReadOneProductModelPanel.this, "Erreur lors de la recherche : " + exception.getMessage());
            }
        }
    }
}
