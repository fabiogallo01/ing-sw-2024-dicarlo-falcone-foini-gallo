package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;

import java.util.*;

/**
 * Class to handle the gaming decks(resource, golden, starting)
 *
 * @author Fabio Gallo
 */
public class GamingDeck {
    private final ArrayList<Card> deck;

    /**
     * Gaming deck constructor
     *
     * @param deck deck
     * @author Fabio Gallo
     */
    public GamingDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    /**
     * @return size of the deck
     * @author Fabio Gallo
     */
    public int deckSize() {
        return deck.size();
    }

    /**
     * Deck getter
     *
     * @return deck
     * @author Fabio Gallo
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Deck shuffler
     *
     * @author Fabio Gallo
     */
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    /**
     * To draw a card from a deck
     *
     * @return the top card of the deck
     * @throws EmptyDeckException if the deck is empty
     * @author Fabio Gallo
     */
    public Card drawTopCard() throws EmptyDeckException {
        if (deck.isEmpty()) {
            throw new EmptyDeckException("This deck is empty, you can't draw from this deck.");
        } else {
            return deck.removeLast();
        }
    }

}
