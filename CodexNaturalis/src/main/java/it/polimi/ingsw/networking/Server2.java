package it.polimi.ingsw.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.controller.Controller;

/**
 * Server's class
 * @author Gallo Fabio
 * @author Foini Lorenzo
 * */
public class Server2 {
    private static final int PORT = 12345;
    private static List<Controller> controllers = new ArrayList<>();
    private static final ArrayList<String> clientsUsernames = new ArrayList<>();

    /**
     * Main method for the server
     * It starts the server and lets it accept new connections
     * @author Gallo Fabio
     * @author Foini Lorenzo
     * */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            //This loop will accept all the new client connections
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler2 clientHandler = new ClientHandler2(socket, controllers);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }//serverSocket is closed here
    }

    public static ArrayList<String> getClientsUsername() {
        return clientsUsernames;
    }

    public static List<Controller> getControllers() {
        return controllers;
    }

    public static void addController(Controller controller) {
        controllers.add(controller);
    }

    public static void addClientUsername(String clientUsername) {
        clientsUsernames.add(clientUsername);
    }

    public static int countGameNotFull(){
        int count = 0;
        for(Controller controller:controllers){
            if(!controller.getGameTable().isFull()){
                count++;
            }
        }
        return count;
    }
}
