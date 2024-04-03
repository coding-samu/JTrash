package controller;

import java.util.LinkedList;
import java.util.Queue;

import model.GameModel;
import model.UserModel;
import view.CardView;
import view.GameView;

/**
 * La classe GameController gestisce il flusso di controllo del gioco JTrash.
 */
public class GameController {
    private GameModel gameModel;
    private GameView gameView;
    private Queue<Runnable> gameQueue = new LinkedList<>();
    private Thread gameThread;

    /**
     * Crea una nuova istanza di GameController.
     */
    public GameController() {

    }

    /**
     * Imposta la vista del gioco.
     *
     * @param gameView La vista del gioco da impostare.
     */
    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Restituisce la vista del gioco.
     *
     * @return La vista del gioco.
     */
    public GameView getGameView() {
        return gameView;
    }

    /**
     * Imposta il modello del gioco.
     *
     * @param gameModel Il modello del gioco da impostare.
     */
    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    /**
     * Restituisce il modello del gioco.
     *
     * @return Il modello del gioco.
     */
    public GameModel getGameModel() {
        return gameModel;
    }

    /**
     * Gestisce il clic sul mazzo del giocatore.
     *
     * @param player Il giocatore che ha cliccato sul mazzo.
     */
    public void handleDeckClick(UserModel player) {
        gameModel.drawFromDeck(player);
        gameView.getBoardView().updateView();
    }

    /**
     * Gestisce il clic sulla pila di scarti del giocatore.
     *
     * @param player Il giocatore che ha cliccato sulla pila di scarti.
     */
    public void handleDiscardPileClick(UserModel player) {
        gameModel.drawFromDiscardPile(player);
        gameView.getBoardView().updateView();
    }

    /**
     * Gestisce il clic su una carta nella mano del giocatore.
     *
     * @param cardView La vista della carta cliccata.
     * @param player   Il giocatore proprietario della mano.
     */
    public void handleCardOnHandClick(CardView cardView, UserModel player) {
        gameModel.placeCard(cardView, player);
        player.getPlayerView().updateView();
        gameView.getBoardView().updateView();
    }

    /**
     * Avvia il thread di gioco.
     */
    public void game() {
        gameThread = new Thread(() -> {
            while (!gameModel.isGameWon() && !gameModel.isGameStopped()) {
                synchronized (gameQueue) { // Aggiungi una sincronizzazione sulla coda dei task
                    if (!gameModel.getCurrentPlayer().equals(gameModel.getHumanUser())) {
                        try {
                            Runnable task = gameQueue.poll();
                            if (task != null) { // Controlla se il task è nullo prima di eseguirlo
                                task.run();
                            } else {
                                gameQueue.add(() -> {
                                    gameModel.playAI();
                                    update();
                                });
                            }
                        } catch (NullPointerException e) {
                            // Gestisci eventuali eccezioni
                        }
                    }
                }
            }
        });
        gameThread.start();
    }

    /**
     * Interrompe il gioco.
     */
    public void stopGame() {
        gameModel.setGameStopped(true);
        gameThread.interrupt();
    }

    /**
     * Gestisce la mossa dell'IA.
     */
    public void playAI() {
        gameQueue.add(() -> {
            gameModel.playAI();
            update();
        });
    }

    /**
     * Aggiorna l'interfaccia utente.
     */
    public void update() {
        gameView.updateView();
    }
}
