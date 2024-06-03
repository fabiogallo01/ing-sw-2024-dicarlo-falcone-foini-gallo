package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;
import java.util.List;

/**
 * Class representing GUI
 * It has different methods which will be used in the client-server communication using GUI
 * Such methods use Swing
 *
 * @author Foini Lorenzo
 */
public class ViewGUI {

    private static int numPlayers = 0; // number of players counter
    private static String username; // Client username
    private static String selectedColor; // Client selected color

    public String displayNumberPlayer(){
        // TODO: Write code in English and improve Frame graphics: add font, color, background, ...
        // TODO: Move code in a new class and call it like this:
        /*
        // See other classes in gui package for example and better understanding
        NumPlayersFrame numPlayersFrame = new NumPlayersFrame("Select number of players");
        return numPlayersFrame.getNumPlayers(); // As to be returned as a string
        */

        // Creazione del frame principale
        JFrame frame = new JFrame("SELECT NUMBER OF PLAYER");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 100);
        frame.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            frame.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creazione del pannello per l'input
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Etichetta per il menu a tendina
        JLabel label = new JLabel("Numero di Giocatori:");

        // Menu a tendina per la selezione del numero di giocatori
        Integer[] playerNumbers = {2, 3, 4};
        JComboBox<Integer> comboBox = new JComboBox<>(playerNumbers);

        // Aggiunta dell'etichetta e del menu a tendina al pannello
        panel.add(label);
        panel.add(comboBox);

        // Creazione del pulsante per inviare l'input
        JButton button = new JButton("Invia");

        // Aggiunta del pannello e del pulsante al frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        // Creazione di un'etichetta per mostrare il numero di giocatori
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(resultLabel, BorderLayout.NORTH);

        // Aggiunta di un listener al pulsante per gestire l'evento di clic
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recupero del numero selezionato dal menu a tendina
                numPlayers = (Integer) comboBox.getSelectedItem();
                frame.dispose();
                synchronized (frame) {
                    frame.notify();
                }
            }
        });

        // Rendere visibile il frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        synchronized (frame){
            try{
                frame.wait();
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }

        return String.valueOf(numPlayers);
    }

    public String displayUsername(){
        // TODO: Write code in English and improve Frame graphics: add font, color, background, ...
        // TODO: Move code in a new class and call it like this:
        /*
        // See other classes in gui package for example and better understanding
        // Add a boolean parameter repeated so:
        // If true => first username was not valid, so show a new label which says "Sorry, that username is already used"
        // If false => Do not show that label
        GetUsernameFrame getUsernameFrame = new GetUsernameFrame("Insert username", repeated);
        return getUsernameFrame.getUsername();
        */

        // Creazione del frame principale
        JFrame frame = new JFrame("INSERT YOUR USERNAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            frame.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create background panel and set it
        BackgroundPanel backgroundPanel = new BackgroundPanel("CodexNaturalis\\resources\\Screen.jpg");
        frame.setContentPane(backgroundPanel);

        // Creazione del pannello per l'input
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Etichetta per il campo di testo
        JLabel label = new JLabel("Nome:");

        // Campo di testo per l'inserimento del nome
        JTextField textField = new JTextField(15);

        // Aggiunta dell'etichetta e del campo di testo al pannello
        panel.add(label);
        panel.add(textField);

        // Creazione del pulsante per inviare l'input
        JButton button = new JButton("Invia");

        // Aggiunta del pannello e del pulsante al frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        // Creazione di un'etichetta per mostrare il nome inserito
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(resultLabel, BorderLayout.NORTH);

        // Aggiunta di un listener al pulsante per gestire l'evento di clic
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recupero del testo dal campo di testo
                username = textField.getText().trim();
                frame.dispose();
                synchronized (frame) {
                    frame.notify();
                }
            }
        });

        // Rendere visibile il frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        synchronized (frame){
            try{
                frame.wait();
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }

        return username;
    }

    public String displayColor(ArrayList<String> colors){
        // TODO: Write code in English and improve Frame graphics: add font, color, background, ...
        // TODO: Move code in a new class and call it like this:
        /*
        // See other classes in gui package for example and better understanding
        // Add a boolean parameter repeated so:
        // If true => color was not valid, so show a new label which says "Sorry, that color is already used"
        // If false => Do not show that label
        GetColorFrame getColorFrame = new GetColorFrame("Insert color", repeated);
        return getColorFrame.getColor(); // where color is parameter of the class, representing client color
        */

        // Creazione del frame principale
        JFrame frame = new JFrame("SELECT YOUR COLOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            frame.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creazione del pannello per l'input
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Etichetta per il menu a tendina
        JLabel label = new JLabel("Scegli un Colore:");

        // Menu a tendina per la selezione del colore
        JComboBox<String> comboBox = new JComboBox<>(colors.toArray(new String[0]));

        // Aggiunta dell'etichetta e del menu a tendina al pannello
        panel.add(label);
        panel.add(comboBox);

        // Creazione del pulsante per inviare l'input
        JButton button = new JButton("Invia");

        // Aggiunta del pannello e del pulsante al frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        // Creazione di un'etichetta per mostrare il colore selezionato
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(resultLabel, BorderLayout.NORTH);

        // Aggiunta di un listener al pulsante per gestire l'evento di clic
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recupero del testo dal campo di testo
                selectedColor = (String) comboBox.getSelectedItem();
                frame.dispose();
                synchronized (frame) {
                    frame.notify();
                }
            }
        });

        // Rendere visibile il frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        synchronized (frame){
            try{
                frame.wait();
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }

        return selectedColor;
    }

    /**
     * Method to display the starter card and get on which side the
     * player wants to play it.
     *
     * @param starterCardID used to get the file path of the card
     * @return the chosen side as a string: "front" or "back"
     * @author giacomofalcone
     */
    public String displayStarterCard(int starterCardID) {
        // TODO: Write code in English and improve Frame graphics: add font, color, background, ...
        // TODO: Move code in a new class and call it like this:
        /*
        // See other classes in gui package for example and better understanding
        GetStarterCardFrame getStarterCardFrame = new GetStarterCardFrame("Select starter card", starterCardID);
        return getStarterCardFrame.getSide(); // Where side con be: "front" or "back" as string
        */

        final String[] side = {""}; // Store selected side
        final Object lock = new Object();

        // Creazione del frame principale
        JFrame frame = new JFrame("SELECT STARTER CARD'S SIDE");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Use DISPOSE_ON_CLOSE to close only this window
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Setting custom image icon
        try {
            Image icon = ImageIO.read(new File("CodexNaturalis\\resources\\Logo.png"));
            frame.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2)); // Modifica layout per visualizzare le carte in una riga

        JLabel instructionLabel = new JLabel("On which side do you want to play the starter card?", SwingConstants.CENTER);
        frame.add(instructionLabel, BorderLayout.NORTH);

        String starterPathBack = "CodexNaturalis\\resources\\front\\img_" + starterCardID + ".jpeg";
        String starterPathFront = "CodexNaturalis\\resources\\back\\img_" + starterCardID + ".jpeg";

        // Displaying back side
        try {
            BufferedImage cardImage = ImageIO.read(new File(starterPathBack));
            ImageIcon cardIcon = new ImageIcon(cardImage.getScaledInstance(250, 200, Image.SCALE_SMOOTH));
            JLabel cardLabel = new JLabel(cardIcon);
            cardLabel.setHorizontalAlignment(JLabel.CENTER);
            cardLabel.setVerticalAlignment(JLabel.CENTER);

            JButton backButton = new JButton("FRONT");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    side[0] = "front";
                    frame.dispose(); // Close the window
                    synchronized (lock) {
                        lock.notify(); // Notify waiting thread
                    }
                }
            });

            JPanel cardPanel = new JPanel(new BorderLayout());
            cardPanel.add(cardLabel, BorderLayout.CENTER);
            cardPanel.add(backButton, BorderLayout.SOUTH);

            panel.add(cardPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Displaying front side
        try {
            BufferedImage cardImage = ImageIO.read(new File(starterPathFront));
            ImageIcon cardIcon = new ImageIcon(cardImage.getScaledInstance(250, 200, Image.SCALE_SMOOTH));
            JLabel cardLabel = new JLabel(cardIcon);
            cardLabel.setHorizontalAlignment(JLabel.CENTER);
            cardLabel.setVerticalAlignment(JLabel.CENTER);

            JButton frontButton = new JButton("BACK");
            frontButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    side[0] = "back";
                    frame.dispose(); // Close the window
                    synchronized (lock) {
                        lock.notify(); // Notify waiting thread
                    }
                }
            });

            JPanel cardPanel = new JPanel(new BorderLayout());
            cardPanel.add(cardLabel, BorderLayout.CENTER);
            cardPanel.add(frontButton, BorderLayout.SOUTH);

            panel.add(cardPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.add(panel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        synchronized (lock) {
            try {
                lock.wait(); // Wait until user selects a side
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return side[0];
    }


    /**
     * Method to display two cards and get the index of the selected card.
     *
     * @param starterCardSide side on which the starter card have been played
     * @param starterCardID ID used to get the file path of the starter cards
     * @param handCardIDs array of IDs used to get the file path of the hand's cards
     * @param commonObjectiveCardIDs array of IDs used to get the file path of the common objective cards
     * @param secretObjectiveCardIDs array of IDs used to get the file path of the secret objective cards
     * @return the index of the chosen card
     * @author giacomofalcone
     */
    public String displayObjectiveCards(String starterCardSide, int starterCardID, int[] handCardIDs, int[] commonObjectiveCardIDs, int[] secretObjectiveCardIDs) {
        // TODO: Improve Frame graphics: add font, color, background, ...
        SecretObjectiveFrame secretObjectiveFrame = new SecretObjectiveFrame("SELECT SECRET OBJECTIVE CARD", starterCardSide, starterCardID, handCardIDs, commonObjectiveCardIDs, secretObjectiveCardIDs);
        return secretObjectiveFrame.getSelectedSecretCard();
    }

    /**
     * Method to ask the client if he wants to create or join a game
     *
     * @param countNotFullGame: how many games can be joined
     * @return client's choice
     * @author Foini Lorenzo
     */
    public String displayCreateJoinGame(int countNotFullGame){
        // TODO: Improve Frame graphics: add font, color, background, ...
        CreateJoinFrame createJoinFrame = new CreateJoinFrame("CREATE OR JOIN GAME", countNotFullGame);
        return createJoinFrame.getChoice();
    }

    /**
     * Method to ask the client which game he wants to join
     *
     * @param joinGamesAndPlayers: map of games and their clients' usernames
     * @return client's choice: an index of which game to join (It can be 1,2,...)
     * @author Foini Lorenzo
     */
    public String displayJoinGameIndex(Map<String, List<String>> joinGamesAndPlayers){
        // TODO: Improve Frame graphics: add font, color, background, ...
        JoinGameIndexFrame joinGameIndexFrame = new JoinGameIndexFrame("SELECT GAME TO JOIN", joinGamesAndPlayers);
        return joinGameIndexFrame.getSelectedIndex();
    }

    public WaitStartGameFrame displayWaitStartGame(boolean create){
        // TODO: Improve Frame graphics: add font, color, background, ...
        // TODO: Close the window when the game started
        return new WaitStartGameFrame("WAIT START OF THE GAME", create);
    }

    public void playgame(Player player, GameTable gameTable){
        GameFrame gameFrame = new GameFrame("CODEX NATURALIS", player, gameTable);
    }

    public int displayDrawChoice(GameTable gameTable){
        DrawCardFrame drawCardFrame = new DrawCardFrame("SELECT FROM WHERE YOU WANT DO DRAW", gameTable);
        return drawCardFrame.getIndexSelectedCard();
    }
}