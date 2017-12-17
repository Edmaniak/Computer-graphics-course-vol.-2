package model.objects;

import transforms.Vec3D;

import java.awt.*;

public abstract class Curve extends Solid {

    protected int step;

    protected Curve(Color color, Vec3D pivotPoint, Vec3D initialPosition, int step) {
        super(color, pivotPoint, initialPosition);
        this.step = step;
    }
}
