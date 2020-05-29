package framework3d.ecs.component;

import framework3d.geometry.*;

/*
Componente base presente in tutte le entità. Definisce la posizione dell'entità, e la rotazione.
*/
public class PositionComponent implements Component
{
    public Vector4D position;
    public Matrix4x4 rotation;
}