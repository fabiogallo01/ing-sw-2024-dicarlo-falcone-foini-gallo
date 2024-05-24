package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.game.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * New class for creating a new window which will be used by client
 * It asks the client if he wants to create or join a game
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class JoinGameIndexFrame extends JFrame {
    private int selectedIndex; // contains the selected index of game to join
    private final Object lock = new Object();

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
        // Assign some parameters
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 400);
        this.setLayout(new BorderLayout());

        // Create label with question
        JLabel questionsLabel = new JLabel("Which game you want to join?", SwingConstants.CENTER);
        this.add(questionsLabel, BorderLayout.NORTH);

        // Create new panel
        // It contains list of games and their players' username
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Iterate through controllers for getting games and players
        ArrayList<Integer> gameNotFullIndex = new ArrayList<>(); // Contains index of game that can be joined
        int i = 1;
        for(Controller controller : controllers){
            // Check if the gameTable is not full => If so, then client can join such game
            if(!controller.getGameTable().isFull()){
                // Add such index in the list of game that can be joined
                gameNotFullIndex.add(i);

                // Create new game label and add into main panel
                JLabel gameLabel = new JLabel("Game "+i+":");
                mainPanel.add(gameLabel);

                // Add players
                for(Player player: controller.getGameTable().getPlayers()){
                    // Create new player label and add into main panel
                    JLabel playerPanel = new JLabel(" - " + player.getUsername());
                    mainPanel.add(playerPanel);
                }
            }
            i++;
        }
        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        this.add(scrollPane, BorderLayout.CENTER);

        // Create a panel, it contains the two buttons
        JPanel buttonPanel = createButtonPanel(gameNotFullIndex);

        // Add button panel in the frame
        this.add(buttonPanel, BorderLayout.SOUTH);

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
     * @param gameNotFullIndex: list of indexes of games that can be joined
     * @return JPanel with buttons
     * @author Foini Lorenzo
     */
    public JPanel createButtonPanel(ArrayList<Integer> gameNotFullIndex){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Iterate through available indexes
        for(int gameIndex: gameNotFullIndex){
            // Create button
            JButton gameButton = new JButton("JOIN GAME "+gameIndex);
            //gameButton.setPreferredSize(new Dimension(100, 50));
            gameButton.addActionListener(e -> {
                selectedIndex = gameIndex;
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
     * choice getter
     *
     * @return client selected index of the game to join
     * @author Foini Lorenzo
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }
}