package framework3d.geometry;

/*
Questa classe rappresenta un vettore immutabile (il suo stato interno non può cambiare) a coordinate omogenee.
Un vettore a coordinate omogenee (VCO) rappresenta un vettore in uno spazio di proiezione. La differenza con le coordinate cartesiane è che 
queste ultime agiscono su spazi euclidei. Infatti un VCO può anche essere visto come una mappatura tra lo spazio 4D a quello 3D e 
viceversa. Tutto questo è possibile grazie alla 4 coordinata del vettore che agisce sul vettore stesso "normalizzandolo".
Facciamo un esempio: supponiamo di avere un vettore 3d v = (x, y, z). Per "trasformare" questo vettore in un VCO basta aggiungere 
una quarta coordinata w e abbiamo un VCO k = (w * x, w * y, w * z, w). Per l'operazione inversa basta dividere per w.
Come si può notare un vettore in 3d dimensioni è equivalente a infiniti VCO definiti come sopra (basta prendere w diverse).

Alcune proprietà dei VCO:
- Se eseguo la sottrazione di due vettori P - Q, la quarta coordinata diventa 0, questo perché mi interessa la direzione e non 
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
*/

public class Vector4D 
{
    //Array che rappresenta il vettore 4d.
    private final float[] v;

    //******************* COSTRUTTORI ******************************** */
    public Vector4D()
    {
        v = new float[4];
        v[0] = 0.0f;
        v[1] = 0.0f;
        v[2] = 0.0f;
        v[3] = 1.0f;
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



    // ************************* METODI STATIC PER LA CREAZIONE DI VETTORI ******************************


    // ************************** FINE METODI STATIC PER LA CREAZIONE DI VETTORI *******************************


}