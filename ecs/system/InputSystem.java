package framework3d.ecs.system;

import java.util.ArrayList;
import java.util.HashMap;

import framework3d.ecs.Engine;
import framework3d.ecs.action.ActionInterface;
import framework3d.ecs.component.InputComponent;
import framework3d.ecs.component.Component;
import framework3d.ecs.entity.EntityRef;
import framework3d.utility.RawInputInterface;

public class InputSystem implements ComponentSystem
{
    private Engine engine;

    //Dovrebbe essere un'array, ma io utilizzo solo la tastiera come input basso livello.
    private RawInputInterface rawInput;
    
    // private HashMap<Integer, String> input;
    // private HashMap<String, ActionInterface> output;

    private int size;
    private ArrayList<InputComponent> inputs;

    
    public InputSystem(Engine e)
    {
        this(e, null);
    }

    
    public InputSystem(Engine e, RawInputInterface r)
    {
        super();

        engine = e;
        rawInput = r;
        size = 10;

        inputs = new ArrayList<InputComponent>(size);
        
        for (int i = 0; i < size; ++i)
        {
            inputs.add(new InputComponent());
        }
    }
  

    // public void registerInputAction(int key, String actionName)
    // {
    //     input.put(key, actionName);
    // }


    // public void registerOutputAction(String actionName, ActionInterface a)
    // {
    //     output.put(actionName, a);
    // }


    public void registerRawInput(RawInputInterface r)
    {
        rawInput = r;
    }

    
    public void processRawInput()
    {
        //Faccio l'update dello stato dell'input, salvando i dati.
        rawInput.updateInputState();

        int[] r = rawInput.getRawInput();
        for (int i = 0; i < r.length && r[i] != -1; ++i)
        {
            int key = r[i];

            for (InputComponent input : inputs)
            {   
                if (input.getState())
                {
                    var a = input.output.get(input.input.get(key));
                    if (a != null)
                    {
                        a.executeAction();
                    }
                }
            }
        }
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
        //Registra questa entità nel sistema di input.

        int id = e.getID();

        if (id >= size)
        {
            for (int i = 0; i < id - size + 10; ++i)
            {
                inputs.add(new InputComponent());
            }
            size = inputs.size();
        }
        

        InputComponent i = inputs.get(id);
        //i.activateComponent();
        e.registerComponent(InputComponent.class, i);
    }

    @Override
    public void activateComponent(EntityRef e)
    {
        int id = e.getID();

        //Attivo i componenti.
        inputs.get(id).activateComponent();
    }
    
    /******************************** FINE INTERFACCIA COMPONENT SYSTEM ************************************* */
}