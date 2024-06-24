package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * New class for creating a new window which will be used by client
 * It asks the client his username in the game
 * It extends JFrame.
 *
 * @author Foini Lorenzo
 */
public class AskUsernameFrame extends JFrame {
    private final String resourcesPath = "CodexNaturalis\\src\\main\\java\\it\\polimi\\ingsw\\view\\resources\\";
    private String username; // Contains client's username
    private final Object lock = new Object(); // Lock for getting clint choice
    private final Font customFont = new Font("SansSerif", Font.BOLD, 18); // Used font

    /**
     * AskUsernameFrame constructor, it calls method init() for initialization of the frame
     * In this case it's the first time that this window is shown
     *
     * @param title window's title
     * @author Foini Lorenzo
     */
    AskUsernameFrame(String title){
        super(title);
        init(false,"");
    }

    /**
     * AskUsernameFrame constructor, it calls method init() for initialization of the frame
     * Show a label which says that the previous username is already selected
     *
     * @param title window's title
     * @param previousUsername contains client's previous repeated username
     *                         if is empty => Client click "CONFIRM" but didn't insert any username
     * @author Foini Lorenzo
     */
    AskUsernameFrame(String title, String previousUsername){
        super(title);
        init(true, previousUsername);
    }

    /**
     * This method is used for initialization of frame
     *
     * @param repeated: If true => Show a label which says that the previous username was already selected
     *                  If false => Don't show such label
     * @param previousUsername contains client's previous repeated username
     *                         if is empty => Client click "CONFIRM" but didn't insert any username
     * @author Foini Lorenzo
     */
    private void init(boolean repeated, String previousUsername) {
        // Set frame default close operation, size and layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(550, 300);
        this.setLayout(new BorderLayout());

        // Setting custom image icon from resource
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
        gbc.insets = new Insets(25, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // If repeated is true => Show label which says that previousUsername is already used
        if (repeated) {
            JLabel repeatedLabel;
            if (!previousUsername.isEmpty()) {
                // Create label and set horizontal alignment
                repeatedLabel = new JLabel("Sorry, " + previousUsername + " is already used.");
            } else {
                // Create label and set horizontal alignment
                repeatedLabel = new JLabel("Sorry, you didn't insert a username.");
            }
            repeatedLabel.setHorizontalAlignment(SwingConstants.CENTER);
            repeatedLabel.setFont(customFont); // Set custom font

            // Add label in the transparent panel with grid back constraint
            transparentPanel.add(repeatedLabel, gbc);
        }

        // Create main panel for input
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout());

        // Create label for asking client's username
        JLabel usernameLabel = new JLabel("Insert your username:");
        usernameLabel.setFont(customFont); // Set custom font

        // Create text field to insert username
        JTextField textField = new JTextField(15);

        // Add username label and text field in the panel
        panel.add(usernameLabel);
        panel.add(textField);

        // Add input panel to the transparent panel with grid back constraint
        transparentPanel.add(panel, gbc);

        // Create button for sending input and close this frame
        JButton confirmButton = new JButton("CONFIRM");
        confirmButton.setPreferredSize(new Dimension(200, 40)); // Set preferred size
        confirmButton.setFont(customFont); // Set custom font
        confirmButton.setForeground(Color.BLACK); // Set text color
        confirmButton.setBorder(new LineBorder(Color.BLACK, 2)); // Set border

        // Add listener when client clicks on "CONFIRM" => Set frame's parameter
        confirmButton.addActionListener(e -> {
            username = textField.getText().trim();
            this.dispose(); // Close the window
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        });

        // Add button to the transparent panel with grid back constraint
        transparentPanel.add(confirmButton, gbc);

        // Add transparent panel to the frame
        this.add(transparentPanel, BorderLayout.CENTER);

        // Set frame's location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Add synchronization on object lock
        synchronized (lock){
            try{
                lock.wait(); // Wait selection
            } catch (InterruptedException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * username getter
     *
     * @return client's username
     * @author Foini Lorenzo
     */
    public String getUsername() {
        return username;
    }
}