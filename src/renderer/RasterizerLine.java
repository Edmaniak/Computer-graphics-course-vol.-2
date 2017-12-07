package renderer;

import java.awt.image.BufferedImage;

import transforms.Vec3D;

public class RasterizerLine {
	private BufferedImage bf;
	
	public void draw(Vec3D v1, Vec3D v2) {
		bf.getGraphics().drawLine((int)v1.getX(), (int)v1.getY(), (int)v2.getX(), (int)v2.getY());
	}
	


}
