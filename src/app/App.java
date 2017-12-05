package app;

import model.objects.Axis;
import model.objects.Cube;
import renderer.Renderer;

public class App {
    public static void main(String args[]) {
        Axis a = new Axis();
        Renderer r = new Renderer();
        r.render(a);
    }
}
