package framework3d.ecs.component;

import framework3d.geometry.Vector4D;

public class AccelerationComponent extends Component 
{
    public Vector4D acceleration = new Vector4D();
    public Vector4D angularAcceleration = new Vector4D();
}