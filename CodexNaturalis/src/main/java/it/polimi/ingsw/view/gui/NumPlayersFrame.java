package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * New class for creating a new window which will be used by client
 * It asks the client the number of players for the game
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class NumPlayersFrame extends JFrame {
    private String selectedNumPlayers; // contains the number of players as a string
    private final Object lock = new Object();

    /**
     * NumPlayersFrame constructor, it calls method init() for initialization of the frame
     *
     * @param title window's title
     * @author Foini Lorenzo
     */
    NumPlayersFrame(String title){
        super(title);
        init();
    }

    /**
     * This method is used for initialization of frame
     *
     * @author Foini Lorenzo
     */
    private void init() {
        // Set frame default close operation, size and layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 100);
        this.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create of the panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Add label for combo box
        JLabel label = new JLabel("Number of players:");

        // Create combo box for selecting the number of players
        String[] numPlayers = {"2", "3", "4"};
        JComboBox<String> comboBox = new JComboBox<>(numPlayers);

        // add label and combo box to the panel
        panel.add(label);
        panel.add(comboBox);

        // Create button for sending input and close this frame
        JButton confirmButton = new JButton("CONFIRM");
        // Add listener when client click on "CONFIRM" => Set frame's parameter
        confirmButton.addActionListener(e -> {
            selectedNumPlayers = comboBox.getSelectedItem().toString();
            this.dispose(); // Close the window
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        });

        // Add panel and button in the frame
        this.add(panel, BorderLayout.CENTER);
        this.add(confirmButton, BorderLayout.SOUTH);

        // Set frame's location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Add synchronization on object lock
        synchronized (lock){
            try{
                lock.wait();
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * selectedNumPlayer getter
     *
     * @return client selected number of players as a string
     * @author Foini Lorenzo
     */
    public String getSelectedNumPlayers() {
        return selectedNumPlayers;
    }
}