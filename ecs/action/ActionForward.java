package framework3d.ecs.action;

import framework3d.ecs.component.ControllerComponent;

public class ActionForward implements ActionInterface
{
    private ControllerComponent c;
    
    public ActionForward(ControllerComponent c)
    {
        this.c = c;
    }


    @Override
    public void executeAction()
    {
        c.setForward(true);
    }
}