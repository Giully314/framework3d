package framework3d.ecs.system;

import framework3d.ecs.Engine;
import framework3d.ecs.component.MeshComponent;
import framework3d.ecs.component.PositionComponent;
import framework3d.ecs.entity.EntityRef;
import framework3d.geometry.Matrix4x4;
import framework3d.geometry.PolygonMesh;
import framework3d.geometry.Triangle;
import framework3d.geometry.Vector4D;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/*
La fase di rendering è divisa in 5 step:
object space -> world space -> camera space -> homogeneus clip space -> window space.

*/

public class RenderingSystem implements ComponentSystem
{
    private Engine engine;
    private int size;
    private EntityRef camera;

    private ArrayList<MeshComponent> meshes;

    private Matrix4x4 projectionMatrix;
    private Matrix4x4 viewportMatrix;


    public RenderingSystem(Engine e)
    {
        engine = e;
        size = 10;
        meshes = new ArrayList<>(size);

        for (int i = 0; i < size; ++i)
        {
            meshes.add(new MeshComponent());
        }
    }


    public void setCamera(EntityRef camera)
    {
        this.camera = camera;
    }

    public EntityRef getCamera()
    {
        return camera;
    }

    public void createProjectionMatrix(float near, float far, int width, int height, float fov)
    {
        projectionMatrix = Matrix4x4.makeProjection(near, far, width / height, fov);

        //da controllare origine x e y
        viewportMatrix = Matrix4x4.makeViewport(0, 0, near, far, width, height);
    }


    //Da modificare il nome della funzione. Questo sistema non renderizza la grafica, ma i poligoni.
    public void render(ArrayList<PositionComponent> positions, Graphics g)
    {
        ArrayList<Triangle> buffer = fromObjectSpaceToWorldSpace(positions);
        buffer = fromWorldSpaceToProjectionSpace(buffer);

        for (Triangle t : buffer)
        {
            //t.print();
            t.setupAndDraw(g);
        }
    }


    private ArrayList<Triangle> fromObjectSpaceToWorldSpace(ArrayList<PositionComponent> positions)
    {
        /*
        Da aggiungere scaling della mesh
        */  
        //Da calcolare la capacity giusta
        ArrayList<Triangle> buffer = new ArrayList<>(100);

        //DA PARALLELIZZARE 
        for (int i = 0; i < meshes.size(); ++i)
        {
            PositionComponent p = positions.get(i);
            MeshComponent w = meshes.get(i);
            
            if (!p.getState() || !w.getState()) continue;

            //scaling dell'oggetto mancante. 
            Matrix4x4 world = Matrix4x4.multiplication(p.rotation, w.scale);
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

    //Oppure posso salvare il riferimento alla camera all'interno del sistema.
    private ArrayList<Triangle> fromWorldSpaceToProjectionSpace(ArrayList<Triangle> buffer)
    {
        PositionComponent pc = camera.getComponent(PositionComponent.class);
      
        Vector4D p = pc.position;

        Matrix4x4 cam = Matrix4x4.makeTranslation(p.getCoordinate(0), p.getCoordinate(1), p.getCoordinate(2));
        //Matrix4x4.printMatrix(cam);
        cam = Matrix4x4.multiplication(cam, pc.rotation);
        //Matrix4x4.printMatrix(cam);

        Matrix4x4 view = Matrix4x4.makeInverse(cam);
        //Matrix4x4.printMatrix(view);
        
        Matrix4x4 proj = Matrix4x4.multiplication(projectionMatrix, view);
        
        ArrayList<Triangle> b = new ArrayList<>(buffer.size());
        for (Triangle t : buffer)
        {
            Triangle w = Matrix4x4.multiplyByTriangle(proj, t);
            w.normalizeByW();
            b.add(w);
        }
        
        proj = Matrix4x4.multiplication(viewportMatrix, proj);
        buffer.clear();
        for (Triangle t : b)
        {   
            t.clearW();
            buffer.add(Matrix4x4.multiplyByTriangle(proj, t));
        }
        return buffer;
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

        int id = e.getID();

        if (id >= size)
        {
            for (int i = 0; i < id - size + 10; ++i)
            {
                meshes.add(new MeshComponent());
            }

            size = meshes.size();
        }


        MeshComponent m = meshes.get(id);
        //m.activateComponent();

        e.registerComponent(MeshComponent.class, m);
    }


    @Override
    public void activateComponent(EntityRef e)
    {
        int id = e.getID();

        //Attivo i componenti.
        meshes.get(id).activateComponent();
    }

    /******************************** FINE INTERFACCIA COMPONENT SYSTEM ************************************* */


    public void loadMesh(EntityRef e, String meshName)
    {
        int id = e.getID();
        MeshComponent m = meshes.get(id);

        m.mesh = new PolygonMesh(meshName);
    }
}