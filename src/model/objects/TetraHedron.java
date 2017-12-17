package model.objects;

import model.Part;
import model.Vertex;
import transforms.Vec3D;

import java.awt.*;
import java.util.Arrays;

public class TetraHedron extends Solid {


    private final Vertex[] vertex_definition = {
            new Vertex(0, 0, 0, renderColor),
            new Vertex(0.5, 0, 1, renderColor),
            new Vertex(1, 0, 0, renderColor),
            new Vertex(0.5, 1, 0.5, renderColor)
    };
    private final Integer[] indexes_definiton = {
            0, 1, 2,
            0, 3, 2,
            0, 3, 1,
            1, 2, 3
    };
    private final Part[] parts_definition = {new Part(Part.Type.TRIANGLE, 12, 0)};

    public TetraHedron(Color color, Vec3D initialPosition) {
        super(color, new Vec3D(0, 0, 0), initialPosition);
        vertices().addAll(Arrays.asList(vertex_definition));
        indexes().addAll(Arrays.asList(indexes_definiton));
        parts().addAll(Arrays.asList(parts_definition));
    }


}
