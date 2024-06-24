package it.polimi.ingsw.view.gui;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;

/**
 * New class for creating a new window which will be used by client
 * It asks the client which color he wants to use
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class AskColorFrame extends JFrame {
    private final String resourcesPath = "/it/polimi/ingsw/view/resources/";
    private String selectedColor; // Contains client's selected color
    private final Object lock = new Object(); // Lock for getting clint choice
    private final Font customFont = new Font("SansSerif", Font.BOLD, 18); // Used font for graphics

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

        // Create new grid bag constraint for adding the label/button in a pre-fixed position and margin
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(25, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // If repeated is true => Show label which says that previousColor is already used
        if(repeated){
            // Removed used color for visualization
            colors.remove(previousColor);

            // Create label
            JLabel repeatedLabel = new JLabel("Sorry, "+ previousColor+" is already used.");
            repeatedLabel.setHorizontalAlignment(SwingConstants.CENTER);
            repeatedLabel.setFont(customFont);

            // Add label in the transparent panel with grid back constraint
            transparentPanel.add(repeatedLabel, gbc);
        }

        // Create panel for client's input
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setOpaque(false);

        // Add label for asking client is color
        JLabel colorLabel = new JLabel("Select a color:");
        colorLabel.setFont(customFont);

        // Create combo box with available colors
        JComboBox<String> comboBox = new JComboBox<>(colors.toArray(new String[0]));

        // Set custom renderer for display the colors with their color
        comboBox.setRenderer(new ColorComboBoxCellRenderer());

        // Add label and combo box to the panel
        panel.add(colorLabel);
        panel.add(comboBox);

        // Create button for sending input and close this frame
        JButton confirmButton = new JButton("CONFIRM");
        confirmButton.setPreferredSize(new Dimension(200, 40)); // Set preferred size
        confirmButton.setFont(customFont); // Set custom font
        confirmButton.setForeground(Color.BLACK); // Set text color
        confirmButton.setBorder(new LineBorder(Color.BLACK, 2)); // Set border

        // Add listener when client click on "CONFIRM" => Set frame's parameter
        confirmButton.addActionListener(e -> {
            selectedColor = (String) comboBox.getSelectedItem();
            this.dispose(); // Close the window
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        });

        // Add panel and button to the transparent panel with grid back constraint
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
     * selectedColor getter
     *
     * @return client's selected color
     * @author Foini Lorenzo
     */
    public String getSelectedColor() {
        return selectedColor;
    }
}

/**
 * New class for custom renderer for JComboBox
 * Used for adding to the colors' labels their colors
 * It extends DefaultListCellRenderer
 *
 * @author Foini Lorenzo
 */
class ColorComboBoxCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (component instanceof JLabel) {
            JLabel label = (JLabel) component;

            // Set the color based on the value
            switch (value.toString()) {
                case "blue":
                    label.setForeground(Color.BLUE);
                    break;
                case "green":
                    label.setForeground(Color.GREEN);
                    break;
                case "red":
                    label.setForeground(Color.RED);
                    break;
                default: // yellow
                    label.setForeground(Color.YELLOW);
                    break;
            }
        }
        return component;
    }
}