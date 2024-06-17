package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * New class for creating a new window which will be used by client
 * It asks the client if he wants to create or join a game
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class CreateJoinFrame extends JFrame {
    private String choice; // "create" or "join"
    private final Object lock = new Object();
    private final Font customFont = new Font("SansSerif", Font.BOLD, 18);

    /**
     * CreateJoinFrame constructor, it calls method init() for initialization of the frame
     *
     * @param title window's title
     * @param countNotFullGame: how many games can be joined
     * @author Foini Lorenzo
     */
    CreateJoinFrame(String title, int countNotFullGame){
        super(title);
        init(countNotFullGame);
    }

    /**
     * This method is used for initialization of frame
     *
     * @param countNotFullGame: how many games can be joined
     * @author Foini Lorenzo
     */
    private void init(int countNotFullGame){
        // Set parameters
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

        // Create a transparent panel for labels and buttons
        JPanel transparentPanel = new JPanel(new GridBagLayout());
        transparentPanel.setOpaque(false);

        // Create new grid bag constraint for adding the label/button in a pre-fixed position
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(25, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Create label with the question
        JLabel instructionLabel = new JLabel("Do you want to create a new game or join a game?", SwingConstants.CENTER);
        instructionLabel.setFont(customFont);

        // Add label in the transparent panel with grid back constraint
        transparentPanel.add(instructionLabel, gbc);

        // Create a panel, it contains the two buttons
        JPanel buttonsPanel = createButtonPanel(countNotFullGame);

        // Add buttons in the transparent panel with grid back constraint
        transparentPanel.add(buttonsPanel, gbc);

        this.add(transparentPanel, BorderLayout.CENTER);

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

    /**
     * This method is used for create a new panel with two buttons
     *
     * @param countNotFullGame: how many games can be joined
     * @author Foini Lorenzo
     */
    private JPanel createButtonPanel(int countNotFullGame){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // First button: create new game
        JButton createButton = new JButton("CREATE NEW GAME");
        createButton.setPreferredSize(new Dimension(200, 50)); // Set preferred size
        createButton.setFont(customFont); // Set custom font
        createButton.setForeground(Color.BLACK); // Set text color
        createButton.setBorder(new LineBorder(Color.BLACK, 2)); // Set border

        // Add listener to create new game
        createButton.addActionListener(e -> {
            choice = "create";
            this.dispose(); // Close the window
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        });

        // Second button: join a game
        JButton joinButton = new JButton("JOIN A GAME");
        joinButton.setPreferredSize(new Dimension(200, 50));
        joinButton.setFont(customFont); // Set custom font
        joinButton.setForeground(Color.BLACK); // Set text color
        joinButton.setBorder(new LineBorder(Color.BLACK, 2)); // Set border

        // Add listener to create new game
        joinButton.addActionListener(e -> {
            choice = "join";
            this.dispose(); // Close the window
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        });

        // Need to check if there are game that can be joined or not
        // If not, then this button will disable
        if(countNotFullGame == 0){
            joinButton.setText("NO GAME TO JOIN"); // Change text
            joinButton.setEnabled(false); // Disable button
        }

        // Add buttons in the button panel
        buttonPanel.add(createButton);
        buttonPanel.add(joinButton);

        return buttonPanel;
    }

    /**
     * choice getter
     *
     * @return client choice: "create" or "join"
     * @author Foini Lorenzo
     */
    public String getChoice() {
        return choice;
    }
}