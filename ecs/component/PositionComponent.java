package framework3d.ecs.component;

import framework3d.geometry.*;

/*
Componente base presente in tutte le entità. Definisce la posizione dell'entità, la rotazione e la massa.
*/
public class PositionComponent implements Component
{
    private Vector4D position;
    private Matrix4x4 rotation;

    private float mass;
    

    public PositionComponent()
    {
        position = new Vector4D();
        rotation = new Matrix4x4();

        mass = 0.0f;
    }




    //****************************** GET *************************************** */
    
    public Vector4D getPosition()
    {
        return position;
    }


    public Matrix4x4 getRotation()
    {
        return rotation;
    }


    public float getMass()
    {
        return mass;
    }
    
    //****************************** FINE GET *************************************** */



    //************************************ SET ************************************ */
    
    public void setPosition(final Vector4D position)
    {
        this.position = position;
    }


    public void setRotation(final Matrix4x4 rotation)
    {
        this.rotation = rotation;
    }


    public void setMass(final float mass)
    {
        this.mass = mass;
    }
    
    //************************************ FINE SET ************************************ */


}