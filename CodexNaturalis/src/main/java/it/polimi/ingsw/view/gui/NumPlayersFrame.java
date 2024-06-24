package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
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
    private final String resourcesPath = "/it/polimi/ingsw/view/resources/";
    private String selectedNumPlayers; // Contains the number of players as a string
    private final Object lock = new Object(); // Lock for getting clint choice
    private final Font customFont = new Font("SansSerif", Font.BOLD, 18); // Used font

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
        this.setSize(550, 300);
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

        // Create of the panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setOpaque(false);

        // Add label for combo box
        JLabel label = new JLabel("Number of players:");
        label.setFont(customFont);

        // Create combo box for selecting the number of players
        String[] numPlayers = {"2", "3", "4"};
        JComboBox<String> comboBox = new JComboBox<>(numPlayers);

        // add label and combo box to the panel
        panel.add(label);
        panel.add(comboBox);

        // Create button for sending input and close this frame
        JButton confirmButton = new JButton("CONFIRM");
        confirmButton.setPreferredSize(new Dimension(200, 40));
        confirmButton.setFont(customFont); // Set custom font
        confirmButton.setForeground(Color.BLACK); // Set text color
        confirmButton.setBorder(new LineBorder(Color.BLACK, 2)); // Set border

        // Add listener when client click on "CONFIRM" => Set frame's parameter
        confirmButton.addActionListener(e -> {
            selectedNumPlayers = comboBox.getSelectedItem().toString();
            this.dispose(); // Close the window
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        });

        // Add panel and buttons in the transparent panel with grid back constraint
        transparentPanel.add(panel, gbc);
        transparentPanel.add(confirmButton, gbc);

        // Add transparent panel to the frame
        this.add(transparentPanel, BorderLayout.CENTER);

        // Set frame's location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Add synchronization on object lock
        synchronized (lock){
            try{
                lock.wait(); // Wait selection
            } catch (InterruptedException ex){
                System.out.println(ex.getMessage());
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