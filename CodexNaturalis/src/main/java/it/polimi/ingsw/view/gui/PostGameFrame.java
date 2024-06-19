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
    private final Font customFont = new Font("SansSerif", Font.BOLD, 18); // Used font

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
        this.setSize(800, 400);
        this.setLayout(new GridLayout(4, 1));

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Create background panel and set it
        BackgroundPanel backgroundPanel = new BackgroundPanel("CodexNaturalis\\resources\\Screen.jpg");
        this.setContentPane(backgroundPanel);

        // Create a transparent panel for labels and button
        JPanel transparentPanel = new JPanel(new GridBagLayout());
        transparentPanel.setOpaque(false);

        // Create new grid bag constraint for adding the label/button in a pre-fixed position and with margin
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(25, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Create label with winner(s) message and add it in transparent panel with grid bag constraint
        JLabel winnerLabel = addWinnerLabel(winnerMessage);
        transparentPanel.add(winnerLabel, gbc);

        // Create label with has won/lose message and add it in transparent panel with grid bag constraint
        JLabel hasWonLabel = addHasWonLabel(hasWon);
        transparentPanel.add(hasWonLabel, gbc);

        // Create panel with scoreboard and add it in transparent panel with grid bag constraint
        JPanel finalScoreboardPanel = addFinalScoreboardPanel(finalScoreboard);
        transparentPanel.add(finalScoreboardPanel, gbc);

        // Create label with exit message and add it in transparent panel with grid bag constraint
        JLabel exitGameLabel = addExitGameLabel();
        transparentPanel.add(exitGameLabel, gbc);

        // Add transparent panel to the frame
        this.add(transparentPanel, BorderLayout.CENTER);

        // Set frame relative location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * This method is used for create a label with the winner(s) of the game
     *
     * @param winnerMessage: string which contains the message with the winners
     * @return the created JLabel
     * @author Foini Lorenzo
     */
    private JLabel addWinnerLabel(String winnerMessage){
        // Create new label with winner(s) message
        JLabel winnerMessageLabel = new JLabel(winnerMessage);

        // Set label's font and alignment
        winnerMessageLabel.setFont(customFont);
        winnerMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerMessageLabel.setVerticalAlignment(SwingConstants.CENTER);

        return winnerMessageLabel;
    }

    /**
     * This method is used for create a label for showing to the client if he won/lost
     *
     * @param hasWon: true => Client has won
     *                false => Client has lost
     * @return the created JLabel
     * @author Foini Lorenzo
     */
    private JLabel addHasWonLabel(boolean hasWon){
        // Create new label based on value of hasWon
        JLabel hasWonLabel;
        if(hasWon) hasWonLabel = new JLabel("CONGRATULATIONS, YOU WON!!!");
        else hasWonLabel = new JLabel("SORRY, YOU LOST.");

        // Set label's font and alignment
        hasWonLabel.setFont(customFont);
        hasWonLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hasWonLabel.setVerticalAlignment(SwingConstants.CENTER);

        return hasWonLabel;
    }

    /**
     * This method is used for create a panel which contains the final scoreboard
     *
     * @param finalScoreboard: list of string representing the scores by the player
     * @return the created JPanel
     * @author Foini Lorenzo
     */
    private JPanel addFinalScoreboardPanel(ArrayList<String> finalScoreboard){
        // Create new panel for containing the elements of finalScoreboard
        JPanel scoreboardPanel = new JPanel();
        scoreboardPanel.setLayout(new GridLayout(5, 1));
        scoreboardPanel.setOpaque(false);

        // Iterate through elements of finalScoreboard
        for(String string : finalScoreboard){
            // Create new label with string's text
            JLabel label = new JLabel(string);

            // Set label's font and alignment
            label.setFont(customFont);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);

            // Add label in the panel
            scoreboardPanel.add(label);
        }

        return scoreboardPanel;
    }

    /**
     * This method is used for create a label with exit message
     *
     * @return the created JLabel
     * @author Foini Lorenzo
     */
    private JLabel addExitGameLabel(){
        // Create new label with exit message
        JLabel exitLabel = new JLabel("THANKS FOR PLAYING, now close this window to exit.");

        // Set label's font and alignment
        exitLabel.setFont(customFont);
        exitLabel.setHorizontalAlignment(SwingConstants.CENTER);
        exitLabel.setVerticalAlignment(SwingConstants.CENTER);

        return exitLabel;
    }
}