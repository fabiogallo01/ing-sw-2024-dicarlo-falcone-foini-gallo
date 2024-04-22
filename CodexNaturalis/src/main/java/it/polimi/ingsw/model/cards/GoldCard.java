package it.polimi.ingsw.model.cards;

/**
 * Cards that require resources to play
 * GoldCard extends {@link GamingCard} by adding resource kingdoms and condition points
 * @author giacomofalcone, LorenzoFoini
 */

public class GoldCard extends GamingCard {
    private final Kingdom[] resources;
    private final ConditionPoint conditionPoint;

    /**
     * Constructor of GoldCard
     *
     * @param side indicates which side of the card is visible
     * @param kingdom the kingdom of the card
     * @param points how many points you can get playing this card
     * @param frontCorners array of card front face corners with their properties defined
     * @param resources the amount of kingdoms objects that must be present on the playing field for the card to be played
     * @param conditionPoints the conditions that are applied for the assignment of points
     */
    public GoldCard(boolean side, Kingdom kingdom, int points, Corner[] frontCorners, Kingdom[] resources, ConditionPoint conditionPoints) {
        super(side, kingdom, points, frontCorners);
        this.resources = resources;
        this.conditionPoint = conditionPoints;
    }

    /**
     * @return An array of {@link Kingdom} representing the resources needed to play this card
     */
    public Kingdom[] getResources() {
        return resources;
    }

    /**
     * @return The {@link ConditionPoint} that defines the specific condition for the assignment of points
     */
    public ConditionPoint getConditionPoint() {
        return conditionPoint;
    }
}