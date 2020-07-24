package framework3d.game.state;

import framework3d.ecs.Engine;


import java.awt.Graphics;

public abstract class State 
{
    private static State currentState;

    protected Engine engine;


    static
    {
        currentState = null;
    }


    public State(Engine e)
    {
        engine = e;
    }


    public static void setState(State state)
    {
        currentState = state;
    }


    public static State getState()
    {
        return State.currentState;
    }


    public Engine getEngine()
    {
        return engine;
    }



    //********************* METODI DA IMPLEMENTARE DA PARTE DELLE CLASSI CHE EREDITANO ********************** */

    public abstract void updateLogicState(double elapsedTime);
    
    public abstract void render(Graphics g);


}