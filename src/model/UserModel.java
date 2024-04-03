package model;

import java.io.Serializable;

import view.PlayerView;

/**
 * La classe User rappresenta un profilo utente nel gioco JTrash. Ogni utente ha
 * un nickname, un avatar e statistiche relative alle partite giocate.
 */
public class UserModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nickname;
	private String avatar;
	private int gamesPlayed;
	private int gamesWon;
	private int gamesLost;
	private int level;
	private DeckModel hand;
	private int cardsOnHand;
	private PlayerView view;

	/**
	 * Costruttore per la classe User.
	 * 
	 * @param nickname Il nickname dell'utente.
	 */
	public UserModel(String nickname) {
		this.nickname = nickname;
		this.avatar = "profileImages/Default.jpg";
		this.gamesPlayed = 0;
		this.gamesWon = 0;
		this.gamesLost = 0;
		this.level = 1;
	}

	/**
	 * Restituisce il nickname dell'utente.
	 * 
	 * @return Il nickname dell'utente.
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Restituisce l'avatar dell'utente.
	 * 
	 * @return Il percorso o il nome dell'avatar dell'utente.
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Restituisce il numero di partite giocate dall'utente.
	 * 
	 * @return Il numero di partite giocate.
	 */
	public int getGamesPlayed() {
		return gamesPlayed;
	}

	/**
	 * Restituisce il numero di partite vinte dall'utente.
	 * 
	 * @return Il numero di partite vinte.
	 */
	public int getGamesWon() {
		return gamesWon;
	}

	/**
	 * Restituisce il numero di partite perse dall'utente.
	 * 
	 * @return Il numero di partite perse.
	 */
	public int getGamesLost() {
		return gamesLost;
	}

	/**
	 * Restituisce il livello dell'utente nel gioco.
	 * 
	 * @return Il livello dell'utente.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Imposta il nickname dell'utente.
	 * 
	 * @param nickname Il nuovo nickname da impostare.
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Imposta l'avatar dell'utente.
	 * 
	 * @param avatar Il nuovo avatar da impostare.
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * Incrementa il numero di partite giocate dall'utente.
	 */
	public void incrementGamesPlayed() {
		gamesPlayed++;
	}

	/**
	 * Incrementa il numero di partite vinte dall'utente.
	 */
	public void incrementGamesWon() {
		gamesWon++;
	}

	/**
	 * Incrementa il numero di partite perse dall'utente.
	 */
	public void incrementGamesLost() {
		gamesLost++;
	}

	/**
	 * Aumenta il livello dell'utente nel gioco.
	 */
	public void levelUp() {
		level++;
	}
	
	/**
	 * Aggiunge una carta alla mano del giocatore.
	 *
	 * @param card La carta da aggiungere alla mano.
	 */
	public void addCardToHand(CardModel card) {
	    hand.addCard(card);
	}

	/**
	 * Restituisce la mano del giocatore.
	 *
	 * @return La mano del giocatore come oggetto DeckModel.
	 */
	public DeckModel getHand() {
	    return hand;
	}

	/**
	 * Crea una nuova mano iniziale per il giocatore.
	 */
	public void newHand() {
	    hand = new DeckModel(cardsOnHand);
	}

	/**
	 * Restituisce il numero di carte attualmente in mano al giocatore.
	 *
	 * @return Il numero di carte in mano al giocatore.
	 */
	public int getCardsOnHand() {
	    return cardsOnHand;
	}

	/**
	 * Decrementa il numero di carte in mano al giocatore.
	 */
	public void decrementCardsOnHand() {
	    cardsOnHand--;
	}

	/**
	 * Verifica se una carta è attualmente in mano al giocatore.
	 *
	 * @param card La carta da verificare.
	 * @return True se la carta è in mano al giocatore, altrimenti False.
	 */
	public boolean isOnHand(CardModel card) {
	    for (CardModel cardA : hand) {
	        if (cardA.equals(card)) return true;
	    }
	    return false;
	}

	/**
	 * Imposta la vista del giocatore.
	 *
	 * @param view La vista del giocatore.
	 */
	public void setPlayerView(PlayerView view) {
	    this.view = view;
	}

	/**
	 * Restituisce la vista del giocatore.
	 *
	 * @return La vista del giocatore.
	 */
	public PlayerView getPlayerView() {
	    return view;
	}

	/**
	 * Imposta il numero di carte attualmente in mano al giocatore.
	 *
	 * @param cardsOnHand Il numero di carte in mano al giocatore.
	 */
	public void setCardsOnHand(int cardsOnHand) {
	    this.cardsOnHand = cardsOnHand;
	}

}
