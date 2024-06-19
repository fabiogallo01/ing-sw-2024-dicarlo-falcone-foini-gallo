package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.model.cards.*;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Class representing TUI
 * It has different methods which will be used in teh client-server communication using TUI
 *
 * @author Di Carlo Andrea, Falcone Giacomo
 */
public class ViewTUI {
    // ANSI escape codes for colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Method to print card's corners
     *
     * @param corner is the corner that has to be printed
     * @param left if true => print has to be done on the left
     *             if false => print has to be done on the right
     * @author Falcone Giacomo
     */
    public String printCorner(Corner corner, Boolean left){
        // Check if corner is visible => A card can be played on such corner
        if (corner.getVisible()){
            // Check if corner isn't empty
            if(!corner.getEmpty()) {
                // Now print colored kingdom based on the kingdom in such corner and its position
                if (corner.getKingdom() != Kingdom.NONE) {
                    if (corner.getKingdom() == Kingdom.ANIMALKINGDOM) {
                        if (!left) {
                            return (ANSI_CYAN + "  ANIMALKINGDOM" + ANSI_RESET);
                        } else {
                            return (ANSI_CYAN + "ANIMALKINGDOM  " + ANSI_RESET);
                        }
                    }
                    if (corner.getKingdom() == Kingdom.PLANTKINGDOM) {
                        if (!left) {
                            return (ANSI_GREEN + "   PLANTKINGDOM" + ANSI_RESET);
                        } else {
                            return (ANSI_GREEN + "PLANTKINGDOM   " + ANSI_RESET);
                        }
                    }
                    if (corner.getKingdom() == Kingdom.INSECTKINGDOM) {
                        if (!left) {
                            return (ANSI_PURPLE + "  INSECTKINGDOM" + ANSI_RESET);
                        } else {
                            return (ANSI_PURPLE + "INSECTKINGDOM  " + ANSI_RESET);
                        }
                    }
                    if (corner.getKingdom() == Kingdom.FUNGIKINGDOM) {
                        if (!left) {
                            return (ANSI_RED + "   FUNGIKINGDOM" + ANSI_RESET);
                        } else {
                            return (ANSI_RED + "FUNGIKINGDOM   " + ANSI_RESET);
                        }
                    }
                } else if(corner.getObject() != GameObject.NONE){
                    // Now print colored object based on the object in such corner and its position
                    if (corner.getObject() == GameObject.INKWELL) {
                        if (!left) {
                            return (ANSI_YELLOW + "        INKWELL" + ANSI_RESET);
                        } else {
                            return (ANSI_YELLOW + "INKWELL        " + ANSI_RESET);
                        }
                    }
                    if (corner.getObject() == GameObject.QUILL) {
                        if (!left) {
                            return (ANSI_YELLOW + "          QUILL" + ANSI_RESET);
                        } else {
                            return (ANSI_YELLOW + "QUILL          " + ANSI_RESET);
                        }
                    }
                    if (corner.getObject() == GameObject.MANUSCRIPT) {
                        if (!left) {
                            return (ANSI_YELLOW + "     MANUSCRIPT" + ANSI_RESET);
                        } else {
                            return (ANSI_YELLOW + "MANUSCRIPT     " + ANSI_RESET);
                        }
                    }
                } else {
                    // The corner is empty => Print EMPTY
                    if (!left) {
                        return "          EMPTY";
                    } else {
                        return "EMPTY          ";
                    }
                }
            }
            // There isn't a kingdom or an object in this corner, so print nothing
            else{
                return "               ";
            }
            return "               ";
        }
        // Corner is not visible => A card can't be played on such corner
        else {
            if (!left) {
                return "        COVERED";
            } else {
                return "COVERED        ";
            }
        }
    }

    /**
     * Method to display a given starter card
     *
     * @param starterCard given starter card to be displayed
     * @param out client's printWriter
     * @author Di Carlo Andrea, Falcone Giacomo
     */
    public void displayStarterCard(StarterCard starterCard, PrintWriter out){
        // Print the whole starter card
        out.println("\nThis is your starter card: \n" +
                "Front:\n" +
                "______________________________________________\n" +
                "|" + printCorner(starterCard.getFrontCorners()[0], true) + "              " + printCorner(starterCard.getFrontCorners()[1], false) +"|\n" +
                "           ");
        // Print the kingdom in the center of the card
        for (int i=0; i < starterCard.getFrontKingdoms().length; i++) {
            if (starterCard.getFrontKingdoms()[i] == Kingdom.ANIMALKINGDOM){
                out.println("             " + ANSI_CYAN + starterCard.getFrontKingdoms()[i] + ANSI_RESET + "   ");
            }
            if (starterCard.getFrontKingdoms()[i] == Kingdom.FUNGIKINGDOM){
                out.println("             " + ANSI_RED + starterCard.getFrontKingdoms()[i] + ANSI_RESET + "   ");
            }
            if (starterCard.getFrontKingdoms()[i] == Kingdom.INSECTKINGDOM){
                out.println("             " + ANSI_PURPLE + starterCard.getFrontKingdoms()[i] + ANSI_RESET + "   ");
            }
            if (starterCard.getFrontKingdoms()[i] == Kingdom.PLANTKINGDOM){
                out.println("             " + ANSI_GREEN + starterCard.getFrontKingdoms()[i] + ANSI_RESET + "   ");
            }
        }
        out.println(
                "\n" +
                "|" + printCorner(starterCard.getFrontCorners()[2], true) + "              " + printCorner(starterCard.getFrontCorners()[3], false) +"|\n" +
                "______________________________________________\n" +
                "\n" +
                "Back:\n" +
                "______________________________________________\n" +
                "|" + printCorner(starterCard.getBackCorners()[0], true) + "              " + printCorner(starterCard.getBackCorners()[1], false) +"|\n" +
                "\n" +
                "\n" +
                "|" + printCorner(starterCard.getBackCorners()[2], true) + "              " + printCorner(starterCard.getBackCorners()[3], false) +"|\n" +
                "______________________________________________\n"
                );
    }

    /**
     * Method to display a given resource card
     *
     * @param resourceCard given resource card to be displayed
     * @param out client's printWriter
     * @author Di Carlo Andrea, Falcone Giacomo
     */
    public void displayResourceCard(GamingCard resourceCard, PrintWriter out){
        // Print resource card's front and the kingdom in the back
        out.println("Resource Card: \nPoints: " + resourceCard.getPoints() + "\n" +
                "Front:\n" +
                "______________________________________________\n" +
                "|" + printCorner(resourceCard.getFrontCorners()[0], true) + "              " + printCorner(resourceCard.getFrontCorners()[1], false) +"|\n" +
                "\n" +
                "|" + printCorner(resourceCard.getFrontCorners()[2], true) + "              " + printCorner(resourceCard.getFrontCorners()[3], false) +"|\n" +
                "______________________________________________\n" +
                "Back kingdom: " + printKingdom(resourceCard.getKingdom()) + "\n");
    }

    /**
     * Method to display a colored kingdom
     *
     * @param kingdom given kingdom
     * @author all
     */
    public String printKingdom (Kingdom kingdom) {
        if (kingdom == Kingdom.ANIMALKINGDOM) {
            return (ANSI_CYAN + "ANIMALKINGDOM" + ANSI_RESET);
        }
        if (kingdom == Kingdom.PLANTKINGDOM) {
            return (ANSI_GREEN + "PLANTKINGDOM" + ANSI_RESET);
        }
        if (kingdom == Kingdom.INSECTKINGDOM) {
            return (ANSI_PURPLE + "INSECTKINGDOM" + ANSI_RESET);
        }
        return (ANSI_RED + "FUNGIKINGDOM" + ANSI_RESET);
    }

    /**
     * Method to display a given gold card
     *
     * @param goldCard given gold card to be displayed
     * @param out client's printWriter
     * @author Di Carlo Andrea, Falcone Giacomo
     */
    public void displayGoldCard(GoldCard goldCard, PrintWriter out){
        // Print gold card's front and the kingdom in the back
        out.println("Gold card: \nPoints: " + goldCard.getPoints() + "\n" +
                "Front:\n" +
                "______________________________________________\n" +
                "|" + printCorner(goldCard.getFrontCorners()[0], true) + "              " + printCorner(goldCard.getFrontCorners()[1], false) +"|\n" +
                "\n" +
                "|" + printCorner(goldCard.getFrontCorners()[2], true) + "              " + printCorner(goldCard.getFrontCorners()[3], false) +"|\n" +
                "______________________________________________" + "\n" + "Condition to make points: " + goldCard.getConditionPoint().toString());
                out.print("Condition to play the card:  ");
                for (int i=0; i < goldCard.getResources().length; i++) {
                    out.print(printKingdom(goldCard.getResources()[i]) + "     ");
                }
                out.println("\nBack kingdom: " + printKingdom(goldCard.getKingdom()) + "\n" );
    }

    /**
     * Method to display a given objective card
     *
     * @param objectiveCard given objective card to be displayed
     * @param out client's printWriter
     * @author giacomofalcone
     */
    public void displayObjectiveCard(ObjectiveCard objectiveCard, PrintWriter out) {
        // Print objective card
        out.println("Objective card:\nPoints:" + objectiveCard.getPoints());
        out.println("Type of objective:");
        if (objectiveCard.getFrontKingdom() != Kingdom.NONE) {
            // Objective with kingdoms
            if(objectiveCard.getPattern() == Pattern.NONE) {
                if (objectiveCard.getFrontKingdom() == Kingdom.ANIMALKINGDOM) {
                    out.println(ANSI_CYAN + "ANIMALKINGDOM\nANIMALKINGDOM\nANIMALKINGDOM" + ANSI_RESET);
                }
                if (objectiveCard.getFrontKingdom() == Kingdom.PLANTKINGDOM) {
                    out.println(ANSI_GREEN + "PLANTKINGDOM\nPLANTKINGDOM\nPLANTKINGDOM" + ANSI_RESET);
                }
                if (objectiveCard.getFrontKingdom() == Kingdom.INSECTKINGDOM) {
                    out.println(ANSI_PURPLE + "INSECTKINGDOM\nINSECTKINGDOM\nINSECTKINGDOM" + ANSI_RESET);
                }
                if (objectiveCard.getFrontKingdom() == Kingdom.FUNGIKINGDOM) {
                    out.println(ANSI_RED + "FUNGIKINGDOM\nFUNGIKINGDOM\nFUNGIKINGDOM" + ANSI_RESET);
                }
            } else {
                // Objective with pattern
                // Print the pattern
                if (objectiveCard.getPattern() == Pattern.LOWERLEFT) {
                    out.println(ANSI_GREEN +
                            "      ------\n" +
                            "      |     |\n" +
                            "      ------\n" +
                            "      ------\n" +
                            "      |     |\n" +
                            "      ------\n" + ANSI_RESET + ANSI_PURPLE +
                            "------\n" +
                            "|     |\n" +
                            "------\n" + ANSI_RESET);
                } else if (objectiveCard.getPattern() == Pattern.LOWERRIGHT) {
                    out.println(ANSI_RED +
                            "      ------\n" +
                            "      |     |\n" +
                            "      ------\n" +
                            "      ------\n" +
                            "      |     |\n" +
                            "      ------\n" + ANSI_RESET + ANSI_GREEN +
                            "            ------\n" +
                            "            |     |\n" +
                            "            ------\n" + ANSI_RESET);
                } else if (objectiveCard.getPattern() == Pattern.UPPERLEFT) {
                    out.println(ANSI_CYAN +
                            "------\n" +
                            "|     |\n" +
                            "------\n" + ANSI_RESET + ANSI_PURPLE +
                            "      ------\n" +
                            "      |     |\n" +
                            "      ------\n" +
                            "      ------\n" +
                            "      |     |\n" +
                            "      ------\n" + ANSI_RESET);
                } else if (objectiveCard.getPattern() == Pattern.UPPERRIGHT) {
                    out.println(ANSI_RED +
                            "             ------\n" +
                            "             |     |\n" +
                            "             ------\n" + ANSI_RESET + ANSI_CYAN +
                            "      ------\n" +
                            "      |     |\n" +
                            "      ------\n" +
                            "      ------\n" +
                            "      |     |\n" +
                            "      ------\n" + ANSI_RESET);
                } else if (objectiveCard.getPattern() == Pattern.PRIMARYDIAGONAL) {
                    if (objectiveCard.getFrontKingdom() == Kingdom.PLANTKINGDOM) {
                        out.println(ANSI_GREEN +
                                "------\n" +
                                "|     |\n" +
                                "------\n" +
                                "      ------\n" +
                                "      |     |\n" +
                                "      ------\n" +
                                "             ------\n" +
                                "             |     |\n" +
                                "             ------\n" + ANSI_RESET);
                    } else {
                        out.println(ANSI_PURPLE +
                                "------\n" +
                                "|     |\n" +
                                "------\n" +
                                "       ------\n" +
                                "       |     |\n" +
                                "       ------\n" +
                                "              ------\n" +
                                "              |     |\n" +
                                "              ------\n" + ANSI_RESET);
                    }
                } else if (objectiveCard.getPattern() == Pattern.SECONDARYDIAGONAL) {
                    if (objectiveCard.getFrontKingdom() == Kingdom.FUNGIKINGDOM) {
                        out.println(ANSI_RED +
                                "              ------\n" +
                                "              |     |\n" +
                                "              ------\n" +
                                "       ------\n" +
                                "       |     |\n" +
                                "       ------\n" +
                                "------\n" +
                                "|     |\n" +
                                "------\n" + ANSI_RESET);
                    } else {
                        out.println(ANSI_CYAN +
                                "              ------\n" +
                                "              |     |\n" +
                                "              ------\n" +
                                "       ------\n" +
                                "       |     |\n" +
                                "       ------\n" +
                                "------\n" +
                                "|     |\n" +
                                "------\n" + ANSI_RESET);
                    }
                }
            }
        } else { // Objective with objects
            for (int j = 0; j < objectiveCard.getObjects().length; j++) {
                if (objectiveCard.getObjects()[j] == GameObject.INKWELL) {
                    out.println(ANSI_YELLOW + "INKWELL" + ANSI_RESET);
                } else if (objectiveCard.getObjects()[j] == GameObject.QUILL) {
                    out.println(ANSI_YELLOW + "QUILL" + ANSI_RESET);
                } else { //if (objectiveCard.getObjects()[j] == GameObject.MANUSCRIPT) {
                    out.println(ANSI_YELLOW + "MANUSCRIPT" + ANSI_RESET);
                }
            }
        }
        out.print("\n");
    }

    /**
     * Method to display the hand of a player
     *
     * @param hand given hand to be displayed
     * @param out client's printWriter
     * @author Falcone Giacomo
     */
    public synchronized void displayHand(ArrayList<GamingCard> hand, PrintWriter out){
        out.println("This is your hand:\n");
        // Iterate through cards
        for (int i=0; i < hand.size(); i++) {
            // Display card using display card methods
            out.println("Card " + (i+1));
            if (hand.get(i) instanceof GoldCard){
                displayGoldCard((GoldCard) hand.get(i), out);
            } else { //if (hand.get(i) is a resource card)
                displayResourceCard(hand.get(i), out);
            }
        }
    }

    /**
     * Method to display the visible cards that can be drawn
     *
     * @param visibleCards given 4 visible cards of decks to be displayed
     * @param out client's printWriter
     * @author Falcone Giacomo
     */
    public synchronized void displayVisibleTableCard(ArrayList<GamingCard> visibleCards, PrintWriter out){
        out.println("Visible cards that can be drawn:\n");
        // Iterate through cards
        for (int i=0; i < visibleCards.size(); i++) {
            // Display card using display card methods
            out.println("Card " + (i+1));
            if (visibleCards.get(i) instanceof GoldCard){
                displayGoldCard((GoldCard) visibleCards.get(i), out);
            } else { //if (hand.get(i) is a resource card)
                displayResourceCard(visibleCards.get(i), out);
            }
        }
    }

    /**
     * Method to display the back of the top card of resource deck
     *
     * @param topResource given the top card of resource deck
     * @param out client's printWriter
     * @author Falcone Giacomo
     */
    public synchronized void displayTopResource(GamingCard topResource, PrintWriter out){
        // Display the colored kingdom of the card
        out.println("This is the card in top of resource deck: ");
        if(topResource.getKingdom() == Kingdom.ANIMALKINGDOM){
            out.println(ANSI_CYAN + "ANIMALKINGDOM" + ANSI_RESET);
        }
        if(topResource.getKingdom() == Kingdom.PLANTKINGDOM){
            out.println(ANSI_GREEN + "PLANTKINGDOM" + ANSI_RESET);
        }
        if(topResource.getKingdom() == Kingdom.INSECTKINGDOM){
            out.println(ANSI_PURPLE + "INSECTKINGDOM" + ANSI_RESET);
        }
        if(topResource.getKingdom() == Kingdom.FUNGIKINGDOM) {
            out.println(ANSI_RED + "FUNGIKINGDOM" + ANSI_RESET);
        }
        out.print("\n");
    }

    /**
     * Method to display the back of the top card of gold deck
     *
     * @param topGold given the top card of gold deck
     * @param out client's printWriter
     * @author Falcone Giacomo
     */
    public synchronized void displayTopGold(GoldCard topGold, PrintWriter out){
        out.println("This is the card in top of gold deck: ");
        // Display the colored kingdom of the card
        if(topGold.getKingdom() == Kingdom.ANIMALKINGDOM){
            out.println(ANSI_CYAN + "ANIMALKINGDOM" + ANSI_RESET);
        }
        if(topGold.getKingdom() == Kingdom.PLANTKINGDOM){
            out.println(ANSI_GREEN + "PLANTKINGDOM" + ANSI_RESET);
        }
        if(topGold.getKingdom() == Kingdom.INSECTKINGDOM){
            out.println(ANSI_PURPLE + "INSECTKINGDOM" + ANSI_RESET);
        }
        if(topGold.getKingdom() == Kingdom.FUNGIKINGDOM) {
            out.println(ANSI_RED + "FUNGIKINGDOM" + ANSI_RESET);
        }
        out.print("\n");
    }

    /**
     * Method to display the back of the top card of gold deck
     *
     * @param cards list of cards in player's area
     * @param out client's printWriter
     * @author Falcone Giacomo
     */
    public void displayArea(ArrayList<Card> cards, PrintWriter out){
        // Display starter cards
        out.println("Starter card 1:");
        StarterCard starterCard = (StarterCard) cards.getFirst();
        if(starterCard.getSide()){
            // Front
            out.println(
                    "______________________________________________\n" +
                    "|" + printCorner(starterCard.getFrontCorners()[0], true) + "              " + printCorner(starterCard.getFrontCorners()[1], false) +"|\n" +
                    "           ");
            for (int i=0; i < starterCard.getFrontKingdoms().length; i++) {
                if (starterCard.getFrontKingdoms()[i] == Kingdom.ANIMALKINGDOM){
                    out.println("             " + ANSI_CYAN + starterCard.getFrontKingdoms()[i] + ANSI_RESET + "   ");
                }
                if (starterCard.getFrontKingdoms()[i] == Kingdom.FUNGIKINGDOM){
                    out.println("             " + ANSI_RED + starterCard.getFrontKingdoms()[i] + ANSI_RESET + "   ");
                }
                if (starterCard.getFrontKingdoms()[i] == Kingdom.INSECTKINGDOM){
                    out.println("             " + ANSI_PURPLE + starterCard.getFrontKingdoms()[i] + ANSI_RESET + "   ");
                }
                if (starterCard.getFrontKingdoms()[i] == Kingdom.PLANTKINGDOM){
                    out.println("             " + ANSI_GREEN + starterCard.getFrontKingdoms()[i] + ANSI_RESET + "   ");
                }
            }
            out.println(
                    "\n" +
                    "|" + printCorner(starterCard.getFrontCorners()[2], true) + "              " + printCorner(starterCard.getFrontCorners()[3], false) +"|\n" +
                    "______________________________________________");
        } else {
            out.println("______________________________________________\n" +
                    "|" + printCorner(starterCard.getBackCorners()[0], true) + "              " + printCorner(starterCard.getBackCorners()[1], false) +"|\n" +
                    "\n" +
                    "\n" +
                    "|" + printCorner(starterCard.getBackCorners()[2], true) + "              " + printCorner(starterCard.getBackCorners()[3], false) +"|\n" +
                    "______________________________________________");
        }
        out.println("Row position:" + starterCard.getInGamePosition()[0]);
        out.println("Column position:" + starterCard.getInGamePosition()[1] + "\n");

        // Display all the other cards
        for(int i = 1; i < cards.size(); i++){
            if(cards.get(i) instanceof GoldCard){ // Is a gold card
                out.println("Gold card " + i + ":");
                GoldCard goldCard = (GoldCard) cards.get(i);
                if(goldCard.getSide()){
                    out.println("______________________________________________\n" +
                            "|" + printCorner(goldCard.getFrontCorners()[0], true) + "              " + printCorner(goldCard.getFrontCorners()[1], false) +"|\n" +
                            "\n" +
                            "|" + printCorner(goldCard.getFrontCorners()[2], true) + "              " + printCorner(goldCard.getFrontCorners()[3], false) +"|\n" +
                            "______________________________________________");
                } else {
                    out.println("______________________________________________\n" +
                            "|" + printCorner(goldCard.getBackCorners()[0], true) + "              " + printCorner(goldCard.getBackCorners()[1], false) +"|\n" +
                            "               "  + printKingdom(goldCard.getKingdom()) + "\n" + "\n" +
                            "|" + printCorner(goldCard.getBackCorners()[2], true) + "              " + printCorner(goldCard.getBackCorners()[3], false) +"|\n" +
                            "______________________________________________");
                }
            } else { // Is a resource card
                out.println("Resource card " + i + ":");
                GamingCard resourceCard = (GamingCard) cards.get(i);
                if(resourceCard.getSide()){
                    out.println(
                            "______________________________________________\n" +
                            "|" + printCorner(resourceCard.getFrontCorners()[0], true) + "              " + printCorner(resourceCard.getFrontCorners()[1], false) +"|\n" +
                            "\n" +
                            "|" + printCorner(resourceCard.getFrontCorners()[2], true) + "              " + printCorner(resourceCard.getFrontCorners()[3], false) +"|\n" +
                            "______________________________________________");
                } else {
                    out.println("______________________________________________\n" +
                            "|" + printCorner(resourceCard.getBackCorners()[0], true) + "              " + printCorner(resourceCard.getBackCorners()[1], false) +"|\n" +
                            "\n" +   "               "  + printKingdom(resourceCard.getKingdom()) + "\n" + "\n" +
                            "|" + printCorner(resourceCard.getBackCorners()[2], true) + "              " + printCorner(resourceCard.getBackCorners()[3], false) +"|\n" +
                            "______________________________________________");
                }
            }
            out.println("Row position:" + cards.get(i).getInGamePosition()[0]);
            out.println("Column position:" + cards.get(i).getInGamePosition()[1] + "\n");
        }
    }
}