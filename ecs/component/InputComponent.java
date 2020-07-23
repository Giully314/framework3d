package framework3d.ecs.component;

import java.util.HashMap;

import framework3d.ecs.action.*;

public class InputComponent extends Component
{
    public HashMap<Integer, String> input = new HashMap<>();
    public HashMap<String, ActionInterface> output = new HashMap<>();


    public void printComponent()
    {
        System.out.println("InputComponent");
    }
}