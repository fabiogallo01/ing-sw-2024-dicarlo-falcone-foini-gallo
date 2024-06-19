package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.exception.EmptyObjectiveDeckException;
import java.util.*;

/**
 * Class representing objective deck
 *
 * @author Falcone Giacomo, Foini Lorenzo
 */
public class ObjectiveDeck {
    private final ArrayList<ObjectiveCard> deck; // List of objectives cards

    /**
     * ObjectiveDeck constructor, it assigns the parameter
     *
     * @param deck list of cards of deck
     * @author Foini Lorenzo
     */
    public ObjectiveDeck(ArrayList<ObjectiveCard> deck){
        this.deck = deck;
    }

    /**
     * Method for getting deck size
     *
     * @return size of the deck
     * @author Falcone Giacomo
     */
    public int deckSize() {
        return deck.size();
    }

    /**
     * deck getter
     *
     * @return list of deck's cards
     * @author Foini Lorenzo
     */
    public ArrayList<ObjectiveCard> getDeck(){
        return deck;
    }

    /**
     * Method for shuffle cards in the deck
     *
     * @author Foini Lorenzo
     */
    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    /**
     * Method for draw the top card (last in the list) of the deck
     * It also removes that card from the deck
     *
     * @return the objective card at the top
     * @throws EmptyObjectiveDeckException if the deck is empty and player tries to draw from it
     * @author Giacomo Falcone
     */
    public ObjectiveCard drawTopCard() throws EmptyObjectiveDeckException{
        if(deck.isEmpty()){ // Deck is empty => Throw new EmptyObjectiveDeckException
            throw new EmptyObjectiveDeckException("Objective deck is empty, you can't draw from this deck.");
        }
        else{ // Deck has at least a card that can be drawn
            return deck.removeLast();
        }
    }
}