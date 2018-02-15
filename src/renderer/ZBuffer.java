package renderer;

public class ZBuffer {

    private double[][] zBuffer;
    private int width;
    private int height;

    public ZBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        zBuffer = new double[width][height];
        clear();
    }

    public void clear() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                zBuffer[i][j] = 1;
            }
        }
    }

    public void setDepth(int x, int y, double depth) {
        zBuffer[x][y] = depth;
    }

    public double getDepth(int x, int y) {
        return zBuffer[x][y];
    }
}
