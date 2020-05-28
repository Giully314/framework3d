package framework3d.handler;

import framework3d.utility.*;

public class InputHandler 
{
    private KeyboardInput keyboard;

    public InputHandler()
    {
        keyboard = new KeyboardInput();
    }


    //Update delle interfacce input.
    public void update()
    {
        keyboard.poll();
    }

    
    //************************************************ GET ******************************************************* */
    public KeyboardInput getKeyboardInput()
    {
        return keyboard;
    }

    //************************************************ FINE GET ******************************************************* */
}