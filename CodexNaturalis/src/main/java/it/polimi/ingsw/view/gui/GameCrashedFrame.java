package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * New class for creating a new window which shows to the client that the game has crashed
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class GameCrashedFrame extends JFrame {
    private final Font customFont = new Font("SansSerif", Font.BOLD, 18);

    /**
     * GameCrashedFrame constructor, it calls method init() for initialization of frame
     *
     * @param title window's title
     * @author Foini Lorenzo
     */
    GameCrashedFrame(String title){
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
        this.setSize(550, 300);
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
        gbc.insets = new Insets(50, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Create new labels
        JLabel crashLabel = new JLabel("THE GAME HAS CRASHED", SwingConstants.CENTER);
        JLabel sorryLabel = new JLabel("WE ARE SORRY, NOW EXIT THE GAME", SwingConstants.CENTER);

        // Set font to labels
        crashLabel.setFont(customFont);
        sorryLabel.setFont(customFont);

        // Add button to the transparent panel with grid back constraint
        transparentPanel.add(crashLabel, gbc);
        transparentPanel.add(sorryLabel, gbc);

        // Add transparent panel to the frame
        this.add(transparentPanel, BorderLayout.CENTER);

        // Set frame relative location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}