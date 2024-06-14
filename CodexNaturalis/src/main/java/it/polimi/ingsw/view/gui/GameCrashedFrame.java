package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * New class for creating a new window which shows to the client that the game has crashed
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class GameCrashedFrame extends JFrame {

    /**
     * GameCrashedFrame constructor, it calls method init() for initialization of frame
     *
     * @param title window's title
     * @author Foini Lorenzo
     */
    GameCrashedFrame(String title){
        super(title);
        init();
    }

    /**
     * This method is used for initialization of frame
     *
     * @author Foini Lorenzo
     */
    private void init(){
        // Set frame parameters
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 250);
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
        JLabel crashLabel = new JLabel("THE GAME HAS CRASHED", SwingConstants.CENTER);
        JLabel sorryLabel = new JLabel("WE ARE SORRY, NOW EXIT THE GAME", SwingConstants.CENTER);

        // Set font to labels
        crashLabel.setFont(font);
        sorryLabel.setFont(font);

        // Add labels to the frame
        this.add(crashLabel);
        this.add(sorryLabel);

        // Set frame relative location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}