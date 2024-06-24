package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * New class for creating a new window which will be used by client
 * It represents the wait window after login.
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class WaitStartGameFrame extends JFrame {
    private final String resourcesPath = "CodexNaturalis\\src\\main\\java\\it\\polimi\\ingsw\\view\\resources\\";
    private final Font customFont = new Font("SansSerif", Font.BOLD, 18); // Used font

    /**
     * WaitCreateGameFrame constructor, it calls method init() for initialization of frame
     *
     * @param title window's title
     * @param create if true => Client has created a new game
     *               if false => Client has joined a game
     * @author Foini Lorenzo
     */
    WaitStartGameFrame(String title, boolean create){
        super(title);
        init(create);
    }

    /**
     * This method is used for initialization of frame
     *
     * @param create if true => Client has created a new game
     *               if false => Client has joined a game
     * @author Foini Lorenzo
     */
    private void init(boolean create){
        // Set frame default close operation, size and layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(550, 300);
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

        // Create new labels
        String gameLabel;
        if(create) gameLabel = "THE GAME HAS BEEN CREATED";
        else gameLabel = "YOU HAVE CORRECTLY JOINED THE GAME";

        JLabel finalGameLabel = new JLabel(gameLabel, SwingConstants.CENTER);
        JLabel waitLabel = new JLabel("NOW WAIT FOR OTHER PLAYERS TO JOIN", SwingConstants.CENTER);

        // Set font to labels
        finalGameLabel.setFont(customFont);
        waitLabel.setFont(customFont);

        // Add button to the transparent panel with grid back constraint
        transparentPanel.add(finalGameLabel, gbc);
        transparentPanel.add(waitLabel, gbc);

        // Add transparent panel to the frame
        this.add(transparentPanel, BorderLayout.CENTER);

        // Set frame relative location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}