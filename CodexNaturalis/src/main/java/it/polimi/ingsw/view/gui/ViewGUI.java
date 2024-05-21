package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.networking.Server;

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

    private static int numPlayers = 0; // Contatore statico per i client
    private static String username;
    private static String selectedColor;
    private JTextField playerNameField;
    private JComboBox<String> playerColorComboBox;
    private JComboBox<Integer> numberOfPlayersComboBox;

    public ViewGUI() {
    }

    public int displayNumberPlayer(){
        // Creazione del frame principale
        JFrame frame = new JFrame("Seleziona Numero di Giocatori");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BorderLayout());


        // Creazione del pannello per l'input
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Etichetta per il menu a tendina
        JLabel label = new JLabel("Numero di Giocatori:");

        // Menu a tendina per la selezione del numero di giocatori
        Integer[] playerNumbers = {1, 2, 3, 4};
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
            }
        });

        // Rendere visibile il frame
        frame.setVisible(true);
        return numPlayers;
    }

    public String displayUsername(){
        // Creazione del frame principale
        JFrame frame = new JFrame("Inserisci Nome");
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
                    ArrayList<String> alreadyUsedUsername = Server.getClientUsername();
                    if (alreadyUsedUsername.contains(username)) {
                        throw new IllegalArgumentException("Il nome è gia presente.");
                    }
                } catch (IllegalArgumentException ex) {
                    // Gestione del caso in cui il nome sia vuoto
                    resultLabel.setText(ex.getMessage());
                }
            }
        });

        // Rendere visibile il frame
        frame.setVisible(true);
        return username;
    }

    public String displayColor( ArrayList<String> colors){
        // Creazione del frame principale
        JFrame frame = new JFrame("Seleziona Colore");
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
                } catch (IllegalArgumentException ex) {
                    // Gestione del caso in cui il nome sia vuoto
                    resultLabel.setText(ex.getMessage());
                }
            }
        });

        // Rendere visibile il frame
        frame.setVisible(true);
        return selectedColor;
    }




    public void playgame(){
        // Apri la finestra del GameFrame
        GameFrame gameFrame = new GameFrame("CODEX NATURALIS");
        gameFrame.setVisible(true);
    }
}
