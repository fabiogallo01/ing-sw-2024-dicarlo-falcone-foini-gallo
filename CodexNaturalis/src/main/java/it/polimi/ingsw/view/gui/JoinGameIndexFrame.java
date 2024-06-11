package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * New class for creating a new window which will be used by client
 * It asks the client if he wants to create or join a game
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class JoinGameIndexFrame extends JFrame {
    private String selectedGame; // contains the selected game to join
    private final Object lock = new Object();

    /**
     * CreateJoinFrame constructor, it calls method init() for initialization of the frame
     *
     * @param title window's title
     * @param joinGamesAndPlayers: map of games and their clients' username
     * @author Foini Lorenzo
     */
    JoinGameIndexFrame(String title, Map<String, List<String>> joinGamesAndPlayers){
        super(title);
        init(joinGamesAndPlayers);
    }

    /**
     * This method is used for initialization of frame
     *
     * @param joinGamesAndPlayers: map of games and their clients' username
     * @author Foini Lorenzo
     */
    private void init(Map<String, List<String>> joinGamesAndPlayers){
        // Assign some frame's parameters
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 400);
        this.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create label with question
        JLabel questionsLabel = new JLabel("Which game you want to join?", SwingConstants.CENTER);
        this.add(questionsLabel, BorderLayout.NORTH);

        // Create new panel: it contains list of games and their players' username
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Iterate through games for getting their clients' username
        for (Map.Entry<String, List<String>> entry : joinGamesAndPlayers.entrySet()) {
            // Create new game label and add into main panel
            JLabel gameLabel = new JLabel(entry.getKey());
            mainPanel.add(gameLabel);
            // Iterate through players' usernames
            for (String username : entry.getValue()) {
                // Create new player label and add into main panel
                JLabel playerPanel = new JLabel(username);
                mainPanel.add(playerPanel);
            }
        }

        // Add scroll pane and add it into the frame
        JScrollPane scrollPaneGames = new JScrollPane(mainPanel);
        this.add(scrollPaneGames, BorderLayout.CENTER);

        // Create a panel, it contains the two buttons
        JPanel buttonPanel = createButtonPanel(new ArrayList<>(joinGamesAndPlayers.keySet()));

        // Add scroll pane and add it into the frame
        JScrollPane scrollPaneButtons = new JScrollPane(buttonPanel);
        this.add(scrollPaneButtons, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        synchronized (lock) {
            try {
                lock.wait(); // Wait until user selects a card
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * This method is used for create a new panel with a button for every game that can be joined
     *
     * @param gameKeys: list of games that can be joined
     * @return JPanel with buttons
     * @author Foini Lorenzo
     */
    public JPanel createButtonPanel(ArrayList<String> gameKeys){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Create exit button
        JButton backButton = new JButton("<-- BACK");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(e -> {
            selectedGame = "0";
            this.dispose(); // Close the window
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        });
        // Add button
        buttonPanel.add(backButton);

        // Iterate through available games
        for(String gameKey: gameKeys){
            // Create button
            JButton gameButton = new JButton("JOIN "+gameKey.toUpperCase());
            gameButton.setPreferredSize(new Dimension(150, 50));
            gameButton.addActionListener(e -> {
                selectedGame = gameKey;
                this.dispose(); // Close the window
                synchronized (lock) {
                    lock.notify(); // Notify waiting thread
                }
            });

            // Add button
            buttonPanel.add(gameButton);
        }

        return buttonPanel;
    }

    /**
     * This method use selectedGame and extract its index
     *
     * @return client selected index of the game to join as a string
     * @author Foini Lorenzo
     */
    public String getSelectedIndex() {
        if(selectedGame.equals("0")){
            return "0";
        }
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(selectedGame);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}