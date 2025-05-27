package main.view;

import main.controller.ProductController;
import main.controller.SalesDetailsController;

import javax.swing.*;
import java.awt.*;

public class DeleteSalesDetailsPanel extends JPanel {
    private final SalesDetailsController salesDetailsController;
    private final ProductController productController;
    private JPanel formPanel;
    private JPanel buttonsPanel;
    private JLabel idLabel;
    private JTextField id;
    private JButton backToWelcomePanel, submit;

    public DeleteSalesDetailsPanel(SalesDetailsController salesDetailsController, ProductController productController, MainWindow mainWindow) {
        this.salesDetailsController = salesDetailsController;
        this.productController = productController;
        formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        idLabel = new JLabel("ID :");
        id = new JTextField(12);
        id.setPreferredSize(new Dimension(150, 25));
        formPanel.add(idLabel);
        formPanel.add(id);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        backToWelcomePanel = new JButton("Retour");
        backToWelcomePanel.addActionListener(e -> {
            mainWindow.homePage();
        });
        buttonsPanel.add(backToWelcomePanel);

        submit = new JButton("Supprimer un produit");
        submit.addActionListener(e -> {
            if(!validateDeleteSalesDetailsForm()) {
                return;
            }
            try {
                int salesId = Integer.parseInt(id.getText());
                salesDetailsController.deleteSalesDetails(salesId);
                productController.releaseProduct(salesId);
                String[] options = {"Oui", "Non"};
                int response = JOptionPane.showOptionDialog(this, "Vente supprimée.\nVoulez-vous en supprimer une autre ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (response == JOptionPane.YES_OPTION) {
                    id.setText("");
                } else {
                    mainWindow.homePage();
                }

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(formPanel, exception.getMessage(), "Erreur dans l'écriture des informations", JOptionPane.ERROR_MESSAGE);
            }

        });
        buttonsPanel.add(submit);

        this.setLayout(new BorderLayout());
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }
    public boolean validateDeleteSalesDetailsForm() {
        String errorMessages = "";

        if (id.getText().trim().isEmpty()) {
            errorMessages += "- L'ID est requis.\n";
        } else {
            try {
                Integer.parseInt(id.getText());
            } catch (NumberFormatException e) {
                errorMessages += "- L'ID doit être un nombre entier.\n";
            }
        }

        if (!errorMessages.isEmpty()) {
            JOptionPane.showMessageDialog(this, errorMessages, "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}