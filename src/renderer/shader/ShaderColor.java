package renderer.shader;

import java.util.function.Function;

import model.Vertex;
import transforms.Col;

public class ShaderColor extends Shader {

    @Override
    public Col apply(Vertex v) {
        return v.getColor().mul(1 / v.getOne());
    }

}
