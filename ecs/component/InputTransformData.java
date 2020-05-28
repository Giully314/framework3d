package framework3d.ecs.component;

import framework3d.geometry.*;

/*
Questa classe contiene informazioni per il componente Transform. Viene utilizzata dal componente input come 
contenitore di informazioni relative allo spostamento dell'entità.
*/
public class InputTransformData
{
    //vettore velocità normalizzato. Verrà scalato poi dal componente tranform.
    public Vector4D velocity; 

    //Rotazione.
    public Matrix4x4 rotation;

    
}