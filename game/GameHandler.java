package framework3d.game;

import java.awt.image.BufferStrategy;


import java.awt.Graphics;

import framework3d.window.WindowHandler;

import java.awt.event.KeyEvent;

import framework3d.game.state.*;
import framework3d.ecs.*;
import framework3d.ecs.component.InputComponent;
import framework3d.ecs.component.MeshComponent;
import framework3d.ecs.component.PositionComponent;
import framework3d.ecs.component.VelocityComponent;
import framework3d.ecs.entity.*;
import framework3d.ecs.system.InputSystem;
import framework3d.ecs.system.RenderingSystem;

import framework3d.geometry.*;
import framework3d.utility.FrameRate;
import framework3d.utility.KeyboardInput;

public class GameHandler implements Runnable
{
    /*********** Finestra ******** */
    
    private WindowHandler window;
    private int width;
    private int height;

    private KeyboardInput keyboard;

    private FrameRate frameRate;

    /***************************** */


    /*********** State ************/
    
    private State gameStatus;

    /******************************/


    /*********** Game thread ****** */
    
    private Thread gameThread;
    private boolean isRunning;

    /***************************** */


    /************ Entità ********* */
    
    private EntityRef ship;
    private EntityRef camera;
    private float angle;

    /**************************** */


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
        //Inizializzazione finestra e risorse input
        window.initializeWindow();
        keyboard = new KeyboardInput();
        frameRate = new FrameRate();
        frameRate.initialize();

        //Aggiunta risorse input al frame.
        window.getWindowFrame().addKeyListener(keyboard);

        //Creazione engine e inizializzazione dei sistemi.
        WorldEngine engine = new WorldEngine();

        engine.getSystem(InputSystem.class).registerRawInput(keyboard);


        //Inizializzazione entità
        ship = engine.entityCreate();

        angle = 0.0f;

        InputComponent shipInput = ship.getComponent(InputComponent.class);
        // shipInput.input.put(KeyEvent.VK_W, "forward");
        // shipInput.output.put("forward", new Action)

        PositionComponent shipPosition = ship.getComponent(PositionComponent.class);
        shipPosition.position = new Vector4D(0.0f, 0.0f, -4.0f, 1.0f);

        //Questo passaggio è da automatizzare con una funzione
        MeshComponent shipMesh = ship.getComponent(MeshComponent.class);
        shipMesh.mesh = new PolygonMesh("C:\\Users\\Jest\\Desktop\\programmazione\\Java\\Framework3D\\resource\\menlowpoly.obj");
        shipMesh.mesh.printMesh();
        engine.activateAllComponents(ship);

        
        //Inizializzazione camera principale
        camera = engine.entityCreate();

        //setup della camera

        System.out.println(camera.getComponent(PositionComponent.class).position.getCoordinate(0));


        //Collegamento camera navicella attraverso input component.

        camera.getComponent(PositionComponent.class).position = new Vector4D(0.0f, 0.0f, 0.0f, 1.0f);

        engine.activateAllComponents(camera);

        //Registrazione risorse sistemi. (camera, entità ecc)
        RenderingSystem r = engine.getSystem(RenderingSystem.class);
        float near;
        float far;
        far = 100;
        near = far / 1000;
        r.createProjectionMatrix(near, far, width, height, 90.0f);
        r.setCamera(camera);
        camera.getComponent(MeshComponent.class).deactivateComponent();

        //Setup del main State.
        gameStatus = new GameState(engine);

        State.setState(gameStatus);
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

            if (elapsedTime >= 2)
            {
                //update e render
                update(elapsedTime);

                render();

                --elapsedTime;
                --elapsedTime;
            }
        }

        stop();
    }
    
    /********************************* FINE METODI START, STOP E IMPLEMENTAZIONE RUNNABLE  ************************/


    /********************************** METODI PER IL GAME LOOP ************************************* */
    
    private void update(double elapsedTime)
    {
        // if (State.getState() != null)
        // {
        //     State.getState().updateLogicState(elapsedTime);
        // }
        angle += 1.0f * elapsedTime;
        ship.getComponent(PositionComponent.class).rotation = Matrix4x4.makeRotationY(angle);
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

                if (State.getState() != null)
                {
                    State.getState().render(g);
                    frameRate.calculate();
                    g.drawString(frameRate.getFrameRate(), 20, 20);
                }
        
                g.dispose();

            }while (buffer.contentsRestored());

            buffer.show();

        }while (buffer.contentsLost());
    }

    /********************************** FINE METODI PER IL GAME LOOP ************************************* */
}