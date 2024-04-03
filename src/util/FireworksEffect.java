package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * La classe FireworksEffect rappresenta un pannello che visualizza effetti
 * speciali stile fuochi d'artificio su uno sfondo nero.
 */
public class FireworksEffect extends JPanel {
    private static final long serialVersionUID = 1L;
    private int[] xPoints;
    private int[] yPoints;
    private int numPoints;
    private Timer timer;
    private static final int NUM_FIREWORKS = 100; // Numero di fuochi d'artificio da mostrare
    private static final int FIREWORK_RADIUS = 10;

    /**
     * Crea una nuova istanza di FireworksEffect.
     */
    public FireworksEffect() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        // Imposta un timer per aggiornare gli effetti speciali
        timer = new Timer(100, e -> {
            // Aggiorna la posizione dei fuochi d'artificio
            updateFireworks();
            repaint();
        });

        timer.start();
    }

    /**
     * Aggiorna la posizione dei fuochi d'artificio.
     */
    private void updateFireworks() {
        numPoints = NUM_FIREWORKS;
        xPoints = new int[numPoints];
        yPoints = new int[numPoints];

        Random random = new Random();

        // Genera le posizioni casuali dei fuochi d'artificio all'interno del pannello
        for (int i = 0; i < numPoints; i++) {
            xPoints[i] = random.nextInt(800);
            yPoints[i] = random.nextInt(600);
        }
    }

    /**
     * Override del metodo paintComponent per disegnare gli effetti speciali.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.YELLOW);

        // Disegna i fuochi d'artificio come cerchi gialli
        for (int i = 0; i < numPoints; i++) {
            g2d.fillOval(xPoints[i], yPoints[i], FIREWORK_RADIUS, FIREWORK_RADIUS);
        }
    }
}
