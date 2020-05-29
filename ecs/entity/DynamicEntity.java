package framework3d.ecs.entity;

import framework3d.ecs.component.*;

public interface DynamicEntity extends StaticEntity
{
    MovementComponent getMovementComponent();
    InputComponent getInputComponent();
}