package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import java.util.*;

public class GameTable {
    private static final ObjectiveCard[] commonObjectives;
    GamingDeck resourceDeck;
    GamingDeck goldDeck;
    GamingDeck startingDeck;
    ObjectiveDeck objectiveDeck;
    PlayerArea[] playerAreas;
    Scoreboard scoreboard;

    private int numPlayers;
    public final int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void addPlayer(Player player){}

    public void addCardArea(Player player, Card card){}

    public void playTurn(Player player){}

    public boolean isEnded(){
        return false;
    }

}
