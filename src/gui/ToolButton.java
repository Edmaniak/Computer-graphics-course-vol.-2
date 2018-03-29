package gui;

import app.App;

import javax.swing.*;


public class ToolButton extends JButton {

    public ToolButton(String path) {

        setFocusPainted(false);
        setContentAreaFilled(false);
        setIcon(new ImageIcon(path));
        addActionListener(e -> App.resetFocus());
    }

}
