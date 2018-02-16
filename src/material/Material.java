package material;

import java.awt.*;

public class Material {

    private Color color;
    private double ka = 0.5;
    private double ks = 0.5;
    private double kd = 0.5;
    private double h = 10;

    public Material(Color color) {
        this.color = color;
    }

    public Material(double ka, double ks, double kd, double h) {
        this.ka = ka;
        this.ks = ks;
        this.kd = kd;
        this.h = h;
    }

    public Material(Color color, double ka, double ks, double kd, double h) {
        this(ka, ks, kd, h);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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

    public int getIluminatedRed() {
        return (int) (color.getRed() * ka);
    }

    public int getIluminatedBlue() {
        return (int) (color.getBlue() * ka);
    }

    public int getIluminatedGreen() {
        return (int) (color.getGreen() * ka);
    }
}
