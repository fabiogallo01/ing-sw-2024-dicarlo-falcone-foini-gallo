package it.polimi.ingsw.model.cards;

public class GamingCard extends Card{
    //private Kingdom kingdom;
    private int points;

    public GamingCard(boolean side, Kingdom king, int points, Corner[] fC) {
        super(side, fC, king);
        this.points = points;

    }

    /*public Kingdom getKingdom() {
        return kingdom;
    }*/

    public int getPoints() {
        return points;
    }
}

