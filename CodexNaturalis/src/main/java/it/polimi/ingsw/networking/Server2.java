package it.polimi.ingsw.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.controller.Controller;


public class Server2 {
    private static final int PORT = 12345;
    private static List<Controller> controllers = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler2 clientHandler = new ClientHandler2(socket, controllers);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Controller> getControllers() {
        return controllers;
    }
    public static void addController(Controller controller) {
        controllers.add(controller);
    }
}
