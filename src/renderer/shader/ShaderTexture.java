package renderer.shader;

import material.Texture;
import model.Vertex;
import transforms.Col;
import transforms.Point2D;
import transforms.Vec2D;

public class ShaderTexture extends Shader {

    private Texture tex;

    public ShaderTexture(Texture tex) {
        this.tex = tex;
    }

    @Override
    public Col apply(Vertex v) {
        Point2D texUV = v.getTexUV().mul(1/v.getOne());
        int xCoord = (int) (texUV.getX());
        int yCoord = (int) (texUV.getY());
        if (tex.getPixel(xCoord, yCoord).isPresent())
            return tex.getPixel(xCoord, yCoord).get();
        return new Col(0, 0, 0);
    }

}
