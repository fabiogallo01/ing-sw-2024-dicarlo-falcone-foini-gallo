package it.polimi.ingsw.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ViewGUI {
    public static void main(String[] args){
        MyFrame frame = new MyFrame("CODEX NATURALIS");
        frame.pack();
    }
}

class MyFrame extends JFrame {
    final int WIDTH = 1500;
    final int HEIGHT = 800;
    final int NUM_ROWS = 81;
    final int NUM_COLS = 81;
    final int TOTAL_GRID_SIZE = NUM_ROWS * NUM_COLS;

    MyFrame(String title){
        super(title);
        init();
    }

    void init(){
        //this.setSize(WIDTH,HEIGHT);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        // Create new panel for north
        JPanel panelNorth = createPanelNorth();

        // Create new panel for west
        JPanel panelWest = createPanelWest();

        // Create new panel for center
        JScrollPane scrollPaneCenter = createPanelCenter();

        // Create new panel for east
        JPanel panelEast = createPanelEast();

        // Create new panel for south
        JPanel panelSouth = createPanelSouth();

        // Get container
        Container contentPane = this.getContentPane();

        // Add JPanel in JFrame
        contentPane.add(panelNorth,BorderLayout.NORTH);
        contentPane.add(panelWest,BorderLayout.WEST);
        contentPane.add(scrollPaneCenter,BorderLayout.CENTER);
        contentPane.add(panelEast,BorderLayout.EAST);
        contentPane.add(panelSouth,BorderLayout.SOUTH);
    }

    public JPanel createPanelNorth(){
        // Create a new JPanel with a horizontal BoxLayout
        // This panel has three cells, where each cell contains an objective(2 commons, 1 secret)
        JPanel panel = new JPanel(new GridLayout(2, 3)); // Vertical BoxLayout for cells

        JLabel label1 = new JLabel("Common objective n.1");
        JLabel label2 = new JLabel("Common objective n.2");
        JLabel label3 = new JLabel("Secret objective");

        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setVerticalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setVerticalAlignment(JLabel.CENTER);
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setVerticalAlignment(JLabel.CENTER);

        JLabel image1 = new JLabel(new ImageIcon("C:\\Users\\Lenovo\\Desktop\\Carta1.png"));
        JLabel image2 = new JLabel(new ImageIcon("C:\\Users\\Lenovo\\Desktop\\Carta2.png"));
        JLabel image3 = new JLabel(new ImageIcon("C:\\Users\\Lenovo\\Desktop\\Carta3.png"));

        image1.setVerticalAlignment(JLabel.CENTER);
        image2.setVerticalAlignment(JLabel.CENTER);
        image3.setVerticalAlignment(JLabel.CENTER);

        // Add all labels in panel
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(image1);
        panel.add(image2);
        panel.add(image3);

        panel.setBackground(Color.CYAN);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(150, 150));

        return panel;
    }

    public JPanel createPanelWest(){
        // Crea il pannello principale con GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());

        // Imposta i vincoli per la prima griglia (5%)
        JPanel panel1 = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.05; // 5% della altezza del pannello principale
        gbc.fill = GridBagConstraints.BOTH;

        JLabel scoreboard = new JLabel("Live scoreboard:");
        scoreboard.setHorizontalAlignment(JLabel.CENTER);
        scoreboard.setVerticalAlignment(JLabel.CENTER);
        panel1.add(scoreboard);

        mainPanel.add(panel1, gbc);

        // Imposta i vincoli per la seconda griglia (45%)
        JPanel panel2 = new JPanel(new GridLayout(4,2));
        gbc.gridy = 1;
        gbc.weighty = 0.45; // 45% della altezza del pannello principale

        JLabel player1 = new JLabel("- Player 1");
        player1.setHorizontalAlignment(JLabel.CENTER);
        player1.setVerticalAlignment(JLabel.CENTER);

        JLabel player2 = new JLabel("- Player 2");
        player2.setHorizontalAlignment(JLabel.CENTER);
        player2.setVerticalAlignment(JLabel.CENTER);

        JLabel player3 = new JLabel("- Player 3");
        player3.setHorizontalAlignment(JLabel.CENTER);
        player3.setVerticalAlignment(JLabel.CENTER);

        JLabel player4 = new JLabel("- Player 4");
        player4.setHorizontalAlignment(JLabel.CENTER);
        player4.setVerticalAlignment(JLabel.CENTER);

        JLabel score1 = new JLabel("1 pts");
        score1.setHorizontalAlignment(JLabel.CENTER);
        score1.setVerticalAlignment(JLabel.CENTER);

        JLabel score2 = new JLabel("2 pts");
        score2.setHorizontalAlignment(JLabel.CENTER);
        score2.setVerticalAlignment(JLabel.CENTER);

        JLabel score3 = new JLabel("3 pts");
        score3.setHorizontalAlignment(JLabel.CENTER);
        score3.setVerticalAlignment(JLabel.CENTER);

        JLabel score4 = new JLabel("4 pts");
        score4.setHorizontalAlignment(JLabel.CENTER);
        score4.setVerticalAlignment(JLabel.CENTER);

        // Add all labels in panel
        panel2.add(player1);
        panel2.add(score1);
        panel2.add(player2);
        panel2.add(score2);
        panel2.add(player3);
        panel2.add(score3);
        panel2.add(player4);
        panel2.add(score4);

        mainPanel.add(panel2, gbc);

        // Imposta i vincoli per la terza griglia (50%)
        JPanel panel3 = new JPanel(new GridLayout(4, 1));
        gbc.gridy = 2;
        gbc.weighty = 0.5; // 50% della altezza del pannello principale

        JButton button1 = new JButton("P. 1 game area");
        JButton button2 = new JButton("P. 2 game area");
        JButton button3 = new JButton("P. 3 game area");
        JButton button4 = new JButton("P. 4 game area");

        panel3.add(button1);
        panel3.add(button2);
        panel3.add(button3);
        panel3.add(button4);

        mainPanel.add(panel3, gbc);

        mainPanel.setBackground(Color.RED);
        mainPanel.setOpaque(true);
        mainPanel.setPreferredSize(new Dimension(150, 150));

        return mainPanel;
    }

    public JScrollPane createPanelCenter(){
        JPanel panelCenter = new JPanel(new GridLayout(NUM_ROWS, NUM_COLS));

        // Add button in the grid
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                JButton button = null;
                if(i == 39 && j == 39){
                    BufferedImage originalImage = null; // Carica l'immagine di input
                    try {
                        originalImage = ImageIO.read(new File("C:\\Users\\Lenovo\\Desktop\\res.png"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Image scaledImage = originalImage.getScaledInstance(250, 100, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
                    ImageIcon imageIcon = new ImageIcon(scaledImage);
                    button = new JButton(imageIcon);
                } else if(i == 40 && j == 40){
                    BufferedImage originalImage = null; // Carica l'immagine di input
                    try {
                        originalImage = ImageIO.read(new File("C:\\Users\\Lenovo\\Desktop\\start.png"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Image scaledImage = originalImage.getScaledInstance(250, 100, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
                    ImageIcon imageIcon = new ImageIcon(scaledImage);
                    button = new JButton(imageIcon);
                }else{
                    button = new JButton("(" + i + ", " + j + ")");
                }
                button.setPreferredSize(new Dimension(250, 100));
                panelCenter.add(button);
            }
        }

        // Create scrollbar for visualization of the game area
        JScrollPane scrollPane = new JScrollPane(panelCenter);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Impost scroll bat to center
        JViewport viewport = scrollPane.getViewport();
        int viewWidth = viewport.getViewSize().width;
        int viewHeight = viewport.getViewSize().height;
        int offsetX = (viewWidth - viewport.getWidth()) / 2 - 600;
        int offsetY = (viewHeight - viewport.getHeight()) / 2 - 250;
        viewport.setViewPosition(new Point(offsetX, offsetY));

        return scrollPane;
    }

    public JPanel createPanelEast(){
        JPanel panel = new JPanel(new GridLayout(9,1));

        JLabel top1 = new JLabel("Resource deck top card:");
        JLabel top2 = new JLabel("Gold deck top card:");
        JLabel visible = new JLabel("Visible cards:");

        top1.setHorizontalAlignment(JLabel.CENTER);
        top1.setVerticalAlignment(JLabel.CENTER);
        top2.setHorizontalAlignment(JLabel.CENTER);
        top2.setVerticalAlignment(JLabel.CENTER);
        visible.setHorizontalAlignment(JLabel.CENTER);
        visible.setVerticalAlignment(JLabel.CENTER);

        JLabel image1 = new JLabel(new ImageIcon("C:\\Users\\Lenovo\\Desktop\\Carta1.png"));
        JLabel image2 = new JLabel(new ImageIcon("C:\\Users\\Lenovo\\Desktop\\Carta2.png"));
        JLabel image3 = new JLabel(new ImageIcon("C:\\Users\\Lenovo\\Desktop\\Carta3.png"));
        JLabel image4 = new JLabel(new ImageIcon("C:\\Users\\Lenovo\\Desktop\\Carta1.png"));
        JLabel image5 = new JLabel(new ImageIcon("C:\\Users\\Lenovo\\Desktop\\Carta2.png"));
        JLabel image6 = new JLabel(new ImageIcon("C:\\Users\\Lenovo\\Desktop\\Carta3.png"));

        image1.setVerticalAlignment(JLabel.CENTER);
        image2.setVerticalAlignment(JLabel.CENTER);
        image3.setVerticalAlignment(JLabel.CENTER);
        image4.setVerticalAlignment(JLabel.CENTER);
        image5.setVerticalAlignment(JLabel.CENTER);
        image6.setVerticalAlignment(JLabel.CENTER);


        // Add all labels in panel
        panel.add(top1);
        panel.add(image1);
        panel.add(top2);
        panel.add(image2);
        panel.add(visible);
        panel.add(image3);
        panel.add(image4);
        panel.add(image5);
        panel.add(image6);

        panel.setBackground(Color.GREEN);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(150, 150));

        return panel;
    }

    public JPanel createPanelSouth(){
        JPanel panel = new JPanel(new GridLayout(3,3));

        JLabel hand1 = new JLabel("Card 1");
        JLabel hand2 = new JLabel("Card 2");
        JLabel hand3 = new JLabel("Card 3");

        hand1.setHorizontalAlignment(JLabel.CENTER);
        hand1.setVerticalAlignment(JLabel.CENTER);
        hand2.setHorizontalAlignment(JLabel.CENTER);
        hand2.setVerticalAlignment(JLabel.CENTER);
        hand3.setHorizontalAlignment(JLabel.CENTER);
        hand3.setVerticalAlignment(JLabel.CENTER);

        JButton button1 = new JButton(new ImageIcon("C:\\Users\\Lenovo\\Desktop\\Carta1.png"));
        JButton button2 = new JButton(new ImageIcon("C:\\Users\\Lenovo\\Desktop\\Carta2.png"));
        JButton button3 = new JButton(new ImageIcon("C:\\Users\\Lenovo\\Desktop\\Carta3.png"));

        JButton button4 = new JButton("Play card 1");
        JButton button5 = new JButton("Play card 2");
        JButton button6 = new JButton("Play card 3");


        // Add all labels in panel
        panel.add(hand1);
        panel.add(hand2);
        panel.add(hand3);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        panel.add(button6);

        panel.setBackground(Color.ORANGE);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(150, 150));

        return panel;
    }
}