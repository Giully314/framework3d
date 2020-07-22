package framework3d.ecs.component;

import framework3d.geometry.PolygonMesh;

public class MeshComponent extends Component
{   
    public PolygonMesh mesh = null;


    @Override
    public void printComponent()
    {
        System.out.println("MeshComponent");
    }
}