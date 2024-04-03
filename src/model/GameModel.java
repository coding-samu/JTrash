package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controller.GameController;
import util.Observer;
import view.CardView;

/**
 * La classe GameModel rappresenta il modello di un gioco di carte.
 */
public class GameModel {
    private UserModel[] players;
    private DeckModel discardPile;
    private UserModel currentPlayer;
    private DeckModel deck;
    private int playerIndex;
    private GameController controller;
    private CardModel currentPlayableCard;
    private UserModel user;
    private int numberOfPlayers;
    private boolean handWon;
    private boolean gameWon;
    private boolean gameStopped;
    private List<Observer> observers = new ArrayList<>();

    /**
     * Costruttore della classe GameModel per inizializzare una nuova partita.
     *
     * @param numberOfPlayers Il numero di giocatori nella partita.
     * @param user            L'utente principale.
     * @param controller      Il controller del gioco.
     */
    public GameModel(int numberOfPlayers, UserModel user, GameController controller) {
        Random random = new Random();
        gameStopped = false;
        this.user = user;
        this.numberOfPlayers = numberOfPlayers;
        this.controller = controller;
        controller.setGameModel(this);

        // Inizializza i giocatori
        players = new UserModel[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            UserModel player;
            if (i == 0) {
                // Il primo giocatore è l'utente
                player = user;
            } else {
                // Altri giocatori sono AI
                player = new UserModel("AI" + i);
            }
            player.setCardsOnHand(10);
            player.incrementGamesPlayed();
            players[i] = player;
        }

        playerIndex = random.nextInt(numberOfPlayers);

        gameWon = false;
        nextGame();
        controller.game();
    }
    
    /**
     * Distribuisce le carte ai giocatori.
     */
    private void dealCards() {
        
        for (UserModel player : players) {
        	player.newHand();
            for (int i = 0; i < player.getCardsOnHand(); i++) {
                CardModel card = deck.distribuisci();
                player.addCardToHand(card);
            }
        }
        
        discardPile = new DeckModel(deck.getNumberOfCards());
    }

    /**
     * Termina il turno del giocatore corrente e passa al turno successivo.
     *
     * @param player Il giocatore che sta terminando il turno.
     */
    public void endTurn(UserModel player) {
        checkHandWon(player);
        // Implementa la logica per passare al turno successivo
        UserModel nextPlayer = players[getNextPlayer()]; // Memorizza il giocatore successivo
        if (isHandWon()) {
            currentPlayableCard = null;
            endHand(player);
        } else {
            currentPlayer = nextPlayer; // Usa il giocatore successivo memorizzato
            if (currentPlayableCard != null) {
                if (!isPlayable(currentPlayableCard, currentPlayer)) {
                    discardPile.addCard(currentPlayableCard);
                    currentPlayableCard = null;
                }
            }
            controller.getGameView().updateLabels();
        }
        
        notifyObservers();
    }

    /**
     * Inizia una nuova partita distribuendo le carte ai giocatori.
     */
    public void nextGame() {
        handWon = false;

        // Calcola il numero di mazzi in base al numero di giocatori
        int numberOfDecks = (int) ((numberOfPlayers + 1) / 2);

        // Inizializza i mazzi e li mischia
        List<DeckModel> decksList = new ArrayList<>();
        for (int i = 0; i < numberOfDecks; i++) {
            decksList.add(new DeckModel());
        }

        deck = new DeckModel(decksList);
        deck.mescola();

        // Inizia il gioco distribuendo le carte
        dealCards();

        // Inizia il gioco con il primo giocatore
        currentPlayer = players[getNextPlayer()];

    }
    
    /**
     * Termina una mano del gioco e inizia una nuova mano.
     *
     * @param player Il giocatore che ha terminato la mano.
     */
    public void endHand(UserModel player) {
    	controller.getGameView().showHandWonMessage(player);
        player.decrementCardsOnHand();
        if (!isGameWon()) {
            nextGame(); // Distribuisci nuove carte per la nuova mano

            // Aggiorna le view dei giocatori e la board
            for (UserModel userModel : players) {
                userModel.getPlayerView().putCardsOnPanel();
            }

            controller.getGameView().resetView();

            controller.update(); // Aggiorna l'interfaccia utente
        }
    }

    /**
     * Restituisce il giocatore corrente.
     *
     * @return Il giocatore corrente.
     */
    public UserModel getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Restituisce l'elenco dei giocatori.
     *
     * @return L'elenco dei giocatori.
     */
    public UserModel[] getPlayersList() {
        return players;
    }
    
    /**
     * Metodo per gestire la pesca dal deck.
     * @param player Il giocatore che pesca dal deck.
     */
    public void drawFromDeck(UserModel player) {
    	if (deck == null) return;
    	CardModel tempCard = deck.distribuisci();
    	if (!(currentPlayableCard == null) || tempCard == null || (!currentPlayer.equals(player))) return;
    	if (isPlayable(tempCard, player)) {
    		currentPlayableCard = tempCard;
    		return;
    	}
    	else {
    		discardPile.addCard(tempCard);
    		endTurn(player);
    		return;
    	}
    }
    
    /**
     * Metodo per gestire la pesca dal mazzo degli scarti.
     * @param player Il giocatore che pesca dal mazzo degli scarti.
     */
    public void drawFromDiscardPile(UserModel player) {
    	if (discardPile == null) return;
    	if (!(currentPlayableCard == null) || discardPile.getTopCard() == null  || (!currentPlayer.equals(player))) return;
    	if (!isPlayable(discardPile.getTopCard(), player)) return;
    	currentPlayableCard = discardPile.getTopCard();
    	discardPile.removeCardOnTop();
    	controller.update();
    }
    
    /**
     * Metodo setter per impostare la vittoria di un giocatore.
     * @param player Il giocatore che ha vinto la partita.
     */
    public void setVictory(UserModel player) {
    	player.incrementGamesWon();
    	if (player.getGamesWon() >= (player.getLevel()+1)*2) player.levelUp();
    	for (UserModel user : players) {
    		if (!user.equals(player)) user.incrementGamesLost();
    	}
    }
    
    /**
     * Metodo getter per ottenere la carta giocabile currentPlayableCard.
     * @return La carta currentPlayableCard.
     */
    public CardModel getCurrentPlayableCard() {
    	return currentPlayableCard;
    }
    
    /**
     * Metodo getter per ottenere il deck.
     * @return il deck del round corrent.
     */
    public DeckModel getDeck() {
    	return deck;
    }
    
    /**
     * Metodo getter per ottenere la pila degli scarti.
     * @return la pila degli scarti del round corrente.
     */
    public DeckModel getDiscardPile() {
    	return discardPile;
    }
    
    public boolean isPlayable(CardModel card, UserModel player) {
    	if (CardValueModel.QUEEN.equals(card.getValue())) return false;
    	if (CardValueModel.KING.equals(card.getValue())) return false;
    	if (CardValueModel.JACK.equals(card.getValue())) return true;
    	if (card.getValue().getValue() > player.getCardsOnHand()) return false;
    	if (player.getPlayerView().getCardViewList()[card.getValue().getValue()-1].isTurnedUp()) {
    		if (player.getHand().get(card.getValue().getValue()-1).getValue().equals(CardValueModel.JACK)) return true;
    		else return false;
    	}
    	return true;
    }
    
    /**
     * Metodo getter per ottenere l'indice del giocatore successivo.
     * @return l'indice del prossimo giocatore.
     */
    public int getNextPlayer() {
        playerIndex++;
        if (playerIndex >= numberOfPlayers) {
            playerIndex = 0;
        }
        return playerIndex;
    }

    /**
     * Questo metodo imposta il controller del gioco.
     *
     * @param controller Il controller del gioco.
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }

    /**
     * Restituisce il controller del gioco.
     *
     * @return Il controller del gioco.
     */
    public GameController getController() {
        return controller;
    }

    /**
     * Restituisce l'utente umano principale del gioco.
     *
     * @return L'utente umano principale del gioco.
     */
    public UserModel getHumanUser() {
        return user;
    }

    /**
     * Verifica se il valore della carta è uguale alla posizione nella vista della carta.
     *
     * @param cardView La vista della carta.
     * @return True se il valore della carta è uguale alla posizione nella vista della carta, altrimenti false.
     */
    public boolean isCardValueEqualToPosition(CardView cardView) {
        if (currentPlayableCard.getValue().getValue() == getCardPosition(cardView) + 1) return true;
        return false;
    }

    /**
     * Ottiene la posizione della carta nella vista.
     *
     * @param cardView La vista della carta.
     * @return La posizione della carta nella vista.
     */
    public int getCardPosition(CardView cardView) {
        if (cardView.getY() == 0) {
            return (int) (cardView.getX() / 60);
        }
        return (int) (cardView.getX() / 60) + 5;
    }

    /**
     * Posiziona una carta nella vista del giocatore corrente.
     *
     * @param cardView La vista della carta da posizionare.
     * @param player   Il giocatore che sta posizionando la carta.
     */
    public void placeCard(CardView cardView, UserModel player) {
        if (currentPlayableCard == null) return;
        if (!player.equals(currentPlayer)) return;
        if (!player.isOnHand(cardView.getCardModel())) return;
        int cardPosition = getCardPosition(cardView);
        if (!cardView.isTurnedUp()) {
            if (isCardValueEqualToPosition(cardView) || currentPlayableCard.getValue().equals(CardValueModel.JACK)) {
                CardModel tempCard = player.getHand().get(cardPosition);
                player.getHand().replace(currentPlayableCard, cardPosition);
                cardView.setCardModel(currentPlayableCard);
                currentPlayableCard = null;
                cardView.turnUp();

                if (isPlayable(tempCard, player)) {
                    currentPlayableCard = tempCard;
                    controller.update();
                    checkHandWon(player);
                } else {
                    discardPile.addCard(tempCard);
                    controller.update();
                    endTurn(player);
                }
            }
        } else {
            if (cardView.getCardModel().getValue().equals(CardValueModel.JACK) && getCardPosition(cardView) + 1 == currentPlayableCard.getValue().getValue()) {
                CardModel tempCard = cardView.getCardModel();
                player.getHand().replace(currentPlayableCard, cardPosition);
                cardView.setCardModel(currentPlayableCard);
                cardView.turnUp();
                currentPlayableCard = tempCard;
                controller.update();
                checkHandWon(player);
            }
        }
        if (currentPlayableCard != null) {
            if (!isPlayable(currentPlayableCard, player)) {
                discardPile.addCard(currentPlayableCard);
                currentPlayableCard = null;
            }
        }
        checkHandWon(player);
        if (handWon == true) endTurn(player);

    }

    /**
     * Controlla se il giocatore ha vinto il gioco.
     *
     * @param player Il giocatore da controllare.
     */
    public void checkGameWon(UserModel player) {
        controller.update();
        if (player.getCardsOnHand() == 0 && !gameWon) {
            controller.getGameView().showGameWonMessage(player);
            gameWon = true;
            setVictory(player);
        }
    }

    /**
     * Controlla se il giocatore ha vinto la mano attuale.
     *
     * @param player Il giocatore da controllare.
     */
    public void checkHandWon(UserModel player) {
        controller.update();
        boolean handWon = true; // Imposta a true all'inizio, e se trovi una carta non girata, impostalo a false
        CardView[] cardList = getCurrentPlayer().getPlayerView().getCardViewList();
        for (int i = 0; i < player.getCardsOnHand(); i++) {
        	if (cardList[i] != null) {
	            if (!cardList[i].isTurnedUp()) {
	                handWon = false;
	                break; // Esci dal ciclo se trovi una carta non girata
	            }
            }
        }
        this.handWon = handWon;
        if (handWon == true) checkGameWon(player);
    }

    /**
     * Verifica se il gioco è stato vinto.
     *
     * @return True se il gioco è stato vinto, altrimenti false.
     */
    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * Verifica se la mano è stata vinta.
     *
     * @return True se la mano è stata vinta, altrimenti false.
     */
    public boolean isHandWon() {
        return handWon;
    }

    /**
     * Simula il comportamento del giocatore automatico (AI).
     */
    public void playAI() {
        UserModel player = getCurrentPlayer();
        if (player.equals(getHumanUser())) return;
        Random random = new Random();
        while (player.equals(getCurrentPlayer())) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            if (getCurrentPlayableCard() == null) {
                if (getDiscardPile() == null) {
                    controller.handleDeckClick(player);
                    return;
                } else if (getDiscardPile().getTopCard() == null
                        || !isPlayable(getDiscardPile().getTopCard(), player)) {
                    controller.handleDeckClick(player);
                    return;
                } else {
                    if (random.nextInt(2) == 0) {
                        controller.handleDeckClick(player);
                    }
                    controller.handleDiscardPileClick(player);
                }
            } else if (getCurrentPlayableCard() != null) {
                CardView[] cardList = getCurrentPlayer().getPlayerView().getCardViewList();
                if (!getCurrentPlayableCard().getValue().equals(CardValueModel.JACK)) {
                    controller.handleCardOnHandClick(cardList[getCurrentPlayableCard().getValue().getValue() - 1], player);
                } else {
                    CardView[] notTurnedUpCards = getCurrentPlayer().getPlayerView().getCardViewListOfNotTurnedUpCards();
                    int i = random.nextInt(notTurnedUpCards.length);
                    controller.handleCardOnHandClick(notTurnedUpCards[i], player);
                }
            }
            controller.update();
        }
    }

    /**
     * Verifica se il gioco è stato interrotto.
     *
     * @return True se il gioco è stato interrotto, altrimenti false.
     */
	public boolean isGameStopped() {
		return gameStopped;
	}
	
	/**
	 * Imposta lo stato del gioco.
	 * @param gameStopped True se il gioco è stato interrotto, altrimenti false.
	 */
	public void setGameStopped(boolean gameStopped) {
		this.gameStopped = gameStopped;
	}
	
	/**
	 * Aggiunge un observer alla lista di observer.
	 * @param observer
	 */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    /**
     * Rimuove un observer dalla lista di observer.
     * @param observer
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    /**
     * Notifica gli observer.
     */
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
