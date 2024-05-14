package it.polimi.ingsw.model.cards;

/**
 * Abstract class representing game's cards
 * It is extended by GamingCard (resource card and gold card) and StarterCard
 *
 * @author Andrea Di Carlo, Lorenzo Foini, Fabio Gallo
 */
public abstract class Card {
    protected boolean side;
    protected Corner[] frontCorners;
    protected Corner[] backCorners;
    protected int[] inGamePosition;
    protected boolean counted; // true => Counted in patter, false => Not counted in pattern
    protected final int ID;

    /**
     * Card constructor, it assigns all the parameters (back corners)
     * Used by StarterCard
     * Set back corners, inGamePosition to -1 and counted to false
     *
     * @param side for card side. true => Front, false => Back
     * @param frontCorners for card's front corners
     * @param backCorners for card's back corners
     * @author Andrea Di Carlo, Foini Lorenzo
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
     * @author Foini Lorenzo, Fabio Gallo
     */
    public Card(boolean side, Corner[] frontCorners, int ID) {
        this.side = side;
        this.frontCorners = frontCorners;
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
     * Side getter
     *
     * @return boolean for side. true => Front, false => Back
     * @author Lorenzo Foini
     */
    public boolean getSide(){
        return side;
    }

    /**
     * Side setter
     *
     * @param side for side. true => Front, false => Back
     * @author Lorenzo Foini
     */
    public void setSide(boolean side){
        this.side = side;
    }

    /**
     * Back corners getter
     *
     * @return array of back corners
     * @author Andrea Di Carlo
     */
    public Corner[] getBackCorners() {
        return backCorners;
    }

    /**
     * Front corners getter
     *
     * @return array of front corners
     * @author Andrea Di Carlo
     */
    public Corner[] getFrontCorners() {
        return frontCorners;
    }

    /**
     * Card's in game position getter
     *
     * @return array of card's in game position
     * @author Lorenzo Foini
     */
    public int[] getInGamePosition(){
        return inGamePosition;
    }

    /**
     * Card's in game position setter
     *
     * @param inGamePosition array of card's in game position to be set
     * @author Lorenzo Foini
     */
    public void setInGamePosition(int[] inGamePosition){
        this.inGamePosition[0] = inGamePosition[0];
        this.inGamePosition[1] = inGamePosition[1];
    }

    /**
     * counted getter
     *
     * @return boolean value of counted
     * @author Lorenzo Foini
     */
    public boolean getCounted(){
        return counted;
    }

    /**
     * counted setter
     *
     * @param counted boolean value of counted
     * @author Lorenzo Foini
     */
    public void setCounted(boolean counted){
        this.counted = counted;
    }

    /**
     * counted getter
     *
     * @param position representing which corner to set his side to false
     * @author Andrea Di Carlo, Fabio Gallo
     */
    public void setVisibleCornerFalse(int position)
    {
        if (side)
        { getFrontCorners()[position].setVisible(false); }
        else
        { getBackCorners()[position].setVisible(false); }
    }

    public int getID() {
        return ID;
    }
}