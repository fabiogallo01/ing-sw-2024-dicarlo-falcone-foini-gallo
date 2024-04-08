package it.polimi.ingsw.model.cards;

public class GoldCard extends GamingCard {
    private Kingdom[] resources = new Kingdom[5];
    private ConditionPoint conditionPoint;

    public GoldCard(boolean side, Kingdom king, int points, Corner[] fC, Kingdom[] res, ConditionPoint conP) {
        super(side, king, points, fC); // Assuming Card is having same constructor
        this.resources = res;
        this.conditionPoint = conP;
    }

    public Kingdom[] getResources() {
        return resources;
    }

    public ConditionPoint getConditionPoint() {
        return conditionPoint;
    }

}

