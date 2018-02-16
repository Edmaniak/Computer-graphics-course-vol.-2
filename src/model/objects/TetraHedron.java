package model.objects;

import material.Material;
import model.Part;
import model.Vertex;
import transforms.Vec3D;

import java.awt.*;
import java.util.Arrays;

public class TetraHedron extends Solid {


    private final Vertex[] vertex_definition = {
            new Vertex(0, 0, 0),
            new Vertex(0.5, 0, 1),
            new Vertex(1, 0, 0),
            new Vertex(0.5, 1, 0.5)
    };
    private final Integer[] indexes_definiton = {
            0, 1, 2,
            0, 3, 2,
            0, 3, 1,
            1, 2, 3
    };
    private final Part[] parts_definition = {new Part(Part.Type.TRIANGLE, 12, 0)};

    public TetraHedron(Vec3D initialPosition) {
        super(new Vec3D(0, 0, 0), initialPosition);
        define(vertex_definition, indexes_definiton, parts_definition);
    }

    public TetraHedron(Color color, Vec3D initialPosition) {
        super(color, new Vec3D(0, 0, 0), initialPosition);
        define(vertex_definition, indexes_definiton, parts_definition);
    }

    public TetraHedron(Material material, Vec3D initialPosition) {
        super(material, new Vec3D(0, 0, 0), initialPosition);
        define(vertex_definition, indexes_definiton, parts_definition);
    }


}
