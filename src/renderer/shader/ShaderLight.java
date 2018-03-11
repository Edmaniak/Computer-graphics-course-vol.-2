package renderer.shader;

import material.Material;
import model.Vertex;
import model.light.AmbientLight;
import model.light.Light;
import model.light.PointLight;
import transforms.Col;
import transforms.Vec3D;

import java.util.ArrayList;

public class ShaderLight extends Shader {

    private ArrayList<PointLight> lights;
    private Vec3D lookVector;
    private AmbientLight ambientLight;
    private Material material;

    public ShaderLight(ArrayList<PointLight> lights, Vec3D lookVector, AmbientLight ambientLight, Material material) {
        this.lights = lights;
        this.lookVector = lookVector;
        this.ambientLight = ambientLight;
        this.material = material;
    }

    @Override
    public Col apply(Vertex v) {
        Col color = v.getColor().mul(1 / v.getOne());
        Col color2 = ambientLight.getContribution(material);
        Col color3 =color.addna(color2);
        for (Light light : lights) {

        }
        return color3.saturate();
    }
}
