package gui;

import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;

public class Canvas extends JPanel {

    private BufferedImage mainBuffer;
    private final Color bgColor = new Color(70,73,76);
    private Dimension dimensions;

    public Canvas(Dimension d) {
        setDimensions(d);
        mainBuffer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(d);
        setBgColor(bgColor);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mainBuffer, 0, 0, null);
    }

    public void clear(Color c) {
        setBgColor(c);
        repaint();
    }


    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        Graphics newBgCol = mainBuffer.getGraphics();
        newBgCol.setColor(bgColor);
        newBgCol.fillRect(0, 0, dimensions.width, dimensions.height);
        repaint();
    }

    public Dimension getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimension dimensions) {
        this.dimensions = dimensions;
    }

    public BufferedImage getMainBuffer() {
        return mainBuffer;
    }

    @Override
    public Component add(Component comp) {
        repaint();
        return super.add(comp);
    }

    @Override
    public void remove(Component comp) {
        repaint();
        super.remove(comp);
    }

}

