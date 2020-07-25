package framework3d.ecs.action;

import framework3d.ecs.component.ForceComponent;
import framework3d.geometry.*;

public class ForceAction implements ActionInterface
{
    private ForceComponent force;

    public ForceAction(ForceComponent f)
    {
        force = f;
    }

    @Override
    public void executeAction()
    {
        force.force.add(new Vector4D(0.0f, 0.0f, -100));
        // System.out.println("Update force: ");
        // force.force.print();
    }
}