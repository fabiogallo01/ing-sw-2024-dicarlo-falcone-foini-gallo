package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import java.util.*;

public class GameTable {

    private GamingDeck resourceDeck;
    private GamingDeck goldDeck;
    private GamingDeck starterDeck;
    private ObjectiveDeck objectiveDeck;
    private ArrayList<GamingCard> visibleCard;
    private static ObjectiveCard[] commonObjectives;
    private final int numPlayers;
    private ArrayList<Player> players;
    private Scoreboard scoreboard;

    public GameTable(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public GamingDeck getResourceDeck() {

        return resourceDeck;
    }

    public void setResourceDeck(ArrayList<Card> cards, int numCards) {//Ci serve questo metodo? se sì c'è qualcosa da correggere
         this.resourceDeck = new GamingDeck(numCards);
    }

    private GamingCard[] createResourceCards(){}

    public GamingDeck getGoldDeck() {
        return goldDeck;
    }

    public void setGoldDeck(GamingDeck goldDeck) {
        this.goldDeck = goldDeck;
    }

    private GoldCard[] createGoldCards(){}

    public GamingDeck getStarterDeck() {
        return starterDeck;
    }

    public void setStarterDeck(GamingDeck starterDeck) {
        this.starterDeck = starterDeck;
    }

    private StarterCard[] createStarterCards(){}

    public ObjectiveDeck getObjectiveDeck() {
        return objectiveDeck;
    }

    public void setObjectiveDeck(ObjectiveDeck objectiveDeck) {
        this.objectiveDeck = objectiveDeck;
    }

    private ObjectiveCard[] createObjectiveCards(){}

    public ArrayList<GamingCard> getVisibleCard() {
        return visibleCard;
    }

    public void setVisibleCard(ArrayList<GamingCard> visibleCard) {
        this.visibleCard = visibleCard;
    }

    public void addVisibleCard(GamingCard gamingCard){

    }

    public static ObjectiveCard[] getCommonObjectives() {
        return commonObjectives;
    }

    public static void setCommonObjectives(ObjectiveCard[] commonObjectives) {
        GameTable.commonObjectives = commonObjectives;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player){}

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public void playTurn(Player player){}

    public boolean isEnded(){
        return true;
    }
}
