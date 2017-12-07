package renderer;

import java.util.ArrayList;
import java.util.List;

import model.Parts;
import model.Vertex;
import model.objects.Solid;
import transforms.Mat4;

public class Renderer {

    public List<Vertex> vb = new ArrayList<>();

    private Mat4 model = new Mat4();
    private Mat4 view = new Mat4();
    private Mat4 projection = new Mat4();

    public void render(Solid sld) {

        // Transformace
        Mat4 matMVP = model.mul(view).mul(projection);

        for (Vertex v : sld.vertices())
            vb.add(v.mul(matMVP));
        for (Parts p : sld.getParts()) {

            switch (p.getType()) {

                case LINE: {
                    for (int i = p.getStart(); i < ((p.getCount() * 2) + p.getStart()); i += 2) {
                        line(
                                sld.vertices().get(sld.indexes().get(i)),
                                sld.vertices().get(sld.indexes().get(i + 1))
                        );
                    }
                    break;
                }

                case TRIANGLE: {
                    for (int i = p.getStart(); i < ((p.getCount() * 3) + p.getStart()); i += 3) {
                        triangle(
                                sld.vertices().get(sld.indexes().get(i)),
                                sld.vertices().get(sld.indexes().get(i + 1)),
                                sld.vertices().get(sld.indexes().get(i + 2))
                        );
                    }
                    break;
                }
            }
        }
    }

    private void triangle(Vertex v1, Vertex v2, Vertex v3) {
        System.out.println("v1: " + v1 + " v2: " + v2 + " v3 " + v3);
    }

    private void line(Vertex origin, Vertex end) {
        System.out.println("O: " + origin + " " + "E: " + end);
    }

}
