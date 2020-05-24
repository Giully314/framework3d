package framework3d.window;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import framework3d.utility.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public abstract class WindowFramework extends JFrame implements Runnable
{
    private BufferStrategy bStrategy;

    private volatile boolean running;
	private Thread gameThread;
	
	protected KeyboardInput keyboard;
	
	protected FrameRate frameRate;
	protected Canvas canvas;
	protected Color appBackground = Color.BLACK;
	protected Color appBorder = Color.LIGHT_GRAY;
	protected Color appFPSColor = Color.GREEN;
	protected Font appFont = new Font("Courier New", Font.PLAIN, 14);
	protected String appTitle = "TBD-Title";
	
	protected int appHeight;
    protected int appWidth;
    

    public WindowFramework(int appWidth, int appHeight)
    {
		this.appWidth = appWidth;
		this.appHeight = appHeight;
    }


	//****************************** SETUP INIZIALIZZAZIONE GIOCO + INIZIALIZZAZIONE E CHIUSURA RISORSE ********************* */
    public void run()
	{
		running = true;
		initialize();

		int updatePerSecond = 60;
		long nanoSecond = 1000000000L;
		double timePerUpdate = nanoSecond / updatePerSecond;
		double elapsedTime = 0;
		long currentTime;
		long lastTime = System.nanoTime();
		double nsPerFrame;
		
		while (running)
		{
			//IMPOSTARE GAME LOOP OGNI SESSANTESIMO DI SECONDO TRAMITE UN IF.
			currentTime = System.nanoTime();

			elapsedTime += (currentTime - lastTime) / timePerUpdate;
			
			nsPerFrame = currentTime - lastTime;
			
			lastTime = currentTime;

			//non chiamare il game loop ogni sessantesimo di secondo, ma ottimizzare calcolando lo stesso il frame
			//da sistemare.
			//possibile soluzione, multi thread, con update e rendering in due thread diversi.
			if (elapsedTime >= 1.0)
			{
				gameLoop(nsPerFrame / 1.0E9); //Converto in secondi.
				--elapsedTime;
			}
			//da controllare se fare il rendering del frame più velocemente possibile e non vincolato dall'update dello stato
			//del gioco ogni 1/60, sia una cosa positiva alle performance generali e alla visione.
			renderFrame();
		}
		
		terminate();
	}

	//Metodo di cui si può fare l'override, per inizializzare le risorse necessarie necessarie per far partire il gioco.
	//Nota: Risorse legate al gioco(mappe, oggetti, poligoni, ecc), e non alla finestra dell'applicazione.
	protected void initialize()
	{
		frameRate = new FrameRate();
		frameRate.initialize();
	}

	//Azioni da eseguire alla chiusura. (Esempio salvataggio).
	protected void terminate()
	{
		
	}

	//******************************************************* FINE SETUP APPLICAZIONE ********************************** */


	//************************************** GAME LOOP ***************************************** */
	private void gameLoop(double delta) 
	{
		processInput(delta);
		updateObjects(delta);
		//renderFrame();
		
		//SOLO PER ESPERIMENTI. NON UTILIZZARE NEL GIOCO. NON SI USA SLEEP.
		
		// try {
		// 	Thread.sleep(1);
		// } catch (InterruptedException e) {
		// 	e.printStackTrace();
		// }
	}
	//*************************************** FINE GAME LOOP **************************************** */


	//************************* METODI PER IL RENDERING ************************************************* */
	private void renderFrame()
	{
		do
		{
			do
			{
				Graphics g = null;
				
				try
				{
					g = bStrategy.getDrawGraphics();
					g.clearRect(0, 0, getWidth(), getHeight());
					//g.translate(0, appHeight);
					//((Graphics2D)g).scale(1.0, -1.0);
					render(g);
				}
				finally
				{
					if (g != null)
					{
						g.dispose();
					}
				}
				
			}while (bStrategy.contentsRestored());
			
			bStrategy.show();
			
		}while (bStrategy.contentsLost());
	}
	
	protected void render(Graphics g)
	{
		g.setFont(appFont);
		g.setColor(appFPSColor);
		frameRate.calculate();
		g.drawString(frameRate.getFrameRate(), 20, 20);
	}

	//************************** FINE METODI PER IL RENDERING ************************************** */


	//*********************************** UPDATE STATO DEL GIOCO *************************************** */
	protected void processInput(double delta)
	{
		keyboard.poll();
	}
	
	
	protected void updateObjects(double delta)
	{
	}

	//*********************************** FINE UPDATE STATO DEL GIOCO *********************************** */

	//*********************************** AVVIO E CHIUSURADELL'APP ************************************* */
	/** 
     * Inizializza le risorse collegate alla finestra e crea la finestra.
    */
    protected void createAndShowGUI()
	{
		canvas = new Canvas();
		canvas.setBackground(appBackground);
		canvas.setIgnoreRepaint(true);
		getContentPane().add(canvas);
		setLocationByPlatform(true);
		
		canvas.setSize(appWidth, appHeight);
		pack();
	
		setTitle(appTitle);
		
		keyboard = new KeyboardInput();
		canvas.addKeyListener(keyboard);
		
		setVisible(true);
		
		canvas.createBufferStrategy(3); 
		bStrategy = canvas.getBufferStrategy();
		
		canvas.requestFocus();
		
		gameThread = new Thread(this);
		gameThread.start();
	}


	protected void onWindowClosing()
	{
		try
		{
			running = false;
			gameThread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
	
	protected static void launchApp(final WindowFramework app)
	{
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				app.onWindowClosing();
			}
		});
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				app.createAndShowGUI();
			}
		});
	}

	//********************** FINE METODI AVVIO E CHISURA APP **************************************** */
};
