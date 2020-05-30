package framework3d.ecs.component;

import java.awt.event.KeyEvent;

import framework3d.handler.InputHandler;
import framework3d.utility.KeyboardInput;

public class PlayerInput extends InputInterface
{
    private InputHandler inputHandler;
    
    public PlayerInput(InputHandler i)
    {
        inputHandler = i;
    }


    @Override
    public void updateInput(InputComponent inputComponent)
    {
        KeyboardInput k = inputHandler.getKeyboardInput();

        inputComponent.reset();


        if (k.keyDown(KeyEvent.VK_W))
        {
            inputComponent.forward = true;
        }
        else if (k.keyDown(KeyEvent.VK_S))
        {
            inputComponent.back = true;
        }

        if (k.keyDown(KeyEvent.VK_D))
        {
            inputComponent.right = true;
        }
        else if (k.keyDown(KeyEvent.VK_A))
        {
            inputComponent.left = true;
        }
    }

}