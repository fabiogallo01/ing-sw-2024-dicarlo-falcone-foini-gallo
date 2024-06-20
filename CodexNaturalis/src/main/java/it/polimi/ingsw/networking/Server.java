package it.polimi.ingsw.networking;

import java.io.IOException;
import java.net.*;
import java.util.*;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.game.Player;

/**
 * Server's class
 * It contains a list of controllers, so that the server can run multiple games.
 * It also contains a list of the usernames.
 *
 * @author Foini Lorenzo, Gallo Fabio
 * */
public class Server {
    private static final int PORT = 12345;
    private static final List<Controller> controllers = new ArrayList<>();
    private static final ArrayList<String> clientsUsernames = new ArrayList<>();

    /**
     * Main method for the server
     * It starts the server and lets it accept new connections
     *
     * @param args: CLI parameters
     * @author Foini Lornezo, Gallo Fabio
     */
    public static void main(String[] args) {
        // Create new server socket
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Print server IP (using method getWirelessIPAddress()) and port of the connection
            System.out.println("Server IP: " + getWirelessIPAddress().getHostAddress() + "\nServer is listening on port: " + PORT + "\n");

            // This loop will accept all the new client connections
            while (true) {
                // Need to synchronized on controllers for correct access to controllers
                synchronized (controllers) {
                    // use Iterator for iterate through controllers
                    Iterator<Controller> iterator = controllers.iterator();
                    while (iterator.hasNext()) {
                        // Get next
                        Controller controller = iterator.next();
                        // Check if there are come disconnected players
                        if (controller.getDisconnectedPlayers() == controller.getGameTable().getNumPlayers()) {
                            iterator.remove();
                            // Remove username of the disconnected players
                            for (Player player : controller.getGameTable().getPlayers()) {
                                clientsUsernames.remove(player.getUsername());
                            }
                        }
                    }
                }

                // Create socket
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // Create and start new handler for the connected client
                ClientHandler clientHandler = new ClientHandler(socket, controllers);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * clientsUsernames getter
     *
     * @return list of usernames
     * @author Gallo Fabio
     */
    public static ArrayList<String> getClientsUsername() {
        return clientsUsernames;
    }

    /**
     * Method for adding a client's username in the list of usernames
     *
     * @param clientUsername to be added in the list
     * @author Foini Lorenzo
     */
    public static void addClientUsername(String clientUsername) {
        clientsUsernames.add(clientUsername);
    }

    /**
     * Method for adding a controller in the list of controller
     *
     * @param controller to be added in the list
     * @author Gallo Fabio
     */
    public static void addController(Controller controller) {
        controllers.add(controller);
    }

    /**
    * Method for counting the games that did not start yet, since they are waiting for more players to join
     *
    * @return number of games that can be joined
    * @author Foini Lorenzo
    */
    public static int countGameNotFull(){
        int count = 0;
        // Iterate through controllers
        for(Controller controller:controllers){
            // If the gameTable is not full => Increase count by 1
            if(!controller.getGameTable().isFull()){
                count++;
            }
        }
        return count;
    }

    /**
     * Method for getting IP of the server
     *
     * @throws SocketException if happen
     * @author Gallo Fabio
     */
    public static InetAddress getWirelessIPAddress() throws SocketException {
        try {
            // Iterate all NICs (Network Interface Cards)
            for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = ifaces.nextElement();
                // Check if the interface is a wireless interface
                if (iface.isUp() && (iface.getDisplayName().contains("Wireless") || iface.getName().startsWith("wlan"))) {
                    // Iterate all IP addresses assigned to the wireless interface
                    for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                        InetAddress inetAddr = inetAddrs.nextElement();
                        if (!inetAddr.isLoopbackAddress() && inetAddr.isSiteLocalAddress()) {
                            // Found a non-loop back site-local address, return it immediately
                            return inetAddr;
                        }
                    }
                }
            }
            throw new SocketException("No wireless LAN address found.");
        } catch (Exception e) {
            throw new SocketException("Failed to determine wireless LAN address: " + e);
        }
    }
}