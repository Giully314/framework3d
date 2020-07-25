package framework3d.ecs;

import java.util.HashMap;

import framework3d.ecs.entity.EntityHandler;
import framework3d.ecs.entity.EntityRef;
import framework3d.ecs.system.InputSystem;
import framework3d.ecs.system.RenderingSystem;
import framework3d.ecs.system.TransformSystem;
import framework3d.ecs.system.ComponentSystem;


import java.awt.Graphics;

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


    /************ Accesso ai sistemi ********* */
    
    private HashMap<Class<? extends ComponentSystem>, ComponentSystem> systems;

    /**************************************** */


    public WorldEngine()
    {
        entityHandler = new EntityHandler(this);

        transformSystem = new TransformSystem(this);
        renderingSystem = new RenderingSystem(this);
        inputSystem = new InputSystem(this);

        systems = new HashMap<>();

        systems.put(TransformSystem.class, transformSystem);
        systems.put(RenderingSystem.class, renderingSystem);
        systems.put(InputSystem.class, inputSystem);
    }

    /*************************************** INTERFACCIA ENGINE ****************************** */
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



    @Override
    public void updateSceneState(double elapsedTime)
    {
        inputSystem.processRawInput();

        transformSystem.simulate(elapsedTime);
    }


    @Override 
    public void renderScene(Graphics g)
    {
        renderingSystem.render(transformSystem.getPositions(), g);
    }


    @Override
    public EntityHandler getEntityHandler()
    {
        return entityHandler;
    }


    //Ritorna null se il sistema non è presente.
    @Override
    public <T extends ComponentSystem> T getSystem(Class<T> s)
    {
        return s.cast(systems.get(s));
    }


    @Override
    public void activateAllComponents(EntityRef e)
    {
        transformSystem.activateComponent(e);
        inputSystem.activateComponent(e);
        renderingSystem.activateComponent(e);
    }

    /*************************************** FINE INTERFACCIA ENGINE ****************************** */
}