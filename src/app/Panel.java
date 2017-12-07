package app;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import model.objects.Cube;
import renderer.RasterizerLine;
import renderer.Renderer;
import transforms.Mat4Identity;
import transforms.Mat4PerspRH;
import transforms.Mat4ViewRH;
import transforms.Vec3D;

public class Panel {
	private JPanel panel;
	private BufferedImage img;
	
	Renderer renderer;
	
	public Panel() {
		panel = new JPanel();
		img = new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
		renderer = new Renderer(new RasterizerLine(img));
		renderer.setProjection(new Mat4PerspRH(Math.PI/4,1,0.001,100));
		renderer.setView(new Mat4ViewRH(new Vec3D(0,0,50),new Vec3D(0,0,-1),new Vec3D(0,1,0)));
		renderer.setModel(new Mat4Identity());
	}
	
	public void draw() {
		clear();
		Cube cube = new Cube(); 
		renderer.render(cube);
	}
}
