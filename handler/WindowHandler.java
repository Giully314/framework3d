package framework3d.handler;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

// import framework3d.ecs.entity.RenderableEntity;
// import framework3d.ecs.system.RenderingSystem;  
import framework3d.utility.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


//Gestisce la finestra di gioco e il rendering della finestra.

public class WindowHandler extends JFrame // implements Runnable
{
    private BufferStrategy bStrategy;

    //private volatile boolean running;
    //private Thread gameThread;
    
    protected FrameRate frameRate;
	protected Canvas canvas;
	protected Color appBackground = Color.BLACK;
	protected Color appBorder = Color.LIGHT_GRAY;
	protected Color appFPSColor = Color.GREEN;
	protected Font appFont = new Font("Courier New", Font.PLAIN, 14);
	protected String appTitle = "TBD-Title";
	
	protected int appHeight = 720;
    protected int appWidth = 1280;


    public WindowHandler(RawInputHandler i)
    {
        createAndShow(i);
    }


    //*************************** IMPLEMENTAZIONE METODO INTERFACCIA RUNNABLE *********************************************/
    
    
    
    //*************************** FINE IMPLEMENTAZIONE METODO INTERFACCIA RUNNABLE *********************************************/

    // public void render(RenderingSystem renderingSystem, ArrayList<RenderableEntity> renderableEntities)
    // {
    //     do
	// 	{
	// 		do
	// 		{
	// 			Graphics g = null;
				
	// 			try
	// 			{
	// 				g = bStrategy.getDrawGraphics();
	// 				g.clearRect(0, 0, getWidth(), getHeight());
	// 				//g.translate(0, appHeight);
	// 				//((Graphics2D)g).scale(1.0, -1.0);
	// 				//renderingSystem.render(renderableEntities, g);
	// 			}
	// 			finally
	// 			{
	// 				if (g != null)
	// 				{
	// 					g.dispose();
	// 				}
	// 			}
				
	// 		}while (bStrategy.contentsRestored());
			
	// 		bStrategy.show();
			
	// 	}while (bStrategy.contentsLost());
    // }

    //*********************************** AVVIO E CHIUSURADELL'APP ************************************* */
    
    /** 
     * Inizializza le risorse collegate alla finestra e crea la finestra.
    */

    public void createAndShow(RawInputHandler i)
    {
        canvas = new Canvas();
		canvas.setBackground(appBackground);
		canvas.setIgnoreRepaint(true);
		getContentPane().add(canvas);
		//setLocationByPlatform(true);
		
		canvas.setSize(appWidth, appHeight);
		pack();
	
		setTitle(appTitle);
		
		canvas.addKeyListener(i.getKeyboardInput());
		
		setVisible(true);
		
		canvas.createBufferStrategy(3); 
		bStrategy = canvas.getBufferStrategy();
		
		canvas.requestFocus();
		
		// gameThread = new Thread(this);
		// gameThread.start();
    }



    // protected void onWindowClosing()
	// {
	// 	try
	// 	{
	// 		running = false;
	// 		gameThread.join();
	// 	}
	// 	catch (InterruptedException e)
	// 	{
	// 		e.printStackTrace();
	// 	}
		
	// 	System.exit(0);
	// }
	
	
	public static void launchApp(final WindowHandler app, final RawInputHandler i)
	{
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				//app.onWindowClosing();
			}
		});
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				app.createAndShow(i);
			}
		});
	}

	//********************** FINE METODI AVVIO E CHISURA APP **************************************** */
}