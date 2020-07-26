package framework3d.game;

import java.awt.image.BufferStrategy;


import java.awt.Graphics;

import framework3d.window.WindowHandler;

import java.awt.event.KeyEvent;

import framework3d.game.state.*;
import framework3d.ecs.*;
import framework3d.ecs.action.*;
import framework3d.ecs.component.*;
import framework3d.ecs.entity.*;
import framework3d.ecs.system.*;


import framework3d.geometry.*;
import framework3d.utility.FrameRate;
import framework3d.utility.KeyboardInput;
import framework3d.utility.camera.Camera;

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
    private Camera camera;
    private EntityRef star;
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
        RenderingSystem r = engine.getSystem(RenderingSystem.class);

        Vector4D light = new Vector4D(-10, 30, -30);
        r.setShader(new Shader(light));

        engine.getSystem(InputSystem.class).registerRawInput(keyboard);


        //Inizializzazione entità
        ship = engine.entityCreate();

        angle = 0.0f;

        ForceComponent shipForce = ship.getComponent(ForceComponent.class);
        InputComponent shipInput = ship.getComponent(InputComponent.class);

        PositionComponent shipPosition = ship.getComponent(PositionComponent.class);
        MassComponent shipMass = ship.getComponent(MassComponent.class);

        //*************************** Setup input *****************************
        String forward = new String("forward");
        String backwards = new String("backwards");

        String yawRight = new String("yawRight");
        String yawLeft = new String("yawLeft");

        String pitchDown = new String("pitchDown");
        String pitchUp = new String("pitchUp");

        String rollRight = new String("rollRight");
        String rollLeft = new String("rollLeft");

        float step = 0.005f;

        Vector4D forceForward = new Vector4D(0, 0, -1);
        Vector4D forceBackwards = new Vector4D(0, 0, 1);
        
        shipInput.input.put(KeyEvent.VK_W, forward);
        shipInput.output.put(forward, new ForceAction(shipForce, forceForward));

        shipInput.input.put(KeyEvent.VK_S, backwards);
        shipInput.output.put(backwards, new ForceAction(shipForce, forceBackwards));

        shipInput.input.put(KeyEvent.VK_A, yawLeft);
        shipInput.output.put(yawLeft, new RotationYAction(shipPosition, step));

        shipInput.input.put(KeyEvent.VK_D, yawRight);
        shipInput.output.put(yawRight, new RotationYAction(shipPosition, -step));

        shipInput.input.put(KeyEvent.VK_UP, pitchDown);
        shipInput.output.put(pitchDown, new RotationXAction(shipPosition, step));

        shipInput.input.put(KeyEvent.VK_DOWN, pitchUp);
        shipInput.output.put(pitchUp, new RotationXAction(shipPosition, -step));

        shipInput.input.put(KeyEvent.VK_RIGHT, rollRight);
        shipInput.output.put(rollRight, new RotationZAction(shipPosition, step));

        shipInput.input.put(KeyEvent.VK_LEFT, rollLeft);
        shipInput.output.put(rollLeft, new RotationZAction(shipPosition, -step));

        //********************************************************************************** */

        //setup dei componenti transform
        shipPosition.position = new Vector4D(0.0f, 30.0f, -15.0f, 1.0f);

        shipMass.mass = 50000;

        
        //Load ship mesh
        r.loadMesh(ship, "C:\\Users\\Jest\\Desktop\\programmazione\\Java\\Framework3D\\framework3d\\resource\\spaceship.obj");

        engine.activateAllComponents(ship);
        

        //Creazione stella
        star = engine.entityCreate();

        PositionComponent starPosition = star.getComponent(PositionComponent.class);
        MassComponent starMass = star.getComponent(MassComponent.class);

        starMass.mass = 1000000000;
        starPosition.position.add(new Vector4D(0, 0, -30));

        r.loadMesh(star, "C:\\Users\\Jest\\Desktop\\programmazione\\Java\\Framework3D\\framework3d\\resource\\sphere.obj");
        

        engine.activateAllComponents(star);

        
        //Inizializzazione camera principale
        camera = new Camera(ship);

        //setup della camera (stesse caratteristiche della navicella? (componenti transform))

        //Registrazione risorse sistemi. (camera, entità ecc)
        float near;
        float far;
        far = 100;
        near = far / 1000;
        r.createProjectionMatrix(near, far, width, height, 90.0f);
        r.setCamera(camera);

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

        long nsPerFrame;

        //Meglio rendere la variabile atomica.
        while (isRunning)
        {   
            //gameloop

            current = System.nanoTime();

            nsPerFrame = current - lastTime;
            elapsedTime += nsPerFrame / timePerUpdate;


            lastTime = current;

            if (elapsedTime >= 1)
            {
                //update e render
                update(nsPerFrame/1.0E09);

                
                --elapsedTime;
            }
            render();
        }

        stop();
    }
    
    /********************************* FINE METODI START, STOP E IMPLEMENTAZIONE RUNNABLE  ************************/


    /********************************** METODI PER IL GAME LOOP ************************************* */
    
    private void update(double elapsedTime)
    {
        if (State.getState() != null)
        {
            camera.moveCamera();
            //camera.getCameraPosition().print();
            State.getState().updateLogicState(elapsedTime);
            State.getState().getEngine().getSystem(TransformSystem.class).printEntities();
        }
        
        
        // angle += 2.0f * elapsedTime;
        // ship.getComponent(PositionComponent.class).rotation = Matrix4x4.makeRotationY(angle);
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