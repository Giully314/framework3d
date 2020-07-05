package framework3d.ecs.component;

import framework3d.geometry.PolygonMesh;

public class MeshComponent implements Component
{
    private final PolygonMesh mesh;


    //metodi per inizializzazione, rendere final mesh
    public MeshComponent(String filename)
    {
        mesh = new PolygonMesh(filename);

    }

    //********************** GET ************************** */
    
    public PolygonMesh getPolygonMesh()
    {
        return mesh;
    }

    //********************** FINE GET ************************** */
}