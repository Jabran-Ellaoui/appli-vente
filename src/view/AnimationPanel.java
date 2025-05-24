package view;

import javax.swing.*;

public class AnimationPanel extends JPanel {
    MainWindow mainWindow;
    JButton backToWelcomePanel;

    public AnimationPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;


        backToWelcomePanel = new JButton("Retour");
        backToWelcomePanel.addActionListener(e -> {
            mainWindow.homePage();
        });
    }
}
