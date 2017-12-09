package model.objects;

import model.Transform;

import java.awt.*;

public class SceneObject {

    public enum Topology {
        CUBE,PLANE,TETRAHEDRON
    }
    private Solid solid;
    private Axis axis;
    private Transform transform;

    public SceneObject(Topology topology, Color color) {
       switch (topology) {
           case CUBE:
               solid = new Cube(color);
               break;
           case PLANE:
               solid = new Plane(color);
               break;
           case TETRAHEDRON:
               solid = new TetraHedron(color);
               break;
       }
       transform = new Transform(solid.getDefaultPivotPoint());
    }
}
