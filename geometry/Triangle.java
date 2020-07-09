package framework3d.geometry;


import java.awt.Color;
import java.awt.Polygon;

public final class Triangle 
{
    private final Vector4D[] t;
    private final Vector4D normal;
    private Color color; //Questo è il colore "intrinseco" e immutabile del triangolo. 
    //private Color shadingColor; //Colore dopo aver applicato lo shader in base alla luce che arriva.
    //private Polygon triangle;

    //Metodo per debug
    public void print()
    {
        t[0].print();
        t[1].print();
        t[2].print();
    }


    //************************************** COSTRUTTORI ********************************************** */

    public Triangle()
    {
        t = new Vector4D[3];
        normal = Vector4D.crossProduct(Vector4D.sub(t[1], t[0]), Vector4D.sub(t[2], t[0]));
    }


    public Triangle(final Vector4D a, final Vector4D b, final Vector4D c)
    {
        t = new Vector4D[] {a, b, c};
        normal = Vector4D.crossProduct(Vector4D.sub(t[1], t[0]), Vector4D.sub(t[2], t[0]));
    }


    public Triangle(final Vector4D[] t)
    {
        this.t = t;
        normal = Vector4D.crossProduct(Vector4D.sub(t[1], t[0]), Vector4D.sub(t[2], t[0]));
    }

    private Triangle(final Vector4D[] t, final Vector4D normal, final Color color)
    {
        this.t = t;
        this.normal = normal;
        this.color = color;
    }

    //************************************** FINE COSTRUTTORI ********************************************** */


    //*************************************** GET ********************************************************** */
    
    public Vector4D getNormal()
    {
        return normal;
    }

    //responsabilità del chiamante nel verificare l'indice passato.
    public Vector4D getVertex(int index)
    {
        return t[index];
    }

    public Polygon getPolygon()
    {
        int[] x = new int[] { (int)t[0].getCoordinate(0), (int)t[1].getCoordinate(0), (int)t[2].getCoordinate(0)};
        int[] y = new int[] { (int)t[0].getCoordinate(1), (int)t[1].getCoordinate(1), (int)t[2].getCoordinate(1)};

        return new Polygon(x, y, 3);
    }

    public Color getColor()
    {
        return color;
    }
    //*************************************** FINE GET ********************************************************** */

    //*************************************** SET *********************************************************** */
    
    public void setColor(Color color)
    {
        this.color = color;
    }

    //*************************************** FINE SET *********************************************************** */


    //************************************ METODI DI NORMALIZZAZIONE ********************************************* */
    
    //normalizza i 3 vertici. da fare ricerche poiché il triangolo cambia di forma. Anche se c'è da tenere in considerazione che 
    //raggruppo i punti in triangoli per comodità di calcolo e costruzione delle figure, altrimenti i punti sono indipendenti.
    public void normalize()
    {
        t[0].normalize();
        t[1].normalize();
        t[2].normalize();
    }


    public void normalizeByW()
    {
        t[0].normalizeByW();
        t[1].normalizeByW();
        t[2].normalizeByW();
    }
    //************************************ FINE METODI DI NORMALIZZAZIONE ********************************************* */


    //**************************************** METODI STATICI ********************************************** */

    public static Triangle copy(Triangle t)
    {
        final Vector4D[] v = new Vector4D[] {Vector4D.copy(t.t[0]), Vector4D.copy(t.t[1]), Vector4D.copy(t.t[2])};

        return new Triangle(v, Vector4D.copy(t.normal), 
        new Color(t.color.getRed(), t.color.getGreen(), t.color.getBlue(), t.color.getAlpha()));
    }


    //**************************************** FINE METODI STATICI ********************************************** */

}