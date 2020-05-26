package framework3d.ecs.system;

import java.util.ArrayList;

import framework3d.ecs.component.TransformComponent;

public class TransformSystem 
{
    private ArrayList<TransformComponent> components;

    public TransformSystem()
    {
        components = new ArrayList<>();
    }

    //Responsabilità di chiama la funzione nel non passare un oggetto già presente nella lista.
    public void addComponent(TransformComponent t)
    {
        components.add(t);
    }


    public void update()
    {
        components.parallelStream().forEach(t -> t.update());
    }


    // public void removeComponent(TransformComponent t)
    // {
    //     components.remove(t);
    // }
}