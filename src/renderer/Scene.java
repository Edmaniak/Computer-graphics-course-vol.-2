package renderer;

import gui.Canvas;
import model.objects.Solid;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private List<Solid> solids;
    private Renderer renderer;
    private Canvas canvas;

    public Scene(Canvas canvas) {
        this.canvas = canvas;
        solids = new ArrayList<>();
        RasterizerLine rl = new RasterizerLine(canvas.getMainBuffer());
        RasterizerTriangle rt = new RasterizerTriangle(canvas.getMainBuffer());
        renderer = new Renderer(rl, rt);
    }

    public void render() {
        for (Solid solid : solids) {
            renderer.render(solid);
        }
    }
}
