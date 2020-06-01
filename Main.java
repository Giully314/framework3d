package framework3d;

import framework3d.ecs.entity.ProvaPlayer;
import framework3d.handler.GameHandler;

//import framework3d.geometry.*;

public class Main 
{   
    public static void main(String[] args)
    {
        GameHandler game = new GameHandler();

        ProvaPlayer p = new ProvaPlayer(game.getHandler().getInputHandler());

        game.addDynamicEntity(p);

        game.gameLoop();

        p.print();
    }
}