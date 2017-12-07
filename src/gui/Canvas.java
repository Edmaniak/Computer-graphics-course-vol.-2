package gui;

import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;

public class Canvas extends JPanel {

    private BufferedImage mainBuffer;
    private Color bgColor;
    private Dimension dimensions;

    /**
     * Constructor with default color
     *
     * @param d
     */
    public Canvas(Dimension d) {
        setDimensions(d);
        mainBuffer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(d);
    }

    /**
     * Constructor with custom color
     *
     * @param d
     * @param bgColor
     */
    public Canvas(Dimension d, Color bgColor) {
        this(d);
        setBgColor(bgColor);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mainBuffer, 0, 0, null);
    }

    public void clear(Color c) {
        mainBuffer = new BufferedImage(dimensions.width, dimensions.height, BufferedImage.TYPE_INT_RGB);
        setBgColor(c);
        repaint();
    }


    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
        Graphics newBgCol = mainBuffer.getGraphics();
        newBgCol.setColor(bgColor);
        newBgCol.fillRect(0, 0, dimensions.width, dimensions.height);
        repaint();
    }


    /**
     * Main method for drawing pixel into the canvas
     */
    public void putPixel(int x, int y, Color color) {
        if (canDrawAt(x, y))
            mainBuffer.setRGB(x, y, color.hashCode());
    }

    public boolean canDrawAt(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
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

