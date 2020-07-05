package framework3d.ecs.component;

import framework3d.geometry.*;

/*
Questo componente appartiene alle entità non statiche. Serve per definire la velocità nello spazio e la 
velocità di rotazione. 
*/
public class MovementComponent implements Component
{
    private Vector4D velocity;    
    private Vector4D angularVelocity;


    //************************ GET ***************************** */
    
    public Vector4D getVelocity()
    {
        return velocity;
    }


    public Vector4D getAngularVelocity()
    {
        return angularVelocity;
    }
    
    //************************ FINE GET ***************************** */


    //************************ SET ***************************** */
    
    public void setVelocity(final Vector4D velocity)
    {
        this.velocity = velocity;
    }


    public void setAngularVelocity(final Vector4D angularVelocity)
    {
        this.angularVelocity = angularVelocity;
    }
    
    //************************ FINE SET ***************************** */


}