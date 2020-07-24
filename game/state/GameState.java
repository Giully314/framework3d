package framework3d.game.state;

import java.awt.Graphics;

import framework3d.ecs.WorldEngine;
import framework3d.ecs.Engine;

public class GameState extends State 
{

    public GameState()
    {
        this(new WorldEngine());
    }


    public GameState(Engine e)
    {
        super(e);
    }



    @Override
    public void updateLogicState(double elapsedTime)
    {
        engine.updateSceneState(elapsedTime);
    }   

    @Override
    public void render(Graphics g)
    {
        engine.renderScene(g);
    }
}