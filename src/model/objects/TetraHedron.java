package model.objects;

import model.Parts;
import model.Vertex;

import java.util.Arrays;

public class TetraHedron extends Solid {


    private Vertex[] vertex_definition = {
            new Vertex(0, 0, 0),
            new Vertex(0.5, 0, 1),
            new Vertex(1, 0, 0),
            new Vertex(0.5, 1, 0.5)
    };
    private Integer[] indexes_definiton = {
            0,1,2,
            0,3,2,
            0,3,1,
            1,2,3
    };
    private Parts[] parts_definition = {new Parts(Parts.Type.TRIANGLE, 12, 0)};

    public TetraHedron() {
        vertices().addAll(Arrays.asList(vertex_definition));
        indexes().addAll(Arrays.asList(indexes_definiton));
        getParts().addAll(Arrays.asList(parts_definition));
    }


}
