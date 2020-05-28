package framework3d.ecs.component;

import java.awt.event.*;

import framework3d.handler.InputHandler;
import framework3d.utility.KeyboardInput;
import framework3d.geometry.*;

public class InputPlayerComponent extends InputComponent
{
    private InputHandler inputHandler;

    public InputPlayerComponent(InputHandler handler)
    {
        inputHandler = handler;
    }


    @Override
    public InputTransformData getInputData()
    {
        InputTransformData d = new InputTransformData();

        float x = 0.0f;
        float y = 0.0f;
        float z = 0.0f;

        KeyboardInput k = inputHandler.getKeyboardInput();


        if (k.keyDown(KeyEvent.VK_D))
        {
            x += 1.0;
        }
        else if (k.keyDown(KeyEvent.VK_A))
        {
            x -= 1.0;
        }


        if (k.keyDown(KeyEvent.VK_W))
        {
            z += 1.0;
        }
        else if (k.keyDown(KeyEvent.VK_S))
        {
            z -= 1.0;
        }

        d.velocity = new Vector4D(x, y, z, 1.0f);


        //calcolo della rotazione

        return d;
    }
}