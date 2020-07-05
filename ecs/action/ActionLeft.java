package framework3d.ecs.action;

import framework3d.ecs.component.ControllerComponent;

public class ActionLeft implements ActionInterface
{
    private ControllerComponent c;
    
    public ActionLeft(ControllerComponent c)
    {
        this.c = c;
    }


    @Override
    public void executeAction()
    {
        c.setLeft(true);
    }
}