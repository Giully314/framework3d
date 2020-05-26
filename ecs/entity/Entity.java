package framework3d.ecs.entity;

import framework3d.ecs.component.*;

public class Entity 
{
    TransformComponent transformInfo;

    public Entity()
    {
        transformInfo = new TransformComponent();
    }


    public TransformComponent getTransformInfo()
    {
        return transformInfo;
    }
}