package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;

import java.util.*;

public class GamingDeck {
    private int numCards;
    ArrayList<Card> deck;

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public GamingDeck(int numCards) {
        this.numCards = numCards;
    }

    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    public int getNumCards() {
        return numCards;
    }

    public void shuffle(){}

    public GamingDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public Card drawTopCard(){
     }

}
