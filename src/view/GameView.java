package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.GameController;
import model.UserModel;
import util.FireworksEffect;
import util.Observer;

/**
 * La classe GameView rappresenta la vista principale del gioco.
 */
public class GameView extends JPanel implements Observer {
    private static final long serialVersionUID = 1L;
    private GameController controller;
    private BoardView boardView;
    private JPanel playersView;
    private JLabel currentPlayerLabel;
    private int numberOfPlayers;

    /**
     * Crea una nuova istanza di GameView.
     *
     * @param numberOfPlayers Il numero di giocatori nella partita.
     * @param user           L'utente attuale.
     * @param controller     Il controller del gioco.
     */
    public GameView(int numberOfPlayers, UserModel user, GameController controller) {
        this.controller = controller;
        this.numberOfPlayers = numberOfPlayers;
        controller.setGameView(this);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        resetView();

        updateLabels();
        
        controller.getGameModel().addObserver(this);
    }

    /**
     * Imposta il controller del gioco.
     *
     * @param controller Il controller del gioco.
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }

    /**
     * Aggiorna la vista dei giocatori.
     */
    public void updatePlayersView() {
        for (UserModel player : controller.getGameModel().getPlayersList()) {
            if (player.getPlayerView() != null) player.getPlayerView().updateView();
        }

        updateLabels();
    }

    /**
     * Ottieni la vista del tabellone di gioco.
     *
     * @return La vista del tabellone di gioco.
     */
    public BoardView getBoardView() {
        return boardView;
    }

    /**
     * Aggiorna le etichette della vista.
     */
    public void updateLabels() {
        setLabels();
        repaintLabels();
    }

    /**
     * Aggiorna la vista del tabellone di gioco.
     */
    public void updateBoardView() {
        boardView.updateView();
    }
    
    /**
     * Ristampa le JLabel.
     */
    private void repaintLabels() {
        currentPlayerLabel.repaint();
    }
    
    /**
     * Imposta le JLabel.
     */
    private void setLabels() {
        if (null != controller.getGameModel().getCurrentPlayer())
            currentPlayerLabel.setText("È il turno di: " + controller.getGameModel().getCurrentPlayer().getNickname());
        repaintLabels();
    }
    
    /**
     * Imposta le JLabel.
     */
    private void setLabels(String testo) {
    	currentPlayerLabel.setText(testo);
    	repaintLabels();
    }

    /**
     * Mostra un messaggio di vittoria quando un giocatore vince la partita.
     *
     * @param player Il giocatore che ha vinto la partita.
     */
    public void showGameWonMessage(UserModel player) {
        setLabels("Il giocatore " + player.getNickname() + " ha vinto la partita!");
        showFireworks();        
    }
    
    /**
     * Mostra un messaggio di vittoria quando un giocatore vince la mano.
     * @param player Il giocatore che ha vinto la mano.
     */
    public void showHandWonMessage(UserModel player) {
    	setLabels("Il giocatore " + player.getNickname() + " ha vinto la mano!");
    }
    
    /**
     * Metodo per mostrare i fuochi d'artificio a schermo.
     */
    private void showFireworks() {
        // Aggiungi gli effetti speciali dei fuochi d'artificio
        FireworksEffect fireworks = new FireworksEffect();
        add(fireworks, BorderLayout.CENTER);
        
        revalidate();
        repaint();
        
        new Timer(3000, e -> {
            remove(fireworks); // Rimuovi gli effetti speciali dei fuochi d'artificio dopo qualche secondo
        }).start();
	}

	/**
     * Reimposta la vista del gioco.
     */
    public void resetView() {
        this.removeAll();
        boardView = new BoardView(controller);
        this.playersView = new JPanel(new GridLayout(2, (int) (numberOfPlayers / 2)));
        for (UserModel player : controller.getGameModel().getPlayersList()) {
            PlayerView playerView = new PlayerView(player, controller);
            playersView.add(playerView);
        }
        currentPlayerLabel = new JLabel();
        playersView.setBackground(new Color(128, 128, 0));
        add(playersView, BorderLayout.CENTER);
        add(boardView, BorderLayout.NORTH);
        add(currentPlayerLabel, BorderLayout.SOUTH);
    }

    /**
     * Aggiorna la vista del gioco.
     */
    public void updateView() {
        updatePlayersView();
        updateLabels();
        updateBoardView();
    }
    
    @Override
    public void update() {
        // Aggiorna la vista quando viene notificato dal GameModel
        updateView();
    }

}
