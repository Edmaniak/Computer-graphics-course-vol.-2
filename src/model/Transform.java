package model;

import transforms.*;
import utilities.Util;

public class Transform {

    private Vec3D position = new Vec3D();
    private Vec3D rotVec = new Vec3D();
    private double rotX = 0;
    private double rotY = 0;
    private double rotZ = 0;
    private double scale = 1;
    private Vec3D pivot;
    private Mat4 model = new Mat4Identity();


    public Transform(Vec3D pivotPoint) {
        pivot = pivotPoint;
    }

    public void rotate(double angX, double angY, double angZ) {
        rotX += (Math.toDegrees(angX));
        rotY += Math.toDegrees(angY);
        rotZ += Math.toDegrees(angZ);
        rotVec = new Vec3D(rotX, rotY, rotZ);
        Vec3D p = getWorldPosition();
        Mat4 translMat = new Mat4Transl(-p.getX(), -p.getY(), -p.getZ());
        Mat4 rotMat = new Mat4(new Mat4RotX(angX).mul(new Mat4RotY(angY)));
        Mat4 transBack = new Mat4Transl(p.getX(), p.getY(), p.getZ());
        model = new Mat4(model).mul(translMat).mul(rotMat).mul(transBack);
    }

    public void rotate(Vec3D rotVector) {
        rotate(rotVector.getX(), rotVector.getY(), rotVector.getZ());
    }

    private void translate(double x, double y, double z) {
        position = new Vec3D(position).add(new Vec3D(x, y, z));
        model = new Mat4(model).mul(new Mat4Transl(x, y, z));
    }

    public void translate(Vec3D translation) {
        translate(translation.getX(), translation.getY(), translation.getZ());
    }

    public void scale(double s) {
        if (s != 0) {
            double SCALE_FACTOR = 0.01;
            s = SCALE_FACTOR * Math.signum(s);
            scale += s;
            Vec3D p = getWorldPosition();
            Mat4 translMat = new Mat4Transl(-p.getX(), -p.getY(), -p.getZ());
            Mat4 sizeMat = new Mat4Scale(1 + SCALE_FACTOR * Math.signum(s));
            Mat4 transBack = new Mat4Transl(p.getX(), p.getY(), p.getZ());
            model = new Mat4(model).mul(translMat).mul(sizeMat).mul(transBack);
        }
    }

    public Vec3D getWorldPosition() {
        return new Vec3D(position).add(pivot);
    }

    public void setWorldPosition(Vec3D position) {
        translateToOrigin();
        translate(position);
    }

    private void setRotation(double x, double y, double z) {
        resetRotation();
        rotate(x, y, z);
    }

    public void setRotation(Vec3D rotVec) {
        setRotation(rotVec.getX(), rotVec.getY(), rotVec.getZ());
    }

    private void resetRotation() {
        Vec3D p = getWorldPosition();
        Mat4 translMat = new Mat4Transl(-p.getX(), -p.getY(), -p.getZ());
        Mat4 rotMat = new Mat4(new Mat4RotX(Math.toRadians(-rotX)).mul(new Mat4RotY(Math.toRadians(-rotY))));
        Mat4 transBack = new Mat4Transl(p.getX(), p.getY(), p.getZ());
        model = new Mat4(model).mul(translMat).mul(rotMat).mul(transBack);
        rotX = 0;
        rotY = 0;
        rotZ = 0;
    }

    private void translateToOrigin() {
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

    public void setRotX(double rotX) {
        this.rotX = rotX % 360;
    }

    public void setRotY(double rotY) {
        this.rotY = rotY % 360;
    }

    public void setRotZ(double rotZ) {
        this.rotZ = rotZ % 360;
    }

    public Mat4 getModel() {
        return model;
    }

    public Vec3D getRotVec() {
        return rotVec;
    }

    @Override
    public String toString() {
        String out = "Position: x: " + Util.round(position.getX()) + " y: " + Util.round(position.getY()) + " z: " + Util.round(position.getZ()) + "\n";
        out += "Rotation: x: " + Util.round(rotX) + " y: " + Util.round(rotY) + " z: " + Util.round(rotZ) + "\n";
        out += "Size: " + scale;
        return out;
    }
}
