package framework3d.ecs;

import framework3d.ecs.entity.EntityRef;

/*
Interfaccia per la definizione di un Engine. Una classe Engine simula una scena all'interno di una sezione 
del gioco. Ogni engine può avere un solo sistea per tipo e un solo entity handler. Un gioco però può avere
più engine che simulano diverse scene per diverse parti del gioco.
*/
public interface Engine 
{
    default void initialize()
    {

    }


    EntityRef entityCreate();

    void entityDestroy(EntityRef e);

    default void shutdown()
    {

    }
}