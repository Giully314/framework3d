package framework3d.ecs.system;

import framework3d.ecs.Engine;
import framework3d.ecs.component.MeshComponent;
import framework3d.ecs.component.PositionComponent;
import framework3d.ecs.entity.EntityRef;
import framework3d.geometry.Matrix4x4;
import framework3d.geometry.PolygonMesh;
import framework3d.geometry.Triangle;
import framework3d.geometry.Vector4D;
import framework3d.utility.camera.Camera;

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
    private Camera camera;

    private ArrayList<MeshComponent> meshes;

    private Matrix4x4 projectionMatrix;
    private Matrix4x4 viewportMatrix;

    private Shader shader;


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


    public void setCamera(Camera camera)
    {
        this.camera = camera;
    }

    public Camera getCamera()
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
        // ArrayList<Triangle> buffer = fromObjectSpaceToWorldSpace(positions);
        // buffer = fromWorldSpaceToProjectionSpace(buffer);
        ArrayList<Triangle> out = new ArrayList<Triangle>(100);
        
        
        // cam.rotation = Matrix4x4.makeRotationX(0.785398f);
        Matrix4x4 view = camera.getViewMatrix();


        //Matrix4x4.printMatrix(view);
        for (int i = 0; i < positions.size(); ++i)
        {
            PositionComponent p = positions.get(i);
            MeshComponent m = meshes.get(i);

            if (!p.getState() || !m.getState())
            {
                continue;
            }

    
            Matrix4x4 world = Matrix4x4.makeAffineTransformation(m.scale, p.rotation, p.position);
            var mesh = m.mesh.getMesh();
            ArrayList<Triangle> buffer = new ArrayList<>(mesh.size());

            
            for (Triangle t : mesh)
            {   
                //******************* dall'object space al world space ******************************* */
                
                Triangle transformed = Matrix4x4.multiplyByTriangle(world, t);
                
                /*************************************************************************************** */
                

                /************************clipping space ******************************************* */


                /********************************************************************************* */

                transformed.calculateNormal();
                transformed.getNormal().normalize();
                
                //transformed.getNormal().print();

                Vector4D visible = Vector4D.sub(transformed.t[0], camera.getCameraPosition());

                if (Vector4D.dotProduct(transformed.getNormal(), visible) > 0)
                {
                    //*********************dal world space al camera space ******************************** */
                
                    transformed = Matrix4x4.multiplyByTriangle(view, transformed);

                    /********************************************************************************** */

                    /******************************** shader *********************************************** */
                    
                    shader.shader(transformed);
                    

                    /************************************************************************************** */

                    /************************* projection space *************************************** */
                    transformed = Matrix4x4.multiplyByTriangle(projectionMatrix, transformed);
                    
                    transformed.normalizeByW();

                    //NOTA IMPORTANTE 
                    //x e y forse sono invertite, reinvertirle moltiplicando per -1.
                    //farlo nella matrice viewport. check glViewport
                    //System.out.println("RENDERING SYSTEM DOPO PROJECTION MATRIX, NOTA");

                    transformed.clearW();

                    Vector4D p1 = transformed.getVertex(0);
                    Vector4D p2 = transformed.getVertex(1);
                    Vector4D p3 = transformed.getVertex(2);

                    p1.setCoordinate(0, p1.getCoordinate(0) * -1);
                    p1.setCoordinate(1, p1.getCoordinate(1) * -1);

                    p2.setCoordinate(0, p2.getCoordinate(0) * -1);
                    p2.setCoordinate(1, p2.getCoordinate(1) * -1);

                    p3.setCoordinate(0, p3.getCoordinate(0) * -1);
                    p3.setCoordinate(1, p3.getCoordinate(1) * -1);



                    transformed = Matrix4x4.multiplyByTriangle(viewportMatrix, transformed);
                    
                    /********************************************************************************* */

                    buffer.add(transformed);
                }
            }

            out.addAll(buffer);
        }

        //riordino i triangoli in base alla profondità.
        out.sort((Triangle t1, Triangle t2) -> 
		{
			float z1 = (t1.t[0].v[2] + t1.t[1].v[2] + t1.t[2].v[2]) / 3.0f;
			float z2 = (t2.t[0].v[2] + t2.t[1].v[2] + t2.t[2].v[2]) / 3.0f;
            
			return -Float.compare(z1, z2);
		});

        for (Triangle t : out)
        {
            //t.print();
            t.setupAndDraw(g);
        }
    }


    // private ArrayList<Triangle> fromObjectSpaceToWorldSpace(ArrayList<PositionComponent> positions)
    // {
    //     /*
    //     Da aggiungere scaling della mesh
    //     */  
    //     //Da calcolare la capacity giusta
    //     ArrayList<Triangle> buffer = new ArrayList<>(100);

    //     //DA PARALLELIZZARE 
    //     for (int i = 0; i < meshes.size(); ++i)
    //     {
    //         PositionComponent p = positions.get(i);
    //         MeshComponent w = meshes.get(i);
            
    //         if (!p.getState() || !w.getState()) continue;

    //         //scaling dell'oggetto mancante. 
            
    //         // Matrix4x4 transl = Matrix4x4.makeTranslation(p.position.getCoordinate(0), 
    //         //                                                  p.position.getCoordinate(1), 
    //         //                                                 p.position.getCoordinate(2));
            
            
    //         Matrix4x4 world = Matrix4x4.makeAffineTransformation(w.scale, p.rotation, p.position);
    //         // p.position.print();
    //         // System.out.println("entity i: " + i );

    //         List<Triangle> m = meshes.get(i).mesh.getMesh();
    //         ArrayList<Triangle> worldSpaceObject = new ArrayList<>(m.size());
            
    //         for (int j = 0; j < m.size(); ++j)
    //         {
    //             worldSpaceObject.add(Matrix4x4.multiplyByTriangle(world, m.get(i)));
    //         }

    //         buffer.addAll(worldSpaceObject);
    //     }

    //     // System.out.println("world space");
    //     // for (Triangle t : buffer)
    //     // {
    //     //     t.print();
    //     // }

    //     return buffer;
    // }

    // //Oppure posso salvare il riferimento alla camera all'interno del sistema.
    // private ArrayList<Triangle> fromWorldSpaceToProjectionSpace(ArrayList<Triangle> buffer)
    // {
    //     PositionComponent pc = camera.getComponent(PositionComponent.class);
      
    //     Vector4D p = pc.position;

    //     Matrix4x4 cam = Matrix4x4.makeTranslation(p.getCoordinate(0), p.getCoordinate(1), p.getCoordinate(2));
    //     //Matrix4x4.printMatrix(cam);
    //     cam = Matrix4x4.multiplication(cam, pc.rotation);
    //     //Matrix4x4.printMatrix(cam);

    //     Matrix4x4 view = Matrix4x4.makeInverse(cam);
        
        
    //     ArrayList<Triangle> b = new ArrayList<>(buffer.size());
    //     for (Triangle t : buffer)
    //     {
    //         b.add(Matrix4x4.multiplyByTriangle(view, t));
    //     }

    //     Matrix4x4 proj = projectionMatrix;
        
    //     buffer.clear();
    //     for (Triangle t : b)
    //     {
    //         Triangle w = Matrix4x4.multiplyByTriangle(proj, t);
    //         w.normalizeByW();

    //         w.clearW();

    //         w = Matrix4x4.multiplyByTriangle(viewportMatrix, w);

    //         buffer.add(w);
    //     }

    //     buffer.sort((Triangle t1, Triangle t2) -> 
	// 	{
	// 		float z1 = (t1.t[0].v[2] + t1.t[1].v[2] + t1.t[2].v[2]) / 3.0f;
	// 		float z2 = (t2.t[0].v[2] + t2.t[1].v[2] + t2.t[2].v[2]) / 3.0f;
            
	// 		return Float.compare(z1, z2);
	// 	});
        
    //     return buffer;
    // }
    
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


    public void setShader(Shader s)
    {
        shader = s;
    }
}