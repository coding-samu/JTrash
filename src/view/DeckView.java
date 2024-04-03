package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

import model.CardModel;
import model.DeckModel;

/**
 * La classe DeckView rappresenta la vista di un mazzo di carte.
 */
public class DeckView extends JPanel {
    private static final long serialVersionUID = 1L;
    private DeckModel deck;
    private CardModel card;
    private boolean isFaceUp;

    /**
     * Crea una nuova istanza di DeckView per un dato mazzo di carte.
     *
     * @param deck Il mazzo di carte da visualizzare.
     */
    public DeckView(DeckModel deck) {
        this.deck = deck;
        setPreferredSize(new Dimension(60, 90)); // Imposta le dimensioni del componente
        setBackground(new Color(153, 76, 0));
        isFaceUp = false;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        card = deck.getTopCard();
        if (card != null) {
            // Disegna l'immagine della carta
            if (isFaceUp) {
                Image cardImage = Toolkit.getDefaultToolkit().getImage(card.getImagePath());
                g.drawImage(cardImage, 0, 0, 60, 90, this);
            } else {
                Image cardImage = Toolkit.getDefaultToolkit().getImage("cardImages/background.png");
                g.drawImage(cardImage, 0, 0, 60, 90, this);
            }
        }
    }

    /**
     * Gira la carta sopra.
     */
    public void turnUp() {
        isFaceUp = true;
        repaint();
    }

    /**
     * Gira la carta sotto.
     */
    public void turnDown() {
        isFaceUp = false;
        repaint();
    }

    /**
     * Aggiorna la vista del mazzo di carte.
     */
    public void updateView() {
        repaint();
    }
}
