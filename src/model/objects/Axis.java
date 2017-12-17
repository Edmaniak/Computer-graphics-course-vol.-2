package model.objects;

import model.Part;
import model.Vertex;
import transforms.Vec3D;

import java.awt.*;
import java.util.Arrays;

public class Axis extends Solid {

    private final Vertex[] vertex_definition = {

            // Axis
            new Vertex(0, 0, 0, renderColor),
            new Vertex(1, 0, 0, renderColor),
            new Vertex(0, 0, 1, renderColor),
            new Vertex(0, 1, 0, renderColor),

            // End triangle X
            new Vertex(1, 0, -0.2, renderColor),
            new Vertex(1, 0, 0.2, renderColor),
            new Vertex(1.2, 0, 0, renderColor),

            // End triangle Z
            new Vertex(-0.2, 0, 1, renderColor),
            new Vertex(0.2, 0, 1, renderColor),
            new Vertex(0, 0, 1.2, renderColor),

            // End triangle Y
            new Vertex(-0.2, 1, 0, renderColor),
            new Vertex(0.2, 1, 0, renderColor),
            new Vertex(0, 1.2, 0, renderColor),

    };

    private final Integer[] index_definition = {
            // Lines
            0, 1,
            0, 2,
            0, 3,
            // Triangles
            4, 5, 6,
            7, 8, 9,
            10, 11, 12
    };

    private final Part[] parts_definition = {
            new Part(Part.Type.LINE, 6, 0),
            new Part(Part.Type.TRIANGLE, 9, 6)
    };

    public Axis(Color color, Vec3D initialPosition) {
        super(color, new Vec3D(0, 0, 0), initialPosition);
        vertices().addAll(Arrays.asList(vertex_definition));
        indexes().addAll(Arrays.asList(index_definition));
        parts().addAll(Arrays.asList(parts_definition));
    }

}
