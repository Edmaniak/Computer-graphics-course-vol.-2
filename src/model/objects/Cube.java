package model.objects;

import model.Part;
import model.Vertex;
import transforms.Vec3D;

import java.awt.*;
import java.util.Arrays;

public class Cube extends Solid {

    private Vertex[] vertex_definition = {
            // dolní podstava
            new Vertex(0, 0, 0, color),
            new Vertex(1, 0, 0, color),
            new Vertex(1, 0, 1, color),
            new Vertex(0, 0, 1, color),
            // horní podstava
            new Vertex(0, 1, 0, color),
            new Vertex(1, 1, 0, color),
            new Vertex(1, 1, 1, color),
            new Vertex(0, 1, 1, color)
    };
    private Integer[] indexes_definiton = {
            // triangles
            // 1 spodni
            0, 1, 3,
            1, 2, 3,
            // 2
            0, 1, 5,
            0, 4, 5,
            // 3
            1, 5, 6,
            1, 2, 6,
            // 4
            2, 6, 7,
            2, 3, 7,
            // 5
            0, 3, 4,
            3, 4, 7,
            // 6 horni
            4, 5, 6,
            4, 7, 5
    };
    private Part[] parts_definition = {new Part(Part.Type.TRIANGLE, 36, 0)};

    public Cube(Color color,Vec3D initialPosition) {
        super(color, new Vec3D(0.5, 0.5, 0.5),initialPosition);
        vertices().addAll(Arrays.asList(vertex_definition));
        indexes().addAll(Arrays.asList(indexes_definiton));
        getParts().addAll(Arrays.asList(parts_definition));
    }

}
