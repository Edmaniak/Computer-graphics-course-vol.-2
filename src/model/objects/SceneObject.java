package model.objects;

import model.Transform;
import transforms.Vec3D;

public abstract class SceneObject {
    public Transform transform;
    private Vec3D pivotPoint;
    private Vec3D position;

    protected SceneObject() {
        this.pivotPoint = new Vec3D(0, 0, 0);
        transform = new Transform(pivotPoint);
        this.position = new Vec3D(0, 0, 0);
    }

    public SceneObject(Vec3D pivotPoint, Vec3D position) {
        this.pivotPoint = pivotPoint;
        this.transform = new Transform(pivotPoint);
        this.position = position;
        transform.translate(position);

    }

    public SceneObject(Vec3D position) {
        this.pivotPoint = new Vec3D(0, 0, 0);
        this.transform = new Transform(pivotPoint);
        this.position = position;
        transform.translate(position);
    }

    public Vec3D getDefaultPivotPoint() {
        return pivotPoint;
    }

    public Vec3D getWorldPosition() {
        return transform.getWorldPosition();
    }

    public Vec3D getPosition() {
        return position;
    }
}
