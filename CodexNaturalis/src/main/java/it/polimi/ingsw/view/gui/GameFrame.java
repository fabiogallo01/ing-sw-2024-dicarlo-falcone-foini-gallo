package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    /**
     * MyFrame constructor, it calls method init() for initialization of panel
     *
     * @param title window's title
     * @author Foini Lorenzo
     */
    GameFrame(String title){
        super(title);
        init();
    }

    /**
     * This method is used for initialization of panel
     *
     * @author Foini Lorenzo
     */
    void init(){
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // MAX dimension
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

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
        JLabel imageCommonObjective1 = new JLabel(getImageFromID(91, true,200, 80));
        JLabel imageCommonObjective2 = new JLabel(getImageFromID(92, true,200, 80));
        JLabel imageSecretObjective = new JLabel(getImageFromID(93, true,200, 80));

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
        panel.setBackground(Color.CYAN);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(150, 100));

        return panel;
    }

    /**
     * This method is used for creating the west panel
     * The west panel contains the cards that can be drawn from a deck or from table
     *
     * @return the panel that will be added in the content pane
     * @author Foini Lorenzo
     */
    public JPanel createPanelWest(){
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
        addComponent(panel, scoreboard, gbc, 0, 0, 1, 1, 1, 0.1);


        // Create labels for players and their scores
        JLabel player1 = new JLabel("- Player 1: 5 pts");
        JLabel player2 = new JLabel("- Player 2: 3 pts");
        JLabel player3 = new JLabel("- Player 3: 7 pts");
        JLabel player4 = new JLabel("- Player 4: 2 pts");

        // Set vertical alignment to center
        player1.setVerticalAlignment(JLabel.CENTER);
        player2.setVerticalAlignment(JLabel.CENTER);
        player3.setVerticalAlignment(JLabel.CENTER);
        player4.setVerticalAlignment(JLabel.CENTER);

        // Add labels in the panel using bag constraints
        addComponent(panel, player1, gbc, 1, 0, 1, 1, 1, 0.1);
        addComponent(panel, player2, gbc, 2, 0, 1, 1, 1, 0.1);
        addComponent(panel, player3, gbc, 3, 0, 1, 1, 1, 0.1);
        addComponent(panel, player4, gbc, 4, 0, 1, 1, 1, 0.1);


        // Create buttons to reset scroll bar and add it in the panel
        JButton buttonResetScrollBar = new JButton("Reset scroll bar");
        addComponent(panel, buttonResetScrollBar, gbc, 5, 0, 1, 1, 1, 0.1);


        // Create buttons to see others players' game area
        JButton buttonPlayerArea1 = new JButton("Player 1 area");
        JButton buttonPlayerArea2 = new JButton("Player 2 area");
        JButton buttonPlayerArea3 = new JButton("Player 3 area");
        JButton buttonPlayerArea4 = new JButton("Player 4 area");

        // Add buttons in the panel in second row
        // These buttons will occupy 30% of the panel
        addComponent(panel, buttonPlayerArea1, gbc, 6, 0, 1, 1, 1, 0.1);
        addComponent(panel, buttonPlayerArea2, gbc, 7, 0, 1, 1, 1, 0.1);
        addComponent(panel, buttonPlayerArea3, gbc, 8, 0, 1, 1, 1, 0.1);
        addComponent(panel, buttonPlayerArea4, gbc, 9, 0, 1, 1, 1, 0.1);

        // Set panel values
        panel.setBackground(Color.RED);
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
        JPanel panel = new JPanel(new GridLayout(NUM_ROWS, NUM_COLS));

        // Add button in the grid
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                JButton button;
                if(i == 39 && j == 39){
                    // Create new button with image
                    button = new JButton(getImageFromID(57, true,250, 100));
                    button.setDisabledIcon(getImageFromID(57, true,250, 100));
                } else if(i == 40 && j == 40){
                    // Create new button with image
                    button = new JButton(getImageFromID(82, true,250, 100));
                    button.setDisabledIcon(getImageFromID(82, true,250, 100));
                }else{
                    button = new JButton("(" + i + ", " + j + ")");
                }
                button.setEnabled(false);
                button.setPreferredSize(new Dimension(250, 100));
                panel.add(button);
            }
        }

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
     * The east panel contains the live scoreboard and buttons for view other players' game area
     *
     * @return the panel that will be added in the content pane
     * @author Foini Lorenzo
     */
    public JPanel createPanelEast(){
        // Create new panel and use a GridBagLayout, so we can choose the size of the cells
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Create label for resource deck top card
        JLabel resourceDeckTopCard = new JLabel("Resource deck top card:");
        resourceDeckTopCard.setHorizontalAlignment(JLabel.CENTER);
        resourceDeckTopCard.setVerticalAlignment(JLabel.CENTER);

        // Add label in the panel in first row
        // This label will occupy 7% of the panel
        addComponent(panel, resourceDeckTopCard, gbc, 0, 0, 1, 1, 1, 0.07);

        // Create image button for resource deck top card
        JButton imageResourceDeckTopCard = new JButton(getImageFromID(11, false,175, 80));
        imageResourceDeckTopCard.setEnabled(false);
        imageResourceDeckTopCard.setDisabledIcon(getImageFromID(11, false,175, 80));
        imageResourceDeckTopCard.setVerticalAlignment(JLabel.CENTER);

        // Add image button in the panel in second row
        // This image button will occupy 12% of the panel
        addComponent(panel, imageResourceDeckTopCard, gbc, 1, 0, 1, 1, 1, 0.12);


        // Create label for gold deck top card
        JLabel goldDeckTopCard = new JLabel("Gold deck top card:");
        goldDeckTopCard.setHorizontalAlignment(JLabel.CENTER);
        goldDeckTopCard.setVerticalAlignment(JLabel.CENTER);

        // Add label in the panel in third row
        // This label will occupy 7% of the panel
        addComponent(panel, goldDeckTopCard, gbc, 2, 0, 1, 1, 1, 0.07);

        // Create image button for gold deck top card
        JButton imageGoldDeckTopCard = new JButton(getImageFromID(41, false,175, 80));
        imageGoldDeckTopCard.setEnabled(false);
        imageGoldDeckTopCard.setDisabledIcon(getImageFromID(41, false,175, 80));
        imageGoldDeckTopCard.setVerticalAlignment(JLabel.CENTER);

        // Add image button in the panel in forth row
        // This image button will occupy 12% of the panel
        addComponent(panel, imageGoldDeckTopCard, gbc, 3, 0, 1, 1, 1, 0.12);


        // Create label for visible cards in the table
        JLabel visibleCards = new JLabel("Visible cards:");
        visibleCards.setHorizontalAlignment(JLabel.CENTER);
        visibleCards.setVerticalAlignment(JLabel.CENTER);

        // Add label in the panel in fifth row
        // This label will occupy 6% of the panel
        addComponent(panel, visibleCards, gbc, 4, 0, 1, 1, 1, 0.06);

        // Create image button for first visible card
        JButton imageVisibleCard1 = new JButton(getImageFromID(23, true,175, 80));
        imageVisibleCard1.setEnabled(false);
        imageVisibleCard1.setDisabledIcon(getImageFromID(23, true,175, 80));
        imageVisibleCard1.setVerticalAlignment(JLabel.CENTER);

        // Add image button in the panel in sixth row
        // This image button will occupy 14% of the panel
        addComponent(panel, imageVisibleCard1, gbc, 5, 0, 1, 1, 1, 0.14);

        // Create image button for second visible card
        JButton imageVisibleCard2 = new JButton(getImageFromID(24, true,175, 80));
        imageVisibleCard2.setEnabled(false);
        imageVisibleCard2.setDisabledIcon(getImageFromID(24, true,175, 80));
        imageVisibleCard2.setVerticalAlignment(JLabel.CENTER);

        // Add image button in the panel in seventh row
        // This image button will occupy 14% of the panel
        addComponent(panel, imageVisibleCard2, gbc, 6, 0, 1, 1, 1, 0.14);

        // Create image button for third visible card
        JButton imageVisibleCard3 = new JButton(getImageFromID(46, true,175, 80));
        imageVisibleCard3.setEnabled(false);
        imageVisibleCard3.setDisabledIcon(getImageFromID(46, true,175, 80));
        imageVisibleCard3.setVerticalAlignment(JLabel.CENTER);

        // Add image button in the panel in eight row
        // This image button will occupy 14% of the panel
        addComponent(panel, imageVisibleCard3, gbc, 7, 0, 1, 1, 1, 0.14);

        // Create image button for forth visible card
        JButton imageVisibleCard4 = new JButton(getImageFromID(47, true,175, 80));
        imageVisibleCard4.setEnabled(false);
        imageVisibleCard4.setDisabledIcon(getImageFromID(47, true,175, 80));
        imageVisibleCard4.setVerticalAlignment(JLabel.CENTER);

        // Add image button in the panel in ninth row
        // This image button will occupy 14% of the panel
        addComponent(panel, imageVisibleCard4, gbc, 8, 0, 1, 1, 1, 0.14);


        // Set panel values
        panel.setBackground(Color.YELLOW);
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
        JButton buttonCard1 = new JButton(getImageFromID(21, false,350, 120));
        JButton buttonCard2 = new JButton(getImageFromID(23, true,350, 120));
        JButton buttonCard3 = new JButton(getImageFromID(42, true,350, 120));

        // Add buttons in the panel in first row
        // These buttons will occupy 70% of the panel
        addComponent(panel, buttonCard1, gbc, 0, 0, 1, 1, 1, 0.7);
        addComponent(panel, buttonCard2, gbc, 0, 1, 1, 1, 1, 0.7);
        addComponent(panel, buttonCard3, gbc, 0, 2, 1, 1, 1, 0.7);

        // Create buttons for playing the respective card
        JButton buttonPlayCard1 = new JButton("Play card 1");
        JButton buttonPlayCard2 = new JButton("Play card 2");
        JButton buttonPlayCard3 = new JButton("Play card 3");

        // Add buttons in the panel in second row
        // These buttons will occupy 30% of the panel
        addComponent(panel, buttonPlayCard1, gbc, 1, 0, 1, 1, 1, 0.3);
        addComponent(panel, buttonPlayCard2, gbc, 1, 1, 1, 1, 1, 0.3);
        addComponent(panel, buttonPlayCard3, gbc, 1, 2, 1, 1, 1, 0.3);

        // Set panel values
        panel.setBackground(Color.ORANGE);
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

        String path = "C:\\Users\\Lenovo\\Desktop\\Politecnico\\Materie\\3 Anno\\Ingegneria del software\\Progetto\\ing-sw-2024-dicarlo-falcone-foini-gallo\\CodexNaturalis\\resources\\"+stringSide+"\\img_"+cardID+".jpeg";
        //String path = "CodexNaturalis\\resources\\"+stringSide+"\\img_"+cardID+".jpeg";
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
}