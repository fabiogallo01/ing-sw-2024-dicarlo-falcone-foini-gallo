package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    private String username; // contains client's username
    private final Object lock = new Object();

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
        this.setSize(400, 300);
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

        // If repeated is true => Show label which says that previousUsername is already used
        if(repeated){
            JLabel repeatedLabel;
            if(!previousUsername.isEmpty()){
                // Create label and set horizontal alignment
                repeatedLabel = new JLabel("Sorry, "+previousUsername+" is already used.");
            } else{
                // Create label and set horizontal alignment
                repeatedLabel = new JLabel("Sorry, you didn't insert a username.");
            }
            repeatedLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Add label in the frame
            this.add(repeatedLabel, BorderLayout.NORTH);
        }

        // Create main panel for input
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());


        // Create label for asking client's username
        JLabel usernameLabel = new JLabel("Insert your username:");

        // Create text field to insert username
        JTextField textField = new JTextField(15);

        // Add username label and text field in the panel
        panel.add(usernameLabel);
        panel.add(textField);

        // Create button for sending input and close this frame
        JButton confirmButton = new JButton("CONFIRM");
        // Add listener when client click on "CONFIRM" => Set frame's parameter
        confirmButton.addActionListener(e -> {
            username = textField.getText().trim();
            this.dispose(); // Close the window
            synchronized (lock) {
                lock.notify(); // Notify waiting thread
            }
        });

        // Add panel and button in the frame
        this.add(panel, BorderLayout.CENTER);
        this.add(confirmButton, BorderLayout.SOUTH);

        // Set frame's location and visibility
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Add synchronization on object lock
        synchronized (lock){
            try{
                lock.wait();
            } catch (InterruptedException ex){
                ex.printStackTrace();
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