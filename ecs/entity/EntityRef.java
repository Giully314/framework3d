package framework3d.ecs.entity;

import java.util.HashMap;

import framework3d.ecs.component.Component;

public class EntityRef 
{
    //Aggiungere riferimento all'entity handler da cui è stata creata questa classe.


    //Si potrebbe utilizzare uno short, visto che il numero di entità disponibili contemporaneamente è basso.
    private final int id;

    //Posso pensare di aggiungere delle variabili riferimento ai sistemi a cui è legata questa entità.

    //da aggiungere map con i componenti.
    private HashMap<Class<? extends Component>, Component> components;


    public void printEntity()
    {
        System.out.println("Entity id " + id);
    }


    public EntityRef(int id)
    {
        this.id = id;
        
        components = new HashMap<>();
    }


    public int getID()
    {
        return id;
    }


    public <T extends Component> T getComponent(Class<T> t)
    {
        var c = components.get(t);

        if (c != null)
        {
            return t.cast(c);
        }
        return null;
    }
    
    
    public <T extends Component> void registerComponent(Class<T> t, T c)
    {
        components.put(t, c);
    }

    //Disattiva i componenti così da non essere considerati durante l'iterazione del sistema.
    public void deleteComponents()
    {
        components.forEach((k, c) -> c.deactivateComponent());
    }

}