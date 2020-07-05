package framework3d.ecs.entity;

import framework3d.ecs.component.PositionComponent;
// import framework3d.geometry.Matrix4x4;
// import framework3d.geometry.Vector4D;

/*
Classe base astratta che descrive un'entità di base nel gioco. Ogni entità di base ha un PositionComponent,
un ImageComponent e un HealthComponeent. 
Ogni entità è responsabile della sola inizializzazione dei propri componenti, il resto è responsabilità dei sistemi.
*/
public abstract class Entity implements StaticEntity
{
    protected PositionComponent position;

    public Entity()
    {
        
    }


    //Inizializzazione dei componenti appartanenti all'entità.
    public abstract void initialize();


    /********************************* METODI INTERFACCIA StaticEntity ********************************* */
    @Override
    public PositionComponent getPositionComponent()
    {
        return position;
    }

    /********************************* FINE METODI INTERFACCIA StaticEntity ********************************* */
}