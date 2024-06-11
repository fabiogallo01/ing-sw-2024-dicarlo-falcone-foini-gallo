package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * New class for creating a new window which will be used by client
 * It represents the wait for ending of the game.
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class WaitEndGameFrame extends JFrame {

    /**
     * WaitEndGameFrame constructor, it calls method init() for initialization of frame
     *
     * @param title window's title
     * @author Foini Lorenzo
     */
    WaitEndGameFrame(String title){
        super(title);
        init();
    }

    /**
     * This method is used for initialization of frame
     *
     * @author Foini Lorenzo
     */
    private WaitEndGameFrame init(){
        // Set frame parameters
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);
        this.setLayout(new GridLayout(2, 1));

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create new font for labels
        Font font = new Font("Arial", Font.BOLD, 15);

        // Create new labels
        JLabel label1 = new JLabel("THIS WAS YOUR LAST TURN", SwingConstants.CENTER);
        JLabel label2 = new JLabel("NOW WAIT FOR OTHER PLAYERS TO FINISH THEIR TURN", SwingConstants.CENTER);

        // Set font to labels
        label1.setFont(font);
        label2.setFont(font);

        // Add labels to the frame
        this.add(label1);
        this.add(label2);

        // Set frame relative location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        return this;
    }
}