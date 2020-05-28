package framework3d.ecs.component;

import framework3d.geometry.*;

/*
Questo componente si occupa di gestire la posizione dell'oggetto nel world space.

*/

public final class TransformComponent
{   
    //Non è completo, versione basilare per fare prove
    private Vector4D position;
    private Matrix4x4 rotation;
    private Vector4D velocity;
    private float v; // scalare che viene moltiplicato per il vettore velocità.
    
    private InputComponent input;
    
    private Vector4D scaling; //vettore di scaling della mesh


    //aggiunta matrici da object space a world space.
    
    /*
    rotazione
    assi di riferimento right, up e forward
    vettore di scaling
    altra roba per resettare lo stato in caso di collisione.
    */

    public TransformComponent(final Vector4D position, final float velocity, final Vector4D scaling, InputComponent input)
    {
        this.position = position;
        v = velocity;
        
        this.scaling = scaling;
        
        this.input = input;
    }

    
    public void update()
    {
        InputTransformData d = input.getInputData();

        rotation = d.rotation;
        velocity = Vector4D.multiplyByScalar(d.velocity, v);


        //Calcolo della nuova posizione + rotazione.
    }

    //*********************************** GET ****************************************************** */
    public Vector4D getPosition()
    {
        return position;
    }


    public Vector4D getVelocity()
    {
        return velocity;
    }


    public Matrix4x4 getRotation()
    {
        return rotation;
    }


    public Vector4D getScaling()
    {
        return scaling;
    }
    //*********************************** FINE GET ****************************************************** */
}