package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

import model.CardModel;

/**
 * La classe CardView rappresenta la vista di una carta.
 */
public class CardView extends JPanel {
    private static final long serialVersionUID = 1L;
    private CardModel card;
    private boolean isTurnedUp;

    /**
     * Crea una nuova istanza di CardView per una data carta.
     *
     * @param card La carta da visualizzare.
     */
    public CardView(CardModel card) {
        this.card = card;
        setBackground(new Color(153, 76, 0));
        setPreferredSize(new Dimension(60, 90)); // Imposta le dimensioni del componente
        if (card != null) isTurnedUp = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (card != null) {
            // Disegna l'immagine della carta
            if (isTurnedUp) {
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
        isTurnedUp = true;
        repaint();
    }

    /**
     * Gira la carta sotto.
     */
    public void turnDown() {
        isTurnedUp = false;
        repaint();
    }

    /**
     * Aggiorna la vista della carta.
     */
    public void updateView() {
        repaint();
    }

    /**
     * Verifica se la carta è girata sopra.
     *
     * @return True se la carta è girata sopra, altrimenti False.
     */
    public boolean isTurnedUp() {
        return isTurnedUp;
    }

    /**
     * Restituisce il modello della carta associato a questa vista.
     *
     * @return Il modello della carta.
     */
    public CardModel getCardModel() {
        return card;
    }

    /**
     * Imposta il modello della carta associato a questa vista.
     *
     * @param card Il nuovo modello della carta.
     */
    public void setCardModel(CardModel card) {
        this.card = card;
        repaint();
    }
}