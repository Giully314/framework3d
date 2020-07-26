package framework3d.ecs.system;

import java.util.ArrayList;

import framework3d.geometry.Triangle;
import framework3d.geometry.Sphere;
import framework3d.geometry.Vector4D;

import java.awt.Color;


/*
Lo shader, in questo caso, non è un vero e proprio sistema, poiché nel gioco non si gesticono complicate situazioni di luce in cui
c'è bisogno di componenti specifici.

Nota: questa classe prende spunto da cosa fa uno shader, ma è molto lontana dal suo vero scopo. Uno shader si occupa solo 
di stabilire il colore di un pixel in base a dei dati in input.
In questa classe invece si calcola la distanza dalla sorgente di luce, si verificano possibili intersezioni e si determina il colore.
*/

public class Shader 
{
    //Assumo che ci sia solo una fonte di luce.
    private Vector4D lightSource;

    public Shader()
    {
    }


    public Shader(Vector4D lightSource)
    {
        this.lightSource = lightSource;
    }


    //assumo che in sphere non ci sia l'oggetto su cui sto applicando lo shader.
    public void shader(Triangle t)//, ArrayList<Sphere> objects)
    {
        Vector4D centralTrianglePoint = Vector4D.add(Vector4D.add(t.getVertex(0), t.getVertex(1)), t.getVertex(2));
        centralTrianglePoint.multiplyByScalar(1.0f / 3.0f);

        Vector4D distance = Vector4D.sub(centralTrianglePoint, lightSource);

        //check delle collisioni lo salto momentaneamente

        //distance.print();
        distance.normalize();

        //Il normale viene già calcolato nella pipeline (ma la mia matrice non è ortonormale quindi devo ricalcolarlo)
        t.calculateNormal();
        // System.out.println("\n**********************\n");
        // t.print();
        // System.out.print("Normal: ");
        // t.getNormal().print();
        // System.out.println("\n********************\n");
        t.getNormal().normalize();
        float lightIntensity = Vector4D.dotProduct(distance, t.getNormal());

        

        int pixel = (int)(13 * lightIntensity);
        Color c;
        switch (pixel)
		{
		case 0:
			c = Color.BLACK;
			break;
		case 1:
			c = new Color(25, 25, 25);
			break;
		case 2:
			c = new Color(51, 51, 51);
			break;
		case 3:
			c = new Color(76, 76, 76);
			break;
		case 4:
			c = new Color(102, 102, 102);
			break;
		
		case 5:
			c = new Color(127, 127, 127);
			break;
		case 6:
			c = new Color(153, 153, 153);
			break;
		case 7:
			c = new Color(178, 178, 178);
			break;
		case 8:
			c = new Color(204, 204, 204);
			break;
		
		case 9:
			c = new Color(229, 229, 229);
			break;
		case 10:
			c = new Color(239, 239, 239);
			break;
		case 11:
			c = new Color(248, 248, 248);
			break;
		case 12:
			c = Color.WHITE;
			break;
		
		default:
			c = Color.BLACK;
			break;
        }
        
        //System.out.println(c);
        t.setColor(c);
    }



    /******************************************* SET AND GET ******************************************************** */
    public void setLightSource(Vector4D lightSource)
    {
        this.lightSource = lightSource;
    }


    public Vector4D getLightSource()
    {
        return lightSource;
    }
    /******************************************* FINE SET AND GET ******************************************************** */
}