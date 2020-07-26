package framework3d.geometry;

/*
Per vedere se due entità in game collidono usiamo la tecnica delle Bounding spheres.
Si crea una sfera per ogni entità in game del quale serve gestire l'eventuale collisione e
si controlla la loro potenziale intersezione (fra le due sfere o fra un punto ed una sfera).
*/



public final class Intersection {

    // un punto e una sfera collidono se la distanza fra il punto ed il centro della sfera e minore del raggio della sfera.
    // la radice quadrata può essere costosa da calcolare quindi si può ottimizzare il codice confrontando il quadrato della distanza in questione
    // con il quadrato del raggio della sfera.
    public static boolean intersect(Vector4D point, Sphere s)
    {   
        var px = point.getCoordinate(0);
        var py = point.getCoordinate(1);
        var pz = point.getCoordinate(2);

        var sx = s.position.getCoordinate(0);
        var sy = s.position.getCoordinate(1);
        var sz = s.position.getCoordinate(2);

        var squared_distance = ((px - sx) * (px - sx) +
                                (py - sy) * (py - sy) +
                                (pz - sz) * (pz - sz) );

        return squared_distance < s.radius * s.radius;
    }

    // due sfere collidono se la distanza dai centri delle sfere e minore della somma dei due raggi
    public static boolean intersect(Sphere a, Sphere b)
    {
        var ax = a.position.getCoordinate(0);
        var ay = a.position.getCoordinate(1);
        var az = a.position.getCoordinate(2);

        var bx = b.position.getCoordinate(0);
        var by = b.position.getCoordinate(1);
        var bz = b.position.getCoordinate(2);

        // usiamo la moltiplicazione perchè è più veloce di chiamare Math.pow
        var distance = Math.sqrt( (ax - bx) * (ax - bx) +
                                  (ay - by) * (ay - by) +
                                  (az - bz) * (az - bz) );

        return distance < (a.radius + b.radius);
    }


    public static Vector4D Vector_IntersectPlane(Vector4D plane_p, Vector4D plane_n, Vector4D lineStart, Vector4D lineEnd)
    {
        plane_n.normalize();
        float plane_d = -Vector4D.dotProduct(plane_n, plane_p);
        float ad = Vector4D.dotProduct(lineStart, plane_n);
        float bd = Vector4D.dotProduct(lineEnd, plane_n);
        float t = (-plane_d - ad) / (bd - ad);
        Vector4D lineStartToEnd = Vector4D.sub(lineEnd, lineStart);
        Vector4D lineToIntersect = Vector4D.multiplyByScalar(lineStartToEnd, t);

        return Vector4D.add(lineStart, lineToIntersect);
    }

    public static Vector4D lineIntersection(Vector4D planePoint, Vector4D planeNormal, Vector4D linePoint, Vector4D lineDirection) 
    {
        lineDirection.normalize();

        if (Vector4D.dotProduct(planeNormal, lineDirection) == 0) 
        {
            return null;
        }
        
        double s = (Vector4D.dotProduct(planeNormal, planePoint) - Vector4D.dotProduct(planeNormal, linePoint)) / Vector4D.dotProduct(planeNormal, lineDirection);

        return Vector4D.add(linePoint, Vector4D.multiplyByScalar(lineDirection, (float)s));
    }
}