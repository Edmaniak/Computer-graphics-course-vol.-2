package model.objects;

import model.Part;
import model.Vertex;
import transforms.Col;
import transforms.Vec3D;

import java.awt.*;
import java.util.Arrays;

public class IcoSphere extends Solid {

    private final Integer[] indexes_definiton = {
            0, 11, 5,
            0, 5, 1,
            0, 1, 7,
            0, 7, 10,
            0, 10, 11,
            1, 5, 9,
            5, 11, 4,
            11, 10, 2,
            10, 7, 6,
            7, 1, 8,
            3,9,4,
            3,4,2,
            3,2,6,
            3,6,8,
            3,8,9,
            4,9,5,
            2,4,11,
            6,2,10,
            8,6,7,
            9,8,1
    };

    private final Part[] parts_definition = {new Part(Part.Type.TRIANGLE, 60, 0)};

    public IcoSphere(Vec3D initialPosition, double size) {
        super(new Vec3D(0, 0, 0),initialPosition);
        double t = (1 + Math.sqrt(size)) / 2;

        vertices().add(new Vertex(-1, t, 0));
        vertices().add(new Vertex(1, t, 0));
        vertices().add(new Vertex(-1, -t, 0));
        vertices().add(new Vertex(1, t, 0));

        vertices().add(new Vertex(0, -1, t));
        vertices().add(new Vertex(0, 1, t));
        vertices().add(new Vertex(0, -1, -t));
        vertices().add(new Vertex(0, 1, t));

        vertices().add(new Vertex(t, 0, -1));
        vertices().add(new Vertex(t, 0, 1));
        vertices().add(new Vertex(-t, 0, -1));
        vertices().add(new Vertex(-t, 0, 1));

        indexes().addAll(Arrays.asList(indexes_definiton));
        parts().addAll(Arrays.asList(parts_definition));

    }
}
