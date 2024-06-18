package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * New class for creating a new window which will be used by client
 * It displays starter card on selected side, hand, common objectives, secret objectives and asks client to choose
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class SecretObjectiveFrame extends JFrame{
    private String selectedSecretCard; // Index of the selected secret objective card as string: "1" or "2"
    private final Object lock = new Object();
    private Font customFont = new Font("SansSerif", Font.BOLD, 15); // // Create a custom Font
    private final int width = 180;
    private final int imageHeight = 100;
    private final int buttonHeight = 35;

    /**
     * SecretObjectiveFrame constructor, it calls method init() for initialization of the frame
     *
     * @param title window's title
     * @param starterCardSide side on which the starter card have been played
     * @param starterCardID ID used to get the file path of the starter cards
     * @param handCardIDs array of IDs used to get the file path of the hand's cards
     * @param commonObjectiveCardIDs array of IDs used to get the file path of the common objective cards
     * @param secretObjectiveCardIDs array of IDs used to get the file path of the secret objective cards
     * @author Foini Lorenzo
     */
    SecretObjectiveFrame(String title, String starterCardSide , int starterCardID, int[] handCardIDs, int[] commonObjectiveCardIDs, int[] secretObjectiveCardIDs) {
        super(title);
        init(starterCardSide, starterCardID, handCardIDs, commonObjectiveCardIDs, secretObjectiveCardIDs);
    }

    /**
     * This method is used for initialization of frame
     *
     * @param starterCardSide side on which the starter card have been played
     * @param starterCardID ID used to get the file path of the starter cards
     * @param handCardIDs array of IDs used to get the file path of the hand's cards
     * @param commonObjectiveCardIDs array of IDs used to get the file path of the common objective cards
     * @param secretObjectiveCardIDs array of IDs used to get the file path of the secret objective cards
     * @author Foini Lorenzo
     */
    private void init(String starterCardSide, int starterCardID, int[] handCardIDs, int[] commonObjectiveCardIDs, int[] secretObjectiveCardIDs){
        // Set frame parameters
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Setting custom image icon
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
        gbc.insets = new Insets(30, 25, 30, 25);
        gbc.anchor = GridBagConstraints.CENTER;

        // Create new panel for displaying starter card
        JPanel starterCardPanel = createStarterPanel(starterCardSide, starterCardID);
        transparentPanel.add(starterCardPanel, gbc);

        // Reset gbc insect
        gbc.insets = new Insets(0, 25, 30, 25);

        // Create new panel for displaying hand's cards
        JPanel handPanel = createHandCommonPanel("This is your hand", handCardIDs);
        transparentPanel.add(handPanel, gbc);

        // Create new panel for displaying common objective cards
        JPanel commonObjectivesPanel = createHandCommonPanel("These are the two common objective cards", commonObjectiveCardIDs);
        transparentPanel.add(commonObjectivesPanel, gbc);

        // Create new panel for display secret objective cards
        JPanel secretObjectivesPanel = createSecretObjectivePanel(secretObjectiveCardIDs);
        transparentPanel.add(secretObjectivesPanel, gbc);

        this.add(transparentPanel, BorderLayout.CENTER);

        this.pack();
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
     * This method is used for create a new panel with images of the starter card
     *
     * @param side: side on which the starter card have been played
     * @param starterCardID: ID of the starter card
     * @return JPanel with label and images
     * @author Foini Lorenzo
     */
    private JPanel createStarterPanel(String side, int starterCardID){
        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

        // Create label
        JLabel label = new JLabel("This is your starter card", SwingConstants.CENTER);
        label.setFont(customFont);
        panel.add(label, BorderLayout.NORTH);

        // Create label for image of the starter card played on the selected side
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(getImage(starterCardID, side));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add images panel in main panel
        panel.add(imageLabel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * This method is used for create a new panel with images of the hand/common objective cards
     *
     * @param labelText: label text
     * @param cardsIDs: IDs of the cards
     * @return JPanel with label and images
     * @author Foini Lorenzo
     */
    private JPanel createHandCommonPanel(String labelText, int[] cardsIDs){
        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

        // Create label
        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setFont(customFont);
        panel.add(label, BorderLayout.NORTH);

        // Create panel for images
        JPanel imagesPanel = new JPanel();
        imagesPanel.setLayout(new FlowLayout());
        imagesPanel.setOpaque(false);

        // Iterate through IDs
        for (int cardID : cardsIDs) {
            JLabel imageLabel = new JLabel();
            imageLabel.setIcon(getImage(cardID, "front"));
            imagesPanel.add(imageLabel);
        }

        // Add images label
        panel.add(imagesPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * This method is used for create a new panel with images of the two secret objective cards
     *
     * @param secretObjectiveCardIDs: ID of the secret objective cards
     * @return JPanel with label and images
     * @author Foini Lorenzo
     */
    private JPanel createSecretObjectivePanel(int[] secretObjectiveCardIDs){
        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

        // Create label with question
        JLabel instructionLabel = new JLabel("Choose which secret objective card you want to use", SwingConstants.CENTER);
        instructionLabel.setFont(customFont);
        panel.add(instructionLabel, BorderLayout.NORTH);

        // Create panel for images
        JPanel imagesPanel = new JPanel();
        imagesPanel.setLayout(new FlowLayout());
        imagesPanel.setOpaque(false);

        // Create panel for buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setOpaque(false);

        // Iterate through IDs
        for (int i = 0; i < secretObjectiveCardIDs.length; i++) {
            JLabel imageLabel = new JLabel();
            imageLabel.setIcon(getImage(secretObjectiveCardIDs[i], "front"));
            imagesPanel.add(imageLabel);

            int index = i+1; // i start from 0, so add 1
            JButton selectButton = new JButton("SELECT");
            selectButton.setPreferredSize(new Dimension(width, buttonHeight)); // Set preferred size
            selectButton.setFont(customFont); // Set custom font
            selectButton.setForeground(Color.BLACK); // Set text color
            selectButton.setBorder(new LineBorder(Color.BLACK, 2)); // Set border

            // Add listener when client click on "CONFIRM" => Set frame's parameter
            selectButton.addActionListener(e -> {
                selectedSecretCard = String.valueOf(index);
                this.dispose(); // Close the window
                synchronized (lock) {
                    lock.notify(); // Notify waiting thread
                }
            });
            buttonsPanel.add(selectButton);
        }

        // Add images and buttons panels
        panel.add(imagesPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * This method is used for create a resized image icon given an ID and side
     *
     * @param id: ID of the card
     * @param side: side of the card to display
     * @return ImageIcon resized
     * @author Foini Lorenzo
     */
    private ImageIcon getImage(int id, String side) {

        String path = "CodexNaturalis\\resources\\"+side+"\\img_" + id + ".jpeg";
        try {
            BufferedImage cardImage = ImageIO.read(new File(path));
            Image scaledImage = cardImage.getScaledInstance(width, imageHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * selected secret card index getter
     *
     * @return client selected secret card index: "1" or "2"
     * @author Foini Lorenzo
     */
    public String getSelectedSecretCard(){
        return selectedSecretCard;
    }
}