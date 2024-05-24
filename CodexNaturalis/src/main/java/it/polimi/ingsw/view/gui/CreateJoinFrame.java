package it.polimi.ingsw.view.gui;

import javax.swing.*;
import java.awt.*;

/**
 * New class for creating a new window which will be used by client
 * It asks the client if he wants to create or join a game
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class CreateJoinFrame extends JFrame {
    private String choice; // "create" or "join"
    private final Object lock = new Object();

    /**
     * CreateJoinFrame constructor, it calls method init() for initialization of the frame
     *
     * @param title window's title
     * @param countNotFullGame: how many games can be joined
     * @author Foini Lorenzo
     */
    CreateJoinFrame(String title, int countNotFullGame){
        super(title);
        init(countNotFullGame);
    }

    /**
     * This method is used for initialization of frame
     *
     * @param countNotFullGame: how many games can be joined
     * @author Foini Lorenzo
     */
    private void init(int countNotFullGame){
        // Set parameters
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Use DISPOSE_ON_CLOSE to close only this window
        this.setSize(500, 150);
        this.setLayout(new BorderLayout());

        // Create label with the question
        JLabel instructionLabel = new JLabel("Do you want to create a new game or join a game?", SwingConstants.CENTER);
        this.add(instructionLabel, BorderLayout.NORTH);

        // Create a panel, it contains the two buttons
        JPanel buttonPanel = createButtonPanel(countNotFullGame);

        // Add button panel in the frame
        this.add(buttonPanel, BorderLayout.CENTER);

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
     * This method is used for create a new panel with two buttons
     *
     * @param countNotFullGame: how many games can be joined
     * @author Foini Lorenzo
     */
    private JPanel createButtonPanel(int countNotFullGame){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // First button: create new game
        JButton createButton = new JButton("CREATE NEW GAME");
        createButton.setPreferredSize(new Dimension(200, 50));
        createButton.addActionListener(e -> {
            choice = "create";
            this.dispose(); // Close the window
        });

        // Second button: join a game
        JButton joinButton = new JButton("JOIN A GAME");
        joinButton.setPreferredSize(new Dimension(200, 50));
        joinButton.addActionListener(e -> {
            choice = "join";
            this.dispose(); // Close the window
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        });

        // Need to check if there are game that can be joined or not
        // If not, then this button will disable
        if(countNotFullGame == 0){
            joinButton.setText("NO GAME TO JOIN"); // Change text
            joinButton.setEnabled(false); // Disable button
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        }

        // Add buttons in the button panel
        buttonPanel.add(createButton);
        buttonPanel.add(joinButton);

        return buttonPanel;
    }

    /**
     * choice getter
     *
     * @return client choice: "create" or "join"
     * @author Foini Lorenzo
     */
    public String getChoice() {
        return choice;
    }
}