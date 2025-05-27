package main.view;

import main.controller.ProductModelController;
import main.exception.ProductModelException;

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
        formPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        barcodeLabel = new JLabel("Code-barres :");
        barcode = new JTextField(9);
        barcode.setPreferredSize(new Dimension(150, 25));
        formPanel.add(barcodeLabel);
        formPanel.add(barcode);


        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        backToWelcomePanel = new JButton("Retour");
        backToWelcomePanel.addActionListener(e -> {
            mainWindow.homePage();
        });
        buttonsPanel.add(backToWelcomePanel);

        submit = new JButton("Supprimer un produit");
        submit.addActionListener(e -> {
            if (!validateDeleteProductModelForm()) return;

            try {
                productModelController.deleteProductModel(
                        Integer.parseInt(barcode.getText()));
                // si on arrive ici, suppression OK
                int response = JOptionPane.showOptionDialog(
                        this,
                        "Produit supprimé.\nVoulez-vous en supprimer un autre ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Oui", "Non"},
                        "Oui"
                );
                if (response == JOptionPane.YES_OPTION) {
                    barcode.setText("");
                } else {
                    mainWindow.homePage();
                }

            } catch (ProductModelException exception) {
                // suppression impossible
                JOptionPane.showMessageDialog(
                        this,
                        exception.getMessage(),
                        "Erreur suppression",
                        JOptionPane.ERROR_MESSAGE
                );
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

        if (!errorMessages.isEmpty()) {
            JOptionPane.showMessageDialog(this, errorMessages, "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
