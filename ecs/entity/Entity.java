package framework3d.ecs.entity;

import framework3d.ecs.component.HealthComponent;
import framework3d.ecs.component.ImageComponent;
import framework3d.ecs.component.PositionComponent;

/*
Classe base astratta che descrive un'entità di base nel gioco. Ogni entità di base ha un PositionComponent,
un ImageComponent e un HealthComponeent. 
Ogni entità è responsabile della sola inizializzazione dei propri componenti, il resto è responsabilità dei sistemi.
*/
public abstract class Entity implements StaticEntity
{
    private PositionComponent position;
    private ImageComponent image;
    private HealthComponent health; 


    /********************************* METODI INTERFACCIA StaticEntity ********************************* */
    @Override
    public PositionComponent getPositionComponent()
    {
        return position;
    }


    @Override
    public ImageComponent getImageComponent()
    {
        return image;
    }


    @Override
    public HealthComponent getHealthComponent()
    {
        return health;
    }

    /********************************* FINE METODI INTERFACCIA StaticEntity ********************************* */
}