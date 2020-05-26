package framework3d.ecs.component;

import framework3d.geometry.*;

public class TransformComponent implements Component
{   
    //Non è completo, versione basilare per fare prove
    private Vector4D position;
    private Vector4D velocity;
    
    /*
    rotazione
    assi di riferimento right, up e forward
    vettore di scaling
    altra roba per resettare lo stato in caso di collisione.
    */
    public TransformComponent()
    { 
        this(new Vector4D(), new Vector4D(1.0f, 1.0f, 0.0f));
    }

    public TransformComponent(final Vector4D position, final Vector4D velocity)
    {
        this.position = position;
        this.velocity = velocity;
    }

    /*posso pensare che il componente che gestisce l'input, possa passare informazioni a questo componente.
    Oppure, poiché il componente input è un component "statico e read only condiviso da tutti", potrei passare l'oggetto ai componenti
    che ne hanno bisogno.
    */
    @Override
    public void update()
    {

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
    //*********************************** FINE GET ****************************************************** */
}