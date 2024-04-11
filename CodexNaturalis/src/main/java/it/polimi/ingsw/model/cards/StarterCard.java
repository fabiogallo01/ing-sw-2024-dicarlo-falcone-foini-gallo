package it.polimi.ingsw.model.cards;

public class StarterCard extends Card{
    private final Kingdom[] backKingdoms;

    public StarterCard(boolean side, Corner[] frontCorners, Corner[] backCorners, Kingdom[] backKingdoms) {
        super(side, frontCorners, backCorners);
        this.backKingdoms = backKingdoms;
    }

    public Kingdom[] getBackKingdoms() {
        return backKingdoms;
    }
}