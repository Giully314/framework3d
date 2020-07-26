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

import java.awt.Color;

import java.util.Random;


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

    private EntityRef[] planets;
    private int numberOfPlanets;
    
    /**************************** */

    //******************************* DATI INIZIALI ************************************************* */
    private final long STAR_MASS = 10000000000000L;
    private final long PLANET_MASS = 1000000000L;
    private final long DISTANCE = 150;
    private final long VELOCITY = 1;

    private final String PLANET_MESH_NAME = new String("framework3d\\resource\\Sphere");
    private final String[] PLANETS_MESHES = new String[] { "_60.obj", "_112.obj", "_220.obj", "_316.obj", "_374.obj", "_440.obj"};
    private final Color[] PLANET_COLORS = new Color[] { Color.GRAY, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, 
                                                            Color.ORANGE, Color.PINK, Color.YELLOW};


    private final int updateStars = 1000;
    private int counter = 1000;

    private int[] starsX = new int[100];
    private int[] starsY = new int[100];
    private final int starSize = 3;
    //******************************************************************************************************* */

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
        TransformSystem t = engine.getSystem(TransformSystem.class);
        RenderingSystem r = engine.getSystem(RenderingSystem.class);

        // Vector4D light = new Vector4D(-10, 30, -30);
        // r.setShader(new Shader(light));

        engine.getSystem(InputSystem.class).registerRawInput(keyboard);


        //Inizializzazione navicella 
        ship = engine.entityCreate();


        ForceComponent shipForce = ship.getComponent(ForceComponent.class);
        InputComponent shipInput = ship.getComponent(InputComponent.class);

        PositionComponent shipPosition = ship.getComponent(PositionComponent.class);
        MassComponent shipMass = ship.getComponent(MassComponent.class);

        //*************************** Setup input navicella *****************************
        String forward = new String("forward");
        String backwards = new String("backwards");

        String yawRight = new String("yawRight");
        String yawLeft = new String("yawLeft");

        String pitchDown = new String("pitchDown");
        String pitchUp = new String("pitchUp");

        String rollRight = new String("rollRight");
        String rollLeft = new String("rollLeft");

        float step = 0.01f;

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
        shipInput.output.put(rollRight, new RotationZAction(shipPosition, -step));

        shipInput.input.put(KeyEvent.VK_LEFT, rollLeft);
        shipInput.output.put(rollLeft, new RotationZAction(shipPosition, step));

        //********************************************************************************** */

        //setup dei componenti transform
        shipPosition.position.add(new Vector4D(0, 40.0f, 1500.0f));

        shipMass.mass = 50000;

        
        //Load ship mesh
        r.loadMesh(ship, "framework3d\\resource\\spaceship-Z.obj", Color.GRAY);

        engine.activateAllComponents(ship);
        

        /**************************************** FINE SETUP NAVICELLA ************************************************************ */


        //**************************************************** Creazione stella *********************************************
        star = engine.entityCreate();

        PositionComponent starPosition = star.getComponent(PositionComponent.class);
        MassComponent starMass = star.getComponent(MassComponent.class);

        starMass.mass = STAR_MASS;
        starPosition.position.add(new Vector4D(0, 0, 0));
        
        r.loadMesh(star, "framework3d\\resource\\sphere.obj", Color.BLUE);
        star.getComponent(MeshComponent.class).scale.add(new Vector4D(20, 20, 20));
        

        engine.activateAllComponents(star);
        

        //**************************************** FINE CREAZIONE STELLA ********************************************************** */


        //****************************************** CREAZIONE PIANETI ************************************************************* */
        numberOfPlanets = 150;
        planets = new EntityRef[numberOfPlanets];

        PositionComponent planetPosition;
        VelocityComponent planetVelocity;
        MassComponent planetMass;
        MeshComponent planetMesh;

        Random rand = new Random();
        for (int i = 0; i < planets.length; ++i)
        {
            planets[i] = engine.entityCreate();

            planetPosition = planets[i].getComponent(PositionComponent.class);
            planetVelocity = planets[i].getComponent(VelocityComponent.class);
            planetMass = planets[i].getComponent(MassComponent.class);

            planetMass.mass = (long)(PLANET_MASS * Math.random());
            
            float sign1;
            float sign2;
            float sign3;

            if (rand.nextBoolean()) sign1 = 1;
            else sign1 = -1;

            if (rand.nextBoolean()) sign2 = 1;
            else sign2 = -1;

            if (rand.nextBoolean()) sign3 = 1;
            else sign3 = -1;



            planetPosition.position.add(new Vector4D(sign1 * DISTANCE * (i + 1), 
                sign2 * (int)(Math.random() * DISTANCE * (i + 1)), 
                sign3 * (int)(Math.random() * DISTANCE * (i + 1))));


            planetVelocity.velocity.add(new Vector4D(
                sign1 * (float)(VELOCITY * Math.random()), 
                sign2 * (float)(Math.random() * VELOCITY), 
                sign3 * VELOCITY * (i + 1)));

            r.loadMesh(planets[i], PLANET_MESH_NAME + PLANETS_MESHES[i % PLANETS_MESHES.length], PLANET_COLORS[i % PLANET_COLORS.length]);

            planetMesh = planets[i].getComponent(MeshComponent.class);
            planetMesh.scale.add(new Vector4D(
                (float)Math.random() * (i % 20), 
                (float)Math.random() * (i % 20), 
                (float)Math.random() * (i % 20)));

            engine.activateAllComponents(planets[i]);
        }

        planets[10].getComponent(PositionComponent.class).position = new Vector4D(0, 40, 0);


        //******************************************* FINE CREAZIONE PIANETI ********************************************** */


        //Inizializzazione camera principale
        camera = new Camera(ship);


        //Registrazione risorse sistemi. (camera, entità ecc, shader)
        float near;
        float far;
        far = 100;
        near = far / 1000; //divido per 1000 poiché è il rapporto ottimale per garantire una visione grafica "realistica"
        r.createProjectionMatrix(near, far, width, height, 90.0f);
        r.setCamera(camera);
        r.setShader(new Shader(starPosition.position));

        
        t.registerEntityCollision(ship);
        t.registerEntityCollision(star);


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
            //State.getState().getEngine().getSystem(TransformSystem.class).printEntities();
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
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, width, height);

                g.setColor(Color.WHITE);
                
                if (counter == updateStars)
                {
                    counter = 0;
                    for (int i = 0; i < starsX.length; ++i)
                    {
                        starsX[i] = (int)(Math.random() * width);
                        starsY[i] = (int)(Math.random() * height);
                    }
                }
                ++counter;


                for (int i = 0; i < starsX.length; ++i)
                {
                    g.fillOval(starsX[i], starsY[i], starSize, starSize);
                }

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