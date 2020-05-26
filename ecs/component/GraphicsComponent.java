package framework3d.ecs.component;

import java.awt.Graphics;

import framework3d.geometry.PolygonMesh;

public class GraphicsComponent implements RenderComponent
{
    private PolygonMesh mesh;
    
    public GraphicsComponent(String filename)
    {
        mesh = new PolygonMesh(filename);
    }


    public void render(Graphics g)
    {
        //draw
    }
}