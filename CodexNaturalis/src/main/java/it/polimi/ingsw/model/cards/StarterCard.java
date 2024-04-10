package it.polimi.ingsw.model.cards;

public class StarterCard extends Card{
    private Kingdom[] backKingdoms;

    public StarterCard(boolean side, Corner[] fC, Corner[] bC, Kingdom[] bK) {
        super(side, fC, bC, bK);
    }
    public Kingdom[] getBackKingdoms() {
        return backKingdoms;
    }
}

