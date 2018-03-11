package model.objects;

import material.Material;
import material.Texture;
import model.Part;
import model.Vertex;
import transforms.Col;
import transforms.Point2D;
import transforms.Vec3D;

import java.awt.*;
import java.util.Arrays;

public class Plane extends Solid {

    private final Vertex[] vertex_definition = {
            // two triangles
            new Vertex(-4, 0, -2, new Col(255, 0, 0), new Point2D(0, 0)),
            new Vertex(4, 0, -2, new Col(255, 255, 0), new Point2D(1280, 0)),
            new Vertex(-4, 0, 2, new Col(255, 0, 255), new Point2D(0, 720)),
            new Vertex(4, 0, 2, new Col(0, 255, 0), new Point2D(1280, 720)),
    };

    private final Integer[] index_definition = {
            // Triangles
            0, 1, 2,
            2, 3, 1,
    };

    private final Part[] parts_definition = {
            new Part(Part.Type.TRIANGLE, 6, 0)
    };

    public Plane(Color color, Vec3D initialPosition) {
        super(color, new Vec3D(0, 0, 0), initialPosition);
        define(vertex_definition, index_definition, parts_definition);
    }

    public Plane(Material material, Vec3D initialPosition) {
        super(material, new Vec3D(0, 0, 0), initialPosition);
        define(vertex_definition, index_definition, parts_definition);
    }

    public Plane(Texture texture, Vec3D initialPosition) {
        super(texture, initialPosition);
        define(vertex_definition, index_definition, parts_definition);
    }

}
