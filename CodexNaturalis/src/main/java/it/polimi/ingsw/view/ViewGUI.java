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
    final int NUM_ROWS = 81;
    final int NUM_COLS = 81;

    BufferedImage originalImage = null;
    Image scaledImage = null;
    ImageIcon imageIcon = null;

    MyFrame(String title){
        super(title);
        init();
    }

    void init(){
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

        // Add JPanel in contentPane
        contentPane.add(panelNorth,BorderLayout.NORTH);
        contentPane.add(panelWest,BorderLayout.WEST);
        contentPane.add(scrollPaneCenter,BorderLayout.CENTER);
        contentPane.add(panelEast,BorderLayout.EAST);
        contentPane.add(panelSouth,BorderLayout.SOUTH);
    }

    // Method for create north panel
    public JPanel createPanelNorth(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Create label
        JLabel label1 = new JLabel("Common objective n.1");
        JLabel label2 = new JLabel("Common objective n.2");
        JLabel label3 = new JLabel("Secret objective");

        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setVerticalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setVerticalAlignment(JLabel.CENTER);
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setVerticalAlignment(JLabel.CENTER);

        addComponent(panel, label1, gbc, 0, 0, 1, 1, 1, 0.3); // Prima riga
        addComponent(panel, label2, gbc, 0, 1, 1, 1, 1, 0.3); // Prima riga
        addComponent(panel, label3, gbc, 0, 2, 1, 1, 1, 0.3); // Prima riga

        // Get images
        try {
            originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\front\\img_91.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scaledImage = originalImage.getScaledInstance(200, 80, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
        imageIcon = new ImageIcon(scaledImage);
        JLabel image1 = new JLabel(imageIcon);

        try {
            originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\front\\img_92.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scaledImage = originalImage.getScaledInstance(200, 80, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
        imageIcon = new ImageIcon(scaledImage);
        JLabel image2 = new JLabel(imageIcon);

        try {
            originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\front\\img_93.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scaledImage = originalImage.getScaledInstance(200, 80, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
        imageIcon = new ImageIcon(scaledImage);
        JLabel image3 = new JLabel(imageIcon);

        image1.setVerticalAlignment(JLabel.CENTER);
        image2.setVerticalAlignment(JLabel.CENTER);
        image3.setVerticalAlignment(JLabel.CENTER);

        addComponent(panel, image1, gbc, 1, 0, 1, 1, 1, 0.7);
        addComponent(panel, image2, gbc, 1, 1, 1, 1, 1, 0.7);
        addComponent(panel, image3, gbc, 1, 2, 1, 1, 1, 0.7); // Seconda riga

        // Set panel values
        panel.setBackground(Color.CYAN);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(150, 100));

        return panel;
    }

    // Method for create west panel
    public JPanel createPanelWest(){
        JPanel mainPanel = new JPanel(new GridBagLayout());

        // Add grid constraint: 5%
        JPanel panel1 = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.05; // 5% of height
        gbc.fill = GridBagConstraints.BOTH;

        // Create label
        JLabel scoreboard = new JLabel("Live scoreboard:");
        scoreboard.setHorizontalAlignment(JLabel.CENTER);
        scoreboard.setVerticalAlignment(JLabel.CENTER);
        panel1.add(scoreboard);

        mainPanel.add(panel1, gbc);

        // add grid constraint: 45%
        JPanel panel2 = new JPanel(new GridLayout(4,2));
        gbc.gridy = 1;
        gbc.weighty = 0.45; // 45% of height

        // Create labels
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

        // Add grid constraint: 50%
        JPanel panel3 = new JPanel(new GridLayout(4, 1));
        gbc.gridy = 2;
        gbc.weighty = 0.5; // 50% of height

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

    // Method for create center panel
    public JScrollPane createPanelCenter(){
        JPanel panelCenter = new JPanel(new GridLayout(NUM_ROWS, NUM_COLS));

        // Add button in the grid
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                JButton button = null;
                if(i == 39 && j == 39){
                    originalImage = null; // Carica l'immagine di input
                    try {
                        originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\front\\img_57.jpeg"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    scaledImage = originalImage.getScaledInstance(250, 100, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
                    imageIcon = new ImageIcon(scaledImage);
                    button = new JButton(imageIcon);
                    button.setEnabled(false);
                    button.setDisabledIcon(imageIcon);
                } else if(i == 40 && j == 40){
                    originalImage = null; // Carica l'immagine di input
                    try {
                        originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\front\\img_82.jpeg"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    scaledImage = originalImage.getScaledInstance(250, 100, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
                    imageIcon = new ImageIcon(scaledImage);
                    button = new JButton(imageIcon);
                    button.setEnabled(false);
                    button.setDisabledIcon(imageIcon);
                }else{
                    button = new JButton("(" + i + ", " + j + ")");
                    button.setEnabled(false);
                }
                button.setPreferredSize(new Dimension(250, 100));
                panelCenter.add(button);
            }
        }

        // Create scrollbar for visualization of the game area
        JScrollPane scrollPane = new JScrollPane(panelCenter);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Impost scroll bar to center
        JViewport viewport = scrollPane.getViewport();
        int viewWidth = viewport.getViewSize().width;
        int viewHeight = viewport.getViewSize().height;
        int offsetX = (viewWidth - viewport.getWidth()) / 2 - 600;
        int offsetY = (viewHeight - viewport.getHeight()) / 2 - 250;
        viewport.setViewPosition(new Point(offsetX, offsetY));

        return scrollPane;
    }

    // Method for create east panel
    public JPanel createPanelEast(){
        JPanel panel = new JPanel(new GridLayout(9,1));

        // Create labels
        JLabel top1 = new JLabel("Resource deck top card:");
        JLabel top2 = new JLabel("Gold deck top card:");
        JLabel visible = new JLabel("Visible cards:");

        top1.setHorizontalAlignment(JLabel.CENTER);
        top1.setVerticalAlignment(JLabel.CENTER);
        top2.setHorizontalAlignment(JLabel.CENTER);
        top2.setVerticalAlignment(JLabel.CENTER);
        visible.setHorizontalAlignment(JLabel.CENTER);
        visible.setVerticalAlignment(JLabel.CENTER);

        // Get images
        try {
            originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\back\\img_11.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scaledImage = originalImage.getScaledInstance(200, 80, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
        imageIcon = new ImageIcon(scaledImage);
        JLabel image1 = new JLabel(imageIcon);

        try {
            originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\back\\img_41.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scaledImage = originalImage.getScaledInstance(200, 80, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
        imageIcon = new ImageIcon(scaledImage);
        JLabel image2 = new JLabel(imageIcon);

        try {
            originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\front\\img_23.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scaledImage = originalImage.getScaledInstance(120, 60, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
        imageIcon = new ImageIcon(scaledImage);
        JLabel image3 = new JLabel(imageIcon);

        try {
            originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\front\\img_25.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scaledImage = originalImage.getScaledInstance(120, 60, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
        imageIcon = new ImageIcon(scaledImage);
        JLabel image4 = new JLabel(imageIcon);

        try {
            originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\front\\img_58.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scaledImage = originalImage.getScaledInstance(120, 60, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
        imageIcon = new ImageIcon(scaledImage);
        JLabel image5 = new JLabel(imageIcon);

        try {
            originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\front\\img_59.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scaledImage = originalImage.getScaledInstance(120, 60, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
        imageIcon = new ImageIcon(scaledImage);
        JLabel image6 = new JLabel(imageIcon);

        image1.setVerticalAlignment(JLabel.CENTER);
        image2.setVerticalAlignment(JLabel.CENTER);
        image3.setVerticalAlignment(JLabel.CENTER);
        image4.setVerticalAlignment(JLabel.CENTER);
        image5.setVerticalAlignment(JLabel.CENTER);
        image6.setVerticalAlignment(JLabel.CENTER);

        // Add all labels and images in panel
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

    // Method for create south panel
    public JPanel createPanelSouth(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // get buttons for player's hand: 70% - 30%
        try {
            BufferedImage originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\back\\img_21.jpeg"));
            Image scaledImage = originalImage.getScaledInstance(350, 120, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            JButton button1 = new JButton(imageIcon);
            addComponent(panel, button1, gbc, 0, 0, 1, 1, 1, 0.7); // Prima riga

            originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\front\\img_23.jpeg"));
            scaledImage = originalImage.getScaledInstance(350, 120, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImage);
            JButton button2 = new JButton(imageIcon);
            addComponent(panel, button2, gbc, 0, 1, 1, 1, 1, 0.7); // Prima riga

            originalImage = ImageIO.read(new File("CodexNaturalis\\resources\\front\\img_42.jpeg"));
            scaledImage = originalImage.getScaledInstance(350, 120, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImage);
            JButton button3 = new JButton(imageIcon);
            addComponent(panel, button3, gbc, 0, 2, 1, 1, 1, 0.7); // Prima riga

            JButton button4 = new JButton("Play card 1");
            addComponent(panel, button4, gbc, 1, 0, 1, 1, 1, 0.3); // Seconda riga

            JButton button5 = new JButton("Play card 2");
            addComponent(panel, button5, gbc, 1, 1, 1, 1, 1, 0.3); // Seconda riga

            JButton button6 = new JButton("Play card 3");
            addComponent(panel, button6, gbc, 1, 2, 1, 1, 1, 0.3); // Seconda riga

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        panel.setBackground(Color.ORANGE);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(150, 170));

        return panel;
    }

    private void addComponent(JPanel panel, Component comp, GridBagConstraints gbc,
                              int row, int col, int width, int height, double weightx, double weighty) {
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        panel.add(comp, gbc);
    }
}