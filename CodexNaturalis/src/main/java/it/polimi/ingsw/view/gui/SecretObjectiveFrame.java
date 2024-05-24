package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.ObjectiveCard;

import javax.swing.*;

/**
 * New class for creating a new window which will be used by client
 * It displays starter card on selected side, hand, common objectives, secret objectives and asks client to choose
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class SecretObjectiveFrame extends JFrame{
    private ObjectiveCard selectedSecretCard; // Client selected secret objective card
    private final Object lock = new Object();

    /**
     * SecretObjectiveFrame constructor, it calls method init() for initialization of the frame
     *
     * @param title        window's title
     * @author Foini Lorenzo
     */
    SecretObjectiveFrame(String title) {
        super(title);
        init();
    }

    private void init(){
        // TODO: Display starter card on selected side, hand, common objectives, secret objectives and ask client to choose
    }

    public ObjectiveCard getSelectedSecretCard(){
        return selectedSecretCard;
    }
}
