package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * New class for creating a new window which will be used by client
 * It represents the post game window with results
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class PostGameFrame extends JFrame {

    /**
     * PostGameFrame constructor, it calls method init() for initialization of frame
     *
     * @param title window's title
     * @param winnerMessage : string with name(s) of winner(s)
     * @param hasWon: If true => Client has won the game
     *                If false => Client has lost the game
     * @param finalScoreboard: list of scoreboard as a string, just display it
     * @author Foini Lorenzo
     */
    PostGameFrame(String title, String winnerMessage, boolean hasWon, ArrayList<String> finalScoreboard){
        super(title);
        init(winnerMessage, hasWon, finalScoreboard);
    }

    /**
     * This method is used for initialization of frame
     *
     * @param winnerMessage : string with name(s) of winner(s)
     * @param hasWon: If true => Client has won the game
     *                If false => Client has lost the game
     * @param finalScoreboard: list of scoreboard as a string, just display it
     * @author Foini Lorenzo
     */
    private void init(String winnerMessage, boolean hasWon, ArrayList<String> finalScoreboard){
        // Set frame parameters
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 500);
        this.setLayout(new GridLayout(4, 1));

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add label with winner(s) message
        addWinnerLabel(winnerMessage);

        // Add label with message based on hasWon value
        addHasWonMessage(hasWon);

        // Add label with final scoreboard
        addFinalScoreboard(finalScoreboard);

        // Add label with exit game message
        addExitGameMessage();

        // Set frame relative location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void addWinnerLabel(String winnerMessage){
        // Create new font for this label
        Font font = new Font("Arial", Font.BOLD, 15);

        // Create new label with winner(s) message
        JLabel winnerMessageLabel = new JLabel(winnerMessage);

        // Set label's font and alignment
        winnerMessageLabel.setFont(font);
        winnerMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerMessageLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Add label in the frame's first row
        this.add(winnerMessageLabel);
    }

    private void addHasWonMessage(boolean hasWon){
        // Create new font for this label
        Font font = new Font("Arial", Font.BOLD, 25);

        // Create new label based on value of hasWon
        JLabel hasWonLabel;
        if(hasWon) hasWonLabel = new JLabel("CONGRATULATIONS, YOU WON!!!");
        else hasWonLabel = new JLabel("SORRY, YOU LOST.");

        // Set label's font and alignment
        hasWonLabel.setFont(font);
        hasWonLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hasWonLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Add label in the frame's second row
        this.add(hasWonLabel);
    }

    private void addFinalScoreboard(ArrayList<String> finalScoreboard){
        Font font = new Font("Arial", Font.BOLD, 20);

        // Create new panel for containing the elements of finalScoreboard
        JPanel scoreboardPanel = new JPanel();
        scoreboardPanel.setLayout(new GridLayout(5, 1));

        // Iterate through elements of finalScoreboard
        for(String string : finalScoreboard){
            // Create new label with string's text
            JLabel label = new JLabel(string);

            // Set label's font and alignment
            label.setFont(font);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);

            // Add label in the panel
            scoreboardPanel.add(label);
        }

        // Add panel in the frame's third row
        this.add(scoreboardPanel);
    }

    private void addExitGameMessage(){
        // Create new font for this label
        Font font = new Font("Arial", Font.BOLD, 20);

        // Create new label with exit message
        JLabel exitLabel = new JLabel("THANKS FOR PLAYING, now close this window to exit.");

        // Set label's font and alignment
        exitLabel.setFont(font);
        exitLabel.setHorizontalAlignment(SwingConstants.CENTER);
        exitLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Add label in the frame's fourth row
        this.add(exitLabel);
    }
}