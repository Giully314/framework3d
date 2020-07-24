package framework3d;

import java.util.ArrayList;

import framework3d.ecs.component.ForceComponent;
import framework3d.ecs.component.MassComponent;
import framework3d.ecs.component.PositionComponent;
import framework3d.ecs.component.VelocityComponent;
import framework3d.ecs.component.Component;
import framework3d.ecs.entity.EntityHandler;
import framework3d.ecs.entity.EntityRef;
import framework3d.ecs.system.TransformSystem;
import framework3d.game.GameHandler;
import framework3d.geometry.*;

//import framework3d.handler.GameHandler;

//import framework3d.geometry.*;

public class Main 
{   
    public static void main(String[] args)// throws InterruptedException
    {
        // ArrayList<PositionComponent> p = new ArrayList<>();
        // ArrayList<? extends Component> c = p;

        // p = (ArrayList<PositionComponent>)c;

        GameHandler g = new GameHandler("ciao", 1280, 720);

        g.start();

        
    }
}