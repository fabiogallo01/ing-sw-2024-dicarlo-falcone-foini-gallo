package it.polimi.ingsw.networking;

import it.polimi.ingsw.view.gui.ViewGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/**
 * Class representing client in a client-server architecture
 * It uses socket to establish connection and show/get server's/client's messages
 *
 *
 * @author Foini Lorenzo
 * @author Fabio Gallo
 */
public class Client2 {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;


    /**
     * Main method
     * It is necessary to establish connection to the server (if possible) and play the game
     *
     * @param args in the position [0] it contains the UI choice: TUI or GUI (GUI as default if args is empty)
     * @author Foini Lorenzo
     * @author Fabio Gallo
     */
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            try {
                if (args[0].equalsIgnoreCase("TUI")) {
                    startTUI(out, in); // Start communication with TUI
                } else {
                    startGUI(out, in); // Start communication with GUI
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                startGUI(out, in); // Start GUI as default without command line parameter
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is called when the client chooses to use TUI
     *
     * @author Foini Lorenzo
     */
    private static void startTUI(PrintWriter out, BufferedReader in) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Connected to the game server");//TUI

        String response;
        while ((response = in.readLine()) != null) {
            System.out.println(response);//TUI
            //TODO sostituire tutti gli or con una condizione sola, creando una classe di un genere di messaggi
            if (response.equals("Do you want to create a new game or join a game? (insert create/join):") ||
                    response.equals("Which game you want to join (insert 0 to exit):") ||
                    response.equals("Please insert a valid number of game (insert 0 to exit):") ||
                    response.equals("Enter number of players (insert 2/3/4):") ||
                    response.equals("Please insert 2/3/4:") ||
                    response.equals("Insert your username:") ||
                    response.equals("Username already in use. Please insert a new username:") ||
                    response.equals("Insert your color:") ||
                    response.equals("Invalid color. Please select a color from the previous list:") ||
                    response.equals("On which side you want to play the starter card (insert front/back):") ||
                    response.equals("Insert front/back:") ||
                    response.equals("Select your secret objective card (insert 1/2):") ||
                    response.equals("Which card you want to play (insert 1/2/3):") ||
                    response.equals("Please insert 1/2/3:") ||
                    response.equals("On which side you want to play this card (insert front/back):") ||
                    response.equals("Insert integer row value (from 0 to 80):") ||
                    response.equals("Insert integer column value (from 0 to 80):") ||
                    response.equals("Insert 1/2/3:") ||
                    response.equals("Insert the card's number (insert 1/2/3/4):")
            ) {
                String input = scanner.nextLine();//TUI
                out.println(input);//TUI
            }
        }
    }

    /**
     * This method is called when the client chooses to use GUI
     *
     * @author Foini Lorenzo
     * @author Fabio Gallo
     */
    private static void startGUI(PrintWriter out, BufferedReader in) throws IOException {
        ViewGUI viewGUI = new ViewGUI();
        String response;
        while ((response = in.readLine()) != null) {
            if (response.equals("Insert your username:")){
                out.println(viewGUI.displayUsername());
            }
            if(response.equals("Username already in use. Please insert a new username:")){
                // ERROR: This case is already managed in displayUsername() but doesn't work

                //out.println(viewGUI.displayUsername());
            }
            if (response.equals("Do you want to create a new game or join a game? (insert create/join):")){
                //out.println(viewGUI.displayChoice());
            }
            if (response.equals("Enter number of players (insert 2/3/4):")){
                out.println(viewGUI.displayNumberPlayer());
            }
            //TODO implement all the rest
        }
    }
}