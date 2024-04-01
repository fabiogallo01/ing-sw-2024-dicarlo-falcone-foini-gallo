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
    public Player(String username, int score, boolean isYourTurn, PlayerArea playerArea, Color color, ObjectiveCard secretObjective, StarterCard starterCard, ArrayList<GamingCard> hand){
        this.username = username;
        this.score = score;
        this.isYourTurn = isYourTurn;
        this.playerArea = playerArea;
        this.color = color;
        this.secretObjective = secretObjective;
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
        else{
            this.hand = hand;
        }
    }

    /*
        Method for play a card from the hand.
        It raises multiple exception given by:
        - Card "position" invalid
        - Card played
        - Player area

        At the end, the selected card is removed from the player's hand
    */
    public void playCard(int positionCardHand, int[] positionArea) throws InvalidPlayCardIndexException, InvalidPositionAreaException, InvalidPlayException {
        if (positionCardHand < 1 || positionCardHand > 3) {
            throw new InvalidPlayCardIndexException("Invalid selection of the card from hand.");
        }
        else if(!isValidPosition(positionArea)){
            throw new InvalidPositionAreaException("Not valid index's position.");
        }
        else{
            // Get the card form hand
            GamingCard cardToPlay = hand.get(positionCardHand-1);

            // Check if the card is actually playable given the game's rules
            if (!isPlayable(cardToPlay)) {
                throw new InvalidPlayException("You can't play this card in this position");
            }
            else{ //The card is playable
                // Add the card in the given position
                playerArea.addCard(cardToPlay, positionArea);

                // Remove the card from the player's hand
                hand.remove(positionCardHand-1);
            }
        }
    }

    private boolean isValidPosition(int[] position) {
        // Check if the position is valid in the matrix
        // Return true if the position is valid, false otherwise
    }

    private boolean isPlayable(GamingCard card) {
        // Check if the card is actually playable given the game's rules and current player's area
        // Return true if the card is playable, false otherwise
    }
}