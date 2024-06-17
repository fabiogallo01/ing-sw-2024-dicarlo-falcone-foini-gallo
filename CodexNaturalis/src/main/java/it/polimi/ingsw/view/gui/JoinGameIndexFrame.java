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
    private final Object lock = new Object();
    private final Font customFont = new Font("SansSerif", Font.BOLD, 18);

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

        // Setting custom image icon from resource
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create background panel and set it
        BackgroundPanel backgroundPanel = new BackgroundPanel("CodexNaturalis\\resources\\Screen.jpg");
        this.setContentPane(backgroundPanel);

        // Create a transparent panel for labels and button
        JPanel transparentPanel = new JPanel(new GridBagLayout());
        transparentPanel.setOpaque(false);

        // Create new grid bag constraint for adding the label/button in a pre-fixed position
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(25, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Create label with question
        JLabel questionsLabel = new JLabel("Which game you want to join?", SwingConstants.CENTER);
        questionsLabel.setFont(customFont);

        // Add label in the transparent panel with grid back constraint
        transparentPanel.add(questionsLabel, gbc);

        // Create new panel: it contains list of games and their players' username
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Iterate through games for getting their clients' username
        for (Map.Entry<String, List<String>> entry : joinGamesAndPlayers.entrySet()) {
            // Create new game label and add into main panel
            JLabel gameLabel = new JLabel(entry.getKey());
            gameLabel.setFont(customFont);
            mainPanel.add(gameLabel);

            // Iterate through players' usernames
            for (String username : entry.getValue()) {
                // Create new player label and add into main panel
                JLabel playerPanel = new JLabel(username);
                playerPanel.setFont(customFont);
                mainPanel.add(playerPanel);
            }
        }

        // Add scroll pane and add it into the frame
        JScrollPane scrollPaneGames = new JScrollPane(mainPanel);

        // Add scroll pane in the transparent panel with grid back constraint
        transparentPanel.add(scrollPaneGames, gbc);

        // Create a panel, it contains the buttons for joining a game or back to the previous window
        JPanel buttonPanel = createButtonPanel(new ArrayList<>(joinGamesAndPlayers.keySet()));

        // Add scroll pane and add it into the frame
        JScrollPane scrollPaneButtons = new JScrollPane(buttonPanel);

        // Add scroll pane in the transparent panel with grid back constraint
        transparentPanel.add(scrollPaneButtons, gbc);

        // Add transparent panel to the frame
        this.add(transparentPanel, BorderLayout.CENTER);

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
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.setFont(customFont); // Set custom font
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
            // Create button
            JButton gameButton = new JButton("JOIN "+gameKey.toUpperCase());
            gameButton.setPreferredSize(new Dimension(200, 50));
            gameButton.setFont(customFont); // Set custom font
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
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(selectedGame);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}