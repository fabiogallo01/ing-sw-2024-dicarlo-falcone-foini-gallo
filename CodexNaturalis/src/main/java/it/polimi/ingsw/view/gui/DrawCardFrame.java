package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.GamingCard;
import it.polimi.ingsw.model.game.GameTable;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    private final Object lock = new Object();
    private final int width = 225;
    private final GameTable gameTable; // It represents the gameTable of the match
    private int indexSelectedCard;

    /**
     * DrawCardFrame constructor, it calls method init() for initialization of frame
     *
     * @param title window's title
     * @param gameTable game table
     * @author Foini Lorenzo
     */
    DrawCardFrame(String title, GameTable gameTable){
        super(title);
        this.gameTable = gameTable;
        init();
    }

    /**
     * This method is used for initialization of frame
     *
     * @author Foini Lorenzo
     */
    private void init(){
        // Set frame parameters
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Use DISPOSE_ON_CLOSE to close only this window
        this.setSize(1500, 300);
        this.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create label with question and add it in the frame
        JLabel label = new JLabel("Select which card you want to draw");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label, BorderLayout.NORTH);

        // Call to function for create main panel
        JPanel mainPanel = createMainPanel();
        this.add(mainPanel, BorderLayout.CENTER);

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

    private JPanel createMainPanel(){
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        int indexColumn = 0;

        // GET IMAGES AND BUTTONS, if they exist
        // THEN ADD THEM IN THE PANEL
        // For doing so, call to function and pass it the image

        // Resource deck top card
        if(!gameTable.getResourceDeck().getDeck().isEmpty()){
            // Resource deck is not empty => Add image and button
            addImageLabel(mainPanel, gameTable.getResourceDeck().getDeck().getLast().getID(), false, gbc, indexColumn);
            addButton(mainPanel, "RESOURCE DECK TOP CARD", 1, gbc, indexColumn);
            indexColumn++;
        }

        // Gold deck top card
        if(!gameTable.getGoldDeck().getDeck().isEmpty()){
            // Gold deck is not empty => Add image and button
            addImageLabel(mainPanel, gameTable.getGoldDeck().getDeck().getLast().getID(),false, gbc, indexColumn);
            addButton(mainPanel, "GOLD DECK TOP CARD", 2, gbc, indexColumn);
            indexColumn++;
        }

        // For loop for visible cards
        int indexCard = 1; // Variable representing the number of the card
        int indexVisible = 3; // Variable representing the returned value
        for(GamingCard card: gameTable.getVisibleCard()){
            // Add image and button for this card
            addImageLabel(mainPanel, card.getID(), true, gbc, indexColumn);
            addButton(mainPanel, "VISIBLE CARD "+indexCard, indexVisible, gbc, indexColumn);
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
        int imageHeight = 120;
        // Get image of the resource deck top card
        JLabel imageLabel = new JLabel(getImageFromID(cardId, side, width, imageHeight));
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // Add image in the panel in first row
        // This image will occupy 50% of the panel's height
        addComponent(panel, imageLabel, gbc, 0, column, 1, 1, 1, 0.5);
    }

    /**
     * This method is used for adding in the given JPanel a component with given constraints
     *
     * @param panel: Panel to which to add the component.
     * @param text: text of the button
     * @param returnedValue: value to assign to variable indexSelectedCard
     * @param gbc: grid bag constraint
     * @param column: Insert image in such column of the grid bag
     * @author Foini Lorenzo
     */
    private void addButton(JPanel panel, String text, int returnedValue, GridBagConstraints gbc, int column){
        int buttonHeight = 150;
        JButton selectButton = new JButton(text);
        selectButton.setPreferredSize(new Dimension(width, buttonHeight));
        selectButton.addActionListener(e -> {
            indexSelectedCard = returnedValue;
            this.dispose(); // Close the window
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        });

        // Add button in the panel in second row
        // This button will occupy 50% of the panel's height
        addComponent(panel, selectButton, gbc, 1, column, 1, 1, 1, 0.5);
    }

    /**
     * This method is used for return the image (which a fixed size) of a card given its ID
     *
     * @param cardID: card's id.
     * @param side: card's side.
     * @param width: final card's width.
     * @param height: final card's height.
     * @return the image as a ImageIcon
     * @author Foini Lorenzo
     */
    private ImageIcon getImageFromID(int cardID, boolean side, int width, int height){
        BufferedImage originalImage; // Original image with its size
        Image scaledImage; // Same image as before but with parameters size
        ImageIcon imageIcon; // ImageIcon to return

        String stringSide;
        if(side) stringSide="front"; // side: true => front
        else stringSide="back"; // side: false => back

        String path = "CodexNaturalis\\resources\\"+stringSide+"\\img_"+cardID+".jpeg";
        try {
            originalImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Re-dimensioning the image
        scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
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
     * @param width: Horizontal cells the component should occupy in the layout.
     * @param height: Vertical cells the component should occupy in the layout.
     * @param weightx: Horizontal expansion priority of the component in the layout.
     * @param weighty: Vertical expansion priority of the component in the layout.
     * @author Falcone Giacomo
     */
    private void addComponent(JPanel panel, Component comp, GridBagConstraints gbc,
                              int row, int col, int width, int height, double weightx, double weighty) {
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        panel.add(comp, gbc);
    }

    /**
     * selected card index getter
     *
     * @return client selected secret card index: from 1 to 6
     * @author Foini Lorenzo
     */
    public int getIndexSelectedCard(){
        return indexSelectedCard;
    }
}