package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.game.*;

import java.io.PrintWriter;
import java.util.*;

/**
 * Class representing GUI
 * It has different methods which will be used in the client-server communication using GUI
 * Such methods use Swing
 *
 * @author Di Carlo Andrea, Falcone Giacomo, Foini Lorenzo
 */
public class ViewGUI {

    /**
     * Method to display numPlayersFrame
     *
     * @return string value of the number of player
     * @author Foini Lorenzo
     */
    public String displayNumberPlayer(){
        NumPlayersFrame numPlayersFrame = new NumPlayersFrame("SELECT NUMBER OF PLAYERS");
        return numPlayersFrame.getSelectedNumPlayers();
    }

    /**
     * Method to display askUsernameFrame
     *
     * @return selected username
     * @author Di Carlo Andrea, Foini Lorenzo
     */
    public String displayUsername(){
        AskUsernameFrame askUsernameFrame = new AskUsernameFrame("INSERT USERNAME");
        return askUsernameFrame.getUsername();
    }

    /**
     * Method to display askValidUsernameFrame
     *
     * @param previousUsername insert by client, and it wasn't valid
     * @return selected username
     * @author Foini Lorenzo
     */
    public String displayRepeatedUsername(String previousUsername){
        AskUsernameFrame askValidUsernameFrame = new AskUsernameFrame("INSERT VALID USERNAME", previousUsername);
        return askValidUsernameFrame.getUsername();
    }

    /**
     * Method to display getColorFrame
     *
     * @param colors: list of colors (as strings)
     * @return selected color as a string
     * @author Di Carlo Andrea, Foini Lorenzo
     */
    public String displayColor(ArrayList<String> colors){
        AskColorFrame getColorFrame = new AskColorFrame("INSERT COLOR", colors);
        return getColorFrame.getSelectedColor();
    }

    /**
     * Method to display getValidColorFrame
     *
     * @param colors: list of colors (as strings)
     * @param previousColor insert by client, and it wasn't valid
     * @return selected color as a string
     * @author Foini Lorenzo
     */
    public String displayInvalidColor(ArrayList<String> colors, String previousColor){
        AskColorFrame getValidColorFrame = new AskColorFrame("INSERT VALID COLOR", colors, previousColor);
        return getValidColorFrame.getSelectedColor();
    }

    /**
     * Method to display the starter card and get on which side the
     * player wants to play it.
     *
     * @param starterCardID used to get the file path of the card
     * @return the chosen side as a string: "front" or "back"
     * @author Foini Lorenzo
     */
    public String displayStarterCard(int starterCardID) {
        StarterCardFrame getStarterCardFrame = new StarterCardFrame("SELECT STARTER CARD'S SIDE", starterCardID);
        return getStarterCardFrame.getSelectedSide();
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
     * @author Foini Lorenzo
     */
    public String displayObjectiveCards(String starterCardSide, int starterCardID, int[] handCardIDs, int[] commonObjectiveCardIDs, int[] secretObjectiveCardIDs) {
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
        JoinGameIndexFrame joinGameIndexFrame = new JoinGameIndexFrame("SELECT GAME TO JOIN", joinGamesAndPlayers);
        return joinGameIndexFrame.getSelectedIndex();
    }

    /**
     * Method to display WaitStartGameFrame
     *
     * @param create: true => Client wants to create a new game
     *                false => Client wants to join a game
     * @return references of the WaitStartGameFrame
     * @author Foini Lorenzo
     */
    public WaitStartGameFrame displayWaitStartGame(boolean create){
        return new WaitStartGameFrame("WAIT START OF THE GAME", create);
    }

    /**
     * Method to display GameFrame, which represents the "main frame" of the game
     *
     * @param out: client's printWriter
     * @param player: client's player's reference
     * @param gameTable: gameTable of the game
     * @param counterResources: list which contains the counter of the resources in player's area
     * @param invalidPlay: contains invalid play error
     * @param mistakePlay: contains which type of error has been made by the client
     * @return references of the GameFrame
     * @author Foini Lorenzo
     */
    public GameFrame playGame(PrintWriter out, Player player, GameTable gameTable, ArrayList<Integer> counterResources, String invalidPlay, String mistakePlay){
        return new GameFrame("CODEX NATURALIS", out, player, gameTable, counterResources, invalidPlay, mistakePlay);
    }

    /**
     * Method to display drawCardFrame
     *
     * @param gameTable: gameTable of the game
     * @return integer value of where the client's wants to draw
     * @author Foini Lorenzo
     */
    public int displayDrawChoice(GameTable gameTable){
        DrawCardFrame drawCardFrame = new DrawCardFrame("SELECT FROM WHERE YOU WANT DO DRAW", gameTable, true);
        return drawCardFrame.getIndexSelectedCard();
    }

    /**
     * Method to display WaitEndGameFrame
     *
     * @return reference of the WaitEndGameFrame
     * @author Foini Lorenzo
     */
    public WaitEndGameFrame displayWaitEndGame(){
        return new WaitEndGameFrame("WAIT END OF THE GAME");
    }

    /**
     * Method to display PostGameFrame
     *
     * @author Foini Lorenzo
     */
    public void displayPostGame(String winnerMessage, boolean hasWon, ArrayList<String> finalScoreboard){
        new PostGameFrame("END GAME", winnerMessage, hasWon, finalScoreboard);
    }

    /**
     * Method to display GameCrashedFrame
     *
     * @author Foini Lorenzo
     */
    public void displayGameCrashed(){
        new GameCrashedFrame("GAME CRASHED");
    }
}