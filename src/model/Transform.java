package model;

import com.sun.javafx.geom.Vec3d;
import transforms.*;

public class Transform {

    Vec3D position;
    double rotX;
    double rotY;
    double rotZ;
    Vec3D pivot;
    Mat4 model = new Mat4Identity();


    public Transform(Vec3D pivotPoint) {
        pivot = pivotPoint;
    }

    public void rotate(double x, double y, double z) {
        rotX = x;
        rotY = y;
        rotZ = z;
        model = new Mat4(model).mul(new Mat4RotY(y).mul(new Mat4RotX(x)));
    }

    public void translate(double x, double y, double z) {
        position = new Vec3D(x, y, z);
        model = new Mat4(model).mul(new Mat4Transl(x, y, z));
    }

    public void translate(Vec3D translation) {
        translate(translation.getX(),translation.getY(),translation.getZ());
    }

    public Vec3D getWorldPosition() {
        return new Vec3D(position).add(pivot);
    }

    public double getRotX() {
        return rotX;
    }

    public double getRotY() {
        return rotY;
    }

    public double getRotZ() {
        return rotZ;
    }

    public Mat4 getModel() {
        return model;
    }
}
