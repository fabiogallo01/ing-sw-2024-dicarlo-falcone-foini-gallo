package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.*;

public class GameTable {
    private static final ObjectiveCard[] commonObjectives;
    private int numPlayers;
    public int getNumPlayers() {
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
