package view;

import model.SalesDetails;
import javax.swing.*;

public class CreateSalesDetailsPanel extends JPanel {
    JLabel idLabel, quantityLabel, fidelityPointsUsedLabel, paymentMethodLabel, commentLabel, dateLabel, sellerLabel, customerLabel, customer;
    JTextField id, comment;
    JCheckBox fidelityPointUsed;
    JComboBox dateDay, dateMonth, dateYear, paymentMethod, employee;
    JButton reset, backToWelcomePanel, submit;
    public CreateSalesDetailsPanel() {

    }
}
