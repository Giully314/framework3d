package framework3d.ecs;

import framework3d.ecs.entity.EntityHandler;
import framework3d.ecs.entity.EntityRef;
import framework3d.ecs.system.InputSystem;
import framework3d.ecs.system.RenderingSystem;
import framework3d.ecs.system.TransformSystem;

public class WorldEngine implements Engine
{
    /* Gestione entità */

    private EntityHandler entityHandler;
    
    /* ***************** */


    /********* Sistemi ********** */

    private TransformSystem transformSystem;
    private RenderingSystem renderingSystem;
    private InputSystem inputSystem;

    /*************************** */


    public WorldEngine()
    {
        entityHandler = new EntityHandler();

        transformSystem = new TransformSystem();
        renderingSystem = new RenderingSystem();
        inputSystem = new InputSystem();
    }

    @Override
    public EntityRef entityCreate()
    {
        EntityRef e = entityHandler.entityCreate();

        transformSystem.registerEntity(e);
        renderingSystem.registerEntity(e);
        inputSystem.registerEntity(e);

        return e;
    }


    @Override
    public void entityDestroy(EntityRef e)
    {   
        entityHandler.entityDestroy(e);
    }



}