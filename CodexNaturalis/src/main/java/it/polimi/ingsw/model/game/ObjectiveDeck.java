package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.ObjectiveCard;

import java.util.ArrayList;

public class ObjectiveDeck {
    // Attributes
    private int numCards;
    private ArrayList<ObjectiveCard> objectiveCards;

    // Methods
    public ObjectiveDeck(int numCards, ObjectiveCard[] objectiveCards){
        this.numCards = numCards;
        this.objectiveCards = new ArrayList<ObjectiveCard>(objectiveCards);
    }

    public int getNumCards(){
        return numCards;
    }

    public void setNumCards(int numCards){
        this.numCards = numCards;
    }

    public ObjectiveCard[] getObjectiveCards(){
        return objectiveCards;
    }

    public void setObjectiveCards(ObjectiveCard[] objectiveCards){
        this.objectiveCards = objectiveCards;
    }

    public static ObjectiveCard drawTopCard(){
        ObjectiveCard top =  getObjectiveCards()[getNumCards()-1];

        setNumCard(getNumCards()-1)
        return top;
        */
    }

    public static void shuffle(){

    }
}
