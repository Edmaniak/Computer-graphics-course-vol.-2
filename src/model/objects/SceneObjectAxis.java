package model.objects;

import transforms.Col;
import transforms.Vec3D;

import java.awt.*;

public class SceneObjectAxis extends Axis {

    public SceneObjectAxis(Col color, Vec3D initialPosition) {
        super(initialPosition);
    }

    public void alignFor(SceneObject object) {
        transform.setWorldPosition(object.transform.getWorldPosition());
    }
}
