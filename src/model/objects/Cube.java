package model.objects;

import material.Material;
import model.Part;
import model.Vertex;
import transforms.Col;
import transforms.Mat4;
import transforms.Vec3D;

import java.awt.*;
import java.util.Arrays;

public class Cube extends Solid {

    private final Vertex[] vertex_definition = {
            // dolní podstava
            new Vertex(0, 0, 0, new Col(255,0,0)),
            new Vertex(1, 0, 0, new Col(255,0,0)),
            new Vertex(1, 0, 1, new Col(255,0,0)),
            new Vertex(0, 0, 1, new Col(255,0,0)),
            // horní podstava
            new Vertex(0, 1, 0, new Col(0,0,255)),
            new Vertex(1, 1, 0, new Col(0,0,255)),
            new Vertex(1, 1, 1, new Col(0,0,255)),
            new Vertex(0, 1, 1, new Col(0,0,255))
    };
    private final Integer[] index_definition = {
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
            4, 7, 6
    };

    private final Part[] parts_definition = {new Part(Part.Type.TRIANGLE, 36, 0)};

    public Cube(Material material, Vec3D initialPosition) {
        super(material, new Vec3D(0.5, 0.5, 0.5), initialPosition);
        define(vertex_definition, index_definition, parts_definition);
    }

    public Cube(Color color, Vec3D initialPosition) {
        super(color, new Vec3D(0.5, 0.5, 0.5), initialPosition);
        define(vertex_definition, index_definition, parts_definition);
    }


}
