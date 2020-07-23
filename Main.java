package framework3d;

import framework3d.ecs.component.ForceComponent;
import framework3d.ecs.component.MassComponent;
import framework3d.ecs.component.PositionComponent;
import framework3d.ecs.component.VelocityComponent;
import framework3d.ecs.entity.EntityHandler;
import framework3d.ecs.entity.EntityRef;
import framework3d.ecs.system.TransformSystem;
import framework3d.game.GameHandler;
import framework3d.geometry.Vector4D;

//import framework3d.handler.GameHandler;

//import framework3d.geometry.*;

public class Main 
{   
    public static void main(String[] args) throws InterruptedException
    {
        // EntityHandler handler = new EntityHandler();

        // EntityRef e1 = handler.entityCreate();
        // EntityRef e2 = handler.entityCreate();

        // e1.printEntity();
        // e2.printEntity();

        // TransformSystem t = new TransformSystem();

        // t.registerEntity(e1);
        // t.registerEntity(e2);
        

        // PositionComponent p1 = e1.getComponent(PositionComponent.class);
        // PositionComponent p2 = e2.getComponent(PositionComponent.class);

        // p1.position = new Vector4D(0, 0, 0);
        // p2.position = new Vector4D(60, 0, 0);

        // MassComponent m1 = e1.getComponent(MassComponent.class);
        // MassComponent m2 = e2.getComponent(MassComponent.class);

        // m1.mass = 100000000;
        // m2.mass = 100;


        // ForceComponent f1 = e1.getComponent(ForceComponent.class);
        // ForceComponent f2 = e2.getComponent(ForceComponent.class);

        // VelocityComponent v1 = e1.getComponent(VelocityComponent.class);
        // VelocityComponent v2 = e2.getComponent(VelocityComponent.class);


        // //v2.velocity = new Vector4D(0, 40, 0);
        

        // // f1.force = new Vector4D(100, 100, 100);
        // // f2.force = new Vector4D();

        // //t.simulate(1);
        // for (int i = 0; i < 60; ++i)
        // {
        //     System.out.println("Step: " + i);
        //     t.printEntities();
        //     t.simulate(1);
        //     Thread.sleep(2000);
        // }


        
        // t.printEntities();


        GameHandler g = new GameHandler("ciao", 1280, 720);

        g.start();
    }
}