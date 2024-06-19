package it.polimi.ingsw.networking;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.game.Player;

/**
 * Server's class
 * It contains a list of controllers, so that the server can run multiple games.
 * It also contains a list of the usernames.
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
            System.out.println("Server is listening on port " + PORT + ". IP: " + getWirelessIPAddress().getHostAddress());

            //This loop will accept all the new client connections
            while (true) {
                for (Controller controller : controllers) {
                    if(controller.getDisconnectedPlayers() == controller.getGameTable().getNumPlayers()){
                        controllers.remove(controller);
                        for(Player player: controller.getGameTable().getPlayers()){
                            clientsUsernames.remove(player.getUsername());
                        }
                    }
                }//if all the players disconnected, it removes the controller from the list and frees the usernames
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
/**
 * It counts the games that did not start yet, since they are waiting for more players to join
 * @return games that can be joined
 * @author Lorenzo Foini
 *
 */
    public static int countGameNotFull(){
        int count = 0;
        for(Controller controller:controllers){
            if(!controller.getGameTable().isFull()){
                count++;
            }
        }
        return count;
    }

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
                            // Found a non-loopback site-local address, return it immediately
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
