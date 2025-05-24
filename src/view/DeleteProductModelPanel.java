package view;

import controller.ProductModelController;

import javax.swing.*;
import java.awt.*;

public class DeleteProductModelPanel extends JPanel {
    private final ProductModelController productModelController;
    private JPanel formPanel;
    private JPanel buttonsPanel;
    private JLabel barcodeLabel;
    private JTextField barcode;
    private JButton backToWelcomePanel, submit;

    public DeleteProductModelPanel(ProductModelController productModelController, MainWindow mainWindow) {
        this.productModelController = productModelController;
        formPanel = new JPanel();
        buttonsPanel = new JPanel();
        formPanel.setLayout(new GridLayout(1,2,5,5));
        barcodeLabel = new JLabel("Code-barres :");
        formPanel.add(barcodeLabel);
        barcode = new JTextField(9);
        formPanel.add(barcode);

        backToWelcomePanel = new JButton("Retour");
        backToWelcomePanel.addActionListener(e -> {
            mainWindow.homePage();
        });
        buttonsPanel.add(backToWelcomePanel);

        submit = new JButton("Supprimer un produit");
        submit.addActionListener(e -> {
            if(!validateDeleteProductModelForm()) {
                return;
            }
            try {
                productModelController.deleteProductModel(Integer.parseInt(barcode.getText()));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(formPanel, exception.getMessage(), "Erreur dans l'écriture des informations", JOptionPane.ERROR_MESSAGE);
            }

        });
        buttonsPanel.add(submit);

        this.setLayout(new BorderLayout());
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }
    private boolean validateDeleteProductModelForm() {
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
        return true;
    }
}
