package material;

import transforms.Col;

import java.awt.*;

public class Material {

    private double ka = 0.5;
    private double ks = 0.5;
    private double kd = 0.5;
    private double h = 10;

    public Material() {

    }

    public Material(double ka, double ks, double kd, double h) {
        this.ka = ka;
        this.ks = ks;
        this.kd = kd;
        this.h = h;
    }

    public double getKa() {
        return ka;
    }

    public void setKa(double ka) {
        this.ka = ka;
    }

    public double getKs() {
        return ks;
    }

    public void setKs(double ks) {
        this.ks = ks;
    }

    public double getKd() {
        return kd;
    }

    public void setKd(double kd) {
        this.kd = kd;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

}
