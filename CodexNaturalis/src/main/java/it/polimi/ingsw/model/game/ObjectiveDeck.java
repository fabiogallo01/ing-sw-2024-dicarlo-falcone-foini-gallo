package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.exception.EmptyObjectiveDeckException;
import java.util.*;

/**
 * Class representing objective deck
 *
 * @author Giacomo Falcone, Foini Lorenzo
 */
public class ObjectiveDeck {
    private final ArrayList<ObjectiveCard> deck;

    /**
     * objective deck constructor, it assigns the parameter
     *
     * @param deck list of cards of deck
     * @author Foini Lorenzo
     */
    public ObjectiveDeck(ArrayList<ObjectiveCard> deck){
        this.deck = deck;
    }

    /**
     * Method for gettin deck size
     *
     * @return size of the deck
     * @author Giacomo Falcone
     */
    public int deckSize() {
        return deck.size();
    }

    /**
     * deck getter
     *
     * @return list of deck's cards
     * @author Lorenzzo Foini
     */
    public ArrayList<ObjectiveCard> getDeck(){
        return deck;
    }

    /**
     * Method for shuffle cards in the deck
     *
     * @author Lorenzo Foini
     */
    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    /**
     * Method for draw the top card (last in the list) of the deck
     * It also removes that card from the deck
     *
     * @throws EmptyObjectiveDeckException if the deck is empty and player tries to draw from it
     * @return the objective card at the top
     * @author Giacomo Falcone
     */
    public ObjectiveCard drawTopCard() throws EmptyObjectiveDeckException{
        if(deck.isEmpty()){
            throw new EmptyObjectiveDeckException("Objective deck is empty, you can't draw from this deck.");
        }
        else{
            return deck.removeLast();
        }
    }
}