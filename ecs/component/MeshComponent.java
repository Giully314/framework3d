package framework3d.ecs.component;

import framework3d.geometry.Matrix4x4;
import framework3d.geometry.PolygonMesh;

public class MeshComponent extends Component
{   
    public PolygonMesh mesh = null;
    public Matrix4x4 scale = Matrix4x4.makeScaling(1.5f, 1.5f, 1.5f);

    @Override
    public void printComponent()
    {
        System.out.println("MeshComponent");
    }
}