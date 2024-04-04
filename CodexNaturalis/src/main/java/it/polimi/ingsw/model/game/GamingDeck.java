package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import java.util.*;

public class GamingDeck {
    private ArrayList<Card> deck;

    public GamingDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public int deckSize() {
        return deck.size();
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public Card drawTopCard() throws EmptyDeckException {
        if (deck.isEmpty()) {
            throw new EmptyDeckException("This deck is empty, you can't draw from this deck.");
        } else{
            return deck.removeLast();
        }
    }

}
