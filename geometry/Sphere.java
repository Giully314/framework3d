package framework3d.geometry;
/*
Per vedere se due entità in game collidono usiamo la tecnica delle Bounding spheres.
Si crea una sfera per ogni entità in game del quale serve gestire l'eventuale collisione e
si controlla la loro potenziale intersezione (fra le due sfere).
*/
import framework3d.ecs.component.PositionComponent;


public final class Sphere
{

    public Vector4D position;
    public float radius;

    public Sphere(PositionComponent _p, float _radius)
    {
        position = _p.position;
        radius = _radius;
    }

}

