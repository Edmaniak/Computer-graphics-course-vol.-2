package model.objects;

import transforms.Vec3D;

import java.awt.*;

public abstract class Curve extends Solid {


    protected Curve(Color color, Vec3D pivotPoint, Vec3D initialPosition) {
        super(color, pivotPoint, initialPosition);
    }
}
