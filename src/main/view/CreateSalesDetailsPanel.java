package main.view;

import main.controller.CustomerController;
import main.controller.EmployeeController;
import main.controller.ProductController;
import main.controller.SalesDetailsController;
import main.model.Customer;
import main.model.Employee;
import main.model.SalesDetails;
import main.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class CreateSalesDetailsPanel extends JPanel {
    private final SalesDetailsController salesDetailsController;
    private final EmployeeController employeeController;
    private final CustomerController customerController;
    private final ProductController productController;

    private JPanel formPanel, buttonsPanel, productsPanel, mainPanel;
    private JLabel idLabel, quantityLabel, paymentMethodLabel, commentLabel, dateLabel, sellerLabel, buyerLabel;
    private JTextField id, quantity, comment, paymentMethod;
    private JCheckBox fidelityPointUsed;
    private JComboBox seller, buyer;
    private JSpinner date;
    private JButton reset, backToWelcomePanel, submit, copyProduct;
    private JList<Product> products, chosenProducts;


    public CreateSalesDetailsPanel(SalesDetailsController salesDetailsController, EmployeeController employeeController, CustomerController customerController, ProductController productController, MainWindow mainWindow) {
        this.salesDetailsController = salesDetailsController;
        this.employeeController = employeeController;
        this.customerController = customerController;
        this.productController = productController;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        // form panel
        formPanel = new JPanel();
        buttonsPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8,2,5,5));

        idLabel = new JLabel("ID :");
        idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(idLabel);
        id = new JTextField(10);
        formPanel.add(id);

        quantityLabel = new JLabel("Quantité :");
        quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(quantityLabel);
        quantity = new JTextField(3);
        formPanel.add(quantity);

        buyerLabel = new JLabel("Client :");
        buyerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(buyerLabel);
        buyer = new JComboBox<>();
        ArrayList<Customer> customers = customerController.getAllCustomers();
        for (Customer customer : customers) {
            buyer.addItem(customer);
        }
        formPanel.add(buyer);

        sellerLabel = new JLabel("Employé :");
        sellerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(sellerLabel);
        seller = new JComboBox<>();
        ArrayList<Employee> employees = employeeController.getAllEmployee();
        for (Employee employee : employees) {
            seller.addItem(employee);
        }
        formPanel.add(seller);

        dateLabel = new JLabel("Date :");
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(dateLabel);
        SpinnerDateModel dateModel = new SpinnerDateModel();
        date = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(date, "yyyy-MM-dd");
        date.setEditor(editor);
        formPanel.add(date);

        paymentMethodLabel = new JLabel("Méthode de paiement : ");
        paymentMethodLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(paymentMethodLabel);
        paymentMethod = new JTextField(10);
        formPanel.add(paymentMethod);

        commentLabel = new JLabel("Commentaire : ");
        commentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(commentLabel);
        comment = new JTextField(40);
        formPanel.add(comment);

        fidelityPointUsed = new JCheckBox("A Utilisé des points de fidélité ?");
        fidelityPointUsed.setHorizontalAlignment(SwingConstants.RIGHT);
        formPanel.add(fidelityPointUsed);
        mainPanel.add(formPanel, BorderLayout.NORTH);

        // product panel
        productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(1, 3));
        ArrayList<Product> productsList = new ArrayList<>();
        productsList = productController.getAllUnsoldProduct();
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

        // button panel
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
                if (!validateCreateSalesDetailsForm()) {
                    return;
                }
                try {
                    SalesDetails salesDetails;
                    Date selected = (Date) date.getValue();
                    LocalDate dateFormat = selected.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    salesDetails = new SalesDetails(Integer.parseInt(id.getText()), Integer.parseInt(quantity.getText()), fidelityPointUsed.isSelected(), (!paymentMethod.getText().equals("") ? paymentMethod.getText() : null), (!comment.getText().equals("") ? comment.getText() : null), dateFormat, (Customer) buyer.getSelectedItem(), (Employee) seller.getSelectedItem());
                    ListModel<Product> model = chosenProducts.getModel();
                    int modelListSize = model.getSize();
                    for (int i = 0; i < model.getSize(); i++) {
                        Product product = model.getElementAt(i);
                        productController.updateProduct(product.getId(), salesDetails.getId());
                    }
                    JOptionPane.showMessageDialog(null, salesDetails,"Fiche Produit", JOptionPane.INFORMATION_MESSAGE);
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
        buttonsPanel.add(reset);

        this.setLayout(new BorderLayout());
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(productsPanel, BorderLayout.SOUTH);
        this.add(mainPanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }
    public boolean validateCreateSalesDetailsForm() {
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

        if (quantity.getText().trim().isEmpty()) {
            errorMessages += "- La quantité est requise.\n";
        } else {
            try {
                Integer.parseInt(quantity.getText());
            } catch (NumberFormatException e) {
                errorMessages += "- La quantité doit être un nombre entier.\n";
            }
        }

        if (buyer.getSelectedItem() == null) {
            errorMessages += "- Un client doit être sélectionné.\n";
        }

        if (seller.getSelectedItem() == null) {
            errorMessages += "- Un employé doit être sélectionné.\n";
        }

        if (!errorMessages.isEmpty()) {
            JOptionPane.showMessageDialog(this, errorMessages, "Erreur de validation", JOptionPane.ERROR_MESSAGE);
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
                    newModel.addElement(p); // évite les doublons
                }
            }
            chosenProducts.setModel(newModel);
            productsPanel.revalidate();
            productsPanel.repaint();
            }
    }
}
