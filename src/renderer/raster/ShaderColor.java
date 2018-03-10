package renderer.raster;

import java.util.function.Function;

import model.Vertex;
import transforms.Col;

public class ShaderColor implements Function<Vertex, Col> {

	@Override
	public Col apply(Vertex t) {
		return t.getColor().mul(1 / t.getOne());
	}

}
