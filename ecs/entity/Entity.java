package framework3d.ecs.entity;

import framework3d.ecs.component.ImageComponent;
import framework3d.ecs.component.PositionComponent;

/*
Classe base astratta che descrive un'entità di base nel gioco. Ogni entità di base ha un PositionComponent,
un ImageComponent e un HealthComponeent. 
Ogni entità è responsabile della sola inizializzazione dei propri componenti, il resto è responsabilità dei sistemi.
*/
public class Entity implements StaticEntity
{
    protected PositionComponent position;
    protected ImageComponent image;

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

    /********************************* FINE METODI INTERFACCIA StaticEntity ********************************* */
}