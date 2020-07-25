package framework3d.ecs.action;

import framework3d.ecs.component.PositionComponent;
import framework3d.geometry.Matrix4x4;

public class RotationYAction implements ActionInterface
{
    private float angle;
    private float step;
    private PositionComponent p;

    public RotationYAction(PositionComponent p, float step)
    {
        angle = 0.0f;
        this.step = step;
        this.p = p;
    }


    @Override
    public void executeAction()
    {
        p.rotation = Matrix4x4.multiplication(p.rotation, Matrix4x4.makeRotationY(step));
    }
}