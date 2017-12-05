package app;

import model.objects.Cube;
import renderer.Renderer;

public class App {
    public static void main(String args[]) {
        Cube c = new Cube();
        Renderer r = new Renderer();
        r.render(c);
    }
}
