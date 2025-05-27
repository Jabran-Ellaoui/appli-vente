package main.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationPanel extends JPanel
{
    private final MainWindow mainWindow;
    private final BufferedImage[] sprites = new BufferedImage[3];
    private final int[] xcoords = new int[3];
    private final int[] delay = new int[3];

    private static final int BELT_HEIGHT = 200;

    // déclarés ici mais initialisés dans le constructeur avant usage
    private final AnimationRunnable animator;
    private final Thread animationThread;

    public AnimationPanel(MainWindow mainWindow)
    {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // permet juste de charger les sprites et de setup la position initiale
        for (int i = 0; i < sprites.length; i++)
        {
            try
            {
                sprites[i] = ImageIO.read(getClass().getResourceAsStream("assets/sprite" + i + ".png"));
            }
            catch (Exception exception)
            {
                sprites[i] = null;
            }
            xcoords[i]     = getPreferredSize().width + i * 200;
            delay[i] = 0;
        }

        animator = new AnimationRunnable(this, sprites, xcoords, delay);
        animationThread = new Thread(animator, "AnimationThread");
        animationThread.start();


        JButton back = new JButton("Retour");
        back.addActionListener(e -> {animator.stop();mainWindow.homePage();});
        add(back, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics draw)
    {
        super.paintComponent(draw);
        int w = getWidth(), h = getHeight();

        // plateau
        draw.setColor(Color.LIGHT_GRAY);
        draw.fillRect(0, h - BELT_HEIGHT, w, BELT_HEIGHT);

        // Dessin des sprites
        for (int i = 0; i < sprites.length; i++)
        {
            if (sprites[i] != null && delay[i] == 0)
            {
                int y = h - BELT_HEIGHT + 20 - sprites[i].getHeight() / 10;
                draw.drawImage(sprites[i], xcoords[i], y, this);
            }
        }
    }
}
