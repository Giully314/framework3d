package framework3d.ecs.system;

import java.util.ArrayList;
import java.util.HashMap;

import framework3d.ecs.action.ActionInterface;
import framework3d.handler.InputHandlerInterface;

/*
Questo sistema si occupa di "interpretare" i dati, provenienti da input basso livello, in azioni. 
*/

public class InputSystem implements ComponentSystem
{
    private ArrayList<InputHandlerInterface> rawInput;
    
    private HashMap<Integer, String> input; 
    private HashMap<String, ActionInterface> output;


    public InputSystem(InputHandlerInterface i)
    {
        initialize();
        
        rawInput.add(i);
    }


    @Override
    public void initialize()
    {
        rawInput = new ArrayList<>();

        input = new HashMap<>();
        output = new HashMap<>();
    }



    public void registerInput(int event, String actionName)
    {
        System.out.println(event + " " + actionName);
        input.put(event, actionName);
    }

    public void registerOutput(String actionName, ActionInterface action)
    {
        System.out.println(actionName + action.getClass().getName());
        output.put(actionName, action);
    }

    // public void deleteInputAction(int event)
    // {
    //     input.remove(event);
    // }


    // public void deleteOutputAction(String actionName)
    // {
    //     output.remove(actionName);
    // }


    public void update()
    {
        //da cambiare utilizzando stream
        for (int i = 0; i < rawInput.size(); ++i)
        {
            int[] events = rawInput.get(i).getKeysEvents();

            for (int j = 0; j < events.length && events[j] != -1; ++j)
            {
                var a = output.get(input.get(events[j]));

                if (a != null)
                {
                    a.executeAction();
                }
            }
        }
    }
}