package framework3d.ecs.component;

import framework3d.geometry.Vector4D;
import framework3d.geometry.PolygonMesh;

public class MeshComponent extends Component
{   
    public PolygonMesh mesh = null;
    public Vector4D scale = new Vector4D(2, 2, 2);

    @Override
    public void printComponent()
    {
        System.out.println("MeshComponent");
    }
}