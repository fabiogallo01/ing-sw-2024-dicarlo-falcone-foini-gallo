package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.game.PlayerArea;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerAreaFrame extends JFrame {
    private final PlayerArea playerArea;
    private final int numRows;
    private final int numCols;
    private final Font customFont = new Font("SansSerif", Font.BOLD, 15);

    public PlayerAreaFrame(String title, PlayerArea playerArea, int numRows, int numCols) {
        super(title);
        this.playerArea = playerArea;
        this.numRows = numRows;
        this.numCols = numCols;
        init();
    }

    private void init(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1000, 500);
        this.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create new panel
        JPanel panel = new JPanel(new GridLayout(numRows,numCols));

        // Add image label
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                JLabel label;

                // Check if in position (i,j) of player's area there is a card or not
                // If there is card => Add such card's image in the label
                if(playerArea.getArea()[i][j]){ // If is true => Cell is empty
                    label = new JLabel("( " + i + " , " + j + " )");
                    label.setFont(customFont);
                }else{ // If is false => There is a card in such position (i,j)
                    // Get card
                    int[] position = new int[2];
                    position[0] = i;
                    position[1] = j;
                    Card card = playerArea.getCardByPosition(position);

                    // Create new button with image
                    label = new JLabel(getImageFromID(card.getID(), card.getSide(),150, 75));
                }

                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setPreferredSize(new Dimension(150, 75));
                panel.add(label);
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
        int offsetX = (viewWidth - viewport.getWidth()) / 2 - 475; // Offset for put (40,40) at center
        int offsetY = (viewHeight - viewport.getHeight()) / 2 - 225; // Offset for put (40,40) at center
        viewport.setViewPosition(new Point(offsetX, offsetY));

        // Add panel in the frame
        this.add(scrollPane);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
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
}
