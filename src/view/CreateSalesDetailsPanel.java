package view;

import model.SalesDetails;
import javax.swing.*;
import java.awt.*;

public class CreateSalesDetailsPanel extends JPanel {
    private JPanel FormPanel;
    private JPanel ButtonsPanel;
    private JLabel idLabel, quantityLabel, fidelityPointsUsedLabel, paymentMethodLabel, commentLabel, dateLabel, sellerLabel, customerLabel;
    private JTextField id, comment;
    private JCheckBox fidelityPointUsed;
    private JComboBox dateDay, dateMonth, dateYear, paymentMethod, employee;
    private JButton reset, backToWelcomePanel, submit;
    public CreateSalesDetailsPanel() {
        FormPanel = new JPanel();
        ButtonsPanel = new JPanel();
        FormPanel.setLayout(new GridLayout(8,2,5,5));

    }
}
