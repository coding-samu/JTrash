package model;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * La classe Deck rappresenta un mazzo di carte e fornisce funzionalità per mescolare e distribuire le carte.
 */
public class DeckModel implements Iterable<CardModel> {
    private CardModel[] mazzo;
    private int cursore;

    /**
     * Costruttore della classe Deck che inizializza il mazzo utilizzando i valori dell'enumerazione Card.
     */
    public DeckModel() {
        mazzo = CardModel.values();
    }

    /**
     * Costruisce un mazzo che è l'unione di un elenco di mazzi.
     *
     * @param decks L'elenco di mazzi da unire.
     */
    public DeckModel(List<DeckModel> decks) {
        mazzo = new CardModel[decks.stream().mapToInt(DeckModel::getNumberOfCards).sum()];
        int i = 0;
        for (DeckModel deck : decks) {
            for (CardModel card : deck.mazzo) {
                mazzo[i++] = card;
            }
        }
    }

    /**
     * Costruttore della classe Deck che inizializza il mazzo come un mazzo vuoto.
     *
     * @param numberOfCards Il numero di carte nel mazzo.
     */
    public DeckModel(int numberOfCards) {
        mazzo = new CardModel[numberOfCards];
    }

    /**
     * Restituisce il numero di carte nel mazzo.
     *
     * @return Il numero di carte nel mazzo.
     */
    public int getNumberOfCards() {
        return mazzo.length;
    }

    /**
     * Mescola le carte nel mazzo in modo casuale.
     */
    public void mescola() {
        Random r = new Random();
        for (int i = 0; i < mazzo.length; i++) {
            int t = r.nextInt(mazzo.length);
            CardModel appoggio = mazzo[i];
            mazzo[i] = mazzo[t];
            mazzo[t] = appoggio;
        }
        cursore = 0;
    }

    /**
     * Distribuisce una carta dal mazzo.
     *
     * @return La carta distribuita, o null se tutte le carte sono state distribuite.
     */
    public CardModel distribuisci() {
        return (cursore < mazzo.length) ? mazzo[cursore++] : null;
    }

    /**
     * Aggiunge una carta al mazzo in cima alla pila.
     *
     * @param card La carta da aggiungere.
     */
    public void addCard(CardModel card) {
        mazzo[cursore++] = card;
    }

    @Override
    public Iterator<CardModel> iterator() {
        return new DeckIterator();
    }

    private class DeckIterator implements Iterator<CardModel> {
        private int cursore;

        public DeckIterator() {
            this.cursore = 0;
        }

        @Override
        public boolean hasNext() {
            return cursore < mazzo.length;
        }

        @Override
        public CardModel next() {
            return mazzo[cursore++];
        }
    }

    /**
     * Restituisce la carta in cima al mazzo senza rimuoverla.
     *
     * @return La carta in cima al mazzo, o null se il mazzo è vuoto.
     */
    public CardModel getTopCard() {
        return (cursore > 0) ? mazzo[cursore - 1] : null;
    }

    /**
     * Restituisce la carta alla posizione specificata nel mazzo.
     *
     * @param pos La posizione della carta da restituire.
     * @return La carta alla posizione specificata, o null se la posizione è fuori dai limiti.
     */
    public CardModel get(int pos) {
        return mazzo[pos];
    }

    /**
     * Sostituisce la carta alla posizione specificata nel mazzo con una nuova carta.
     *
     * @param card La nuova carta da sostituire.
     * @param pos  La posizione della carta da sostituire.
     */
    public void replace(CardModel card, int pos) {
        mazzo[pos] = card;
    }

    /**
     * Rimuove la carta in cima al mazzo.
     */
    public void removeCardOnTop() {
        mazzo[cursore] = null;
        cursore--;
    }
}
