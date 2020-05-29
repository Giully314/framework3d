package framework3d.ecs.component;

import framework3d.geometry.*;

/*
Questo componente appartiene alle entità non statiche. Serve per definire la velocità nello spazio e la rotazione. 
*/
public class MovementComponent implements Component
{
    public Vector4D velocity;    
    public Vector4D angularVelocity;
}