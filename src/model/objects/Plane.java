package model.objects;

import model.Parts;
import model.Vertex;
import transforms.Vec3D;

import java.awt.*;
import java.util.Arrays;

public class Plane extends Solid {

    private Vertex[] vertex_definition = {
            // two triangles
            new Vertex(-2, 0, -2, color),
            new Vertex(2, 0, -2, color),
            new Vertex(-2, 0, 2, color),
            new Vertex(2, 0, 2, color),
    };

    private Integer[] index_definition = {
            // Triangles
            0, 1, 2,
            2, 3, 1,
    };

    private Parts[] parts_definition = {
            new Parts(Parts.Type.TRIANGLE, 6, 0)
    };

    public Plane(Color color) {
        super(color, new Vec3D(0,0,0));
        vertices().addAll(Arrays.asList(vertex_definition));
        indexes().addAll(Arrays.asList(index_definition));
        getParts().addAll(Arrays.asList(parts_definition));
    }

}
