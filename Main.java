package framework3d;

import framework3d.geometry.*;

public class Main 
{   
    public static void main(String[] args)
    {
        Matrix4x4 m = Matrix4x4.makeTranslation(2, 4, 3);
        Vector4D v = new Vector4D(1, 0, 0);
        Vector4D e = new Vector4D(0, 1, 0);
        Vector4D b = new Vector4D(0, 0, 1);

        Triangle t = new Triangle(v, e, b);

        //Vector4D w = Matrix4x4.multiplyByVector(m, v);
        
        Triangle p = Matrix4x4.multiplyByTriangle(m, t);

        PolygonMesh mesh = new PolygonMesh("C:\\Users\\Jest\\Desktop\\programmazione\\Java\\Framework3D\\resource\\menlowpoly.obj");
        
        //mesh.getMesh().get(0).print();

        //p.print();
    }
}