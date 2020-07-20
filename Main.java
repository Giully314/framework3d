package framework3d;



import java.util.HashMap;

//import framework3d.handler.GameHandler;

//import framework3d.geometry.*;

public class Main 
{   
    public static void main(String[] args)
    {
        //GameHandler game = new GameHandler();
        
        HashMap<Class<? extends Component>, Component> m = new HashMap<>();

        Class<Position> p = Position.class;
        
        Position p2 = new Position();
        m.put(p, p2);


        m.put(Velocity.class, new Velocity());

        //game.gameLoop();

        Class<? extends Component> s = Position.class;

        if (s == Position.class)
        {
            System.out.println("EVVIVAAAAAAAAAAAAAAAAAAA");
        }

        //(Position)s;
        
        // Position o = p.cast(s);

        // int w = 4;

        // short q = 5;

        // int e = (int)q + w;
        

        //System.out.println(((Position)m.get(Position.class)).a);

    }


    public interface Component
    {

    }


    public static class Position implements Component
    {
        public int a = 32;
    }

    public static class Velocity implements Component
    {
        public int a = 89;
    }

}