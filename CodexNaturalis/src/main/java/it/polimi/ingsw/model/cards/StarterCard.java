package it.polimi.ingsw.model.cards;

public class StarterCard extends Card{
    private final Kingdom[] frontKingdoms;

    public StarterCard(boolean side, Corner[] frontCorners, Corner[] backCorners, Kingdom[] frontKingdoms) {
        super(side, frontCorners, backCorners); // Using the constructor with backCorners
        this.frontKingdoms = frontKingdoms;
    }

    public Kingdom[] getFrontKingdoms() {
        return frontKingdoms;
    }
}