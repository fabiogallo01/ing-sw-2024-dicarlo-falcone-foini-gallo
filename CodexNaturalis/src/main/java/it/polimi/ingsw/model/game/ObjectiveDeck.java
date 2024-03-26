package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.ObjectiveCard;
public class ObjectiveDeck {
    // Attributes
    private int numCards;
    private ObjectiveCard[] objectiveCards;

    // Methods
    public ObjectiveDeck(int numCards, ObjectiveCard[] objectiveCards){
        this.numCards = numCards;
        this.objectiveCards = objectiveCards;
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
        /*
        ObjectiveCard topCard
        setNumCards(getNumCards()-1)

        setObjectiveCard()

        return getObjectiveCards()[getNumCards()-1];
        */
    }

    public static void shuffle(){

    }
}
