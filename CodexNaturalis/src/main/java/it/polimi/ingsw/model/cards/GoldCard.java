package it.polimi.ingsw.model.cards;

public class GoldCard extends GamingCard {
    private final Kingdom[] resources;
    private final ConditionPoint conditionPoint;

    public GoldCard(boolean side, Kingdom kingdom, int points, Corner[] frontCorners, Kingdom[] resources, ConditionPoint conditionPoints) {
        super(side, kingdom, points, frontCorners);
        this.resources = resources;
        this.conditionPoint = conditionPoints;
    }

    public Kingdom[] getResources() {
        return resources;
    }

    public ConditionPoint getConditionPoint() {
        return conditionPoint;
    }
}