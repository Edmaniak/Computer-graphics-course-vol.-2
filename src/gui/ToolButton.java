package gui;

import javax.swing.*;

public class ToolButton extends JButton {
    public ToolButton(String text) {
        setFocusPainted(false);
        setContentAreaFilled(false);
        setText(text);
    }

}
