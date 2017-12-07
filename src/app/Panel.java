package app;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import renderer.Renderer;

public class Panel {
	private JPanel panel;
	private BufferedImage img;
	
	Renderer rednerer;
	
	public Panel() {
		panel = new JPanel();
		img = new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
		renderer = new Renderer(new RasterizerLine(img));
		renderer.
	}
}
