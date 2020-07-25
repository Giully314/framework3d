package framework3d.ecs.action;

import framework3d.ecs.component.PositionComponent;
import framework3d.geometry.Matrix4x4;

public class RotationXAction implements ActionInterface
{
    private PositionComponent p;
    private float step;

    public RotationXAction(PositionComponent p, float step)
    {
        this.p = p;
        this.step = step;
    }

    
    @Override 
    public void executeAction()
    {
        p.rotation = Matrix4x4.multiplication(p.rotation, Matrix4x4.makeRotationX(step));
    }
}