package gui;

import app.App;
import transforms.Mat4;
import transforms.Point2D;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel {

    private BufferedImage mainBuffer;
    private final Color bgColor;
    private Dimension dimensions;

    public Canvas(Dimension d, Color color) {
        setDimensions(d);
        mainBuffer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(d);
        setBgColor(color);
        bgColor = color;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(mainBuffer, 0, 0, null);
    }

    public void clear() {
        setBgColor(bgColor);
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

    public void debug(Mat4 model) {
       drawString(model.toString(),App.WIDTH-200,10);
    }

    void drawString(String text, int x, int y) {
        for (String line : text.split("\n"))
            mainBuffer.getGraphics().drawString(line, x, y += mainBuffer.getGraphics().getFontMetrics().getHeight());
    }


}

