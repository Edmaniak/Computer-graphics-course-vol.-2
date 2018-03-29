package model.objects;

import material.Material;
import model.Part;
import model.Vertex;
import transforms.Col;
import transforms.Vec3D;

import java.awt.*;
import java.util.Arrays;

public class TetraHedron extends Solid {


    private final Vertex[] vertex_definition = {
            new Vertex(0, 0, 0, new Col(255, 0, 0)),
            new Vertex(0.5, 0, 1, new Col(255, 0, 0)),
            new Vertex(1, 0, 0, new Col(255, 0, 0)),
            new Vertex(0.5, 3, 0.5, new Col(255, 0, 0)),
            new Vertex(0.5, 0, 1, new Col(200, 100, 100)),
            new Vertex(1, 0, 0, new Col(200, 100, 100)),
            new Vertex(0.5, 3, 0.5, new Col(200, 100, 100)),
    };
    private final Integer[] indexes_definiton = {
            0, 2, 1,
            0, 3, 2,
            0, 3, 1,
            4, 5, 6,
    };
    private final Part[] parts_definition = {new Part(Part.Type.TRIANGLE, 12, 0)};


    public TetraHedron(Vec3D initialPosition) {
        super(new Vec3D(0, 0, 0), initialPosition);
        define(vertex_definition, indexes_definiton, parts_definition);
    }

    public TetraHedron(Material material, Vec3D initialPosition) {
        super(material, new Vec3D(0, 0, 0), initialPosition);
        define(vertex_definition, indexes_definiton, parts_definition);
    }


}
