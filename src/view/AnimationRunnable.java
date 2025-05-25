package view;

import java.awt.image.BufferedImage;

public class AnimationRunnable implements Runnable {
    private final AnimationPanel panel;
    private final BufferedImage[] sprites;
    private final int[] xcoords;
    private final int[] delay;

    private static final int SPEED = 5;
    private static final int DELAY = 50; // durée de la pause entre chaque boucle du run
    private static final int MAX_DELAY = 40;

    private boolean running = true;

    public AnimationRunnable(AnimationPanel panel, BufferedImage[] sprites, int[] xcoords, int[] delay)
    {
        this.panel   = panel;
        this.sprites = sprites;
        this.xcoords = xcoords;
        this.delay   = delay; // le tableau stock le nb de cycles à attendre avant de redessiner chaque sprite, pas confondre avec DELAY la constante
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run()
    {
        while (running)
        {
            int w = panel.getWidth();
            for (int i = 0; i < sprites.length; i++)
            {
                if (sprites[i] == null) continue;

                if (delay[i] > 0)
                {
                    delay[i]--;
                } else
                {
                    xcoords[i] -= SPEED;
                    if (xcoords[i] + sprites[i].getWidth() < 0)
                    {
                        delay[i] = (int)(Math.random() * MAX_DELAY);
                        xcoords[i]     = w;
                    }
                }
            }
            panel.repaint();

            try
            {
                Thread.sleep(DELAY);
            }
            catch (InterruptedException exception)
            {
                running = false;
            }
        }
    }
}
