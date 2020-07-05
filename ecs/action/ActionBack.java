package framework3d.ecs.action;

import framework3d.ecs.component.ControllerComponent;

public class ActionBack implements ActionInterface
{
    private ControllerComponent c;
    
    public ActionBack(ControllerComponent c)
    {
        this.c = c;
    }


    @Override
    public void executeAction()
    {
        c.setBack(true);
    }
}