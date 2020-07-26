package framework3d.ecs.component;

import framework3d.geometry.Vector4D;
import framework3d.geometry.Matrix4x4;

public class PositionComponent extends Component
{
    public Vector4D position = new Vector4D();
    public Matrix4x4 rotation = Matrix4x4.makeIdentity();

    //è bruttissima questa variabile così, ma mi serve per orientare la camera. Non ho trovato altre soluzioni.
    public float angleY = 0.0f; //angolo in gradi.


    @Override
    public void printComponent()
    {
        System.out.println("PositionComponent");
        position.print();
        System.out.println("Da stampare la matrice");
    }
}