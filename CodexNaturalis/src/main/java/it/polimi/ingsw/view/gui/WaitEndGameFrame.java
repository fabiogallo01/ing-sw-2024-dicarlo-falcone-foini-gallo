package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * New class for creating a new window which will be used by client
 * It represents the wait for ending of the game.
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class WaitEndGameFrame extends JFrame {
    private final Font customFont = new Font("SansSerif", Font.BOLD, 18); // Used font

    /**
     * WaitEndGameFrame constructor, it calls method init() for initialization of frame
     *
     * @param title window's title
     * @author Foini Lorenzo
     */
    WaitEndGameFrame(String title){
        super(title);
        init();
    }

    /**
     * This method is used for initialization of frame
     *
     * @author Foini Lorenzo
     */
    private void init(){
        // Set frame default close operation, size and layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650, 300);
        this.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Create background panel and set it
        BackgroundPanel backgroundPanel = new BackgroundPanel("CodexNaturalis\\resources\\Screen.jpg");
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

        // Create new labels
        JLabel lastTurnLabel = new JLabel("THIS WAS YOUR LAST TURN", SwingConstants.CENTER);
        JLabel waitLabel = new JLabel("NOW WAIT FOR OTHER PLAYERS TO FINISH THEIR TURN", SwingConstants.CENTER);

        // Set font to labels
        lastTurnLabel.setFont(customFont);
        waitLabel.setFont(customFont);

        // Add button to the transparent panel with grid back constraint
        transparentPanel.add(lastTurnLabel, gbc);
        transparentPanel.add(waitLabel, gbc);

        // Add transparent panel to the frame
        this.add(transparentPanel, BorderLayout.CENTER);

        // Set frame relative location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}