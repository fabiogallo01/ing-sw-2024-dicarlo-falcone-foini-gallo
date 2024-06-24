package it.polimi.ingsw.view.gui;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;

/**
 * New class for creating a new window which will be used by client
 * It asks the client the side of the starter card to be played on
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class StarterCardFrame extends JFrame {
    private final String resourcesPath = "/it/polimi/ingsw/view/resources/";
    private String selectedSide; // Store selected side
    private final Object lock = new Object(); // Lock for getting clint choice
    private final Font customFont = new Font("SansSerif", Font.BOLD, 18); // Used font

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

        // Setting custom image icon from resource
        // Get logo image from URL
        java.net.URL logoImageUrl = getClass().getResource(resourcesPath+"Logo.png");
        if (logoImageUrl != null) {
            // An image is found, so try to set it
            try {
                Image icon = ImageIO.read(logoImageUrl);
                this.setIconImage(icon);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Didn't find such image");
        }

        // Create background panel and set it
        // Get screen image from URL
        java.net.URL screenImageUrl = getClass().getResource(resourcesPath+"Screen.jpg");
        if (logoImageUrl != null) {
            // An image is found, so try to set it as background
            BackgroundPanel backgroundPanel = new BackgroundPanel(screenImageUrl);
            this.setContentPane(backgroundPanel);
        } else {
            System.out.println("Didn't find such image");
        }

        // Create a transparent panel for labels and button
        JPanel transparentPanel = new JPanel(new GridBagLayout());
        transparentPanel.setOpaque(false);

        // Create new grid bag constraint for adding the label/button in a pre-fixed position and with margin
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(25, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Create main panel for showing side and get client's input
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 20, 0)); // Show card on both sides
        mainPanel.setOpaque(false);

        // Create a new label for asking client the side of the starter card
        JLabel sideLabel = new JLabel("On which side do you want to play the starter card?", SwingConstants.CENTER);
        sideLabel.setFont(customFont);

        // Add label in the transparent panel with grid back constraint
        transparentPanel.add(sideLabel, gbc);

        // Displaying front side
        addImageButton(mainPanel, starterCardID, "front");

        // Displaying back side
        addImageButton(mainPanel, starterCardID, "back");

        // Add mainPanel in the transparent panel with grid back constraint
        transparentPanel.add(mainPanel, gbc);

        // Add transparent panel in the frame
        this.add(transparentPanel, BorderLayout.CENTER);

        // Set frame's location adn visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Add synchronization on object lock
        synchronized (lock) {
            try {
                lock.wait(); // Wait selection
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * This method is used for adding images of the starter card and the two buttons for selection
     *
     * @param mainPanel: panel where to add the labels/buttons
     * @param starterCardID: ID of the starter card
     * @param side: true => Front
     *              false => Back
     * @author Foini Lorenzo
     */
    public void addImageButton(JPanel mainPanel, int starterCardID, String side){
        // Get image from resources
        java.net.URL imageUrl = getClass().getResource(resourcesPath+side+"/img_" + starterCardID + ".jpeg");

        if (imageUrl != null) {
            // An image is found, so try to set it
            try {
                // Get card's image
                BufferedImage cardImage = ImageIO.read(imageUrl);
                ImageIcon cardIcon = new ImageIcon(cardImage.getScaledInstance(200, 150, Image.SCALE_SMOOTH));
                JLabel cardLabel = new JLabel(cardIcon);
                // Set alignment
                cardLabel.setHorizontalAlignment(JLabel.CENTER);
                cardLabel.setVerticalAlignment(JLabel.CENTER);
                cardLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

                // Create new button for getting client choice of the side
                // It also closes this window
                JButton confirmButton = new JButton(side.toUpperCase());
                confirmButton.setPreferredSize(new Dimension(200, 40)); // Set preferred size
                confirmButton.setFont(customFont); // Set custom font
                confirmButton.setForeground(Color.BLACK); // Set text color
                confirmButton.setBorder(new LineBorder(Color.BLACK, 2)); // Set border

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
                cardPanel.setOpaque(false);

                // Add label and button to cardPanel
                cardPanel.add(cardLabel, BorderLayout.CENTER);
                cardPanel.add(confirmButton, BorderLayout.SOUTH);

                // Add cardPanel in mainPanel
                mainPanel.add(cardPanel);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Didn't find such image");
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