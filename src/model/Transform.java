package model;

import com.sun.javafx.geom.Vec3d;
import transforms.*;

public class Transform {

    Vec3D position;
    Vec3D rotVec;
    double rotX;
    double rotY;
    double rotZ;
    Vec3D pivot;
    Mat4 model = new Mat4Identity();


    public Transform(Vec3D pivotPoint) {
        pivot = pivotPoint;
        position = new Vec3D();
        rotVec = new Vec3D();
    }

    public void rotate(double x, double y, double z) {
        rotX += Math.toDegrees(x);
        rotY += Math.toDegrees(y);
        rotZ += Math.toDegrees(z);
        rotVec = new Vec3D(rotX, rotY, rotZ);
        Vec3D p = getWorldPosition();
        Mat4 translMat = new Mat4Transl(-p.getX(), -p.getY(), -p.getZ());
        Mat4 rotMat = new Mat4(new Mat4RotX(x).mul(new Mat4RotY(y)));
        Mat4 transBack = new Mat4Transl(p.getX(), p.getY(), p.getZ());
        model = new Mat4(model).mul(translMat).mul(rotMat).mul(transBack);
    }

    public void rotate(Vec3D rotVector) {
        rotate(rotVector.getX(), rotVector.getY(), rotVector.getZ());
    }

    public void translate(double x, double y, double z) {
        position = new Vec3D(position).add(new Vec3D(x, y, z));
        model = new Mat4(model).mul(new Mat4Transl(x, y, z));
    }

    public void translate(Vec3D translation) {
        translate(translation.getX(), translation.getY(), translation.getZ());
    }

    public void scale(double s) {
        model = new Mat4(model).mul(new Mat4Scale(s, s, s));
    }

    public Vec3D getWorldPosition() {
        return new Vec3D(position).add(pivot);
    }

    public void setWorldPosition(Vec3D position) {
        translateToOrigin();
        translate(position);
    }

    public void setRotation(double x, double y, double z) {
        resetRotation();
        rotate(x, y, z);
    }

    public void setRotation(Vec3D rotVec) {
        setRotation(rotVec.getX(), rotVec.getY(), rotVec.getZ());
    }

    public void resetRotation() {
        Vec3D p = getWorldPosition();
        Mat4 translMat = new Mat4Transl(-p.getX(), -p.getY(), -p.getZ());
        Mat4 rotMat = new Mat4(new Mat4RotX(Math.toRadians(-rotX)).mul(new Mat4RotY(Math.toRadians(-rotY))));
        Mat4 transBack = new Mat4Transl(p.getX(), p.getY(), p.getZ());
        model = new Mat4(model).mul(translMat).mul(rotMat).mul(transBack);
        rotX = 0;
        rotY = 0;
        rotZ = 0;
    }

    public void translateToOrigin() {
        translate(position.opposite());
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

    public Vec3D getRotVec() {
        return rotVec;
    }

    @Override
    public String toString() {
        String out = "Position: x: " +  position.getX() + " y: " +  position.getY() + " z: " +  position.getZ() + "\n";
        out += "Rotation: x: " + rotX + " y: " + rotY + " z: " + rotZ;
        return out;
    }
}
