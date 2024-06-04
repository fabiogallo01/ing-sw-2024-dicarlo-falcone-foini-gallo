package it.polimi.ingsw.view.gui;

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
    private final int width = 200;
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
        this.setSize(2000, 400);
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
        // Create new panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,6));

        // GET IMAGES AND ADD THEM IN THE PANEL:
        // For doing so, call to function and pass it the image
        // Resource deck top card
        addImageLabel(mainPanel, gameTable.getResourceDeck().getDeck().getLast().getID(), false);

        // Gold deck top card
        addImageLabel(mainPanel, gameTable.getGoldDeck().getDeck().getLast().getID(), false);

        // First visible card
        addImageLabel(mainPanel, gameTable.getVisibleCard().getFirst().getID(), true);

        // Second visible card
        addImageLabel(mainPanel, gameTable.getVisibleCard().get(1).getID(), true);

        // Third visible card
        addImageLabel(mainPanel, gameTable.getVisibleCard().get(2).getID(), true);

        // Fourth visible card
        addImageLabel(mainPanel, gameTable.getVisibleCard().getLast().getID(), true);


        // CREATE BUTTONS WITH LISTENERS
        // For doing so, call to function and pass it the name of the button and what value to return
        // Resource deck top card
        addButton(mainPanel, "RESOURCE DECK TOP CARD", 1);

        // Gold deck top card
        addButton(mainPanel, "GOLD DECK TOP CARD", 2);

        // First visible card
        addButton(mainPanel, "VISIBLE CARD 1", 3);

        // Second visible card
        addButton(mainPanel, "VISIBLE CARD 2", 4);

        // Third visible card
        addButton(mainPanel, "VISIBLE CARD 3", 5);

        // Fourth visible card
        addButton(mainPanel, "VISIBLE CARD 4", 6);


        return mainPanel;
    }

    /**
     * This method is used for adding in the given JPanel a component with given constraints
     *
     * @param panel: Panel to which to add the component.
     * @param cardId: ID of the card to add
     * @param side: boolean representing the side of the card:
     *              true => front, false => back
     * @author Foini Lorenzo
     */
    private void addImageLabel(JPanel panel, int cardId, boolean side){
        int imageHeight = 125;
        // Get image of the resource deck top card
        JLabel imageLabel = new JLabel(getImageFromID(cardId, side ,width, imageHeight));
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(imageLabel);
    }

    /**
     * This method is used for adding in the given JPanel a component with given constraints
     *
     * @param panel: Panel to which to add the component.
     * @param text: text of the button
     * @param returnedValue: value to assign to variable indexSelectedCard
     * @author Foini Lorenzo
     */
    private void addButton(JPanel panel, String text, int returnedValue){
        int buttonHeight = 25;
        JButton selectButton = new JButton(text);
        selectButton.setPreferredSize(new Dimension(width, buttonHeight));
        selectButton.addActionListener(e -> {
            indexSelectedCard = returnedValue;
            this.dispose(); // Close the window
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        });
        panel.add(selectButton);
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
     * selected card index getter
     *
     * @return client selected secret card index: from 1 to 6
     * @author Foini Lorenzo
     */
    public int getIndexSelectedCard(){
        return indexSelectedCard;
    }
}