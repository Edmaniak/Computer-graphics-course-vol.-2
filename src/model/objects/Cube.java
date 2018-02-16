package model.objects;

import material.Material;
import model.Part;
import model.Vertex;
import transforms.Mat4;
import transforms.Vec3D;

import java.awt.*;
import java.util.Arrays;

public class Cube extends Solid {

    private final Vertex[] vertex_definition = {
            // dolní podstava
            new Vertex(0, 0, 0, Color.RED),
            new Vertex(1, 0, 0, Color.RED),
            new Vertex(1, 0, 1, Color.RED),
            new Vertex(0, 0, 1, Color.RED),
            // horní podstava
            new Vertex(0, 1, 0, Color.BLUE),
            new Vertex(1, 1, 0, Color.BLUE),
            new Vertex(1, 1, 1, Color.BLUE),
            new Vertex(0, 1, 1, Color.BLUE)
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

    public Cube(double size, Material material, Vec3D initialPosition) {
        super(material, new Vec3D(0.5, 0.5, 0.5), initialPosition);
        for (int i = 0; i < vertex_definition.length; i++) {
            double x = vertex_definition[i].getX() * size;
            double y = vertex_definition[i].getX() * size;
            double z = vertex_definition[i].getX() * size;
            vertex_definition[i] = new Vertex(x, y, z);
        }
        define(vertex_definition, index_definition, parts_definition);
    }

    public Cube(Color color, Vec3D initialPosition) {
        super(color, new Vec3D(0.5, 0.5, 0.5), initialPosition);
        define(vertex_definition, index_definition, parts_definition);
    }

    public Cube(Vec3D initialPosition) {
        super(new Vec3D(0.5, 0.5, 0.5), initialPosition);
        define(vertex_definition, index_definition, parts_definition);
    }


}
