package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import java.util.*;

public class GamingDeck {
    private int numCards;
    ArrayList<Card> deck;

    public GamingDeck(ArrayList<Card> deck, int numCards) {
        this.deck = deck;
        this.numCards = numCards;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }
    public int getNumCards() {
        return numCards;
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public Card drawTopCard(){
        Card top=deck.get(getNumCards()-1);
        deck.remove(getNumCards()-1);
        setNumCards(getNumCards()-1);
        return top;
    }

}
