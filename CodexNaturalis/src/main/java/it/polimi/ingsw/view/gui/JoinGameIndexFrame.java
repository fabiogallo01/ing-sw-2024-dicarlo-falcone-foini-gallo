package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
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
    private final Object lock = new Object(); // Lock for getting clint choice
    private final Font labelFont = new Font("SansSerif", Font.BOLD, 18); // Used font for labels
    private final Font buttonFont = new Font("SansSerif", Font.BOLD, 14); // Used font for buttons

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
        this.setSize(1000, 500);
        this.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Setting custom image icon from resource
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

        // Create label with question
        JLabel questionsLabel = new JLabel("Which game you want to join?", SwingConstants.CENTER);
        questionsLabel.setFont(labelFont);

        // Add label in the transparent panel with grid back constraint
        transparentPanel.add(questionsLabel, gbc);

        // Iterate through games for getting their clients' username
        for (Map.Entry<String, List<String>> entry : joinGamesAndPlayers.entrySet()) {
            // Reset gbc insect
            gbc.insets = new Insets(15, 10, 1, 10);

            // Create new game label and add into main panel
            JLabel gameLabel = new JLabel(entry.getKey());
            gameLabel.setFont(labelFont);
            transparentPanel.add(gameLabel, gbc);

            // Reset gbc insect
            gbc.insets = new Insets(1, 10, 1, 10);

            // Iterate through players' usernames
            for (String username : entry.getValue()) {
                // Create new player label and add into main panel
                JLabel playerLabel = new JLabel(username);
                playerLabel.setFont(labelFont);
                transparentPanel.add(playerLabel, gbc);
            }
        }

        // Reset gbc insect
        gbc.insets = new Insets(20, 10, 2, 10);

        // Create a panel, it contains the buttons for joining a game or back to the previous window
        JPanel buttonPanel = createButtonPanel(new ArrayList<>(joinGamesAndPlayers.keySet()));

        // Add scroll pane in the transparent panel with grid back constraint
        transparentPanel.add(buttonPanel, gbc);

        // Add transparent panel to the frame
        this.add(transparentPanel, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        synchronized (lock) {
            try {
                lock.wait(); // Wait selection
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
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
        buttonPanel.setOpaque(false);

        // Create exit button
        JButton backButton = new JButton("<-- BACK");
        backButton.setPreferredSize(new Dimension(125, 35));
        backButton.setFont(buttonFont); // Set custom font
        backButton.setForeground(Color.BLACK); // Set text color
        backButton.setBorder(new LineBorder(Color.BLACK, 2)); // Set border
        // Add action listener
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
            // Create button for this game
            JButton gameButton = new JButton("JOIN "+gameKey.toUpperCase());
            gameButton.setPreferredSize(new Dimension(125, 35));
            gameButton.setFont(buttonFont); // Set custom font
            gameButton.setForeground(Color.BLACK); // Set text color
            gameButton.setBorder(new LineBorder(Color.BLACK, 2)); // Set border

            // Add action listener
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
        // Use Pattern and Matcher for finding the index
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(selectedGame);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}