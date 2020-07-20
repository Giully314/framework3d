package framework3d.ecs.component;

import framework3d.geometry.Vector4D;

public class VelocityComponent extends Component
{
    public Vector4D velocity = new Vector4D();
    public Vector4D angularVelocity = new Vector4D();
}