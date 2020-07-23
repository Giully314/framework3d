package framework3d.game;

import java.awt.image.BufferStrategy;
import java.awt.Graphics;

import framework3d.window.WindowHandler;

import framework3d.game.state.*;

public class GameHandler implements Runnable
{
    /*********** Finestra ******** */
    
    private WindowHandler window;
    private int width;
    private int height;

    /***************************** */


    /*********** State ************/
    
    private State gameStatus;

    /******************************/


    /*********** Game thread ****** */
    
    private Thread gameThread;
    private boolean isRunning;

    /***************************** */


    public GameHandler(String title, int width, int height)
    {
        this.width = width;
        this.height = height;
        window = new WindowHandler(title, width, height);
        isRunning = false;
    }


    /***************************** INIZIALIZZAZIONE RISORSE ************************************** */
    
    private void initializeResource()
    {
        //Inizializzazione finestra
        window.initializeWindow();

        //Aggiunta risorse input al frame.
        //window.getWindowFrame().addKeyListener(...);

        //Caricamento delle risorse.

        //Inizializzazione camera principale

        //Setup del main State.
    }
    
    
    /***************************** FINE INIZIALIZZAZIONE RISORSE ************************************** */


    /********************************* METODI START, STOP E IMPLEMENTAZIONE RUNNABLE  ************************/
    
    public synchronized void start()
    {
        if (isRunning)
        {
            return;
        }

        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    public synchronized void stop()
    {
        if (!isRunning)
        {
            return;
        }

        isRunning = false;

        try
        {
            gameThread.join();
        }
        catch (InterruptedException e)
        {
            //Gestire l'eccezione.
            e.printStackTrace();
        }
    }


    @Override
    public void run()
    {
        initializeResource();


        int updatePerSecond = 60;

        long nanoSecond = 1000000000L;

        double timePerUpdate = nanoSecond / (double)updatePerSecond;

        double elapsedTime = 0.0f;

        long current;
        long lastTime = System.nanoTime();

        //Meglio rendere la variabile atomica.
        while (isRunning)
        {   
            //gameloop

            current = System.nanoTime();

            elapsedTime += (current - lastTime) / timePerUpdate;

            lastTime = current;

            if (elapsedTime >= 1)
            {
                //update e render
                update(elapsedTime);

                render();

                --elapsedTime;
            }
        }

        stop();
    }
    
    /********************************* FINE METODI START, STOP E IMPLEMENTAZIONE RUNNABLE  ************************/


    /********************************** METODI PER IL GAME LOOP ************************************* */
    
    private void update(double elapsedTime)
    {
        //Update in base allo stato attuale.
    }


    private void render()
    {
        BufferStrategy buffer = window.getScreenCanvas().getBufferStrategy();
        //Questo ciclo assicura di renderizzare in maniera corretta e senza perdita di informazioni, le immagini da disegnare sullo schermo.
        do
        {
            do
            {
                Graphics g = buffer.getDrawGraphics();
                
                //Clear screen
                g.clearRect(0, 0, width, height);

                // if (State.getState() != null)
                // {
                //     State.getState().render(this.graphics);
                // }
        
                g.dispose();

            }while (buffer.contentsRestored());

            buffer.show();

        }while (buffer.contentsLost());
    }

    /********************************** FINE METODI PER IL GAME LOOP ************************************* */
}