package framework3d.geometry;

/*
Questa classe rappresenta un vettore "semi immutabile"(?)  a coordinate omogenee.
Un vettore a coordinate omogenee (VCO) rappresenta un vettore in uno spazio di proiezione. La differenza con le coordinate cartesiane è che 
queste ultime agiscono su spazi euclidei. Infatti un VCO può anche essere visto come una mappatura tra lo spazio 4D a quello 3D e 
viceversa. Tutto questo è possibile grazie alla 4 coordinata del vettore che agisce sul vettore stesso "normalizzandolo".
Facciamo un esempio: supponiamo di avere un vettore 3d v = (x, y, z). Per "trasformare" questo vettore in un VCO basta aggiungere 
una quarta coordinata w e abbiamo un VCO k = (w * x, w * y, w * z, w). Per l'operazione inversa basta dividere per w.
Come si può notare un vettore in 3d dimensioni è equivalente a infiniti VCO definiti come sopra (basta prendere w diverse).

Alcune proprietà dei VCO:
- Se eseguo la sottrazione di due vettori P - Q, la quarta coordinata diventa 0 (a patto che i due vettori siano nello spazio 3d. cioè
    con quarta coordinata = 1.0), questo perché mi interessa la direzione e non 
    il punto rappresentato dalla sottrazione dei vettori.

- Le traslazioni di uno spazio 3d non possono avvenire nello stesso spazio 3d, ma serve aumentare di dimensione. Ecco perché
    i VCO saranno fondamentali insieme alle trasformazioni affini (cioè quelle trasformazioni che terminano con una traslazione 
    (che non è una trasformazione lineare).


Alcune proprietà sulle trasformazioni affini:
 * angoli rimangono invariati dopo la trasformazione;
 * la lunghezza rimane invariata;
 * tutti i punti situati su un linea rimangono su una linea dopo la trasformazione;
 * le distanze vengono preservate, cioè il punto medio di un segmento rimane lo stesso dopo la trasformazione.
 * 
 * Le proiezioni non rispettano la proprietà che riguarda le distanze e la lunghezza.


Per concludere, la quarta coordinata servirà per rimappare gli oggetti dopo la proiezione, dividendo tutti i vettori per quest'ultima.

Nota: nelle usuali operazioni 3D (somma tra due vettori, ecc) la quarta coordinata non interviene. Questo perché, come ho già detto,
la quarta coordinata interviene solo durante la fase di proiezione.


(?) Per semi immutabile intendo un vettore che può cambiare le sue coordinate solo in due casi:
normalizzando il vettore e normalizzando il vettore rispetto alla sua quarta coordinata.
*/

public final class Vector4D 
{
    //Array che rappresenta il vettore 4d.
    private final float[] v;


    //Metodo per debug
    public void print()
    {
        System.out.print("( ");
        for (int i = 0; i < v.length - 1; ++i)
        {
            System.out.print(v[i] + " , "); 
        }

        System.out.println(v[3] + " )");
    }

    public void clearW()
	{
		v[3] = 1.0f;
	}

    //******************* COSTRUTTORI ******************************** */
    public Vector4D()
    {
        this(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public Vector4D(final float x, final float y, final float z)
    {
        this(x, y, z, 1.0f);
    }



    public Vector4D(final float x, final float y, final float z, final float w)
    {
        v = new float[4];
        v[0] = x;
        v[1] = y;
        v[2] = z;
        v[3] = w;
    }

    public Vector4D(final float[] v)
    {
        this.v = v;
    }
    //********************* FINE COSTRUTTORI ******************************** */

    //********************************************** OPERAZIONI TRA VETTORI *************************************** */
    
    public void add(final Vector4D w)
    {
        v[0] += w.v[0];
        v[1] += w.v[1];
        v[2] += w.v[2];
    }


    public void sub(final Vector4D w)
    {
        v[0] -= w.v[0];
        v[1] -= w.v[1];
        v[2] -= w.v[2];
    }


    public void multiplyByScalar(float c)
    {
        v[0] *= c;
        v[1] *= c;
        v[2] *= c;
        
        //moltiplicare anche la quarta coordinata?? 
    }


    public void setCoordinate(int index, float value)
    {
        v[index] = value;
    }

    //********************************************** FINE OPERAZIONI TRA VETTORI *************************************** */



    //********************* METODO GET PER LE COORDINATE ******************** */
    
    //Assumo che la responsabilità per la verifica dell'indice è del chiamante.
    public float getCoordinate(int index)
    {  
        return v[index];
    }

    //********************* FINE METODO GET PER LE COORDINATE ******************** */


    //********************* METODI PER LA NORMALIZZAZIONE VETTORE  ****************************/
    public void normalize()
    {
        float l = length();

        if (l != 0)
        {
            v[0] /= l;
            v[1] /= l;
            v[2] /= l;
        }
    }


    public void normalizeByW()
    {
        if (v[3] != 0)
        {
            v[0] /= v[3];
            v[1] /= v[3];
            v[2] /= v[3];
        }
    }


    public float length()
    {
        return (float)Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    }


    public void reset()
    {
        v[0] = 0.0f;
        v[1] = 0.0f;
        v[2] = 0.0f;
        v[3] = 1.0f;
    }
    //********************* FINE METODI PER LA NORMALIZZAZIONE VETTORE  ****************************/


    // ************************* METODI STATIC PER LA CREAZIONE DI VETTORI ******************************

    public static Vector4D copy(Vector4D v)
    {
        return new Vector4D(v.getCoordinate(0), v.getCoordinate(1), v.getCoordinate(2), v.getCoordinate(3));
    }


    public static Vector4D add(Vector4D v, Vector4D w)
    {
        return new Vector4D(v.getCoordinate(0) + w.getCoordinate(0), 
                            v.getCoordinate(1) + w.getCoordinate(1), 
                            v.getCoordinate(2) + w.getCoordinate(2), 
                            1.0f);
    }

    
    //da controllare se sottrarre la 4 coordinata oppure no
    public static Vector4D sub(Vector4D v, Vector4D w)
    {
        return new Vector4D(v.getCoordinate(0) - w.getCoordinate(0), 
                            v.getCoordinate(1) - w.getCoordinate(1), 
                            v.getCoordinate(2) - w.getCoordinate(2), 
                            v.getCoordinate(3) - w.getCoordinate(3));
    }
    

    public static Vector4D multiplyByScalar(Vector4D v, float scalar)
    {
        return new Vector4D(v.getCoordinate(0) * scalar, 
                            v.getCoordinate(1) * scalar, 
                            v.getCoordinate(2) * scalar, 
                            1.0f);
    }


    public static float dotProduct(Vector4D v, Vector4D w)
    {
        return v.getCoordinate(0) * w.getCoordinate(0) +
                v.getCoordinate(1) * w.getCoordinate(1) +
                v.getCoordinate(2) * w.getCoordinate(2);
    }


    //Da controllare correttezza.
    public static Vector4D crossProduct(Vector4D v, Vector4D w)
    {
        return new Vector4D(v.getCoordinate(1) * w.getCoordinate(2) - v.getCoordinate(2) * w.getCoordinate(1),
                        v.getCoordinate(2) * w.getCoordinate(0) - v.getCoordinate(0) * w.getCoordinate(2),
                        v.getCoordinate(0) * w.getCoordinate(1) - v.getCoordinate(1) * w.getCoordinate(0),
                        1.0f);
    }
    // ************************** FINE METODI STATIC PER LA CREAZIONE DI VETTORI ************************
}