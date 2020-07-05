package framework3d.ecs.component;

import framework3d.geometry.*;

/*
Componente base presente in tutte le entità. Definisce la posizione dell'entità, e la rotazione.
*/
public class PositionComponent implements Component
{
    private Vector4D position;
    private Matrix4x4 rotation;
    




    //****************************** GET *************************************** */
    
    public Vector4D getPosition()
    {
        return position;
    }


    public Matrix4x4 getRotation()
    {
        return rotation;
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
    
    //************************************ FINE SET ************************************ */


}