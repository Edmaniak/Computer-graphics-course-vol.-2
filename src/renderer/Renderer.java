package renderer;

import java.util.ArrayList;
import java.util.List;

import model.Parts;
import model.Parts.Type;
import model.Vertex;
import model.objects.Solid;
import transforms.Mat4;

public class Renderer {

    public List<Vertex> vb = new ArrayList<>();

    private Mat4 model;
    private Mat4 view;
    private Mat4 projection;

    public void render(Solid solid) {
        // Transformace
        Mat4 matMVP = model.mul(view).mul(projection);
        for(Vertex v : solid.getVertexBuffer())
            vb.add(v.mul(matMVP));
        for(Parts p : solid.getParts()) {
            switch(p.getType()) {
                case Parts.Type.LINE: {

                }
            }

        }
    }

}
