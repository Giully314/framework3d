package framework3d.ecs.action;

import framework3d.ecs.component.ControllerComponent;

public class ActionRight implements ActionInterface 
{
    private ControllerComponent c;
    
    public ActionRight(ControllerComponent c)
    {
        this.c = c;
    }


    @Override
    public void executeAction()
    {
        c.setRight(true);
    }
}