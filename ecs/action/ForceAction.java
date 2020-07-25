package framework3d.ecs.action;

import framework3d.ecs.component.ForceComponent;
import framework3d.geometry.*;

public class ForceAction implements ActionInterface
{
    private ForceComponent force;
    private Vector4D direction;

    public ForceAction(ForceComponent f, Vector4D direction)
    {
        force = f;
        this.direction = direction;
    }

    @Override
    public void executeAction()
    {
        force.force.add(Vector4D.multiplyByScalar(direction, force.forceStep));
        // System.out.println("Update force: ");
        // force.force.print();
    }
}