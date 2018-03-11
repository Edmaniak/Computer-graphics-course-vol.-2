package model.objects;

import transforms.Col;
import transforms.Vec3D;

import java.awt.*;

public class SceneObjectAxis extends Axis {

    public SceneObjectAxis(Col color, Vec3D initialPosition) {
        super(color, initialPosition);
    }

    public void alignFor(Solid solid) {
        transform.setWorldPosition(solid.transform.getWorldPosition());
    }
}
