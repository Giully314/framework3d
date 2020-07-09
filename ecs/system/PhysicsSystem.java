package framework3d.ecs.system;

import java.util.ArrayList;

import framework3d.ecs.entity.DynamicEntity;
import framework3d.geometry.Vector4D;


/*
Il sistema fisico è composta da 3 parti: 
    update delle entità 
    controllo collisioni
    risposta alle collisioni
*/


public class PhysicsSystem implements ComponentSystem
{
    /*Per ottimizzare, posso pensare di utilizzare due strutture dati: una per gli oggetti che so che verrano mantenuti 
        per molto tempo (esempio la navicella), mentre l'altra per gli oggetti che vengono rimossi e inseriti in modo frequente
        così da ottimizzare i tempi di queste operazioni.
    */

    private ArrayList<DynamicEntity> entities;

    private TransformSystem transformSystem;


    //Il costruttore serve solo per passare dei dati/file che verrano poi utilizzati per inizializzare le risorse nella funziona initialize().
    public PhysicsSystem()
    {

    }



    @Override
    public void initialize()
    {
        transformSystem.initialize();

        entities = new ArrayList<>();
    }


    //***************************************** ADD AND REMOVE *********************************************************** */
    public void addEntity(DynamicEntity e)
    {
        entities.add(e);
    }

    //Pessima operazione da fare su un'array.
    public void removeEntity(DynamicEntity e)
    {
        entities.remove(e);
    }


    public void clear()
    {
        entities.clear();
    }

    //***************************************** FINE ADD AND REMOVE *********************************************************** */

    

    public void update(float elapsedTime)
    {
        for (DynamicEntity e : entities)
        {
            var m = e.getMovementComponent();
            var c = e.getControllerComponent();
            
            if (c.getForward())
            {
                m.setVelocity(Vector4D.add(m.getVelocity(), m.getAcceleration()));
            }
            else if (c.getBack())
            {
                //bloccare la velocità fino a 0 o permetterla anche negativa? 
                m.setVelocity(Vector4D.sub(m.getVelocity(), m.getAcceleration()));
            }
        }

        

        transformSystem.update(entities);
    }



}