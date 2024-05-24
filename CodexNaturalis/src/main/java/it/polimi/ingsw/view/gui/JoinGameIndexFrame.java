package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * New class for creating a new window which will be used by client
 * It asks the client if he wants to create or join a game
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class JoinGameIndexFrame extends JFrame {
    private int choice; // contains client's choice

    /**
     * CreateJoinFrame constructor, it calls method init() for initialization of the frame
     *
     * @param title window's title
     * @param controllers: list of game controllers
     * @author Foini Lorenzo
     */
    JoinGameIndexFrame(String title, java.util.List<Controller> controllers){
        super(title);
        init(controllers);
    }

    /**
     * This method is used for initialization of frame
     *
     * @param controllers: list of game controllers
     * @author Foini Lorenzo
     */
    private void init(java.util.List<Controller> controllers){
        // TODO
    }

    /**
     * choice getter
     *
     * @return client choice: "create" or "join"
     * @author Foini Lorenzo
     */
    public int getChoice() {
        return choice;
    }
}