package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationPanel extends JPanel implements Runnable
{
    private final MainWindow mainWindow;

    private final BufferedImage[] sprites = new BufferedImage[3];
    private final int[] x = new int[3];
    private final int[] delay = new int[3];  // délai avant réapparition

    private static final int BELT_HEIGHT = 200;
    private static final int SPEED = 5;
    private static final int DELAY = 50;
    private static final int MAX_DELAY = 40;

    private boolean running = true;

    public AnimationPanel(MainWindow mainWindow)
    {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        for (int i = 0; i < sprites.length; i++)
        {
            try {
                sprites[i] = ImageIO.read(getClass().getResourceAsStream("assets/sprite" + i + ".png"));
            }
            catch (Exception exception)
            {
                sprites[i] = null;
            }
            x[i] = getPreferredSize().width + i * 200;
            delay[i] = 0;
        }

        JButton back = new JButton("Retour");
        back.addActionListener(e -> {running = false;mainWindow.homePage();});
        add(back, BorderLayout.SOUTH);

        new Thread(this).start();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int w = getWidth(), h = getHeight();

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, h - BELT_HEIGHT, w, BELT_HEIGHT);

        for (int i = 0; i < sprites.length; i++) {
            if (sprites[i] != null && delay[i] == 0) {
                int y = h - BELT_HEIGHT + 20 - sprites[i].getHeight() / 10;
                g.drawImage(sprites[i], x[i], y, this);
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            int w = getWidth();
            for (int i = 0; i < sprites.length; i++) {
                if (sprites[i] == null) continue;

                if (delay[i] > 0) {
                    delay[i]--;
                } else {
                    x[i] -= SPEED;
                    if (x[i] + sprites[i].getWidth() < 0) {
                        delay[i] = (int) (Math.random() * MAX_DELAY);
                        x[i] = w;
                    }
                }
            }
            repaint();

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }
}
