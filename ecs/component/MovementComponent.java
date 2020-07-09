package framework3d.ecs.component;

import framework3d.geometry.*;

/*
Questo componente appartiene alle entità non statiche. Serve per definire la velocità nello spazio e la 
velocità di rotazione così come le rispettive accelerazioni. 
*/
public class MovementComponent implements Component
{
    private Vector4D velocity;    
    private Vector4D angularVelocity;

    private Vector4D acceleration;
    private Vector4D angularAcceleration;

    
    public MovementComponent()
    {
        velocity = new Vector4D();
        angularVelocity = new Vector4D();
        acceleration = new Vector4D();
        angularAcceleration = new Vector4D();
    }


    //************************ GET ***************************** */
    
    public Vector4D getVelocity()
    {
        return velocity;
    }


    public Vector4D getAngularVelocity()
    {
        return angularVelocity;
    }


    public Vector4D getAcceleration()
    {
        return acceleration;
    }


    public Vector4D getAngularAcceleration()
    {
        return angularAcceleration;
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
    

    public void setAcceleration(final Vector4D acceleration)
    {
        this.acceleration = acceleration;
    }


    public void setAngularAcceleration(final Vector4D angularAcceleration)
    {
        this.angularAcceleration = angularAcceleration;
    }
    //************************ FINE SET ***************************** */


}