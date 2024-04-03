package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import controller.GameController;
import model.GameModel;

/**
 * La classe BoardView rappresenta la vista del tabellone di gioco.
 */
public class BoardView extends JPanel {

    private static final long serialVersionUID = 1L;
    private CardView cardView;
    private DeckView deckView;
    private DeckView discardPileView;
    private GameModel game;

    /**
     * Crea una nuova istanza di BoardView associata a un controller di gioco.
     *
     * @param controller Il controller di gioco associato a questa vista.
     */
    public BoardView(GameController controller) {
        this.game = controller.getGameModel();
        setLayout(new FlowLayout());
        setBackground(new Color(150, 70, 0));

        resetBoard();

        deckView.setPreferredSize(new Dimension(60, 90));
        discardPileView.setPreferredSize(new Dimension(60, 90));
        cardView.setPreferredSize(new Dimension(60, 90));

        deckView.setOpaque(true);
        discardPileView.setOpaque(true);
        cardView.setOpaque(true);

        discardPileView.turnUp();

        add(deckView, FlowLayout.LEFT);
        add(cardView, FlowLayout.CENTER);
        add(discardPileView, FlowLayout.RIGHT);

        deckView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (game.getCurrentPlayer().equals(game.getHumanUser())) {
                    game.getController().handleDeckClick(game.getHumanUser());
                    cardView.repaint();
                }
            }
        });

        discardPileView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (game.getCurrentPlayer().equals(game.getHumanUser())) {
                    game.getController().handleDiscardPileClick(game.getHumanUser());
                }
            }
        });
    }

    /**
     * Aggiorna la vista del tabellone di gioco.
     */
    public void updateView() {
        cardView.setCardModel(game.getCurrentPlayableCard());
        deckView.updateView();
        cardView.updateView();
        discardPileView.updateView();
        if (cardView.getCardModel() != null) cardView.turnUp();
    }

    /**
     * Reimposta il tabellone di gioco.
     */
    public void resetBoard() {
        this.removeAll();

        deckView = new DeckView(game.getDeck());
        discardPileView = new DeckView(game.getDiscardPile());
        cardView = new CardView(null);

        add(deckView, FlowLayout.LEFT);
        add(cardView, FlowLayout.CENTER);
        add(discardPileView, FlowLayout.RIGHT);

        updateView();
    }
}
