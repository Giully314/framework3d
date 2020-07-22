package framework3d.ecs.system;

import framework3d.ecs.component.MeshComponent;
import framework3d.ecs.component.PositionComponent;
import framework3d.ecs.entity.EntityRef;
import framework3d.geometry.Matrix4x4;
import framework3d.geometry.Triangle;


import java.util.ArrayList;
import java.util.List;

/*
La fase di rendering è divisa in 5 step:
object space -> world space -> camera space -> homogeneus clip space -> window space.

*/

public class RenderingSystem implements ComponentSystem
{
    private ArrayList<MeshComponent> meshes;


    public RenderingSystem()
    {
        meshes = new ArrayList<>();
    }

    public void registerEntity(EntityRef e)
    {
        //Verifica che ci sia posto per la mesh all'interno dell'array
    }


    //Da modificare il nome della funzione. Questo sistema non renderizza la grafica, ma i poligoni.
    public void render(ArrayList<PositionComponent> positions)
    {
        fromObjectSpaceToWorldSpace(positions);
    }


    private void fromObjectSpaceToWorldSpace(ArrayList<PositionComponent> positions)
    {
        /*
        Da aggiungere scaling della mesh
        */

        //DA PARALLELIZZARE 
        for (int i = 0; i < meshes.size(); ++i)
        {
            PositionComponent p = positions.get(i);
            
            if (!p.getState()) continue;

            Matrix4x4 world = p.rotation;
            Matrix4x4 transl = Matrix4x4.makeTranslation(p.position.getCoordinate(0), 
                                                            p.position.getCoordinate(1), 
                                                            p.position.getCoordinate(2));
            
            world = Matrix4x4.multiplication(transl, world);

            List<Triangle> m = meshes.get(i).mesh.getMesh();
            ArrayList<Triangle> worldSpaceObject = new ArrayList<>(m.size());

            for (int j = 0; j < m.size(); ++j)
            {
                worldSpaceObject.add(Matrix4x4.multiplyByTriangle(world, m.get(i)));
            }
        }
    }
}