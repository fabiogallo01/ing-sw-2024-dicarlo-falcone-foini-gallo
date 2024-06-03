package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.game.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * New class for creating a new window which will be used by client
 * It represents the after login window.
 * It extends JFrame.
 *
 * @author Falcone Giacomo, Foini Lorenzo
 */
public class GameFrame extends JFrame {
    final int NUM_ROWS = 81;
    final int NUM_COLS = 81;
    Player clientPlayer; // It represents the clients
    GameTable gameTable; // It represents the gameTable of the match
    String indexCardToPlay = "";
    boolean enableButtonsArea = false;
    ArrayList<JButton> gridButtons = new ArrayList<>();
    boolean enableHandCardsButtons = false;
    ArrayList<JButton> handCardsButtons = new ArrayList<>();
    boolean enableDrawCardsButtons = false;
    ArrayList<JButton> drawCardsButtons = new ArrayList<>();

    /**
     * GameFrame constructor, it calls method init() for initialization of frame
     *
     * @param title window's title
     * @author Foini Lorenzo
     */
    GameFrame(String title, Player player, GameTable gameTable){
        super(title);
        this.clientPlayer = player;
        this.gameTable = gameTable;
        init();
    }

    /**
     * This method is used for initialization of frame
     *
     * @author Foini Lorenzo
     */
    void init(){
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // MAX dimension
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Call to function createPanelNorth() for creating new panel to be addend in the content pane
        JPanel panelNorth = createPanelNorth();

        // Call to function createPanelWest() for creating new panel to be addend in the content pane
        JPanel panelWest = createPanelWest();

        // Call to function createPanelCenter() for creating new scroll panel to be addend in the content pane
        JScrollPane scrollPaneCenter = createPanelCenter();

        // Call to function createPanelEast() for creating new panel to be addend in the content pane
        JPanel panelEast = createPanelEast();

        // Call to function createPanelSouth() for creating new panel to be addend in the content pane
        JPanel panelSouth = createPanelSouth();

        // Get content pane
        Container contentPane = this.getContentPane();

        // Add JPanels and scroll pane in the content pane
        contentPane.add(panelNorth,BorderLayout.NORTH);
        contentPane.add(panelWest,BorderLayout.WEST);
        contentPane.add(scrollPaneCenter,BorderLayout.CENTER);
        contentPane.add(panelEast,BorderLayout.EAST);
        contentPane.add(panelSouth,BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * This method is used for creating the north panel
     * The north panel contains the two common objectives and the secret one
     *
     * @return the panel that will be added in the content pane
     * @author Falcone Giacomo, Foini Lorenzo
     */
    public JPanel createPanelNorth(){
        // Create new panel and use a GridBagLayout, so we can choose the size of the cells
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Create labels for objectives
        JLabel commonObjective1 = new JLabel("Common objective n.1");
        JLabel commonObjective2 = new JLabel("Common objective n.2");
        JLabel secretObjective1 = new JLabel("Secret objective");

        // Set horizontal and vertical alignment to center
        commonObjective1.setHorizontalAlignment(JLabel.CENTER);
        commonObjective1.setVerticalAlignment(JLabel.CENTER);
        commonObjective2.setHorizontalAlignment(JLabel.CENTER);
        commonObjective2.setVerticalAlignment(JLabel.CENTER);
        secretObjective1.setHorizontalAlignment(JLabel.CENTER);
        secretObjective1.setVerticalAlignment(JLabel.CENTER);

        // Add labels in the panel in first row
        // These labels will occupy 30% of the panel
        addComponent(panel, commonObjective1, gbc, 0, 0, 1, 1, 1, 0.3);
        addComponent(panel, commonObjective2, gbc, 0, 1, 1, 1, 1, 0.3);
        addComponent(panel, secretObjective1, gbc, 0, 2, 1, 1, 1, 0.3);

        // Get images of the three objective cards
        // For doing so we call method getImageFromID with teh cards' IDs, width and height
        ObjectiveCard[] commonObjective = gameTable.getCommonObjectives();
        JLabel imageCommonObjective1 = new JLabel(getImageFromID(commonObjective[0].getID(), true,200, 80));
        JLabel imageCommonObjective2 = new JLabel(getImageFromID(commonObjective[1].getID(), true,200, 80));
        JLabel imageSecretObjective = new JLabel(getImageFromID(clientPlayer.getSecretObjective().getID(), true,200, 80));

        // Set image label in center
        imageCommonObjective1.setVerticalAlignment(JLabel.CENTER);
        imageCommonObjective2.setVerticalAlignment(JLabel.CENTER);
        imageSecretObjective.setVerticalAlignment(JLabel.CENTER);

        // Add image labels in the panel in second row
        // These labels will occupy 70% of the panel
        addComponent(panel, imageCommonObjective1, gbc, 1, 0, 1, 1, 1, 0.7);
        addComponent(panel, imageCommonObjective2, gbc, 1, 1, 1, 1, 1, 0.7);
        addComponent(panel, imageSecretObjective, gbc, 1, 2, 1, 1, 1, 0.7);

        // Set panel values
        panel.setBackground(java.awt.Color.CYAN);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(150, 100));

        return panel;
    }

    /**
     * This method is used for creating the west panel
     * The west panel contains the live scoreboard and buttons for view other players' game area
     *
     * @return the panel that will be added in the content pane
     * @author Foini Lorenzo
     */
    public JPanel createPanelWest(){
        int indexRow = 0; // Variable which represent the total number of rows added in the panel

        // Create new panel and use a GridBagLayout, so we can choose the size of the cells
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;


        // Create label for scoreboard
        JLabel scoreboard = new JLabel("Live scoreboard:");
        scoreboard.setHorizontalAlignment(JLabel.CENTER);
        scoreboard.setVerticalAlignment(JLabel.CENTER);

        // Add label in the panel in first row
        // This label will occupy 10% of the panel
        addComponent(panel, scoreboard, gbc, indexRow, 0, 1, 1, 1, 0.1);
        indexRow++;

        // Create labels for players and their scores
        ArrayList<Player> players = gameTable.getPlayers();
        for(Player player: players){
            String playerUsername = player.getUsername();
            int playerScore = player.getScore();
            JLabel playerLabel = new JLabel("- "+ playerUsername +": "+ playerScore+ " pts");
            playerLabel.setVerticalAlignment(JLabel.CENTER);
            addComponent(panel, playerLabel, gbc, indexRow, 0, 1, 1, 1, 0.1);
            indexRow++;
        }

        // Create buttons to see others players' game area
        for(Player player: players){
            String playerUsername = player.getUsername();
            if(!playerUsername.equals(clientPlayer.getUsername())){
                JButton buttonPlayerArea = new JButton(playerUsername+" area");
                // Add buttons in the panel in second row
                // These buttons will occupy 30% of the panel
                addComponent(panel, buttonPlayerArea, gbc, indexRow, 0, 1, 1, 1, 0.1);
                indexRow++;
            }
        }

        // Create buttons to reset scroll bar and add it in the panel
        // TODO: Implement listener
        JButton buttonResetScrollBar = new JButton("Reset scroll bar");
        addComponent(panel, buttonResetScrollBar, gbc, indexRow, 0, 1, 1, 1, 0.1);

        // Set panel values
        panel.setBackground(java.awt.Color.RED);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(175, 100));

        return panel;
    }

    /**
     * This method is used for creating the center scroll panel
     * The center scroll panel contains the player's game area
     *
     * @return the panel that will be added in the content pane
     * @author Foini Lorenzo
     */
    public JScrollPane createPanelCenter(){
        // Get client's area
        PlayerArea playerArea = clientPlayer.getPlayerArea();

        JPanel panel = new JPanel(new GridLayout(NUM_ROWS, NUM_COLS));

        // Add button in the grid
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                JButton button;

                // Check if in position (i,j) of player's area there is a card or not
                // If there is card => Add such card's image
                if(playerArea.getArea()[i][j]){ // If is true => Cell is empty
                    button = new JButton("(" + i + ", " + j + ")");
                }else{ // If is false => There is a card in such position (i,j)
                    // Get card
                    int[] position = new int[2];
                    position[0] = i;
                    position[1] = j;
                    Card card = playerArea.getCardByPosition(position);

                    // Create new button with image
                    button = new JButton(getImageFromID(card.getID(), card.getSide(),250, 100));
                    button.setDisabledIcon(getImageFromID(card.getID(), card.getSide(),250, 100));
                }
                button.addActionListener(e -> {
                    enableButtonsArea();
                    enableDrawCardsButtons();
                });

                button.setPreferredSize(new Dimension(250, 100));
                panel.add(button);

                // Add the button to the list of grid buttons
                gridButtons.add(button);
            }
        }
        enableButtonsArea();

        // Create scrollbar for visualization of the game area
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Impost scroll bar to center: we want (40,40) to be in the center of the window
        JViewport viewport = scrollPane.getViewport();
        int viewWidth = viewport.getViewSize().width;
        int viewHeight = viewport.getViewSize().height;
        int offsetX = (viewWidth - viewport.getWidth()) / 2 - 600; // Offset for put (40,40) at center
        int offsetY = (viewHeight - viewport.getHeight()) / 2 - 250; // Offset for put (40,40) at center
        viewport.setViewPosition(new Point(offsetX, offsetY));

        return scrollPane;
    }

    /**
     * This method is used for creating the east panel
     * The east panel contains the counters of the 4 resources and 3 objects
     *
     * @return the panel that will be added in the content pane
     * @author Foini Lorenzo
     */
    public JPanel createPanelEast(){
        // Create new panel for display
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Create label
        JLabel titleLabel = new JLabel("Your resources and objects");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        // This label will occupy 10% of the panel
        addComponent(panel, titleLabel, gbc, 0, 0, 1, 1, 1, 0.1);

        // Count number of animal kingdom
        int counterAnimalKingdom = clientPlayer.getPlayerArea().countKingdoms(Kingdom.ANIMALKINGDOM);
        JLabel animalKingdomLabel = new JLabel("- "+counterAnimalKingdom+" animal resources");
        // This label will occupy 15% of the panel
        addComponent(panel, animalKingdomLabel, gbc, 1, 0, 1, 1, 1, 0.15);

        // Count number of fungi kingdom
        int counterFungiKingdom = clientPlayer.getPlayerArea().countKingdoms(Kingdom.FUNGIKINGDOM);
        JLabel fungiKingdomLabel = new JLabel("- "+counterFungiKingdom+" fungi resources");
        // This label will occupy 15% of the panel
        addComponent(panel, fungiKingdomLabel, gbc, 2, 0, 1, 1, 1, 0.15);

        // Count number of insect kingdom
        int counterInsectKingdom = clientPlayer.getPlayerArea().countKingdoms(Kingdom.INSECTKINGDOM);
        JLabel InsectKingdomLabel = new JLabel("- "+counterInsectKingdom+" insect resources");
        // This label will occupy 15% of the panel
        addComponent(panel, InsectKingdomLabel, gbc, 3, 0, 1, 1, 1, 0.15);

        // Count number of plant kingdom
        int counterPlantKingdom = clientPlayer.getPlayerArea().countKingdoms(Kingdom.PLANTKINGDOM);
        JLabel plantKingdomLabel = new JLabel("- "+counterPlantKingdom+" plant resources");
        // This label will occupy 15% of the panel
        addComponent(panel, plantKingdomLabel, gbc, 4, 0, 1, 1, 1, 0.15);

        // Count number of inkwell object
        int counterInkwellObject = clientPlayer.getPlayerArea().countObject(GameObject.INKWELL);
        JLabel inkwellLabel = new JLabel("- "+counterInkwellObject+" inkwell objects");
        // This label will occupy 15% of the panel
        addComponent(panel, inkwellLabel, gbc, 5, 0, 1, 1, 1, 0.15);

        // Count number of manuscript object
        int counterManuscriptObject = clientPlayer.getPlayerArea().countObject(GameObject.MANUSCRIPT);
        JLabel manuscriptLabel = new JLabel("- "+counterManuscriptObject+" manuscript objects");
        // This label will occupy 15% of the panel
        addComponent(panel, manuscriptLabel, gbc, 6, 0, 1, 1, 1, 0.15);

        // Count number of quill object
        int counterQuilObject = clientPlayer.getPlayerArea().countObject(GameObject.QUILL);
        JLabel quillLabel = new JLabel("- "+counterQuilObject+" quill objects");
        // This label will occupy 15% of the panel
        addComponent(panel, quillLabel, gbc, 7, 0, 1, 1, 1, 0.15);

        // Set panel values
        panel.setBackground(java.awt.Color.YELLOW);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(175, 100));

        return panel;
    }

    /**
     * This method is used for creating the south panel
     * The south panel contains player's hand
     *
     * @return the panel that will be added in the content pane
     * @author Falcone Giacomo, Foini Lorenzo
     */
    public JPanel createPanelSouth(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Create buttons for player's cards
        ArrayList<GamingCard> hand = clientPlayer.getHand();
        JButton buttonCard1 = new JButton(getImageFromID(hand.getFirst().getID(), hand.getFirst().getSide(),350, 120));
        buttonCard1.setDisabledIcon(getImageFromID(hand.getFirst().getID(), hand.getFirst().getSide(),350, 120));
        buttonCard1.addActionListener(e -> {
            buttonCard1.setIcon(getImageFromID(hand.getFirst().getID(), !hand.getFirst().getSide(), 350, 120));
            buttonCard1.setDisabledIcon(getImageFromID(hand.getFirst().getID(), !hand.getFirst().getSide(),350, 120));
            hand.getFirst().setSide(!hand.getFirst().getSide());
        });
        handCardsButtons.add(buttonCard1);

        JButton buttonCard2 = new JButton(getImageFromID(hand.get(1).getID(), hand.getFirst().getSide(),350, 120));
        buttonCard2.setDisabledIcon(getImageFromID(hand.get(1).getID(), hand.getFirst().getSide(),350, 120));
        buttonCard2.addActionListener(e -> {
            buttonCard2.setIcon(getImageFromID(hand.get(1).getID(), !hand.get(1).getSide(), 350, 120));
            buttonCard2.setDisabledIcon(getImageFromID(hand.get(1).getID(), !hand.get(1).getSide(),350, 120));
            hand.get(1).setSide(!hand.get(1).getSide());
        });
        handCardsButtons.add(buttonCard2);

        JButton buttonCard3 = new JButton(getImageFromID(hand.getLast().getID(), hand.getFirst().getSide(),350, 120));
        buttonCard3.setDisabledIcon(getImageFromID(hand.getLast().getID(), hand.getFirst().getSide(),350, 120));
        buttonCard3.addActionListener(e -> {
            buttonCard3.setIcon(getImageFromID(hand.getLast().getID(), !hand.getLast().getSide(), 350, 120));
            buttonCard3.setDisabledIcon(getImageFromID(hand.getLast().getID(), !hand.getLast().getSide(),350, 120));
            hand.getLast().setSide(!hand.getLast().getSide());
        });
        handCardsButtons.add(buttonCard3);

        // Add buttons in the panel in first row
        // These buttons will occupy 70% of the panel
        addComponent(panel, buttonCard1, gbc, 0, 0, 1, 1, 1, 0.7);
        addComponent(panel, buttonCard2, gbc, 0, 1, 1, 1, 1, 0.7);
        addComponent(panel, buttonCard3, gbc, 0, 2, 1, 1, 1, 0.7);

        // Create buttons for playing the respective card
        JButton buttonPlayCard1 = new JButton("Play card 1");
        buttonPlayCard1.addActionListener(e -> {
            indexCardToPlay = "0";
            enableButtonsArea();
            enableHandCardsButtons();
        });
        handCardsButtons.add(buttonPlayCard1);

        JButton buttonPlayCard2 = new JButton("Play card 2");
        buttonPlayCard2.addActionListener(e -> {
            indexCardToPlay = "1";
            enableButtonsArea();
            enableHandCardsButtons();
        });
        handCardsButtons.add(buttonPlayCard2);

        JButton buttonPlayCard3 = new JButton("Play card 3");
        buttonPlayCard3.addActionListener(e -> {
            indexCardToPlay = "2";
            enableButtonsArea();
            enableHandCardsButtons();
        });
        handCardsButtons.add(buttonPlayCard3);

        // Add buttons in the panel in second row
        // These buttons will occupy 30% of the panel
        addComponent(panel, buttonPlayCard1, gbc, 1, 0, 1, 1, 1, 0.3);
        addComponent(panel, buttonPlayCard2, gbc, 1, 1, 1, 1, 1, 0.3);
        addComponent(panel, buttonPlayCard3, gbc, 1, 2, 1, 1, 1, 0.3);

        // Set panel values
        panel.setBackground(java.awt.Color.ORANGE);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(150, 170));

        return panel;
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

    private void enableButtonsArea() {
        for (JButton button : gridButtons) {
            button.setEnabled(enableButtonsArea);
        }
        enableButtonsArea = !enableButtonsArea;
    }

    private void enableHandCardsButtons() {
        for (JButton button : handCardsButtons) {
            button.setEnabled(enableHandCardsButtons);
        }
        enableHandCardsButtons = !enableHandCardsButtons;
    }

    private void enableDrawCardsButtons(){
        for(JButton button: drawCardsButtons){
            button.setEnabled(enableDrawCardsButtons);
        }
        enableDrawCardsButtons = !enableDrawCardsButtons;
    }
}