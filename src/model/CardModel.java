package model;

/**
 * L'enumerazione Card rappresenta le carte di un mazzo standard.
 * Ogni carta è associata a un seme e a un valore specifico, come Cuori - Asso, Quadri - Due, ecc.
 */
public enum CardModel {

    // Carte con seme Cuori
    C1(CardSeedModel.CUORI, CardValueModel.ASSO, "cardImages/ace_of_hearts.png"),
    C2(CardSeedModel.CUORI, CardValueModel.DUE, "cardImages/2_of_hearts.png"),
    C3(CardSeedModel.CUORI, CardValueModel.TRE, "cardImages/3_of_hearts.png"),
    C4(CardSeedModel.CUORI, CardValueModel.QUATTRO, "cardImages/4_of_hearts.png"),
    C5(CardSeedModel.CUORI, CardValueModel.CINQUE, "cardImages/5_of_hearts.png"),
    C6(CardSeedModel.CUORI, CardValueModel.SEI, "cardImages/6_of_hearts.png"),
    C7(CardSeedModel.CUORI, CardValueModel.SETTE, "cardImages/7_of_hearts.png"),
    C8(CardSeedModel.CUORI, CardValueModel.OTTO, "cardImages/8_of_hearts.png"),
    C9(CardSeedModel.CUORI, CardValueModel.NOVE, "cardImages/9_of_hearts.png"),
    C10(CardSeedModel.CUORI, CardValueModel.DIECI, "cardImages/10_of_hearts.png"),
    CJ(CardSeedModel.CUORI, CardValueModel.JACK, "cardImages/jack_of_hearts2.png"),
    CQ(CardSeedModel.CUORI, CardValueModel.QUEEN, "cardImages/queen_of_hearts2.png"),
    CK(CardSeedModel.CUORI, CardValueModel.KING, "cardImages/king_of_hearts2.png"),

    // Carte con seme Quadri
    Q1(CardSeedModel.QUADRI, CardValueModel.ASSO, "cardImages/ace_of_diamonds.png"),
    Q2(CardSeedModel.QUADRI, CardValueModel.DUE, "cardImages/2_of_diamonds.png"),
    Q3(CardSeedModel.QUADRI, CardValueModel.TRE, "cardImages/3_of_diamonds.png"),
    Q4(CardSeedModel.QUADRI, CardValueModel.QUATTRO, "cardImages/4_of_diamonds.png"),
    Q5(CardSeedModel.QUADRI, CardValueModel.CINQUE, "cardImages/5_of_diamonds.png"),
    Q6(CardSeedModel.QUADRI, CardValueModel.SEI, "cardImages/6_of_diamonds.png"),
    Q7(CardSeedModel.QUADRI, CardValueModel.SETTE, "cardImages/7_of_diamonds.png"),
    Q8(CardSeedModel.QUADRI, CardValueModel.OTTO, "cardImages/8_of_diamonds.png"),
    Q9(CardSeedModel.QUADRI, CardValueModel.NOVE, "cardImages/9_of_diamonds.png"),
    Q10(CardSeedModel.QUADRI, CardValueModel.DIECI, "cardImages/10_of_diamonds.png"),
    QJ(CardSeedModel.QUADRI, CardValueModel.JACK, "cardImages/jack_of_diamonds2.png"),
    QQ(CardSeedModel.QUADRI, CardValueModel.QUEEN, "cardImages/queen_of_diamonds2.png"),
    QK(CardSeedModel.QUADRI, CardValueModel.KING, "cardImages/king_of_diamonds2.png"),

    // Carte con seme Picche
    P1(CardSeedModel.PICCHE, CardValueModel.ASSO, "cardImages/ace_of_spades.png"),
    P2(CardSeedModel.PICCHE, CardValueModel.DUE, "cardImages/2_of_spades.png"),
    P3(CardSeedModel.PICCHE, CardValueModel.TRE, "cardImages/3_of_spades.png"),
    P4(CardSeedModel.PICCHE, CardValueModel.QUATTRO, "cardImages/4_of_spades.png"),
    P5(CardSeedModel.PICCHE, CardValueModel.CINQUE, "cardImages/5_of_spades.png"),
    P6(CardSeedModel.PICCHE, CardValueModel.SEI, "cardImages/6_of_spades.png"),
    P7(CardSeedModel.PICCHE, CardValueModel.SETTE, "cardImages/7_of_spades.png"),
    P8(CardSeedModel.PICCHE, CardValueModel.OTTO, "cardImages/8_of_spades.png"),
    P9(CardSeedModel.PICCHE, CardValueModel.NOVE, "cardImages/9_of_spades.png"),
    P10(CardSeedModel.PICCHE, CardValueModel.DIECI, "cardImages/10_of_spades.png"),
    PJ(CardSeedModel.PICCHE, CardValueModel.JACK, "cardImages/jack_of_spades2.png"),
    PQ(CardSeedModel.PICCHE, CardValueModel.QUEEN, "cardImages/queen_of_spades2.png"),
    PK(CardSeedModel.PICCHE, CardValueModel.KING, "cardImages/king_of_spades2.png"),

    // Carte con seme Fiori
    F1(CardSeedModel.FIORI, CardValueModel.ASSO, "cardImages/ace_of_clubs.png"),
    F2(CardSeedModel.FIORI, CardValueModel.DUE, "cardImages/2_of_clubs.png"),
    F3(CardSeedModel.FIORI, CardValueModel.TRE, "cardImages/3_of_clubs.png"),
    F4(CardSeedModel.FIORI, CardValueModel.QUATTRO, "cardImages/4_of_clubs.png"),
    F5(CardSeedModel.FIORI, CardValueModel.CINQUE, "cardImages/5_of_clubs.png"),
    F6(CardSeedModel.FIORI, CardValueModel.SEI, "cardImages/6_of_clubs.png"),
    F7(CardSeedModel.FIORI, CardValueModel.SETTE, "cardImages/7_of_clubs.png"),
    F8(CardSeedModel.FIORI, CardValueModel.OTTO, "cardImages/8_of_clubs.png"),
    F9(CardSeedModel.FIORI, CardValueModel.NOVE, "cardImages/9_of_clubs.png"),
    F10(CardSeedModel.FIORI, CardValueModel.DIECI, "cardImages/10_of_clubs.png"),
    FJ(CardSeedModel.FIORI, CardValueModel.JACK, "cardImages/jack_of_clubs2.png"),
    FQ(CardSeedModel.FIORI, CardValueModel.QUEEN, "cardImages/queen_of_clubs2.png"),
    FK(CardSeedModel.FIORI, CardValueModel.KING, "cardImages/king_of_clubs2.png");

    CardValueModel valore;
    CardSeedModel seme;
    String imagePath;

    /**
     * Costruttore per la classe Card.
     * @param seme Il seme della carta.
     * @param valore Il valore della carta.
     * @param imagePath Il percorso all'immagine della carta.
     */
    CardModel(CardSeedModel seme, CardValueModel valore, String imagePath) {
        this.seme = seme;
        this.valore = valore;
        this.imagePath = imagePath;
    }
    
    /**
     * Metodo get per il percorso dell'immagine.
     * @return il percorso dell'immagine
     */
	public String getImagePath() {
		return imagePath;
	}
	
	@Override
	public String toString() {
		return valore + " DI " + seme;
	}
	
	/**
	 * Restituisce il valore della carta.
	 * 
	 * @return Il valore della carta.
	 */
	public CardValueModel getValue() {
	    return valore;
	}

	/**
	 * Restituisce il seme della carta.
	 * 
	 * @return Il seme della carta.
	 */
	public CardSeedModel getSeed() {
	    return seme;
	}

}
