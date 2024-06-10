package it.polimi.ingsw.view.gui;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * New class for creating a new window which will be used by client
 * It asks the client the side of the starter card to be played on
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class StarterCardFrame extends JFrame {
    private String selectedSide; // Store selected side
    private final Object lock = new Object();

    /**
     * StarterCardFrame constructor, it calls method init() for initialization of the frame
     *
     * @param title window's title
     * @param starterCardID: ID of the starter card to display
     * @author Foini Lorenzo
     */
    StarterCardFrame(String title, int starterCardID){
        super(title);
        init(starterCardID);
    }

    /**
     * This method is used for initialization of frame
     *
     * @param starterCardID: ID of the starter card to display
     * @author Foini Lorenzo
     */
    private void init(int starterCardID) {
        // Set frame default close operation, size and layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create main panel for showing side and get client's input
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2)); // Show card on both sides

        // Create a new label for asking client the side of the starter card
        JLabel sideLabel = new JLabel("On which side do you want to play the starter card?", SwingConstants.CENTER);
        this.add(sideLabel, BorderLayout.NORTH);

        // Displaying front side
        addImageButton(mainPanel, starterCardID, "front");

        // Displaying back side
        addImageButton(mainPanel, starterCardID, "back");

        // Add mainPanel int he frame
        this.add(mainPanel, BorderLayout.CENTER);

        // Set frame's location adn visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Add synchronization on object lock
        synchronized (lock) {
            try {
                lock.wait(); // Wait until user selects a side
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addImageButton(JPanel mainPanel, int starterCardID, String side){
        String starterPath;
        // Get path to the front image of the card
        starterPath = "CodexNaturalis\\resources\\"+side+"\\img_" + starterCardID + ".jpeg";

        try {
            // Get card's image
            BufferedImage cardImage = ImageIO.read(new File(starterPath));
            ImageIcon cardIcon = new ImageIcon(cardImage.getScaledInstance(250, 200, Image.SCALE_SMOOTH));
            JLabel cardLabel = new JLabel(cardIcon);
            // Set alignment
            cardLabel.setHorizontalAlignment(JLabel.CENTER);
            cardLabel.setVerticalAlignment(JLabel.CENTER);

            // Create new button for getting client choice of the side
            // It also closes this window
            JButton confirmButton = new JButton(side.toUpperCase());
            // Add listener when client click on "FRONT" => Set frame's parameter
            confirmButton.addActionListener(e -> {
                selectedSide = side;
                this.dispose(); // Close the window
                synchronized (lock) {
                    lock.notify(); // Notify waiting thread
                }
            });

            // Create new panel for adding image label and button
            JPanel cardPanel = new JPanel(new BorderLayout());
            cardPanel.add(cardLabel, BorderLayout.CENTER);
            cardPanel.add(confirmButton, BorderLayout.SOUTH);

            mainPanel.add(cardPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * selectedSide getter
     *
     * @return client's selected side of the starter card
     * @author Foini Lorenzo
     */
    public String getSelectedSide() {
        return selectedSide;
    }
}