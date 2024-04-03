package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import controller.GameController;
import model.CardModel;
import model.UserModel;

/**
 * La classe PlayerView rappresenta la vista del giocatore, che mostra le carte nella mano del giocatore.
 */
public class PlayerView extends JPanel {

    private static final long serialVersionUID = 1L;
    private UserModel player;
    private JPanel playerPanel;
    private JPanel cardPanel;

    /**
     * Crea una nuova istanza di PlayerView.
     *
     * @param player     Il giocatore associato a questa vista.
     * @param controller Il controller del gioco.
     */
    public PlayerView(UserModel player, GameController controller) {
        this.player = player;
        player.setPlayerView(this);
        playerPanel = new JPanel(new BorderLayout());

        // Inizializza il pannello delle carte
        cardPanel = new JPanel(new GridLayout(2, 5));
        putCardsOnPanel();

        setBackground(new Color(128, 128, 0));

        // Aggiungi il pannello delle carte al pannello del giocatore
        playerPanel.add(cardPanel, BorderLayout.CENTER);

        PlayerInfoView avatarNameView = new PlayerInfoView(player);

        playerPanel.add(avatarNameView, BorderLayout.WEST);
        add(playerPanel);
        
        cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller.getGameModel().getCurrentPlayer().equals(controller.getGameModel().getHumanUser()) && player.equals(controller.getGameModel().getHumanUser())) {
                    // Otteniamo le coordinate del punto in cui è stato effettuato il clic
                    int x = e.getX();
                    int y = e.getY();

                    // Iteriamo attraverso i componenti all'interno del pannello delle carte
                    Component[] components = cardPanel.getComponents();
                    for (Component component : components) {
                        if (component instanceof CardView) {
                            CardView cardView = (CardView) component;
                            // Otteniamo il rettangolo di bounding del componente (carta)
                            Rectangle bounds = cardView.getBounds();
                            if (bounds.contains(x, y)) {
                                // Il clic è avvenuto sulla carta cardView
                                // Esegui le azioni desiderate
                                controller.handleCardOnHandClick(cardView, controller.getGameModel().getHumanUser());
                                break; // Esci dal ciclo una volta trovata la carta cliccata
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * Aggiorna la vista del giocatore.
     */
    public void updateView() {
        for (Component component : cardPanel.getComponents()) {
            if (component instanceof CardView) {
                CardView cardView = (CardView) component;
                cardView.repaint();
            }
        }
    }

    /**
     * Ottieni un array di oggetti CardView che rappresentano le carte nella mano del giocatore.
     *
     * @return Un array di oggetti CardView.
     */
    public CardView[] getCardViewList() {
        Component[] components = cardPanel.getComponents();
        ArrayList<CardView> cardViews = new ArrayList<>();

        for (Component component : components) {
            if (component instanceof CardView) {
                CardView cardView = (CardView) component;
                cardViews.add(cardView);
            }
        }

        return cardViews.toArray(new CardView[0]);
    }

    /**
     * Ottieni un array di oggetti CardView che rappresentano le carte non girate nella mano del giocatore.
     *
     * @return Un array di oggetti CardView.
     */
    public CardView[] getCardViewListOfNotTurnedUpCards() {
        Component[] components = cardPanel.getComponents();
        ArrayList<CardView> cardViews = new ArrayList<>();

        for (Component component : components) {
            if (component instanceof CardView) {
                CardView cardView = (CardView) component;
                if (!cardView.isTurnedUp()) cardViews.add(cardView);
            }
        }

        return cardViews.toArray(new CardView[0]);
    }

    /**
     * Posiziona le carte della mano del giocatore sul pannello delle carte.
     */
    public void putCardsOnPanel() {
        // Rimuovi tutti i componenti dal pannello delle carte
        cardPanel.removeAll();

        // Aggiungi le carte della mano del giocatore al pannello delle carte
        for (CardModel card : this.player.getHand()) {
            CardView cardView = new CardView(card);
            cardPanel.add(cardView);
        }

        if (player.getCardsOnHand() < 10) {
            for (int i = player.getCardsOnHand(); i < 10; i++) {
                CardView cardView = new CardView(null);
                cardPanel.add(cardView);
            }
        }

        // Aggiorna il pannello delle carte
        cardPanel.revalidate();
        cardPanel.repaint();
    }
}
