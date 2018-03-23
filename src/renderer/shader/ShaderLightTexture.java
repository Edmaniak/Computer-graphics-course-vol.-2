package renderer.shader;

import model.Vertex;
import transforms.Col;

public class ShaderLightTexture extends Shader {

    private final ShaderLight sl;
    private final ShaderTexture st;

    public ShaderLightTexture(ShaderLight sl, ShaderTexture st) {
        this.sl = sl;
        this.st = st;
    }

    @Override
    public Col apply(Vertex vertex) {
        Col lightContr = sl.apply(vertex);
        Col texContr = st.apply(vertex);
        return lightContr.mul(texContr);
    }

}
