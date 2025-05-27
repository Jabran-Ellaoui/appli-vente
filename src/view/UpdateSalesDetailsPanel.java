package view;

import controller.CustomerController;
import controller.EmployeeController;
import controller.ProductController;
import controller.SalesDetailsController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class UpdateSalesDetailsPanel extends JPanel {
    private final SalesDetailsController salesDetailsController;
    private final EmployeeController employeeController;
    private final CustomerController customerController;
    private final ProductController productController;
    private JTextField id, quantity, comment, paymentMethod, idSearchField;
    private JCheckBox fidelityPointUsed;
    private JComboBox<Employee> seller;
    private JComboBox<Customer> buyer;
    private JSpinner date;
    private JButton submit, reset, clear, backButton, copyProduct;
    private JList<Product> products, chosenProducts;
    private JPanel mainPanel, formPanel, productsPanel, buttonsPanel, searchPanel;
    private JLabel idLabel, quantityLabel, paymentMethodLabel, commentLabel, buyerLabel, sellerLabel, dateLabel, idSearchLabel, info;

    public UpdateSalesDetailsPanel(SalesDetailsController salesDetailsController, EmployeeController employeeController, CustomerController customerController, ProductController productController, MainWindow mainWindow) {
        this.salesDetailsController = salesDetailsController;
        this.employeeController = employeeController;
        this.customerController = customerController;
        this.productController = productController;
        this.setLayout(new BorderLayout());

        // search Panel
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 2));

        idSearchLabel = new JLabel("ID : ");
        idSearchField = new JTextField(9);
        IdTextListener idTextListener = new IdTextListener();
        idSearchField.addActionListener(idTextListener);
        info = new JLabel("Appuyez sur enter après la saisie");

        searchPanel.add(idSearchLabel);
        searchPanel.add(idSearchField);
        searchPanel.add(info);
        this.add(searchPanel, BorderLayout.NORTH);

        mainPanel = new JPanel(new BorderLayout());
        formPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        productsPanel = new JPanel(new GridLayout(1, 3));

        mainPanel.add(formPanel);
        mainPanel.add(productsPanel);
        this.add(mainPanel);

        // Buttons
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Retour");
        backButton.addActionListener(e -> mainWindow.homePage());
        buttonsPanel.add(backButton);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }
    private void createResultsPanel(SalesDetails salesDetails) {
        mainPanel.removeAll();

        id = new JTextField(String.valueOf(salesDetails.getId()));
        id.setEditable(false);
        idLabel = new JLabel("ID :");
        idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(idLabel);
        formPanel.add(id);

        quantity = new JTextField(String.valueOf(salesDetails.getQuantity()));
        quantityLabel = new JLabel("Quantité :");
        quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(quantityLabel);
        formPanel.add(quantity);

        buyer = new JComboBox<>();
        for (Customer c : customerController.getAllCustomers()) buyer.addItem(c);
        buyer.setSelectedItem(salesDetails.getBuyer());
        buyerLabel = new JLabel("Client :");
        buyerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(buyerLabel);
        formPanel.add(buyer);

        seller = new JComboBox<>();
        for (Employee e : employeeController.getAllEmployee()) seller.addItem(e);
        seller.setSelectedItem(salesDetails.getSeller());
        sellerLabel = new JLabel("Employé :");
        sellerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(sellerLabel);
        formPanel.add(seller);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        date = new JSpinner(dateModel);
        date.setEditor(new JSpinner.DateEditor(date, "yyyy-MM-dd"));
        date.setValue(java.sql.Date.valueOf(salesDetails.getDate()));
        dateLabel = new JLabel("Date :");
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(dateLabel);
        formPanel.add(date);

        paymentMethod = new JTextField(salesDetails.getPaymentMethod() != null ? salesDetails.getPaymentMethod() : "");
        paymentMethodLabel = new JLabel("Méthode de paiement :");
        paymentMethodLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(paymentMethodLabel);
        formPanel.add(paymentMethod);

        comment = new JTextField(salesDetails.getComment() != null ? salesDetails.getComment() : "");
        commentLabel = new JLabel("Commentaire :");
        commentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(commentLabel);
        formPanel.add(comment);

        fidelityPointUsed = new JCheckBox("A utilisé des points de fidélité ?", salesDetails.isFidelityPointsUsed());
        formPanel.add(fidelityPointUsed);

        // Product lists setup
        productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(1, 3));
        ArrayList<Product> productsList = new ArrayList<>();
        productsList = productController.getAllProducts();
        products = new JList<>(productsList.toArray(new Product[0]));
        products.setVisibleRowCount(5);
        products.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        chosenProducts = new JList<>();
        chosenProducts.setVisibleRowCount(5);
        chosenProducts.setFixedCellWidth(60);
        chosenProducts.setFixedCellHeight(15);
        chosenProducts.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        copyProduct = new JButton("COPIER>>>");
        CopyButtonListener copyButtonListenerlistener = new CopyButtonListener();
        copyProduct.addActionListener(copyButtonListenerlistener);
        productsPanel.add(new JScrollPane(products));
        productsPanel.add(copyProduct);
        productsPanel.add(new JScrollPane(chosenProducts));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(productsPanel, BorderLayout.SOUTH);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private boolean validateForm() {
        String errors = "";

        try {
            Integer.parseInt(quantity.getText());
        } catch (NumberFormatException e) {
            errors += "- La quantité doit être un nombre.\n";
        }

        if (buyer.getSelectedItem() == null) {
            errors += "- Le client est requis.\n";
        }

        if (seller.getSelectedItem() == null) {
            errors += "- L'employé est requis.\n";
        }

        if (!errors.isEmpty()) {
            JOptionPane.showMessageDialog(this, errors, "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
    private class CopyButtonListener implements ActionListener {
        public void actionPerformed( ActionEvent event) {
            ListModel<Product> currentModel = chosenProducts.getModel();
            DefaultListModel<Product> newModel;

            if (currentModel instanceof DefaultListModel) {
                newModel = (DefaultListModel<Product>) currentModel;
            } else {
                newModel = new DefaultListModel<>();
            }
            for (Product p : products.getSelectedValuesList()) {
                if (!newModel.contains(p)) {
                    newModel.addElement(p);
                }
            }
            chosenProducts.setModel(newModel);
            productsPanel.revalidate();
            productsPanel.repaint();
        }
    }
    private SalesDetails searchSalesDetails(int id) {
        try {
            return salesDetailsController.getSalesDetails(id);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche : " + exception.getMessage());
            return null;
        }
    }
    private class IdTextListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                int id = Integer.parseInt(idSearchField.getText());
                SalesDetails salesDetails = searchSalesDetails(id);
                createResultsPanel(salesDetails);
                addButtonAfterSearch();
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "L'id' doit être un entier.");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la recherche : " + exception.getMessage());
            }
        }
    }
    private void addButtonAfterSearch() {
        submit = new JButton("Mettre à jour");
        submit.addActionListener(e -> {
            if (!validateForm()) {
                return;
            }
            try {
                LocalDate saleDate = ((Date) date.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                SalesDetails salesDetails = new SalesDetails(Integer.parseInt(id.getText()), Integer.parseInt(quantity.getText()), fidelityPointUsed.isSelected(), paymentMethod.getText().isBlank() ? null : paymentMethod.getText(), comment.getText().isBlank() ? null : comment.getText(), saleDate, (Customer) buyer.getSelectedItem(), (Employee) seller.getSelectedItem());
                salesDetailsController.updateSalesDetails(salesDetails);
                JOptionPane.showMessageDialog(null, salesDetails,"Fiche Produit", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur de mise à jour : " + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonsPanel.add(submit);

        clear = new JButton("Supprimer tout");
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                id.setText("");
                quantity.setText("");
                fidelityPointUsed.setSelected(false);
                comment.setText("");
                paymentMethod.setText("");
                if (buyer.getItemCount() > 0) {
                    buyer.setSelectedIndex(0);
                }
                if (seller.getItemCount() > 0) {
                    seller.setSelectedIndex(0);
                }
                date.setValue(new Date());
            }
        });
        buttonsPanel.add(clear);


    }

}