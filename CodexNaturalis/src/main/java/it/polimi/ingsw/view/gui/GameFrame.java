package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.game.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * New class for creating a new window which will be used by client
 * It represents the after login window.
 * It extends JFrame.
 *
 * @author Falcone Giacomo, Foini Lorenzo
 */
public class GameFrame extends JFrame {
    private PrintWriter out;
    private final int NUM_ROWS = 81;
    private final int NUM_COLS = 81;
    private Player clientPlayer; // It represents the clients
    private GameTable gameTable; // It represents the gameTable of the match
    private ArrayList<JButton> gridButtons;
    private ArrayList<JButton> handCardsImageButtons;
    private ArrayList<JButton> handCardsSelectButtons;
    private JLabel errorLabel;
    private String invalidPlay;
    private String mistakePlay;

    /**
     * GameFrame constructor, it calls method init() for initialization of frame
     *
     * @param title window's title
     * @author Foini Lorenzo
     */
    GameFrame(String title, PrintWriter out, Player player, GameTable gameTable, ArrayList<Integer> counterResources, String invalidPlay, String mistakePlay){
        super(title);
        this.out = out;
        this.invalidPlay = invalidPlay;
        this.mistakePlay = mistakePlay;
        init(player, gameTable, counterResources);
    }

    /**
     * This method is used for initialization of frame
     *
     * @author Foini Lorenzo
     */
    void init(Player player, GameTable gameTable, ArrayList<Integer> counterResources){
        // Initialise parameters
        this.clientPlayer = player;
        this.gameTable = gameTable;
        gridButtons = new ArrayList<>();
        handCardsImageButtons = new ArrayList<>();
        handCardsSelectButtons = new ArrayList<>();


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

        // Call to function createPanelCenter() for creating new scroll panel to be addend in the content pane
        JScrollPane scrollPaneCenter = createPanelCenter();

        // Call to function createPanelWest() for creating new panel to be addend in the content pane
        JPanel panelWest = createPanelWest(scrollPaneCenter);

        // Call to function createPanelEast() for creating new panel to be addend in the content pane
        JPanel panelEast = createPanelEast(counterResources);

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

        // Create label which indicates if it is client's turn
        JLabel turnLabel;
        if(clientPlayer.isTurn()){
            turnLabel = new JLabel("IT'S YOUR TURN, PLEASE PLAY THE GAME");
            enableButtons(handCardsSelectButtons,false);
        } else{
            turnLabel = new JLabel("IT'S NOT YOUR TURN, PLEASE WAIT");
        }
        // Set horizontal and vertical alignment to center
        turnLabel.setHorizontalAlignment(JLabel.CENTER);
        turnLabel.setVerticalAlignment(JLabel.CENTER);
        // Add turn label in the panel in first row and fourth column
        // These labels will occupy 30% of the panel
        addComponent(panel, turnLabel, gbc, 0, 3, 1, 1, 1, 0.3);

        // Assign error label: it contains player's error when he does an invalid play
        if(invalidPlay.isEmpty() && mistakePlay.isEmpty()) errorLabel = new JLabel("NO ERROR WHILE PLAYING");
        else{
            // errorLabel is now a multi-lines text, so use HTML for creating such label
            // Need also to set text-align
            String multiLineText = "<html><div style='text-align: center;'>"+invalidPlay+"<br>"+mistakePlay+"</div></html>";
            errorLabel = new JLabel(multiLineText);
        }
        // Set horizontal and vertical alignment to center
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setVerticalAlignment(JLabel.CENTER);
        // Add turn label in the panel in second row and fourth column
        // These labels will occupy 70% of the panel
        addComponent(panel, errorLabel, gbc, 1, 3, 1, 1, 1, 0.7);

        if(!clientPlayer.isTurn()){
            enableButtons(handCardsSelectButtons,false);
        }

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
    public JPanel createPanelWest(JScrollPane scrollPane){
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
                // Add listener
                buttonPlayerArea.addActionListener(e -> {
                    new PlayerAreaFrame(playerUsername + "'s AREA", player.getPlayerArea(), NUM_ROWS, NUM_COLS);
                });

                // Add buttons in the panel in second row
                // These buttons will occupy 30% of the panel
                addComponent(panel, buttonPlayerArea, gbc, indexRow, 0, 1, 1, 1, 0.1);
                indexRow++;
            }
        }

        // Create buttons to reset scroll bar and add it in the panel
        JButton buttonResetScrollBar = new JButton("Reset scroll bar");
        // Add listener
        buttonResetScrollBar.addActionListener(e -> {
            resetScrollBar(scrollPane);
        });
        addComponent(panel, buttonResetScrollBar, gbc, indexRow, 0, 1, 1, 1, 0.1);
        indexRow++;

        // Create button for looking at the deck and visible cards
        JButton drawButton = new JButton("Draw");
        drawButton.addActionListener(e -> {
            new DrawCardFrame("AFTER THE PLAY, YOU CAN DRAW ONE OF THESE CARDS", gameTable, false);
        });
        // Add draw button in the panel in second row and fourth column
        // These labels will occupy 10% of the panel
        addComponent(panel, drawButton, gbc, indexRow, 0, 1, 1, 1, 0.1);

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
                    button = new JButton("( " + i + " , " + j + " )");
                }else{ // If is false => There is a card in such position (i,j)
                    // Get card
                    int[] position = new int[2];
                    position[0] = i;
                    position[1] = j;
                    Card card = playerArea.getCardByPosition(position);

                    // Create new button with image
                    button = new JButton(getImageFromID(card.getID(), card.getSide(),200, 100));
                    button.setDisabledIcon(getImageFromID(card.getID(), card.getSide(),200, 100));
                }
                int finalI = i;
                int finalJ = j;
                button.addActionListener(e -> {
                    out.println(finalI);
                    out.println(finalJ);
                    enableButtons(gridButtons, false);
                    enableButtons(handCardsImageButtons,true);
                });

                button.setPreferredSize(new Dimension(200, 100));
                panel.add(button);

                // Add the button to the list of grid buttons
                gridButtons.add(button);
            }
        }
        enableButtons(gridButtons, false);

        // Create scrollbar for visualization of the game area
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Set scroll bar to center: we want (40,40) to be in the center of the window
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
    public JPanel createPanelEast(ArrayList<Integer> counterResources){
        // Create new panel for display
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Create label for resources
        JLabel titleLabel = new JLabel("Resources and objects");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        // This label will occupy 12.5% of the panel
        addComponent(panel, titleLabel, gbc, 0, 0, 1, 1, 1, 0.125);

        // Count number of animal kingdom
        JLabel animalKingdomLabel = new JLabel("- Animal kingdom: "+counterResources.getFirst());
        // This label will occupy 12.5% of the panel
        addComponent(panel, animalKingdomLabel, gbc, 1, 0, 1, 1, 1, 0.125);

        // Count number of fungi kingdom
        JLabel fungiKingdomLabel = new JLabel("- Fungi kingdom: "+counterResources.get(1));
        // This label will occupy 12.5% of the panel
        addComponent(panel, fungiKingdomLabel, gbc, 2, 0, 1, 1, 1, 0.125);

        // Count number of insect kingdom
        JLabel InsectKingdomLabel = new JLabel("- Insect kingdom: "+counterResources.get(2));
        // This label will occupy 12.5% of the panel
        addComponent(panel, InsectKingdomLabel, gbc, 3, 0, 1, 1, 1, 0.125);

        // Count number of plant kingdom
        JLabel plantKingdomLabel = new JLabel("- Plant kingdom: "+counterResources.getLast());
        // This label will occupy 15% of the panel
        addComponent(panel, plantKingdomLabel, gbc, 4, 0, 1, 1, 1, 0.125);

        // Count number of inkwell object
        int counterInkwellObject = clientPlayer.getPlayerArea().countObject(GameObject.INKWELL);
        JLabel inkwellLabel = new JLabel("- Inkwell: "+counterInkwellObject);
        // This label will occupy 15% of the panel
        addComponent(panel, inkwellLabel, gbc, 5, 0, 1, 1, 1, 0.125);

        // Count number of manuscript object
        int counterManuscriptObject = clientPlayer.getPlayerArea().countObject(GameObject.MANUSCRIPT);
        JLabel manuscriptLabel = new JLabel("- Manuscript: "+counterManuscriptObject);
        // This label will occupy 12.5% of the panel
        addComponent(panel, manuscriptLabel, gbc, 6, 0, 1, 1, 1, 0.125);

        // Count number of quill object
        int counterQuillObject = clientPlayer.getPlayerArea().countObject(GameObject.QUILL);
        JLabel quillLabel = new JLabel("- Quill: "+counterQuillObject);
        // This label will occupy 12.5% of the panel
        addComponent(panel, quillLabel, gbc, 7, 0, 1, 1, 1, 0.125);

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

        // Create buttons for player's cards and the respective buttons
        int indexColumn = 0;
        ArrayList<GamingCard> hand = clientPlayer.getHand();
        for(GamingCard card : hand){
            JButton buttonCard = new JButton(getImageFromID(card.getID(), card.getSide(),350, 120));
            buttonCard.setDisabledIcon(getImageFromID(card.getID(), card.getSide(),350, 120));
            buttonCard.addActionListener(e -> {
                buttonCard.setIcon(getImageFromID(card.getID(), !card.getSide(), 350, 120));
                buttonCard.setDisabledIcon(getImageFromID(card.getID(), !card.getSide(),350, 120));
                card.setSide(!card.getSide());
            });
            handCardsImageButtons.add(buttonCard);

            // Add button in the panel in first row
            // The button will occupy 70% of the panel's height
            addComponent(panel, buttonCard, gbc, 0, indexColumn, 1, 1, 1, 0.7);
            indexColumn++;

            // Create button for playing this card
            int indexCard = indexColumn;
            JButton buttonPlayCard = new JButton("Play card "+indexCard);
            buttonPlayCard.addActionListener(e -> {
                out.println(indexCard);
                if(card.getSide()) out.println("front");
                else out.println("back");
                enableButtons(gridButtons,true);
                enableButtons(handCardsImageButtons,false);
                enableButtons(handCardsSelectButtons,false);
            });
            handCardsSelectButtons.add(buttonPlayCard);

            // Add buttons in the panel in second row
            // These buttons will occupy 30% of the panel
            addComponent(panel, buttonPlayCard, gbc, 1, indexColumn-1, 1, 1, 1, 0.3);
        }

        // Check if is not client's turn or if the player has already played a card
        // If true => Disable select card buttons
        if(!clientPlayer.isTurn() || hand.size()!=3){
            enableButtons(handCardsSelectButtons,false);
        }

        // Set panel values
        panel.setBackground(java.awt.Color.ORANGE);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(150, 170));

        return panel;
    }

    private void resetScrollBar(JScrollPane scrollPane) {
        // Impost scroll bar to center: we want (40,40) to be in the center of the window
        JViewport viewport = scrollPane.getViewport();
        int viewWidth = viewport.getViewSize().width;
        int viewHeight = viewport.getViewSize().height;
        int offsetX = (viewWidth - viewport.getWidth()) / 2; // Offset for put (40,40) at center
        int offsetY = (viewHeight - viewport.getHeight()) / 2; // Offset for put (40,40) at center
        viewport.setViewPosition(new Point(offsetX, offsetY));
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

    private void enableButtons(ArrayList<JButton> buttons, boolean enable) {
        for (JButton button : buttons) {
            button.setEnabled(enable);
        }
    }

    public void updateGameFrame(Player updatedPlayer, GameTable updatedGameTable, ArrayList<Integer> counterResources, String invalidPlay, String mistakePlay){
        // Update string for error while playing
        this.invalidPlay = invalidPlay;
        this.mistakePlay = mistakePlay;

        // Remove previous components
        this.getContentPane().removeAll();

        // Update frame using method init() with updated parameters
        init(updatedPlayer, updatedGameTable, counterResources);

        // Update frame
        //this.revalidate();
        //this.repaint();
    }
}