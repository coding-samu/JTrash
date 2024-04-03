package model;

/**
 * L'enumerazione CardValue rappresenta i possibili valori delle carte in un mazzo di carte.
 * Ogni valore è associato a una carta specifica, come ad esempio Asso, Due, Tre, ecc.
 */
public enum CardValueModel {
    ASSO(1),    // Rappresenta l'Asso.
    DUE(2),     // Rappresenta il Due.
    TRE(3),     // Rappresenta il Tre.
    QUATTRO(4), // Rappresenta il Quattro.
    CINQUE(5),  // Rappresenta il Cinque.
    SEI(6),     // Rappresenta il Sei.
    SETTE(7),   // Rappresenta il Sette.
    OTTO(8),    // Rappresenta l'Otto.
    NOVE(9),    // Rappresenta il Nove.
    DIECI(10),   // Rappresenta il Dieci.
    JACK(11),    // Rappresenta il Jack.
    QUEEN(12),   // Rappresenta la Regina.
    KING(13)     // Rappresenta il Re.
;
	
	private int value;
	
	CardValueModel(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
