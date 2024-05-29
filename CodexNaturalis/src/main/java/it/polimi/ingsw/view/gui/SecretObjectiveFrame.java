package it.polimi.ingsw.view.gui;

import javax.swing.*;

/**
 * New class for creating a new window which will be used by client
 * It displays starter card on selected side, hand, common objectives, secret objectives and asks client to choose
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class SecretObjectiveFrame extends JFrame{
    private String selectedSecretCard; // Index of the selected secret objective card as string: "1" or "2"
    private final Object lock = new Object();

    /**
     * SecretObjectiveFrame constructor, it calls method init() for initialization of the frame
     *
     * @param title window's title
     * @param starterCardID ID of the starter card
     * @param handIDs IDs of the three cards in player's hand
     * @param commonObjectivesIds IDs of the two common objective cards
     * @param secretObjectivesIds IDs of the two secret objective cards
     * @author Foini Lorenzo
     */
    SecretObjectiveFrame(String title, int starterCardID, int[] handIDs, int[] commonObjectivesIds, int[] secretObjectivesIds) {
        super(title);
        init(starterCardID, handIDs, commonObjectivesIds, secretObjectivesIds);
    }

    /**
     * This method is used for initialization of frame
     *
     * @param starterCardID ID of the starter card
     * @param handIDs IDs of the three cards in player's hand
     * @param commonObjectivesIds IDs of the two common objective cards
     * @param secretObjectivesIds IDs of the two secret objective cards
     * @author Foini Lorenzo
     */
    private void init(int starterCardID, int[] handIDs, int[] commonObjectivesIds, int[] secretObjectivesIds){
        // TODO: Display starter card, hand, common objectives, secret objectives and ask client to choose
    }

    public String getSelectedSecretCard(){
        return selectedSecretCard;
    }
}