package framework3d.handler;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import framework3d.utility.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


//Gestisce la finestra di gioco e il rendering della finestra.

public class WindowHandler extends JFrame implements Runnable
{
    private BufferStrategy bStrategy;

    private volatile boolean running;
    private Thread gameThread;
    
    protected FrameRate frameRate;
	protected Canvas canvas;
	protected Color appBackground = Color.BLACK;
	protected Color appBorder = Color.LIGHT_GRAY;
	protected Color appFPSColor = Color.GREEN;
	protected Font appFont = new Font("Courier New", Font.PLAIN, 14);
	protected String appTitle = "TBD-Title";
	
	protected int appHeight;
    protected int appWidth;


    public WindowHandler(InputHandler i)
    {
        // registro eventi input.
    }


    
}