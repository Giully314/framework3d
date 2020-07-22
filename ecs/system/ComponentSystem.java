package framework3d.ecs.system;

import framework3d.ecs.entity.EntityRef;

/*
Interfaccia comune a tutti i sistemi. I sistemi potrebbero eseguire delle azioni per inizializzare il loro stato
(esempio lettura di dati da file) e spegnersi in modo sicuro (salvando i dati su file).
*/

public interface ComponentSystem 
{
    default void initialize()
    {

    }


    void registerEntity(EntityRef e);

    default void shutdown()
    {

    }
}