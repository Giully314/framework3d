package framework3d.ecs.system;

import java.util.ArrayList;

import framework3d.ecs.component.InputComponent;
import framework3d.ecs.component.MovementComponent;
import framework3d.ecs.component.PositionComponent;
import framework3d.ecs.entity.*;
import framework3d.geometry.Vector4D;

/*
Questo sistema si occupa di gestire tutte le entità dinamiche utilizzando 3 componenti per accedere ai dati 
necessari: InputComponent, MovementComponent, PositionComponent.
*/

public class TransformSystem 
{
 
    public static <T extends DynamicEntity> void update(ArrayList<T> entities)
    {
        for (DynamicEntity e : entities)
        {
            InputComponent i = e.getInputComponent();
            PositionComponent p = e.getPositionComponent();
            MovementComponent mv = e.getMovementComponent();

            float x = 0;
            float z = 0; 

            if (i.forward)
            {
                z = 1.0f;
            }
            else if (i.back)
            {
                z = -1.0f;
            }

            if (i.left)
            {
                x = -1.0f;
            }
            else if (i.right)
            {
                x = 1.0f;
            }

            Vector4D v = new Vector4D(mv.velocity.getCoordinate(0) * x, 0.0f,
                                        mv.velocity.getCoordinate(2) * z);

            p.position = Vector4D.add(p.position, v);
        }
    }

}