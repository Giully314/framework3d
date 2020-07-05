package framework3d.handler;

import framework3d.utility.*;

public class RawInputHandler implements InputHandlerInterface
{
    private KeyboardInput keyboard;

    public RawInputHandler()
    {
        keyboard = new KeyboardInput();
    }


    //Update delle interfacce input.
    public void update()
    {
        keyboard.poll();
    }

    public int[] getKeysEvents()
    {
        return keyboard.getKeysEvents();
    }

    
    //************************************************ GET ******************************************************* */
    public KeyboardInput getKeyboardInput()
    {
        return keyboard;
    }

    //************************************************ FINE GET ******************************************************* */
}