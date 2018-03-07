package schoolClasses;

import model.Vertex;
import renderer.ZTest;
import renderer.raster.MyShader;
import renderer.raster.RasterizerSchool;
import renderer.raster.RasterizerTriangle;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec2D;
import transforms.Vec3D;

public class Renderer {

	public static void main(String[] args) {
		ZTest zTest = new ZTest(image);
		MyShader ms = MyShader(); 

		RasterizerSchool rt = new RasterizerSchool(zTest, t -> t.getColor().mul(1 / t.getOne()));

		Vertex v1 = new Vertex(new Point3D(-1, -1, 0.5), new Col(0xff0000), new Vec3D(0, 0, -1), new Vec2D(0, 0));
		Vertex v2 = new Vertex(new Point3D(1, 0, 0.5), new Col(0xff0000), new Vec3D(0, 0, -1), new Vec2D(0, 0));
		Vertex v3 = new Vertex(new Point3D(0, 1, 0.5), new Col(0xff0000), new Vec3D(0, 0, -1), new Vec2D(0, 0));
	}

}
