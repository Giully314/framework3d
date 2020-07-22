package framework3d.ecs.system;

import java.util.HashMap;

import framework3d.ecs.action.ActionInterface;
import framework3d.ecs.entity.EntityRef;
import framework3d.utility.RawInputInterface;

public class InputSystem implements ComponentSystem
{
    //Dovrebbe essere un'array, ma io utilizzo solo la tastiera come input basso livello.
    private RawInputInterface rawInput;
    
    private HashMap<Integer, String> input;
    private HashMap<String, ActionInterface> output;

    
    public InputSystem()
    {
        this(null);
    }

    
    public InputSystem(RawInputInterface r)
    {
        rawInput = r;
        input = new HashMap<>();
        output = new HashMap<>();
    }
    

    public void registerEntity(EntityRef e)
    {
        //Registra questa entità nel sistema di input.
    }


    public void registerInputAction(int key, String actionName)
    {
        input.put(key, actionName);
    }


    public void registerOutputAction(String actionName, ActionInterface a)
    {
        output.put(actionName, a);
    }


    public void registerRawInput(RawInputInterface r)
    {
        rawInput = r;
    }

    
    public void processRawInput()
    {
        int[] r = rawInput.getRawInput();
        for (int i = 0; i < r.length && r[i] != -1; ++i)
        {
            ActionInterface a = output.get(input.get(r[i]));
            
            if (a != null)
            {
                a.executeAction();
            }
        }
    }
}