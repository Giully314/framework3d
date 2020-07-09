package framework3d.ecs.system;

import java.util.List;

import framework3d.ecs.entity.DynamicEntity;
import framework3d.geometry.Vector4D;

public class TransformSystem implements ComponentSystem
{

    public void update(List<DynamicEntity> entities)
    {
        for (DynamicEntity e : entities)
        {
            var p = e.getPositionComponent();
            var m = e.getMovementComponent();
            var c = e.getControllerComponent();

            

            //implementare rotazione, forse conviene utilizzare quaternioni per descrivere la rotazione rispetto alle matrici.
        }
    }

}