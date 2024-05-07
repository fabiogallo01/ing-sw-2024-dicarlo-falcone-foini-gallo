package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.networking.Server;

import java.io.PrintWriter;
import java.util.ArrayList;

public class View {

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
     * @author giacomofalcone
     */
    public String printCorner(Corner corner, Boolean left){
        //TODO What does that a corner is visible mean?
        if(corner.getVisible()){
            if(corner.getEmpty()){
                if(!left){
                    return "          EMPTY";}
                else{
                    return "EMPTY          ";}
            } else if (corner.getKingdom() != Kingdom.NONE) {
                if(corner.getKingdom() == Kingdom.ANIMALKINGDOM){
                    if(!left) {
                        return (ANSI_CYAN + "  ANIMALKINGDOM" + ANSI_RESET);}
                    else{
                        return (ANSI_CYAN + "ANIMALKINGDOM  " + ANSI_RESET);}
                }
                if(corner.getKingdom() == Kingdom.PLANTKINGDOM){
                    if(!left) {
                        return (ANSI_GREEN + "   PLANTKINGDOM" + ANSI_RESET);}
                    else {
                        return (ANSI_GREEN + "PLANTKINGDOM   " + ANSI_RESET);}
                }
                if(corner.getKingdom() == Kingdom.INSECTKINGDOM){
                    if(!left) {
                        return (ANSI_PURPLE + "  INSECTKINGDOM" + ANSI_RESET);}
                    else {
                        return (ANSI_PURPLE + "INSECTKINGDOM  " + ANSI_RESET);}
                }
                if(corner.getKingdom() == Kingdom.FUNGIKINGDOM) {
                    if(!left) {
                        return (ANSI_RED + "   FUNGIKINGDOM" + ANSI_RESET);}
                    else {
                        return (ANSI_RED + "FUNGIKINGDOM   " + ANSI_RESET);}
                }
            } else {
                if(corner.getObject() == GameObject.INKWELL){
                    if(!left) {
                        return (ANSI_YELLOW + "        INKWELL" + ANSI_RESET);}
                    else {
                            return (ANSI_YELLOW + "INKWELL        " + ANSI_RESET);}
                }
                if(corner.getObject() == GameObject.QUILL){
                    if(!left) {
                        return (ANSI_YELLOW + "          QUILL" + ANSI_RESET);}
                    else {
                        return (ANSI_YELLOW + "QUILL          " + ANSI_RESET);}
                }
                if(corner.getObject() == GameObject.MANUSCRIPT) {
                    if(!left) {
                        return (ANSI_YELLOW + "     MANUSCRIPT" + ANSI_RESET);}
                    else {
                        return (ANSI_YELLOW + "MANUSCRIPT     " + ANSI_RESET);}
                }
            }
        }
        else {
            return "               ";
        }
        return "               ";

    }

    /*
    public String printCorner(Corner corner){
        if(corner.getVisible()){
            if(corner.getEmpty()){
                return "EMPTY";
            } else if (corner.getKingdom() != Kingdom.NONE) {
                return corner.getKingdom().toString();
            } else {
                return corner.getObject().toString();
            }
        }
        else {
            return "            ";
        }
    }*/


    /**
     * Method to display a given starter card
     *
     * @param starterCard given starter card to be displayed
     * @author giacomofalcone
     */
    public synchronized void displayStarterCard(StarterCard starterCard, PrintWriter out){
        out.println("This is your starter card: \n" +
                "Front:\n" +
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
     * @author AndreaDiC11
     */
    public void displayResourceCard(GamingCard resourceCard, PrintWriter out){
        out.println("Resource Card: \nPoints: " + resourceCard.getPoints() + "\n" +
                "Front:\n" +
                "______________________________________________\n" +
                "|" + printCorner(resourceCard.getFrontCorners()[0], true) + "              " + printCorner(resourceCard.getFrontCorners()[1], false) +"|\n" +
                "\n" +
                "|" + printCorner(resourceCard.getFrontCorners()[2], true) + "              " + printCorner(resourceCard.getFrontCorners()[3], false) +"|\n" +
                "______________________________________________\n" +
                "\n" +
                "Back:\n" +
                "______________________________________________\n" +
                "|" + printCorner(resourceCard.getBackCorners()[0], true) + "              " + printCorner(resourceCard.getBackCorners()[1], false) +"|\n" +
                "\n" +
                "               "  + resourceCard.getKingdom().toString() + "\n" + "\n" +
                "|" + printCorner(resourceCard.getBackCorners()[2], true) + "              " + printCorner(resourceCard.getBackCorners()[3], false) +"|\n" +
                "______________________________________________\n"
        );
    }

    /**
     * Method to display a given gold card
     *
     * @param goldCard given gold card to be displayed
     * @author AndreaDiC11
     */
    public void displayGoldCard(GoldCard goldCard, PrintWriter out){
        out.println("Gold card: \nPoints: " + goldCard.getPoints() + "\n" +
                "Front:\n" +
                "______________________________________________\n" +
                "|" + printCorner(goldCard.getFrontCorners()[0], true) + "              " + printCorner(goldCard.getFrontCorners()[1], false) +"|\n" +
                "\n" +
                "|" + printCorner(goldCard.getFrontCorners()[2], true) + "              " + printCorner(goldCard.getFrontCorners()[3], false) +"|\n" +
                "______________________________________________" + "\n" + "Condition to make points: " + goldCard.getConditionPoint().toString());
                out.print("Condition to play the card:  ");
                for (int i=0; i < goldCard.getResources().length; i++) {
                    out.print(goldCard.getResources()[i].toString() + "     ");
                }
                out.println("\n" +
                "Back:\n" +
                "______________________________________________\n" +
                "|" + printCorner(goldCard.getBackCorners()[0], true) + "              " + printCorner(goldCard.getBackCorners()[1], false) +"|\n" +
                "\n" +
                "               "  + goldCard.getKingdom().toString() + "\n" + "\n" +
                "|" + printCorner(goldCard.getBackCorners()[2], true) + "              " + printCorner(goldCard.getBackCorners()[3], false) +"|\n" +
                "______________________________________________\n"
        );
    }

    /**
     * Method to display a given objective card
     *
     * @param objectiveCard given objective card to be displayed
     * @author giacomofalcone
     */
    public void displayObjectiveCard(ObjectiveCard objectiveCard, PrintWriter out) {
        out.println("Objective card: \nPoints:" + objectiveCard.getPoints());
        if (objectiveCard.getFrontKingdom() != Kingdom.NONE) {
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
        } else { // (objectiveCard.getObjects()[0] != GameObject.NONE)
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
        out.println("\n");
    }

    /**
     * Method to display the hand of a player
     *
     * @param hand given hand to be displayed
     * @author giacomofalcone
     */
    public synchronized void displayHand(ArrayList<GamingCard> hand, PrintWriter out){
        out.println("This is your hand:\n");
        for (int i=0; i < 3; i++) {
            out.println("Card " + (i+1) + ":\n");
            if (hand.get(i) instanceof GoldCard){
                displayGoldCard((GoldCard) hand.get(i), out);
            } else { //if (hand.get(i) is a resource card)
                displayResourceCard(hand.get(i), out);
            }
            out.println("\n");
        }
    }

    /**
     * Method to display the visible cards that can be drawn
     *
     * @param visibleCards given 4 visible cards of decks to be displayed
     * @author giacomofalcone
     */
    public synchronized void displayVisibleTableCard(ArrayList<GamingCard> visibleCards, PrintWriter out){
        out.println("Visible cards that can be drawn:\n");
        for (int i=0; i < 4; i++) {
            out.println("Card " + (i+1) + ":\n");
            if (visibleCards.get(i) instanceof GoldCard){
                displayGoldCard((GoldCard) visibleCards.get(i), out);
            } else { //if (hand.get(i) is a resource card)
                displayResourceCard(visibleCards.get(i), out);
            }
            out.println("\n");
        }
    }
}
