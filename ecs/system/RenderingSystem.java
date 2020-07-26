package framework3d.ecs.system;

import framework3d.ecs.Engine;
import framework3d.ecs.component.MeshComponent;
import framework3d.ecs.component.PositionComponent;
import framework3d.ecs.entity.EntityRef;
import framework3d.geometry.*;
import framework3d.utility.camera.Camera;

import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.ArrayList;

import java.awt.Color;

/*
La fase di rendering è divisa in 5 step:
object space -> world space -> camera space -> homogeneus clip space -> window space.

*/

public class RenderingSystem implements ComponentSystem
{
    private Engine engine;
    private int size;
    private Camera camera;

    private int width;
    private int height;

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

        this.width = width;
        this.height = height;
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
                    //luce
                    
                    /******************************** shader *********************************************** */
                    
                    //shader.shader(transformed);
                    

                    /************************************************************************************** */


                    //*********************dal world space al camera space ******************************** */
                
                    transformed = Matrix4x4.multiplyByTriangle(view, transformed);

                    /********************************************************************************** */


                    /*********************************** clipping ************************** */

                    //int nClipped = 0;
                    Triangle[] clipped = new Triangle[2];
                    clipped[0] = new Triangle();
                    clipped[1] = new Triangle();

                    clipped[0].setColor(transformed.getColor());
                    clipped[1].setColor(transformed.getColor());

                    //il primo vettore deve avere componente near plane
                    Triangle[] w = ClipAgainstPlane(new Vector4D(0, 0, -0.1f ), 
                    new Vector4D(0, 0, -1), transformed);
 
                    //System.out.println("w: " + w.length);

                    /*********************************************************************** */

                    /************************* projection space *************************************** */
                    for (int j = 0; j < w.length; ++j)
                    {
                        transformed = Matrix4x4.multiplyByTriangle(projectionMatrix, w[j]);
                    
                        transformed.normalizeByW();

                        //NOTA IMPORTANTE 
                        //x e y forse sono invertite, reinvertirle moltiplicando per -1.
                        //farlo nella matrice viewport. check glViewport
                        //System.out.println("RENDERING SYSTEM DOPO PROJECTION MATRIX, NOTA");

                        transformed.clearW();

                        Vector4D p1 = transformed.getVertex(0);
                        Vector4D p2 = transformed.getVertex(1);
                        Vector4D p3 = transformed.getVertex(2);

                        //p1.setCoordinate(0, p1.getCoordinate(0) * -1);
                        p1.setCoordinate(1, p1.getCoordinate(1) * -1);

                        //p2.setCoordinate(0, p2.getCoordinate(0) * -1);
                        p2.setCoordinate(1, p2.getCoordinate(1) * -1);

                        //p3.setCoordinate(0, p3.getCoordinate(0) * -1);
                        p3.setCoordinate(1, p3.getCoordinate(1) * -1);



                        transformed = Matrix4x4.multiplyByTriangle(viewportMatrix, transformed);
                        
                        /********************************************************************************* */

                        buffer.add(transformed);
                    }
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

        //Clipping su i 4 lati dello schermo.
        for (Triangle t : out)
        {
            ArrayDeque<Triangle> q = new ArrayDeque<>();
             
            q.push(t);
            int newTriangles = 1;

            for (int p = 0; p < 4; ++p)
            {
                Triangle[] trisToAdd = new Triangle[0];

                while (newTriangles > 0)
                {
                    Triangle test = q.pollFirst();
                    --newTriangles;

                    switch (p)
                    {
                        case 0:
                            trisToAdd =  ClipAgainstPlane(new Vector4D(), new Vector4D(0,1,0), test);                       
                            break;
                        case 1:
                            trisToAdd =  ClipAgainstPlane(new Vector4D(0, height - 1, 0), new Vector4D(0,-1,0), test);                       
                            break;
                        case 2:
                            trisToAdd =  ClipAgainstPlane(new Vector4D(), new Vector4D(1,0,0), test);                       
                            break;
                        case 3:
                            trisToAdd =  ClipAgainstPlane(new Vector4D(width - 1, 0, 0), new Vector4D(-1,0,0), test);                       
                            break;
                    }

                    for (int w = 0; w < trisToAdd.length; ++w)
                    {
                        q.push(trisToAdd[w]);
                    }
                }
                newTriangles = q.size();
            }
        }


        for (Triangle t : out)
        {
            //t.print();
            t.setupAndDraw(g);
        }
    }

    private float distance(Vector4D p, Vector4D plane_n, Vector4D plane_p)
    {
        return (plane_n.getCoordinate(0) * p.getCoordinate(0) + plane_n.getCoordinate(1) * p.getCoordinate(1) + 
                plane_n.getCoordinate(2) * p.getCoordinate(2) - Vector4D.dotProduct(plane_n, plane_p));
    }

    private Triangle[] ClipAgainstPlane(Vector4D planePoint, Vector4D planeNormal, Triangle t)
    {
        planePoint.normalize();

        Vector4D[] insidePoints = new Vector4D[3];
        int insideCount = 0;

        Vector4D[] outsidePoints = new Vector4D[3];
        int outsideCount = 0;

        float d0 = distance(t.getVertex(0), planePoint, planeNormal);
        float d1 = distance(t.getVertex(1), planePoint, planeNormal);
        float d2 = distance(t.getVertex(2), planePoint, planeNormal);

        if (d0 >= 0)
        {
            insidePoints[insideCount++] = t.getVertex(0);
        }
        else
        {
            outsidePoints[outsideCount++] = t.getVertex(0);
        }

        if (d1 >= 0)
        {
            insidePoints[insideCount++] = t.getVertex(1);
        }
        else
        {
            outsidePoints[outsideCount++] = t.getVertex(1);
        }

        if (d2 >= 0)
        {
            insidePoints[insideCount++] = t.getVertex(2);
        }
        else
        {
            outsidePoints[outsideCount++] = t.getVertex(2);
        }

        //System.out.println("i: " + insideCount + "  out: " + outsideCount);

        if (insideCount == 0)
        {
            return new Triangle[0];
        }

        if (insideCount == 3)
        {
            Triangle[] w = new Triangle[1];
            w[0] = t;
            return w;
        }

        if (insideCount == 1 && outsideCount == 2)
        {
            Triangle[] w = new Triangle[1];

            w[0] = Triangle.copy(t);

            w[0].t[0] = insidePoints[0];
            w[0].t[1] = Intersection.lineIntersection(planePoint, planeNormal, insidePoints[0], outsidePoints[0]);
            w[0].t[2] = Intersection.lineIntersection(planePoint, planeNormal, insidePoints[0], outsidePoints[1]);

            for (int i= 0; i < 3; ++i)
            {
                if (w[0].t[i] == null)
                {
                    return new Triangle[0];
                }
            }
            
            return w;
        }


        if (insideCount == 2 && outsideCount == 1)
        {
            Triangle[] w = new Triangle[2];

            w[0] = Triangle.copy(t);
            w[1] = Triangle.copy(t);
            
            w[0].t[0] = insidePoints[0];
            w[0].t[1] = insidePoints[1];
            w[0].t[2] = Intersection.lineIntersection(planePoint, planeNormal, insidePoints[0], outsidePoints[0]);
            
            
            w[1].t[0] = insidePoints[1];
            w[1].t[1] = w[0].t[2];
            w[1].t[2] = Intersection.lineIntersection(planePoint, planeNormal, insidePoints[1], outsidePoints[0]);

            for (int i= 0; i < 3; ++i)
            {
                if (w[0].t[i] == null || w[1].t[i] == null)
                {
                    return new Triangle[0];
                }
            }
        }

        return new Triangle[0];
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


    public void loadMesh(EntityRef e, String meshName, Color c)
    {
        int id = e.getID();
        MeshComponent m = meshes.get(id);

        m.mesh = new PolygonMesh(meshName, c);
    }


    public void setShader(Shader s)
    {
        shader = s;
    }
}