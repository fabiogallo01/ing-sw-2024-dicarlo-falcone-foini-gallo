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
                return corner.getKingdom().toString();
            } else {
                return corner.getObject().toString();
            }
        }
        else {
            return "            ";
        }
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
            out.println("        " + starterCard.getFrontKingdoms()[i] + "   ");
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
        out.println("Resource Card: \n" +
                "Front:\n" +
                "________________________________________________________________________________________\n" +
                "|" + printCorner(resourceCard.getFrontCorners()[0]) + "              " + printCorner(resourceCard.getFrontCorners()[1]) +"|\n" +
                "\n" +
                "|" + printCorner(resourceCard.getFrontCorners()[2]) + "              " + printCorner(resourceCard.getFrontCorners()[3]) +"|\n" +
                "________________________________________________________________________________________\n" +
                "\n" +
                "Back:\n" +
                "________________________________________________________________________________________\n" +
                "|" + printCorner(resourceCard.getBackCorners()[0]) + "              " + printCorner(resourceCard.getBackCorners()[1]) +"|\n" +
                "\n" +
                "        "  + resourceCard.getKingdom().toString() + "\n" + "\n" +
                "|" + printCorner(resourceCard.getBackCorners()[2]) + "              " + printCorner(resourceCard.getBackCorners()[3]) +"|\n" +
                "________________________________________________________________________________________\n"
        );
    }

    /**
     * Method to display a given gold card
     *
     * @param goldCard given gold card to be displayed
     * @author Foini Lorenzo
     */
    public void displayGoldCard(GoldCard goldCard, PrintWriter out){
        out.println("Gold card: \n" +
                "Front:\n" +
                "________________________________________________________________________________________\n" +
                "|" + printCorner(goldCard.getFrontCorners()[0]) + "              " + printCorner(goldCard.getFrontCorners()[1]) +"|\n" +
                "        " + goldCard.getConditionPoint().toString() +
                "\n");
                for (int i=0; i < goldCard.getResources().length; i++) {
                    out.println("          " + goldCard.getResources()[i].toString() + "   ");
                }
        out.println("\n" +
                "|" + printCorner(goldCard.getFrontCorners()[2]) + "              " + printCorner(goldCard.getFrontCorners()[3]) +"|\n" +
                "________________________________________________________________________________________\n" +
                "\n" +
                "Back:\n" +
                "________________________________________________________________________________________\n" +
                "|" + printCorner(goldCard.getBackCorners()[0]) + "              " + printCorner(goldCard.getBackCorners()[1]) +"|\n" +
                "\n" +
                "       "  + goldCard.getKingdom().toString() + "\n" + "\n" +
                "|" + printCorner(goldCard.getBackCorners()[2]) + "              " + printCorner(goldCard.getBackCorners()[3]) +"|\n" +
                "________________________________________________________________________________________\n"
        );
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
