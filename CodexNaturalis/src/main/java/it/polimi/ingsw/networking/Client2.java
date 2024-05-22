package it.polimi.ingsw.networking;

import it.polimi.ingsw.view.gui.ViewGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

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

    private static void startTUI(PrintWriter out, BufferedReader in) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Connected to the game server");//TUI

        String response;
        while ((response = in.readLine()) != null) {
            System.out.println(response);//TUI
            //TODO sostituire tutti gli or con una condizione sola, creando una classe di un genere di messaggi
            if (response.equals("Enter your choice (create/join):") ||
                    response.equals("Enter number of players (2-4):") ||
                    response.equals("Specify the number of players (insert 2, 3 or 4):") ||
                    response.equals("Insert your username:") ||
                    response.equals("Username already in use. Please insert a new username:") ||
                    response.equals("Insert your color:") ||
                    response.equals("Invalid color. Please select a color from the list") ||
                    response.equals("On which side you want to play the starter card (insert front or back):") ||
                    response.equals("Insert front or back:") ||
                    response.equals("Select your secret objective card (insert 1 or 2):") ||
                    response.equals("Which card you want to play (insert 1, 2 or 3):") ||
                    response.equals("Please insert 1, 2 or 3:") ||
                    response.equals("On which side you want to play this card(insert front or back):") ||
                    response.equals("Insert integer row value:") ||
                    response.equals("Insert integer column value:") ||
                    response.equals("Insert 1, 2 or 3:") ||
                    response.equals("Insert the card's number:") ||
                    response.equals("Do you want to join this game? (Y/N)")
            ) {
                String input = scanner.nextLine();//TUI
                out.println(input);//TUI
            }
        }
    }

    private static void startGUI(PrintWriter out, BufferedReader in) throws IOException {
        ViewGUI viewGUI = new ViewGUI();
        String response;
        while ((response = in.readLine()) != null) {
            if (response.equals("Enter number of players (2-4):")){
                out.println(viewGUI.displayNumberPlayer());
            }
        }
    }
}
