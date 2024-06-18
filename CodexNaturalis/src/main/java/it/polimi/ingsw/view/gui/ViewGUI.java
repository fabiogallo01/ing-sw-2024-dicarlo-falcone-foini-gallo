package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.game.*;

import java.io.PrintWriter;
import java.util.*;

/**
 * Class representing GUI
 * It has different methods which will be used in the client-server communication using GUI
 * Such methods use Swing
 *
 * @author Foini Lorenzo
 */
public class ViewGUI {

    public String displayNumberPlayer(){
        // TODO: Improve Frame graphics: add font, color, background, ...
        // See other classes in gui package for example and better understanding
        NumPlayersFrame numPlayersFrame = new NumPlayersFrame("SELECT NUMBER OF PLAYERS");
        return numPlayersFrame.getSelectedNumPlayers();
    }

    public String displayUsername(){
        // TODO: Improve Frame graphics: add font, color, background, ...
        AskUsernameFrame askUsernameFrame = new AskUsernameFrame("INSERT USERNAME");
        return askUsernameFrame.getUsername();
    }

    public String displayRepeatedUsername(String previousUsername){
        // TODO: Improve Frame graphics: add font, color, background, ...
        AskUsernameFrame askValidUsernameFrame = new AskUsernameFrame("INSERT VALID USERNAME", previousUsername);
        return askValidUsernameFrame.getUsername();
    }

    public String displayColor(ArrayList<String> colors){
        // TODO: Improve Frame graphics: add font, color, background, ...
        AskColorFrame getColorFrame = new AskColorFrame("INSERT COLOR", colors);
        return getColorFrame.getSelectedColor();
    }

    public String displayInvalidColor(ArrayList<String> colors, String previousColor){
        // TODO: Improve Frame graphics: add font, color, background, ...
        AskColorFrame getValidColorFrame = new AskColorFrame("INSERT VALID COLOR", colors, previousColor);
        return getValidColorFrame.getSelectedColor();
    }

    /**
     * Method to display the starter card and get on which side the
     * player wants to play it.
     *
     * @param starterCardID used to get the file path of the card
     * @return the chosen side as a string: "front" or "back"
     * @author giacomofalcone
     */
    public String displayStarterCard(int starterCardID) {
        // TODO: Improve Frame graphics: add font, color, background, ...
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
     * @author giacomofalcone
     */
    public String displayObjectiveCards(String starterCardSide, int starterCardID, int[] handCardIDs, int[] commonObjectiveCardIDs, int[] secretObjectiveCardIDs) {
        // TODO: Improve Frame graphics: add font, color, background, ...
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
        // TODO: Improve Frame graphics: add font, color, background, ...
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
        // TODO: Improve Frame graphics: add font, color, background, ...
        JoinGameIndexFrame joinGameIndexFrame = new JoinGameIndexFrame("SELECT GAME TO JOIN", joinGamesAndPlayers);
        return joinGameIndexFrame.getSelectedIndex();
    }

    public WaitStartGameFrame displayWaitStartGame(boolean create){
        // TODO: Improve Frame graphics: add font, color, background, ...
        // TODO: Close the window when the game started
        return new WaitStartGameFrame("WAIT START OF THE GAME", create);
    }

    public GameFrame playGame(PrintWriter out, Player player, GameTable gameTable, ArrayList<Integer> counterResources, String invalidPlay, String mistakePlay){
        return new GameFrame("CODEX NATURALIS", out, player, gameTable, counterResources, invalidPlay, mistakePlay);
    }

    public int displayDrawChoice(GameTable gameTable){
        DrawCardFrame drawCardFrame = new DrawCardFrame("SELECT FROM WHERE YOU WANT DO DRAW", gameTable, true);
        return drawCardFrame.getIndexSelectedCard();
    }

    public WaitEndGameFrame displayWaitEndGame(){
        // TODO: Improve Frame graphics: add font, color, background, ...
        return new WaitEndGameFrame("WAIT END OF THE GAME");
    }

    public void displayPostGame(String winnerMessage, boolean hasWon, ArrayList<String> finalScoreboard){
        new PostGameFrame("END GAME", winnerMessage, hasWon, finalScoreboard);
    }

    public void displayGameCrashed(){
        // TODO: Improve Frame graphics: add font, color, background, ...
        new GameCrashedFrame("GAME CRASHED");
    }
}