package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
public class GamingDeck {
    private int numCards;

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

    public Card drawTopCard(){

        return (Card);
    }

}
