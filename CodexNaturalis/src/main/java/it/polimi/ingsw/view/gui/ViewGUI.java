package it.polimi.ingsw.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Class representing GUI
 * It has different methods which will be used in the client-server communication using GUI
 * Made using Swing
 *
 * @author Foini Lorenzo
 */

public class ViewGUI extends JFrame {

    private static int clientCount = 0; // Contatore statico per i client
    private JTextField playerNameField;
    private JComboBox<String> playerColorComboBox;
    private JComboBox<Integer> numberOfPlayersComboBox;

    public ViewGUI() {
    }

    public ArrayList<String> startGuiFirst(){
        setTitle("Player Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        // Number of players
        JLabel numberOfPlayersLabel = new JLabel("Numero di giocatori:");
        Integer[] playerNumbers = {1, 2, 3, 4}; // Numero massimo di giocatori
        numberOfPlayersComboBox = new JComboBox<>(playerNumbers);
        panel.add(numberOfPlayersLabel);
        panel.add(numberOfPlayersComboBox);


        // Name of player
        JLabel playerNameLabel = new JLabel("Nome del giocatore:");
        playerNameField = new JTextField();
        panel.add(playerNameLabel);
        panel.add(playerNameField);


        // Player's color
        JLabel playerColorLabel = new JLabel("Colore del giocatore:");
        String[] colors = {"Rosso", "Blu", "Verde", "Giallo"};
        playerColorComboBox = new JComboBox<>(colors);
        panel.add(playerColorLabel);
        panel.add(playerColorComboBox);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitAction();
            }
        });
        panel.add(new JLabel()); // Placeholder for layout symmetry
        panel.add(submitButton);

        add(panel);

        setVisible(true);



        String playerName = playerNameField.getText();
        String playerColor = (String) playerColorComboBox.getSelectedItem();
        int numberOfPlayers = (int) numberOfPlayersComboBox.getSelectedItem();
        ArrayList<String> loginInfo = new ArrayList<String>();
        loginInfo.add(String.valueOf(numberOfPlayers));
        loginInfo.add(playerName);
        loginInfo.add(playerColor);

        return loginInfo;
    }

    private void submitAction() {



        // Chiudi la finestra di login
        dispose();


    }

    public void playgame(){
        // Apri la finestra del GameFrame
        GameFrame gameFrame = new GameFrame("CODEX NATURALIS");
        gameFrame.setVisible(true);
    }
}
