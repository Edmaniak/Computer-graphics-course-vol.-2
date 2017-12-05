package model.objects;

import model.Parts;
import model.Vertex;

import java.util.Arrays;

public class Axis extends Solid {
    private Vertex[] vertex_definition = {
            // Axis
            new Vertex(0, 0, 0),
            new Vertex(1, 0, 0),
            new Vertex(0, 0, 1),
            new Vertex(0, 1, 0),

            // End triangle X
            new Vertex(1, 0, -0.5),
            new Vertex(1, 0, 0.5),
            new Vertex(1.5, 0, 0),

            // End triangle Z
            new Vertex(-0.5, 0, 1),
            new Vertex(0.5, 0, 1),
            new Vertex(0, 0, 1.5),

            // End triangle Y
            new Vertex(-0.5, 1, 0),
            new Vertex(0.5, 1, 0),
            new Vertex(0, 1.5, 0),

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
            new Parts(Parts.Type.LINE, 3, 0),
            new Parts(Parts.Type.TRIANGLE, 3, 6)
    };

    public Axis() {
        vertices().addAll(Arrays.asList(vertex_definition));
        indexes().addAll(Arrays.asList(index_definition));
        getParts().addAll(Arrays.asList(parts_definition));
    }
}
