package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.exception.EmptyObjectiveDeckException;
import java.util.*;

public class ObjectiveDeck {
    // Attributes
    private final ArrayList<ObjectiveCard> deck;

    // Methods
    public ObjectiveDeck(ArrayList<ObjectiveCard> deck){
        this.deck = deck;
    }

    public ArrayList<ObjectiveCard> getDeck(){
        return deck;
    }

    // Method for shuffle deck
    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    // Method for returning the top card of the deck
    // It also removes that card from the deck
    // Throw exception if the deck is empty
    public ObjectiveCard drawTopCard() throws EmptyObjectiveDeckException{
        if(deck.isEmpty()){
            throw new EmptyObjectiveDeckException("Objective deck is empty, you can't draw from this deck.");
        }
        else{
            return deck.removeLast();
        }
    }
}