package model.objects;

import transforms.Camera;
import transforms.Vec3D;

public class SceneCamera extends Camera {


    public SceneCamera(Vec3D vec3D, double azimuth, int zenit, int radius, boolean firstPerson) {
        super(vec3D, azimuth, zenit, radius, firstPerson);
    }

    public void rotate(double ang) {
        addAzimuth(ang);
    }
}
