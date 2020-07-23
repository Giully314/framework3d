package framework3d.ecs.system;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;


import framework3d.ecs.entity.EntityRef;
import framework3d.geometry.Vector4D;
import framework3d.ecs.component.Component;
import framework3d.ecs.component.ForceComponent;
import framework3d.ecs.Engine;
import framework3d.ecs.component.AccelerationComponent;
import framework3d.ecs.component.VelocityComponent;
import framework3d.ecs.component.MassComponent;
import framework3d.ecs.component.PositionComponent;



public class TransformSystem implements ComponentSystem
{
    private Engine engine;


    //Capacità degli array che contengono i vari componenti.
    private final double gravitationalCostant = 6.67408E-11; 
    private int size;

    private ArrayList<PositionComponent> positions;
    private ArrayList<VelocityComponent> velocities;
    private ArrayList<AccelerationComponent> accelerations;
    private ArrayList<MassComponent> masses;
    private ArrayList<ForceComponent> forces;


    

    //HashMap per fast look up 
    //private HashMap<Class<? extends Component>, ArrayList<? extends Component>> cache;

    //costruttore momentaneo per fare i test nel main. Ogni sistema deve avere un riferimento alla scena a cui appartiene.
    public TransformSystem()
    {
        this(null);
    }

    public TransformSystem(Engine e)
    {
        engine = e;
        size = 10;

        positions = new ArrayList<>(size);
        velocities = new ArrayList<>(size);
        accelerations = new ArrayList<>(size);
        masses = new ArrayList<>(size);
        forces = new ArrayList<>(size);


        for (int i = 0; i < size; ++i)
        {
            positions.add(new PositionComponent());
            velocities.add(new VelocityComponent());
            accelerations.add(new AccelerationComponent());
            masses.add(new MassComponent());
            forces.add(new ForceComponent());
        }

        //cache.put(PositionComponent.class, positions);
    }


    /*
    SEMBRA CHE CI SIANO DEI PROBLEMI NEL CALCOLO DELLA FORZA. FARE DEI CHECK.
    */
    public void simulate(double elapsedTime)
    {
        //Implementazione primo stadio motore fisico.
        
        //Implementazione equazioni del moto. (Mancano equazioni moto rotazionale)
        
        //L'accelarazione è costante ma il suo valore è legato all'input da tastiera, quindi devo prima risolvere
        //il problema dell'input.
        //Calcolo delle forze + accelerazione.
        
        //Controllare componenti attivi
        //Sistemare size, magari avendo due parametri per evitare troppi componenti non attivi

        //QUESTO NON SI PUò PARALLELIZZARE
        //Utilizzo approssimazione di Eulero per simulare le 3 leggi di Newton.
        IntStream.range(0, size).filter(index -> 
        positions.get(index).getState()
        ).forEach(index -> 
        {
            updateForce(index);
        }
        );

        
        //DA PARALLELIZZARE TUTTI E 3
        IntStream.range(0, size).filter(index -> 
           positions.get(index).getState()
        ).forEach(index -> 
        {
            updateAcceleration(accelerations.get(index), forces.get(index), masses.get(index));
        }
        );


        IntStream.range(0, size).filter(index -> 
           positions.get(index).getState()
        ).forEach(index -> 
        {
            updateVelocity(velocities.get(index), accelerations.get(index), elapsedTime);
        }
        );


        IntStream.range(0, size).filter(index -> 
           positions.get(index).getState()
        ).forEach(index -> 
        {
            updatePosition(positions.get(index), velocities.get(index), elapsedTime);
        }
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


    private void updateAcceleration(AccelerationComponent a, ForceComponent f, MassComponent m)
    {
        //CHECK SE MASSA != 0
        a.acceleration = Vector4D.multiplyByScalar(f.force, 1.0f / m.mass);
    }


    //Ci penso dopo a scriverla in maniera più efficiente (togliendo if, facendo il refactoring ecc)
    private void updateForce(int index)
    {
        PositionComponent p = positions.get(index);
        ForceComponent f = forces.get(index);
        MassComponent m = masses.get(index);

        f.force.reset();

        for (int i = 0; i < size; ++i)
        {
            if (i == index) continue;
            
            PositionComponent p2 = positions.get(i);
            
            if (!p2.getState()) continue;

            MassComponent m2 = masses.get(i);


            double w = m.mass * m2.mass * (float)gravitationalCostant;
            Vector4D diffPosition = Vector4D.sub(p2.position, p.position);
            // System.out.println(index);
            // diffPosition.print();
            double distance = diffPosition.length();
            double sqrdDistance = Math.pow(diffPosition.length(), 2.0);   

            diffPosition.normalize();

            if (sqrdDistance != 0)
            {
                f.force.add(Vector4D.multiplyByScalar(diffPosition, (float)w / (float)distance));
            }   
        }
    }   

    /******************************************* FINE EQUAZIONI DEL MOTO ****************************** */


    public void printEntities()
    {
        System.out.println("*************************************************\n");
        for (int i = 0; i < size; ++i)
        {
            var p = positions.get(i);
            var v = velocities.get(i);
            var a = accelerations.get(i);
            var m = masses.get(i);
            var f = forces.get(i);

            if (!p.getState())
            {
                continue;
            }

            System.out.println("Entity id: " + i);

            System.out.print("Posizione: ");
            p.position.print();

            System.out.print("Velocità: ");
            v.velocity.print();

            System.out.print("Accelerazione: ");
            a.acceleration.print();

            System.out.println("Massa: " + m.mass);
            
            System.out.print("Forza: ");
            f.force.print();
            
            System.out.print("\n\n");
        }

        System.out.println("*************************************************\n\n");
    }


    /******************************** INTERFACCIA COMPONENT SYSTEM ************************************* */
    
    @Override
    public Engine getEngine()
    {
        return engine;
    }

    @Override
    public void setEngine(Engine e)
    {
        engine = e;
    }

    @Override
    public void registerEntity(EntityRef e)
    {
        int id = e.getID();

        //Controllo se l'id passato è maggiore del numero di elementi allocato e in caso affermativo alloco altri componenti.
        if (id >= size)
        {
            //Da aumentare prima la capacity e poi aggiungere gli elementi.
            for (int i = 0; i < id - size + 10; ++i)
            {
                positions.add(new PositionComponent());
                velocities.add(new VelocityComponent());
                accelerations.add(new AccelerationComponent());
                masses.add(new MassComponent());
                forces.add(new ForceComponent());
            }
            size = positions.size();
        }

        //Attivo i componenti.
        positions.get(id).activateComponent();
        velocities.get(id).activateComponent();
        accelerations.get(id).activateComponent();
        masses.get(id).activateComponent();
        forces.get(id).activateComponent();
        

        //Registro i componenti all'interno dell'entità.
        e.registerComponent(PositionComponent.class, positions.get(id));
        e.registerComponent(VelocityComponent.class, velocities.get(id));
        e.registerComponent(AccelerationComponent.class, accelerations.get(id));
        e.registerComponent(MassComponent.class, masses.get(id));
        e.registerComponent(ForceComponent.class, forces.get(id));
    }

    /******************************** FINE INTERFACCIA COMPONENT SYSTEM ************************************* */
}