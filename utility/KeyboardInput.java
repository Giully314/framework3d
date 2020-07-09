package framework3d.utility;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * 
 * PER SITUAZIONI "SEMPLICI" QUESTA CLASSE VA BENE, ALTRIMENTI è PERICOLOSA. POTREBBE MISSARE DEGLI EVENTI.
*/

public class KeyboardInput implements KeyListener
{
	private boolean[] keys;
	private int[] polled;
	
	public KeyboardInput()
	{
		keys = new boolean[256];
		polled = new int[257]; //uno spazio in più per segnare la fine della coda.
	}
	
	
	// public boolean keyDown(int keyCode)
	// {
	// 	//System.out.println(polled[keyCode] > 0);
	// 	return polled[keyCode];
	// }
	
	
	// public boolean keyDownOnce(int keyCode)
	// {
	// 	return polled[keyCode] == 1;
	// }


	public int[] getKeysEvents()
	{
		return polled;
	}
	
	
	public synchronized void poll()
	{
		//da cambiare e utilizzare stream
		int j = 0;
		for (int i = 0; i < keys.length; ++i)
		{
			if (keys[i])
			{
				polled[j++] = i;
			}
		}

		polled[j] = -1; //-1 indica la fine della "coda".
	}
	
	public synchronized void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		if (keyCode >= 0 && keyCode < keys.length)
		{
			keys[keyCode] = true;
		}
	}
	
	
	public synchronized void keyReleased(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		if (keyCode >= 0 && keyCode < keys.length)
		{
			keys[keyCode] = false;
		}
	}
	
	
	public synchronized void keyTyped(KeyEvent e)
	{
		
	}
}