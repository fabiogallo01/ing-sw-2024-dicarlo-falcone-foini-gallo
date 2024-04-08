package it.polimi.ingsw.model.cards;

public class GamingCard extends Card{
    private Kingdom kingdom;
    private int points;

    public GamingCard(boolean side, Kingdom king, int points, Corner[] fC) {
        super(side, fC);
        this.points = points;
        this.kingdom = king;

    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public int getPoints() {
        return points;
    }
}

