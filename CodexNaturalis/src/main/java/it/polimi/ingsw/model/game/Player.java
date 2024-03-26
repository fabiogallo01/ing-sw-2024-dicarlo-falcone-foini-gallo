package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;

public class Player {
    // Attributes
    private final String username;
    private int score;
    private boolean isYourTurn;
    private PlayerArea playerArea;
    private final Color color;
    private final ObjectiveCard secretObjective;
    private Card[] hand;

    // Methods
    public Player(String username, int score, boolean isYourTurn, PlayerArea playerArea, Color color, Card[] hand){
        this.username = username;
        this.score = score;
        this.isYourTurn = isYourTurn;
        this.playerArea = playerArea;
        this.color = color;
        this.secretObjective = chooseSecretObjective();
        this.hand = hand;
    }

    public String getUsername(){
        return username;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public boolean getIsYourTurn(){
        return isYourTurn;
    }

    public void setIsYourTurn(boolean isYourTurn){
        this.isYourTurn = isYourTurn;
    }

    public Color getColor(){
        return color;
    }

    public ObjectiveCard getSecretObjective(){
        return secretObjective;
    }

    public Card[] getHand(){
        return hand;
    }

    public void setHand(Card[] hand){
        this.hand = hand;
    }

    public ObjectiveCard[] drawSecretObjectiveCards(){
        // This method is used for draw 2 cards from the objective deck
        // It returns such cards
        // TODO
    }
    public ObjectiveCard chooseSecretObjective() {
        // It calls the method "drawSecretObjectiveCards" for getting the 2 cards
        // TODO
    }

    public GamingCard drawGamingCardDeck(){
        // TODO
    }

    public GamingCard drawGamingCardTable(int position){
        // TODO
    }

    public GamingCard drawGoldCardDeck(){
        // TODO
    }

    public GamingCard drawGoldCardTable(int position){
        // TODO
    }

    public void playCard(int position){
        // TODO
    }

    public void removeCardHand(Card card){
        // TODO
    }
}