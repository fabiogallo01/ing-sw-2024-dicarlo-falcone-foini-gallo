package it.polimi.ingsw.view.gui;

import javax.swing.*;
import java.awt.*;

/**
 * New class for creating a new window which will be used by client
 * It represents the wait after login window.
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */

public class WaitStartGameFrame extends JFrame {
    /**
     * WaitCreateGameFrame constructor, it calls method init() for initialization of frame
     *
     * @param title window's title
     * @param create if true => Client has created a new game
     *               if false => Client has joined a game
     * @author Foini Lorenzo
     */
    WaitStartGameFrame(String title, boolean create){
        super(title);
        init(create);
    }

    /**
     * This method is used for initialization of frame
     *
     * @param create if true => Client has created a new game
     *               if false => Client has joined a game
     * @author Foini Lorenzo
     */
    void init(boolean create){
        // Set frame parameters
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 200);
        this.setLayout(new GridLayout(2, 1));

        // Create new font for labels
        Font font = new Font("Arial", Font.BOLD, 15);

        // Create new labels
        String firstLabel = "";
        if(create) firstLabel = "THE GAME HAS BEEN CREATED";
        else firstLabel = "YOU HAVE CORRECTLY JOINED THE GAME";

        JLabel label1 = new JLabel(firstLabel, SwingConstants.CENTER);
        JLabel label2 = new JLabel("NOW WAIT FOR OTHER PLAYERS TO JOIN", SwingConstants.CENTER);

        // Set font to labels
        label1.setFont(font);
        label2.setFont(font);

        // Add labels to the frame
        this.add(label1);
        this.add(label2);

        // Set frame relative location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}