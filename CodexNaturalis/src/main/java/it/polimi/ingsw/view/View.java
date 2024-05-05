package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.cards.*;

import java.io.PrintWriter;

public class View {

    public String printCorner(Corner corner){
        if(corner.getVisible()){
            if(corner.getEmpty()){
                return "EMPTY";
            } else if (corner.getKingdom() != Kingdom.NONE) {
                if(corner.getKingdom() == Kingdom.ANIMALKINGDOM){
                    return "ANIMALKINGDOM";
                }
                if(corner.getKingdom() == Kingdom.PLANTKINGDOM){
                    return "PLANTKINGDOM";
                }
                if(corner.getKingdom() == Kingdom.INSECTKINGDOM){
                    return "INSECTKINGDOM";
                }
                if(corner.getKingdom() == Kingdom.FUNGIKINGDOM) {
                    return "FUNGIKINGDOM";
                }
            } else {
                if(corner.getObject() == GameObject.INKWELL){
                    return "INKWELL";
                }
                if(corner.getObject() == GameObject.QUILL){
                    return "QUILL";
                }
                if(corner.getObject() == GameObject.MANUSCRIPT) {
                    return "MANUSCRIPT";
                }
            }
        }
        else {
            return "            ";
        }
        return "            ";

    }


    /**
     * Method to display a given starter card
     *
     * @param starterCard given starter card to be displayed
     * @author Foini Lorenzo
     */
    public synchronized void displayStarterCard(StarterCard starterCard, PrintWriter out){
        out.println("This is your starter card: \n" +
                "Front:\n" +
                "________________________________________________________________________________________\n" +
                "|" + printCorner(starterCard.getFrontCorners()[0]) + "              " + printCorner(starterCard.getFrontCorners()[1]) +"|\n" +
                "           ");
        for (int i=0; i < starterCard.getFrontKingdoms().length; i++) {
            out.println("                 " + starterCard.getFrontKingdoms()[i] + "   ");
        }
        out.println(
                "\n" +
                "|" + printCorner(starterCard.getFrontCorners()[2]) + "              " + printCorner(starterCard.getFrontCorners()[3]) +"|\n" +
                "________________________________________________________________________________________\n" +
                "\n" +
                "Back:\n" +
                "________________________________________________________________________________________\n" +
                "|" + printCorner(starterCard.getBackCorners()[0]) + "              " + printCorner(starterCard.getBackCorners()[1]) +"|\n" +
                "\n" +
                "\n" +
                "|" + printCorner(starterCard.getBackCorners()[2]) + "              " + printCorner(starterCard.getBackCorners()[3]) +"|\n" +
                "________________________________________________________________________________________\n"
                );
    }

    /**
     * Method to display a given resource card
     *
     * @param resourceCard given resource card to be displayed
     * @author Foini Lorenzo
     */
    public void displayResourceCard(GamingCard resourceCard, PrintWriter out){
        out.println("Resource card: " + resourceCard.toString());
    }

    /**
     * Method to display a given gold card
     *
     * @param goldCard given gold card to be displayed
     * @author Foini Lorenzo
     */
    public void displayGoldCard(GoldCard goldCard, PrintWriter out){
        out.println("Gold card: " + goldCard.toString());
    }

    /**
     * Method to display a given objective card
     *
     * @param objectiveCard given objective card to be displayed
     * @author Foini Lorenzo
     */
    public void displayObjectiveCard(ObjectiveCard objectiveCard, PrintWriter out){
        out.println("Objective card: " + objectiveCard.toString());
    }

}
