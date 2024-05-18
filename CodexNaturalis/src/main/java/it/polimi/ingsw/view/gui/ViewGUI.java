package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// TODO: Add listener and get correct ID of cards to be displayed

/**
 * Class representing GUI
 * It has different methods which will be used in teh client-server communication using GUI
 * Made using Swing
 *
 * @author Foini Lorenzo
 */

public class ViewGUI {
    public void startGui(){
        // TODO: Create new panel for login window

        GameFrame frame = new GameFrame("CODEX NATURALIS");
        frame.pack();

        // TODO: Create new panel for post game window
    }
}