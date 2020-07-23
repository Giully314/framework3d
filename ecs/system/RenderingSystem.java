package framework3d.ecs.system;

import framework3d.ecs.Engine;
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
    private Engine engine;

    private ArrayList<MeshComponent> meshes;


    public RenderingSystem(Engine e)
    {
        engine = e;
        meshes = new ArrayList<>();
    }


    //Da modificare il nome della funzione. Questo sistema non renderizza la grafica, ma i poligoni.
    public void render(ArrayList<PositionComponent> positions)
    {
        ArrayList<Triangle> buffer = fromObjectSpaceToWorldSpace(positions);
        fromWorldSpaceToCameraSpace(buffer);
    }


    private ArrayList<Triangle> fromObjectSpaceToWorldSpace(ArrayList<PositionComponent> positions)
    {
        /*
        Da aggiungere scaling della mesh
        */  
        //Da calcolare la capacity giusta
        ArrayList<Triangle> buffer = new ArrayList<>(1000);

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

            buffer.addAll(worldSpaceObject);
        }

        return buffer;
    }


    private void fromWorldSpaceToCameraSpace(ArrayList<Triangle> buffer /*, camera */)
    {

    }

    /******************************** INTERFACCIA COMPONENT SYSTEM ************************************* */
    
    @Override
    public Engine getEngine()
    {
        return engine;
    }

    @Override
    public void setEngine(Engine e)
    {
        engine = e; 
    }


    @Override
    public void registerEntity(EntityRef e)
    {
        //Verifica che ci sia posto per la mesh all'interno dell'array
    }

    /******************************** FINE INTERFACCIA COMPONENT SYSTEM ************************************* */

}