package model.objects;

import material.Material;
import model.Part;
import model.Vertex;
import transforms.Col;
import transforms.Vec3D;

import java.awt.*;
import java.util.Arrays;

public class Axis extends Solid {

    private final Vertex[] vertex_definition = {

            // Axis x
            new Vertex(0, 0, 0, new Col(255, 0, 0)),
            new Vertex(1, 0, 0, new Col(255, 0, 0)),

            // Axis z
            new Vertex(0, 0, 0, new Col(0, 0, 255)),
            new Vertex(0, 0, 1, new Col(0, 0, 255)),

            // Axis y
            new Vertex(0, 0, 0, new Col(0, 255, 0)),
            new Vertex(0, 1, 0, new Col(0, 255, 0)),

            // End triangle X
            new Vertex(1, 0, -0.2, new Col(255, 0, 0)),
            new Vertex(1, 0, 0.2, new Col(255, 0, 0)),
            new Vertex(1.2, 0, 0, new Col(255, 0, 0)),

            // End triangle Z
            new Vertex(-0.2, 0, 1, new Col(0, 0, 255)),
            new Vertex(0.2, 0, 1, new Col(0, 0, 255)),
            new Vertex(0, 0, 1.2, new Col(0, 0, 255)),

            // End triangle Y
            new Vertex(-0.2, 1, 0, new Col(0, 255, 0)),
            new Vertex(0.2, 1, 0, new Col(0, 255, 0)),
            new Vertex(0, 1.2, 0, new Col(0, 255, 0)),

    };

    private final Integer[] index_definition = {
            // Lines
            0, 1,
            2, 3,
            3, 4,
            // Triangles
            6, 7, 8,
            9, 10, 11,
            12, 13, 14
    };

    private final Part[] parts_definition = {
            new Part(Part.Type.LINE, 6, 0),
            new Part(Part.Type.TRIANGLE, 9, 6)
    };

    public Axis(Material material, Vec3D initialPosition) {
        super(material, new Vec3D(0, 0, 0), initialPosition);
        define(vertex_definition, index_definition, parts_definition);
    }

    public Axis(Color color, Vec3D initialPosition) {
        super(color, new Vec3D(0, 0, 0), initialPosition);
        define(vertex_definition, index_definition, parts_definition);
    }


}
