package it.polimi.ingsw.view.gui;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * New class for creating a new window which will be used by client
 * It asks the client which color he wants to use
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class AskColorFrame extends JFrame {
    private String selectedColor; // contains client's selected color
    private final Object lock = new Object();

    /**
     * AskColorFrame constructor, it calls method init() for initialization of the frame
     * In this case it's the first time that this window is shown
     *
     * @param title window's title
     * @param colors: list of game available colors
     * @author Foini Lorenzo
     */
    AskColorFrame(String title, ArrayList<String> colors){
        super(title);
        init(colors, false, "");
    }

    /**
     * AskColorFrame constructor, it calls method init() for initialization of the frame
     * Show a label which says that the previous color is already selected
     *
     * @param title window's title
     * @param colors: list of game available colors
     * @param previousUsername contains client's previous repeated username
     * @author Foini Lorenzo
     */
    AskColorFrame(String title, ArrayList<String> colors, String previousUsername){
        super(title);
        init(colors, true, previousUsername);
    }

    /**
     * This method is used for initialization of frame
     *
     * @param colors: list of game available colors
     * @param repeated: If true => Show a label which says that the previous color was already taken
     *                  If false => Don't show such label
     * @param previousColor contains client's previous selected color
     *                      if is empty => It's the first time that this windows is shown
     * @author Foini Lorenzo
     */
    private void init(ArrayList<String> colors, boolean repeated, String previousColor) {
        // Set frame default close operation, size and layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 200);
        this.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If repeated is true => Show label which says that previousColor is already used
        if(repeated){
            // Removed used color for visualization
            colors.remove(previousColor);

            JLabel repeatedLabel = new JLabel("Sorry, "+ previousColor+" is already used.");
            repeatedLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Add label in the frame
            this.add(repeatedLabel, BorderLayout.NORTH);
        }

        // Create panel for input
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Add label for asking client is color
        JLabel colorLabel = new JLabel("Select a color:");

        // Create combo box with available colors
        JComboBox<String> comboBox = new JComboBox<>(colors.toArray(new String[0]));

        // Add label and combo box to the panel
        panel.add(colorLabel);
        panel.add(comboBox);

        // Create button for sending input and close this frame
        JButton confirmButton = new JButton("CONFIRM");
        // Add listener when client click on "CONFIRM" => Set frame's parameter
        confirmButton.addActionListener(e -> {
            selectedColor = (String) comboBox.getSelectedItem();
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
     * color getter
     *
     * @return client's selected color
     * @author Foini Lorenzo
     */
    public String getSelectedColor() {
        return selectedColor;
    }
}