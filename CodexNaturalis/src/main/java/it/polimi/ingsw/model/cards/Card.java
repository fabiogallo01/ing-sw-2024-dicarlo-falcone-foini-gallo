package it.polimi.ingsw.model.cards;

/**
 * Class representing game's cards
 * It is not abstract because during the deserialization of GameTable when client plays with GUI, it casts all teh cards to Card
 * => Can't be abstract
 * It is extended by GamingCard (resource card and gold card) and StarterCard
 *
 * @author Di Carlo Andrea, Falcone Giacomo, Foini Lorenzo, Gallo Fabio
 */
public class Card {
    protected boolean side; // true => Front, false => Back
    protected Corner[] frontCorners;
    protected Corner[] backCorners;
    protected int[] inGamePosition;
    protected boolean counted; // true => Counted in pattern, false => Not counted in pattern
    protected final int ID;

    /**
     * Card constructor, it assigns all the parameters (also back corners)
     * Used by StarterCard
     * Set back corners, inGamePosition to -1 and counted to false
     *
     * @param side for card side. true => Front, false => Back
     * @param frontCorners for card's front corners
     * @param backCorners for card's back corners
     * @param ID card's ID
     * @author Di Carlo Andrea, Falcone Giacomo, Foini Lorenzo
     */
    public Card(boolean side, Corner[] frontCorners, Corner[] backCorners, int ID) {
        this.side = side;
        this.frontCorners = frontCorners;
        this.backCorners = backCorners;
        this.inGamePosition = new int[2];
        this.inGamePosition[0] = -1;
        this.inGamePosition[1] = -1; // At the beginning the cards do not have a position
        this.counted = false; // Parameter used for counting pattern for objective cards
        this.ID = ID;
    }

    /**
     * Card constructor, it assigns all the parameters (not back corners).
     * Used by GamingCard and GoldCard
     * Set inGamePosition to -1 and counted to false
     *
     * @param side for card side. true => Front, false => Back
     * @param frontCorners for card's front corners
     * @param ID card's ID
     * @author Foini Lorenzo, Fabio Gallo
     */
    public Card(boolean side, Corner[] frontCorners, int ID) {
        this.side = side;
        this.frontCorners = frontCorners;
        // Create new back corners => They are the same for all cards
        this.backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
            };
        this.inGamePosition = new int[2];
        this.inGamePosition[0] = -1;
        this.inGamePosition[1] = -1; // At the beginning the cards do not have a position
        this.counted = false; // Parameter used for counting pattern for objective cards
        this.ID = ID;
    }

    /**
     * side getter
     *
     * @return boolean for side. true => Front, false => Back
     * @author Foini Lorenzo
     */
    public boolean getSide(){
        return side;
    }

    /**
     * side setter
     *
     * @param side for setting side. true => Front, false => Back
     * @author Foini Lorenzo
     */
    public void setSide(boolean side){
        this.side = side;
    }

    /**
     * backCorners getter
     *
     * @return array of back corners
     * @author Di Carlo Andrea
     */
    public Corner[] getBackCorners() {
        return backCorners;
    }

    /**
     * frontCorners getter
     *
     * @return array of front corners
     * @author Di Carlo Andrea
     */
    public Corner[] getFrontCorners() {
        return frontCorners;
    }

    /**
     * inGamePosition getter
     *
     * @return array of card's in game position
     * @author Foini Lorenzo
     */
    public int[] getInGamePosition(){
        return inGamePosition;
    }

    /**
     * inGamePosition setter
     * It is guaranteed that position to be set is valid
     *
     * @param inGamePosition array of card's in game position to be set
     * @author Foini Lorenzo
     */
    public void setInGamePosition(int[] inGamePosition){
        this.inGamePosition[0] = inGamePosition[0];
        this.inGamePosition[1] = inGamePosition[1];
    }

    /**
     * counted getter
     *
     * @return boolean value of counted. true => Count in pattern, false => Not count in pattern
     * @author Foini Lorenzo
     */
    public boolean getCounted(){
        return counted;
    }

    /**
     * counted setter
     *
     * @param counted boolean value of counted. true => Count in pattern, false => Not count in pattern
     * @author Foini Lorenzo
     */
    public void setCounted(boolean counted){
        this.counted = counted;
    }

    /**
     * Method for setting to false the visibility of a corner, given its position
     *
     * @param position representing which corner to set his side to false
     * @author Di Carlo Andrea, Foini Lorenzo
     */
    public void setVisibleCornerFalse(int position)
    {
        if(side) getFrontCorners()[position].setVisible(false);
        else getBackCorners()[position].setVisible(false);
    }

    /**
     * ID getter
     *
     * @return ID of the card
     * @author Gallo Fabio
     */
    public int getID() {
        return ID;
    }
}