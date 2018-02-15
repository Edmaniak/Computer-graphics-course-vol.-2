package utilities;

public class Util {

    public static double round(double n) {
        return Math.round(n * 100) / 100;
    }

    public static double lerpDouble(double val1, double val2, double par) {
        return val1 * (1.0 - par) + val2 * par;
    }

    public static int lerpInt(double val1, double val2, double par) {
        return (int) lerpDouble(val1, val2, par);
    }

}
