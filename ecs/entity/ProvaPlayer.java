package framework3d.ecs.entity;

import framework3d.ecs.component.*;
import framework3d.geometry.*;
import framework3d.handler.InputHandler;

public class ProvaPlayer extends Entity implements DynamicEntity, LivingEntity
{
    private MovementComponent move;
    private PlayerInput rawInput;
    private InputComponent input;
    private HealthComponent health;

    public ProvaPlayer(InputHandler i)
    {
        super();
        initializePlayer(i); 
    }


    private void initializePlayer(InputHandler i)
    {
        move = new MovementComponent();
        move.velocity = new Vector4D(2.0f, 2.0f, 2.0f);
        move.velocity = new Vector4D(1.0f, 1.0f, 1.0f);

        input = new InputComponent();

        rawInput = new PlayerInput(i);
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