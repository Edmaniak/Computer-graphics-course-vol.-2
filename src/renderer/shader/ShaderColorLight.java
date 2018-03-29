package renderer.shader;

import model.Vertex;
import transforms.Col;

public class ShaderColorLight extends Shader {

    private final ShaderLight sl;
    private final ShaderColor sc;

    public ShaderColorLight(ShaderLight sl, ShaderColor sc) {
        this.sl = sl;
        this.sc = sc;
    }

    @Override
    public Col apply(Vertex vertex) {
        Col lightContr = sl.apply(vertex);
        Col colContr = sc.apply(vertex);
        return colContr.mul(lightContr).saturate();
    }
}
