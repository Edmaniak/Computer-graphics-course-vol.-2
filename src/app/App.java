package app;

import gui.MainFrame;

import javax.swing.*;

public class App {

    public static final String title = "SimpleDraw";
    public static MainFrame gui;
    public static App app;
    public static final int WIDTH = 1055;
    public static final int HEIGHT = 800;

    private App() {
        if (app == null)
            app = this;
        if (gui == null)
            gui = new MainFrame(WIDTH, HEIGHT);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}
