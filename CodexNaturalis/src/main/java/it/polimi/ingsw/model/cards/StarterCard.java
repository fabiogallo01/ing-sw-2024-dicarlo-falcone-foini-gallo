package it.polimi.ingsw.model.cards;

public class StarterCard {
    private Kingdom[] backKingdoms;

    public StarterCard(boolean side, Corner[] fC, Corner[] bC, Kingdom[] bK) {
        super(side, fC, bC);
        this.backKingdoms = bK;
    }
}
