package it.polimi.ingsw.model.cards;

public class GamingCard extends Card{
    private final Kingdom kingdom;
    private final int points;
    public GamingCard(boolean side, Kingdom kingdom, int points, Corner[] frontCorners) {
        super(side, frontCorners); // Using the constructor without backCorners
        this.kingdom = kingdom;
        this.points = points;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public int getPoints() {
        return points;
    }
}