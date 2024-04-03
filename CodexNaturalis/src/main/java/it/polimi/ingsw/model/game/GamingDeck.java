package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import java.util.*;

public class GamingDeck {
    private int numCards;
    private ArrayList<Card> deck;

    public GamingDeck(int numCards, ArrayList<Card> deck) {
        this.numCards = numCards;
        this.deck = deck;
    }

    public int getNumCards() {
        return numCards;
    }

    private void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void shuffleDeck() {
        List<Card> cardsList = new ArrayList<>(deck);
        Collections.shuffle(cardsList);
        deck.clear();
        deck.addAll(cardsList);
    }

    public boolean isEmpty() {
        return getNumCards() == 0;
    }

    public Card drawTopCard() throws EmptyDeckException {
        if (this.isEmpty()) {
            throw new EmptyDeckException("This deck is empty, you can't draw from this deck.");
        } else {
            Card top = deck.get(deck.size() - 1);
            deck.remove(deck.size() - 1);
            setNumCards(getNumCards() - 1);
            return top;
        }
    }
}
