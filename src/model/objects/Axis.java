package model.objects;

import model.Parts;
import model.Vertex;

import java.awt.*;
import java.util.Arrays;

public class Axis extends Solid {
    private Vertex[] vertex_definition = {
            // Axis
            new Vertex(0, 0, 0, color),
            new Vertex(1, 0, 0, color),
            new Vertex(0, 0, 1, color),
            new Vertex(0, 1, 0, color),

            // End triangle X
            new Vertex(1, 0, -0.2, color),
            new Vertex(1, 0, 0.2, color),
            new Vertex(1.2, 0, 0, color),

            // End triangle Z
            new Vertex(-0.2, 0, 1, color),
            new Vertex(0.2, 0, 1, color),
            new Vertex(0, 0, 1.2, color),

            // End triangle Y
            new Vertex(-0.2, 1, 0, color),
            new Vertex(0.2, 1, 0, color),
            new Vertex(0, 1.2, 0, color),

    };

    private Integer[] index_definition = {
            // Lines
            0, 1,
            0, 2,
            0, 3,
            // Triangles
            4, 5, 6,
            7, 8, 9,
            10, 11, 12
    };

    private Parts[] parts_definition = {
            new Parts(Parts.Type.LINE, 6, 0),
            new Parts(Parts.Type.TRIANGLE, 9, 6)
    };

    public Axis(Color color) {
        super(color);
        vertices().addAll(Arrays.asList(vertex_definition));
        indexes().addAll(Arrays.asList(index_definition));
        getParts().addAll(Arrays.asList(parts_definition));
    }
}
