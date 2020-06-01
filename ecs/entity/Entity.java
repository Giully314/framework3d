package framework3d.ecs.entity;

import framework3d.ecs.component.ImageComponent;
import framework3d.ecs.component.PositionComponent;
import framework3d.geometry.Matrix4x4;
import framework3d.geometry.Vector4D;

/*
Classe base astratta che descrive un'entità di base nel gioco. Ogni entità di base ha un PositionComponent,
un ImageComponent e un HealthComponeent. 
Ogni entità è responsabile della sola inizializzazione dei propri componenti, il resto è responsabilità dei sistemi.
*/
public class Entity implements StaticEntity
{
    protected PositionComponent position;
    protected ImageComponent image;

    public Entity()
    {
        initializeEntity(new Vector4D());
        //ImageComponent 
    }


    private void initializeEntity(final Vector4D position)
    {
        this.position = new PositionComponent();

        this.position.position = position;

        this.position.rotation = Matrix4x4.makeIdentity(); //momentaneamente mi interessa solo la posizione per fare le prove.
    }


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