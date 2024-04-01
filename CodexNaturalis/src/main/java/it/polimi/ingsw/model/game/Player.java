package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import java.util.*;

public class Player {
    // Attributes
    private final String username;
    private int score;
    private boolean isYourTurn;
    private PlayerArea playerArea;
    private final Color color;
    private final ObjectiveCard secretObjective;
    private final StarterCard starterCard;
    private ArrayList<GamingCard> hand;

    // Methods
    public Player(String username, int score, boolean isYourTurn, PlayerArea playerArea, Color color, StarterCard starterCard, ArrayList<GamingCard> hand){
        this.username = username;
        this.score = score;
        this.isYourTurn = isYourTurn;
        this.playerArea = playerArea;
        this.color = color;
        this.secretObjective = chooseSecretObjective();
        this.starterCard = starterCard;
        this.hand = hand;
    }

    public String getUsername(){
        return username;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score) throws NegativeScoreException {
        if (score < 0) {
            throw new NegativeScoreException("You can't assign a negative score");
        } else {
            this.score = score;
        }
    }
    public boolean getIsYourTurn(){
        return isYourTurn;
    }

    public void setIsYourTurn(boolean isYourTurn){
        this.isYourTurn = isYourTurn;
    }

    public PlayerArea getPlayerArea(){
        return playerArea;
    }

    public void setPlayerArea(PlayerArea playerArea){
        this.playerArea = playerArea;
    }

    public Color getColor(){
        return color;
    }

    public ObjectiveCard getSecretObjective(){
        return secretObjective;
    }

    public StarterCard getStarterCard(){
        return starterCard;
    }
    public ArrayList<GamingCard> getHand(){
        return hand;
    }

    public void setHand(ArrayList<GamingCard> hand) throws InvalidNumCardsPlayerHandException{
        if(hand.size() != 3){
            throw new InvalidNumCardsPlayerHandException("Invalid number of cards for player's hand. Must be 3.");
        }
        else {
            this.hand = hand;
        }
    }

    public ObjectiveCard[] drawSecretObjectiveCards(){
        // This method is used for draw 2 cards from the objective deck
        // It returns such cards
        // TODO
    }
    public ObjectiveCard chooseSecretObjective() {
        // It calls the method "drawSecretObjectiveCards" for getting the 2 cards
        ObjectiveCard[] objectiveCards = drawSecretObjectiveCards();
        // TODO
    }

    public GamingCard drawResourceCardDeck(){
        // TODO
    }

    public GamingCard drawResourceCardTable(int position){
        // TODO
    }

    public GamingCard drawGoldCardDeck(){
        // TODO
    }

    public GamingCard drawGoldCardTable(int position){
        // TODO
    }

    /*
        Method for play a card from the hand.
        It raises multiple exception given by:
        - Card "position" invalid
        - Card played
        - Player area

        At the end, the selected card is removed from the player's hand
    */
    public void playCard(int position){
        // TODO
    }
}