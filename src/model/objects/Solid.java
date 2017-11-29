package model.objects;

import model.Parts;
import model.Vertex;
import transforms.Point2D;

import java.util.ArrayList;
import java.util.List;

public abstract class Solid {

    private final List<Vertex> vertexBuffer;
    private final List<Integer> indexBuffer;
    private final List<Parts> parts;

    public Solid() {
        this.vertexBuffer = new ArrayList<>();
        this.indexBuffer = new ArrayList<>();
        this.parts = new ArrayList<>();
    }

    public List<Vertex> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public List<Parts> getParts() {
        return parts;
    }

    @Override
    public String toString() {
        String out = getClass().toString();

    }
}
