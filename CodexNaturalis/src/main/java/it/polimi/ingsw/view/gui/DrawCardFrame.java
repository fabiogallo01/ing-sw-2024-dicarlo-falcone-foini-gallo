package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.GamingCard;
import it.polimi.ingsw.model.game.GameTable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * New class for creating a new window which will be used by client
 * It represents the "draw phase" of the game.
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class DrawCardFrame extends JFrame {
    private final String resourcesPath = "CodexNaturalis\\src\\main\\java\\it\\polimi\\ingsw\\view\\resources\\";
    private final Object lock = new Object(); // Lock for getting clint choice
    private final GameTable gameTable; // It represents the gameTable of the match
    private int indexSelectedCard;
    private final boolean enableButton;
    private final Font labelFont = new Font("SansSerif", Font.BOLD, 18); // Used font for labels
    private final Font buttonFont = new Font("SansSerif", Font.BOLD, 12); // Used font for buttons

    /**
     * DrawCardFrame constructor, it calls method init() for initialization of frame
     *
     * @param title window's title
     * @param gameTable game table
     * @param enableButton: If true => The buttons for drawing a card are present
     *                      If false => The button for drawing a card are not present
     * @author Foini Lorenzo
     */
    DrawCardFrame(String title, GameTable gameTable, boolean enableButton){
        super(title);
        this.gameTable = gameTable;
        this.enableButton = enableButton;
        init();
    }

    /**
     * This method is used for initialization of frame
     *
     * @author Foini Lorenzo
     */
    private void init(){
        // Set frame parameters
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500, 500);
        this.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File(resourcesPath+"Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Create background panel and set it
        BackgroundPanel backgroundPanel = new BackgroundPanel(resourcesPath+"Screen.jpg");
        this.setContentPane(backgroundPanel);

        // Create a transparent panel for labels and button
        JPanel transparentPanel = new JPanel(new GridBagLayout());
        transparentPanel.setOpaque(false);

        // Create new grid bag constraint for adding the label/button in a pre-fixed position and with margin
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(50, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Create label with question and add it in the frame
        JLabel informationLabel;
        if(enableButton) informationLabel = new JLabel("Select which card you want to draw");
        else informationLabel = new JLabel("These cards represent: top card of resource and gold deck, visible cards in table");

        informationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        informationLabel.setFont(labelFont);

        // Add label to the transparent panel with grid back constraint
        transparentPanel.add(informationLabel, gbc);

        // Call to function for create main panel
        JPanel mainPanel = createMainPanel();

        // Add main panel to the transparent panel with grid back constraint
        transparentPanel.add(mainPanel, gbc);

        // exit button if enableButton is false
        if(!enableButton){
            JButton exitButton = new JButton("CLICK HERE TO EXIT FROM THIS WINDOW!");
            exitButton.setPreferredSize(new Dimension(350, 50));
            exitButton.setFont(buttonFont); // Set custom font
            exitButton.setForeground(Color.BLACK); // Set text color
            exitButton.setBorder(new LineBorder(Color.BLACK, 2)); // Set border

            // Add action listener for close this window
            exitButton.addActionListener(e -> {
                this.dispose(); // Close the window
            });

            // Add main panel to the transparent panel with grid back constraint
            transparentPanel.add(exitButton, gbc);
        }

        // Add transparent panel to the frame
        this.add(transparentPanel, BorderLayout.CENTER);

        // Set frame's location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // If it's player's turn, then wait until he selects a card to draw
        if(enableButton){
            synchronized (lock) {
                try {
                    lock.wait(); // Wait selection
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    /**
     * This method is used for create the main panel
     * It contains the labels, images and buttons (if enableButton is true)
     *
     * @return such JPanel
     * @author Foini Lorenzo
     */
    private JPanel createMainPanel(){
        // Create new panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);

        // Create new grid back constraint
        GridBagConstraints gbc = new GridBagConstraints();
        // Setting common constraints
        gbc.insets = new Insets(5, 5, 5, 5); // Add space around components
        gbc.ipadx = 5; // Add internal padding horizontally
        gbc.ipady = 5; // Add internal padding vertically

        // Index of the column for adding the component in such column
        int indexColumn = 0;

        // GET IMAGES AND BUTTONS, if they exist
        // THEN ADD THEM IN THE PANEL
        // For doing so, call to function and pass it the image

        // Resource deck top card
        if(!gameTable.getResourceDeck().getDeck().isEmpty()){
            // Resource deck is not empty => Add image and button
            addImageLabel(mainPanel, gameTable.getResourceDeck().getDeck().getLast().getID(), false, gbc, indexColumn);
            if(enableButton){
                addButton(mainPanel, "RESOURCE DECK TOP CARD", 1, gbc, indexColumn);
            }
            indexColumn++;
        }

        // Gold deck top card
        if(!gameTable.getGoldDeck().getDeck().isEmpty()){
            // Gold deck is not empty => Add image and button
            addImageLabel(mainPanel, gameTable.getGoldDeck().getDeck().getLast().getID(),false, gbc, indexColumn);
            if(enableButton){
                addButton(mainPanel, "GOLD DECK TOP CARD", 2, gbc, indexColumn);
            }
            indexColumn++;
        }

        // For loop for visible cards
        int indexCard = 1; // Variable representing the number of the card
        int indexVisible = 3; // Variable representing the returned value
        for(GamingCard card: gameTable.getVisibleCard()){
            // Add image and button for this card
            addImageLabel(mainPanel, card.getID(), true, gbc, indexColumn);
            if(enableButton){
                addButton(mainPanel, "VISIBLE CARD "+indexCard, indexVisible, gbc, indexColumn);
            }
            indexColumn++;
            indexCard++;
            indexVisible++;
        }

        return mainPanel;
    }

    /**
     * This method is used for adding in the given JPanel a component with given constraints
     *
     * @param panel: Panel to which to add the component.
     * @param cardId: ID of the card to add
     * @param side: Side of the card to add
     * @param gbc: grid bag constraint
     * @param column: Insert image in such column of the grid bag
     * @author Foini Lorenzo
     */
    private void addImageLabel(JPanel panel, int cardId, boolean side, GridBagConstraints gbc, int column){
        int imageHeight = 100;
        // Get image of the resource deck top card
        JLabel imageLabel = new JLabel(getImageFromID(cardId, side, imageHeight));
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // Add image in the panel in first row
        // This image will occupy 50% of the panel's height
        addComponent(panel, imageLabel, gbc, 0, column);
    }

    /**
     * This method is used for adding in the given JPanel a button with given constraints
     *
     * @param panel: Panel to which to add the component.
     * @param text: text of the button
     * @param returnedValue: value to assign to variable indexSelectedCard
     * @param gbc: grid bag constraint
     * @param column: Insert image in such column of the grid bag
     * @author Foini Lorenzo
     */
    private void addButton(JPanel panel, String text, int returnedValue, GridBagConstraints gbc, int column){
        // Set button height
        int buttonHeight = 50;

        // Create new button
        JButton selectButton = new JButton(text);
        selectButton.setPreferredSize(new Dimension(200, buttonHeight));
        selectButton.setFont(buttonFont); // Set custom font
        selectButton.setForeground(Color.BLACK); // Set text color
        selectButton.setBorder(new LineBorder(Color.BLACK, 2)); // Set border

        // Add action listener
        selectButton.addActionListener(e -> {
            indexSelectedCard = returnedValue;
            this.dispose(); // Close the window
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        });

        // Add button in the panel in second row
        // This button will occupy 50% of the panel's height
        addComponent(panel, selectButton, gbc, 1, column);
    }

    /**
     * This method is used for return the image (which a fixed size) of a card given its ID
     *
     * @param cardID: card's id.
     * @param side: card's side.
     * @param height: final card's height.
     * @return the image as a ImageIcon
     * @author Foini Lorenzo
     */
    private ImageIcon getImageFromID(int cardID, boolean side, int height){
        BufferedImage originalImage; // Original image with its size
        Image scaledImage; // Same image as before but with parameters size
        ImageIcon imageIcon; // ImageIcon to return

        String stringSide;
        if(side) stringSide="front"; // side: true => front
        else stringSide="back"; // side: false => back

        // Get image from resources
        String path = resourcesPath+stringSide+"\\img_"+cardID+".jpeg";
        try {
            originalImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Re-dimensioning the image
        scaledImage = originalImage.getScaledInstance(200, height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        return imageIcon;
    }

    /**
     * This method is used for adding in the given JPanel a component with given constraints
     *
     * @param panel: Panel to which to add the component.
     * @param comp: The component to add to the panel.
     * @param gbc: Layout specifications for positioning and sizing the component.
     * @param row: Row to place the component in the layout.
     * @param col: Column to place the component in the layout.
     * @author Falcone Giacomo
     */
    private void addComponent(JPanel panel, Component comp, GridBagConstraints gbc, int row, int col) {
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = 1; // Horizontal cells the component should occupy in the layout.
        gbc.gridheight = 1; // Vertical cells the component should occupy in the layout.
        gbc.weightx = 1; // Horizontal expansion priority of the component in the layout.
        gbc.weighty = 0.5; // Vertical expansion priority of the component in the layout.
        panel.add(comp, gbc);
    }

    /**
     * indexSelectedCard index getter
     *
     * @return client selected secret card index: from 1 to 6
     * @author Foini Lorenzo
     */
    public int getIndexSelectedCard(){
        return indexSelectedCard;
    }
}