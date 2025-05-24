package view;

import controller.ProductModelController;
import exception.ProductModelException;
import model.ProductModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReadAllProductModelPanel extends JPanel {
    ProductModelController productModelController;
    MainWindow mainWindow;
    JPanel  resultsPanel, buttonsPanel;
    JLabel barcodeLabelInfo, barcodeInfo, labelLabelInfo, labelInfo, fidelityPointNbLabelInfo, fidelityPointNbInfo, requiredAgeLabelInfo, requiredAgeInfo, keptWarmLabelInfo, keptWarmInfo, keptColdLabelInfo, keptColdInfo, expirationDateLabelInfo, expirationDateInfo, weightLabelInfo, weightInfo, storageTemperatureLabelInfo, storageTemperatureInfo, provenanceLabelInfo, provenanceInfo, ecoScoreLabelInfo, ecoScoreInfo;
    JButton backToWelcomePanel, nextProductModel;

    private ArrayList<ProductModel> productModels = new ArrayList<>();
    private int iProductModels;
    private int productModelsLength;

    public ReadAllProductModelPanel(ProductModelController productModelController, MainWindow mainWindow) {
        this.productModelController = productModelController;
        this.mainWindow = mainWindow;
        this.setLayout(new BorderLayout());

        try{
            iProductModels = 0;
            productModels = productModelController.getAllProductModels();
            productModelsLength = productModels.size();
        } catch (ProductModelException e) {
            throw new RuntimeException(e);
        }

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(11, 2, 5, 5));
        createResultsPanel(productModels.get(iProductModels));
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
        nextProductModel = new JButton("Suivant");
        nextProductModel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(iProductModels < productModelsLength - 1) {
                    iProductModels++;
                    refreshResultsPanel(productModels.get(iProductModels));
                } else {
                    JOptionPane.showMessageDialog(null, "Vous avez atteint le dernier produit.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        buttonsPanel.add(nextProductModel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void createResultsPanel(ProductModel productModel) {
        resultsPanel.removeAll();

        barcodeLabelInfo = new JLabel("Code-barres :");
        barcodeLabelInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        barcodeInfo = new JLabel(String.valueOf(productModel.getBarcode()));
        resultsPanel.add(barcodeLabelInfo);
        resultsPanel.add(barcodeInfo);

        labelLabelInfo = new JLabel("Libellé :");
        labelLabelInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        labelInfo = new JLabel(productModel.getLabel());
        resultsPanel.add(labelLabelInfo);
        resultsPanel.add(labelInfo);

        fidelityPointNbLabelInfo = new JLabel("Points de fidélité :");
        fidelityPointNbLabelInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        fidelityPointNbInfo = new JLabel(String.valueOf(productModel.getFidelityPointNb()));
        resultsPanel.add(fidelityPointNbLabelInfo);
        resultsPanel.add(fidelityPointNbInfo);

        requiredAgeLabelInfo = new JLabel("Age requis :");
        requiredAgeLabelInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        requiredAgeInfo = new JLabel(productModel.getRequiredAge() != null ? String.valueOf(productModel.getRequiredAge()) : "/");
        resultsPanel.add(requiredAgeLabelInfo);
        resultsPanel.add(requiredAgeInfo);

        keptWarmLabelInfo = new JLabel("Conservé au chaud :");
        keptWarmLabelInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        keptWarmInfo = new JLabel(productModel.isKeptWarm() ? "Oui" : "Non");
        resultsPanel.add(keptWarmLabelInfo);
        resultsPanel.add(keptWarmInfo);

        keptColdLabelInfo = new JLabel("Conservé au froid :");
        keptColdLabelInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        keptColdInfo = new JLabel(productModel.isKeptCold() ? "Oui" : "Non");
        resultsPanel.add(keptColdLabelInfo);
        resultsPanel.add(keptColdInfo);

        expirationDateLabelInfo = new JLabel("Date d'expiration :");
        expirationDateLabelInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        expirationDateInfo = new JLabel(productModel.getExpirationDate().toString());
        resultsPanel.add(expirationDateLabelInfo);
        resultsPanel.add(expirationDateInfo);

        weightLabelInfo = new JLabel("Poids :");
        weightLabelInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        weightInfo = new JLabel(String.valueOf(productModel.getWeight()) + "Kg");
        resultsPanel.add(weightLabelInfo);
        resultsPanel.add(weightInfo);

        storageTemperatureLabelInfo = new JLabel("Température de stockage:");
        storageTemperatureLabelInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        storageTemperatureInfo = new JLabel(productModel.getStorageTemperature() != null ? String.valueOf(productModel.getStorageTemperature()) : "/");
        resultsPanel.add(storageTemperatureLabelInfo);
        resultsPanel.add(storageTemperatureInfo);

        provenanceLabelInfo = new JLabel("Provenance :");
        provenanceLabelInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        provenanceInfo = new JLabel(productModel.getProvenance().toString());
        resultsPanel.add(provenanceLabelInfo);
        resultsPanel.add(provenanceInfo);

        ecoScoreLabelInfo = new JLabel("Eco-score :");
        ecoScoreLabelInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        ecoScoreInfo = new JLabel(productModel.getEcoScore() != null ? String.valueOf(productModel.getEcoScore()) : "/");
        resultsPanel.add(ecoScoreLabelInfo);
        resultsPanel.add(ecoScoreInfo);

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    public void refreshResultsPanel(ProductModel productModel) {
        barcodeInfo.setText(String.valueOf(productModel.getBarcode()));
        labelInfo.setText(productModel.getLabel());
        fidelityPointNbInfo.setText(String.valueOf(productModel.getFidelityPointNb()));
        requiredAgeInfo.setText(productModel.getRequiredAge() != null ? String.valueOf(productModel.getRequiredAge()) : "/");
        keptWarmInfo.setText(productModel.isKeptWarm() ? "Oui" : "Non");
        keptColdInfo.setText(productModel.isKeptCold() ? "Oui" : "Non");
        expirationDateInfo.setText(productModel.getExpirationDate().toString());
        weightInfo.setText(String.valueOf(productModel.getWeight()) + "Kg");
        storageTemperatureInfo.setText(productModel.getStorageTemperature() != null ? String.valueOf(productModel.getStorageTemperature()) : "/");
        provenanceInfo.setText(productModel.getProvenance().toString());
        ecoScoreInfo.setText(productModel.getEcoScore() != null ? String.valueOf(productModel.getEcoScore()) : "/");

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

}
