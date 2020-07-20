package framework3d.ecs.system;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

import framework3d.ecs.entity.EntityRef;
import framework3d.geometry.Vector4D;
import framework3d.ecs.component.Component;
import framework3d.ecs.component.ForceComponent;
import framework3d.ecs.component.AccelerationComponent;
import framework3d.ecs.component.VelocityComponent;
import framework3d.ecs.component.MassComponent;
import framework3d.ecs.component.PositionComponent;



public class TransformSystem implements ComponentSystem
{
    //Capacità degli array che contengono i vari componenti.
    private int size;

    private ArrayList<PositionComponent> positions;
    private ArrayList<VelocityComponent> velocities;
    private ArrayList<AccelerationComponent> accelerations;
    private ArrayList<MassComponent> masses;
    private ArrayList<ForceComponent> forces;

    //HashMap per fast look up 
    private HashMap<Class<? extends Component>, ArrayList<? extends Component>> cache;


    public TransformSystem()
    {
        size = 10;

        positions = new ArrayList<>(Arrays.asList(new PositionComponent[size]));
        velocities = new ArrayList<>(Arrays.asList(new VelocityComponent[size]));
        accelerations = new ArrayList<>(Arrays.asList(new AccelerationComponent[size]));
        masses = new ArrayList<>(Arrays.asList(new MassComponent[size]));
        forces = new ArrayList<>(Arrays.asList(new ForceComponent[size]));

        //cache.put(PositionComponent.class, positions);
    }


    public void registerEntity(EntityRef e)
    {
        int id = e.getID();

        if (id >= size)
        {
            positions.addAll(Arrays.asList(new PositionComponent[(id - size) + 10]));
            velocities.addAll(Arrays.asList(new VelocityComponent[(id - size) + 10]));
            accelerations.addAll(Arrays.asList(new AccelerationComponent[(id - size) + 10]));
            masses.addAll(Arrays.asList(new MassComponent[(id - size) + 10]));

            size = positions.size();
        }

        e.registerComponent(PositionComponent.class, positions.get(id));
        e.registerComponent(VelocityComponent.class, velocities.get(id));
        e.registerComponent(AccelerationComponent.class, accelerations.get(id));
        e.registerComponent(MassComponent.class, masses.get(id));
    }

    public void simulate(double elapsedTime)
    {
        //Implementazione primo stadio motore fisico.
        
        //Implementazione equazioni del moto. (Mancano equazioni moto rotazionale)
        
        //L'accelarazione è costante ma il suo valore è legato all'input da tastiera, quindi devo prima risolvere
        //il problema dell'input.
        //Calcolo delle forze + accelerazione.



        //DA PARALLELIZZARE
        IntStream.range(0, size).forEach(index -> 
            updateVelocity(velocities.get(index), accelerations.get(index), elapsedTime)
        );

        IntStream.range(0, size).forEach(index -> 
            updatePosition(positions.get(index), velocities.get(index), elapsedTime)
        );
    }

    /******************************************* EQUAZIONI DEL MOTO ****************************** */
    private void updateVelocity(VelocityComponent v, AccelerationComponent a, double elapsedTime)
    {
        v.velocity.add(Vector4D.multiplyByScalar(a.acceleration, (float)elapsedTime));
    }


    private void updatePosition(PositionComponent p, VelocityComponent v, double elapsedTime)
    {
        p.position.add(Vector4D.multiplyByScalar(v.velocity, (float)elapsedTime));
    }

    /******************************************* FINE EQUAZIONI DEL MOTO ****************************** */
}