package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.GamingCard;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.StarterCard;
import it.polimi.ingsw.networking.*;

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
 * Made using Swing
 *
 * @author Foini Lorenzo
 */

public class ViewGUI extends JFrame {

    private static int numPlayers = 0; // Contatore statico per i client
    private static String username;
    private static String selectedColor;
    private JTextField playerNameField;
    private JComboBox<String> playerColorComboBox;
    private JComboBox<Integer> numberOfPlayersComboBox;


    public ViewGUI() {
    }

    public String displayNumberPlayer(){
        // Creazione del frame principale
        JFrame frame = new JFrame("Seleziona Numero di Giocatori");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLayout(new BorderLayout());

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
        // Creazione del frame principale
        JFrame frame = new JFrame("Inserisci Nome");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BorderLayout());

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
                try {
                    // Recupero del testo dal campo di testo
                    username = textField.getText().trim();
                    ArrayList<String> alreadyUsedUsername = Server2.getClientsUsername();
                    if (alreadyUsedUsername.contains(username)) {
                        throw new IllegalArgumentException("Il nome è gia presente.");
                    }
                    frame.dispose();
                    synchronized (frame) {
                        frame.notify();
                    }
                } catch (IllegalArgumentException ex) {
                    // Gestione del caso in cui il nome sia vuoto
                    resultLabel.setText(ex.getMessage());
                }
            }
        });

        // Rendere visibile il frame
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

    public String displayColor( ArrayList<String> colors){
        // Creazione del frame principale
        JFrame frame = new JFrame("Seleziona Colore");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BorderLayout());

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
                // Recupero del colore selezionato dal menu a tendina
                try {
                    // Recupero del testo dal campo di testo
                    selectedColor = (String) comboBox.getSelectedItem();;
                    if (!colors.contains(selectedColor)) {
                        throw new IllegalArgumentException("Il colore non è disponibile.");
                    }
                    frame.dispose();
                    synchronized (frame) {
                        frame.notify();
                    }
                } catch (IllegalArgumentException ex) {
                    // Gestione del caso in cui il nome sia vuoto
                    resultLabel.setText(ex.getMessage());
                }
            }
        });

        // Rendere visibile il frame
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


    public void playgame(){
        // Apri la finestra del GameFrame
        GameFrame gameFrame = new GameFrame("CODEX NATURALIS");
        gameFrame.setVisible(true);
    }


    /**
     * Method to display the starter card and get on which side the
     * player wants to play it.
     *
     * @param starterCardID used to get the file path of the card
     * @return the chosen side
     * @author giacomofalcone
     */
    public String displayStarterCard(int starterCardID) {
        final String[] side = {""}; // Store selected side
        final Object lock = new Object();

        // Creazione del frame principale
        JFrame frame = new JFrame("Select starter card's side");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Use DISPOSE_ON_CLOSE to close only this window
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

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
     * @param objectiveCardIDs array of IDs used to get the file path of the cards
     * @return the index of the chosen card
     * @author giacomofalcone
     */
    public String displayObjectiveCards(int[] objectiveCardIDs) {
        final String[] selectedIndex = {""}; // Store selected index
        final Object lock = new Object();

        // Creazione del frame principale
        JFrame frame = new JFrame("Select an objective card");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Use DISPOSE_ON_CLOSE to close only this window
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2)); // Modifica layout per visualizzare le carte in una riga

        JLabel instructionLabel = new JLabel("Choose which secret objective card you want to use", SwingConstants.CENTER);
        frame.add(instructionLabel, BorderLayout.NORTH);

        for (int i = 0; i < objectiveCardIDs.length; i++) {
            String starterPath = "CodexNaturalis\\resources\\front\\img_" + objectiveCardIDs[i] + ".jpeg";

            try {
                BufferedImage cardImage = ImageIO.read(new File(starterPath));
                ImageIcon cardIcon = new ImageIcon(cardImage.getScaledInstance(250, 200, Image.SCALE_SMOOTH));
                JLabel cardLabel = new JLabel(cardIcon);
                cardLabel.setHorizontalAlignment(JLabel.CENTER);
                cardLabel.setVerticalAlignment(JLabel.CENTER);

                int index = i+1; // i start from 0, so add 1
                JButton selectButton = new JButton("Select");
                selectButton.addActionListener(e -> {
                    selectedIndex[0] = String.valueOf(index);
                    frame.dispose(); // Close the window
                    synchronized (lock) {
                        lock.notify(); // Notify waiting thread
                    }
                });

                JPanel cardPanel = new JPanel(new BorderLayout());
                cardPanel.add(cardLabel, BorderLayout.CENTER);
                cardPanel.add(selectButton, BorderLayout.SOUTH);

                panel.add(cardPanel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        frame.add(panel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        synchronized (lock) {
            try {
                lock.wait(); // Wait until user selects a card
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return selectedIndex[0];
    }

    /**
     * Method to ask the client if he wants to create or join a game
     *
     * @param countNotFullGame: how many games can be joined
     * @return client's choice
     * @author Foini Lorenzo
     */
    public String displayCreateJoinGame(int countNotFullGame){
        CreateJoinFrame createJoinFrame = new CreateJoinFrame("Create or join a game", countNotFullGame);
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
        JoinGameIndexFrame joinGameIndexFrame = new JoinGameIndexFrame("Game to join", joinGamesAndPlayers);
        return joinGameIndexFrame.getSelectedIndex();
    }

    public void displayWaitStartGame(boolean create){
        WaitStartGameFrame waitStartGameFrame = new WaitStartGameFrame("Wait for the starting of the game", create);
    }
}