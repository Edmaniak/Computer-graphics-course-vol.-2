package renderer;

import java.util.ArrayList;
import java.util.List;

import model.Parts;
import model.Vertex;
import model.objects.Solid;
import transforms.Mat4;
import transforms.Vec3D;

public class Renderer {

	public List<Vertex> vb = new ArrayList<>();

	private Mat4 model = new Mat4();
	private Mat4 view = new Mat4();
	private Mat4 projection = new Mat4();
	private RasterizerLine rl;
	
	public Renderer(RasterizerLine rl) {
		this.rl = rl;
	}

	public void render(Solid sld) {

		// Transformace
		Mat4 matMVP = model.mul(view).mul(projection);

		for (Vertex v : sld.vertices())
			vb.add(v.mul(matMVP));
		for (Parts p : sld.getParts()) {

			switch (p.getType()) {

			case LINE: {
				for (int i = p.getStart(); i < ((p.getCount() * 2) + p.getStart()); i += 2) {
					line(sld.vertices().get(sld.indexes().get(i)), sld.vertices().get(sld.indexes().get(i + 1)));
				}
				break;
			}

			case TRIANGLE: {
				for (int i = p.getStart(); i < ((p.getCount() * 3) + p.getStart()); i += 3) {
					triangle(sld.vertices().get(sld.indexes().get(i)), sld.vertices().get(sld.indexes().get(i + 1)),
							sld.vertices().get(sld.indexes().get(i + 2)));
				}
				break;
			}
			}
		}
	}

	private void triangle(Vertex v1, Vertex v2, Vertex v3) {
		System.out.println("v1: " + v1 + " v2: " + v2 + " v3 " + v3);
	}

	private void line(Vertex origin, Vertex end) {
		// clipp
		// dehomogenizace
		if (!origin.getPosition().dehomog().isPresent())
			return;
		Vec3D vo = origin.getPosition().dehomog().get();
		// zahodit z
		double y = (-vo.getY() + 1)*vyska/2;
		double x = (vo.getX() + 1)*sirka/2;
		Vec3D v1, v2;
		rl.draw(v1,v2);
		

		System.out.println("O: " + origin + " " + "E: " + end);
	}

	public Mat4 getModel() {
		return model;
	}

	public void setModel(Mat4 model) {
		this.model = model;
	}

	public Mat4 getView() {
		return view;
	}

	public void setView(Mat4 view) {
		this.view = view;
	}

	public Mat4 getProjection() {
		return projection;
	}

	public void setProjection(Mat4 projection) {
		this.projection = projection;
	}
	
	

}
