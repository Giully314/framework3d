package framework3d.ecs.entity;

import framework3d.ecs.component.ImageComponent;
import framework3d.ecs.component.PositionComponent;

/*
Classe base astratta che descrive un'entità di base nel gioco. Ogni entità di base ha un PositionComponent e 
un ImageComponent. Ogni entità è responsabile della sola inizializzazione dei propri componenti, il resto è responsabilità dei sistemi.
*/
public abstract class Entity 
{
    public PositionComponent position;
    public ImageComponent image;
}