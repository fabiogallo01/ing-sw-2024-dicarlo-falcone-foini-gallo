package it.polimi.ingsw.model.cards;

/**
 * Cards that require resources to play
 * GoldCard extends GamingCard by adding resource kingdoms and condition points
 *
 * @author Falcone Giacomo, Foini Lorenzo, Gallo Fabio
 */

public class GoldCard extends GamingCard {
    private final Kingdom[] resources; // Array of Kingdom that have to be in the player's area for playing this card
    private final ConditionPoint conditionPoint; // Condition that indicates the type of objects that determinate the number of points scored

    /**
     * GoldCard Constructor
     *
     * @param side indicates the side of the card. true => Front, false => Back
     * @param kingdom the kingdom of the card
     * @param points how many points you can get playing this card (multiples by the condition)
     * @param frontCorners array of card front face corners with their properties defined
     * @param resources the amount of kingdoms objects that must be present on the playing field for the card to be played
     * @param conditionPoints the conditions that are applied for the assignment of points
     * @author Foini Lorenzo, Gallo Fabio
     */
    public GoldCard(boolean side, Kingdom kingdom, int points, Corner[] frontCorners, Kingdom[] resources, ConditionPoint conditionPoints, int ID) {
        super(side, kingdom, points, frontCorners, ID);
        this.resources = resources;
        this.conditionPoint = conditionPoints;
    }

    /**
     * resources getter
     *
     * @return An array of Kingdom representing the resources needed to play this card
     * @author Falcone Giacomo
     */
    public Kingdom[] getResources() {
        return resources;
    }

    /**
     * conditionPoint getter
     *
     * @return the ConditionPoint that defines the specific condition for the assignment of points
     * @author Falcone Giacomo
     */
    public ConditionPoint getConditionPoint() {
        return conditionPoint;
    }
}