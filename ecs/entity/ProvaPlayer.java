package framework3d.ecs.entity;

import framework3d.ecs.component.*;

public class ProvaPlayer extends Entity implements DynamicEntity, LivingEntity
{
    private MovementComponent move;
    private PlayerInput rawInput;
    private InputComponent input;
    private HealthComponent health;

    public ProvaPlayer()
    {
        
    }




    @Override 
    public MovementComponent getMovementComponent()
    {
        return move;
    }


    @Override 
    public InputInterface getInputInterface()
    {
        return rawInput;
    }


    @Override
    public InputComponent getInputComponent()
    {
        return input;
    }


    @Override
    public HealthComponent getHealthComponent()
    {
        return health;
    }

}