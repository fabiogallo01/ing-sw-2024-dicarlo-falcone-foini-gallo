package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import java.util.*;

import java.util.ArrayList;

public class ObjectiveDeck {
    // Attributes
    private int numCards;
    private ArrayList<ObjectiveCard> deck;

    // Methods
    public ObjectiveDeck(int numCards, ArrayList<ObjectiveCard> deck){
        this.numCards = numCards;
        this.deck = deck;
    }

    public int getNumCards(){
        return numCards;
    }

    public void setNumCards(int numCards){
        this.numCards = numCards;
    }

    public ArrayList<ObjectiveCard> getDeck(){
        return deck;
    }

    public void setDeck(ArrayList<ObjectiveCard> deck){
        this.deck = deck;
    }

    public boolean isEmpty(){
        return numCards == 0;
    }

    // Method for shuffle the deck
    public static void shuffle(){
        List<ObjectiveCard> cardsList = new ArrayList<>(deck);
        Collections.shuffle(cardsList);
        deck.clear();
        deck.addAll(cardList);
    }

    // Method for returning the top card of the deck
    // It also remove that card from the deck
    // Throw exception if the deck is empty
    public ObjectiveCard drawTopCard() throws EmptyObjectiveDeckException{
        if(this.isEmpty()){
            throw new EmptyObjectiveDeckException("Objective deck is empty, you can't draw from this deck.");
        }
        else{
            ObjectiveCard top =  deck.get(deck.size() - 1);
            deck.remove(deck.size() - 1);

            // Decrement numCards
            numCards--;
            return top;
        }
    }
}
