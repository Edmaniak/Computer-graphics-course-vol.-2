package model.objects;

import transforms.Vec3D;

import java.awt.*;

public class SceneObjectAxis extends Axis {

    public SceneObjectAxis(Color color, Vec3D initialPosition) {
        super(color, initialPosition);
    }

    public void alignFor(Solid solid) {
        transform.setWorldPosition(solid.transform.getWorldPosition());
    }
}
