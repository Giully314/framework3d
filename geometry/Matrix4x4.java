package framework3d.geometry;

/* 
Matrice immutabile che rappresenta una trasformazione. 

 Le matrici utilizzate per le trasformazioni devono essere ortogonali, cioè una matrice formata a partire da una base ortonormale.
 Questo permette di avere delle trasformazioni che mantengono invariata la lunghezza e l'angolo. Questa proprietà è utile
 per diversi motivi tra cui: 
 l'inversa di una matrice ortogonale è la sua trasposta;
 le trasformazioni effettuate su vettori tangenti o normali ad un dato punto, mantengono le loro proprietà e soprattutto
 non hanno bisogno di una trasformazione diversa per essere correttamente calcolati. Se la matrice non è ortonormale,
 il vettore normale al punto deve essere moltiplicato per (M^-1) trasposta, dove M è la matrice utilizzata per trasformare 
 il punto in questione. 

 L'ordine di rappresentazione utilizzato è column major.
 Il sistema di riferimento è destrorso, x a destra, y sopra, z dallo schermo verso la persona.
*/

public final class Matrix4x4 
{
    // non è la vera è propria dimensione della matrice (che contiene 16 elementi), ma il numero di righe/colonne
    private static final int size = 4; 

    private final float[] m;
    
    public Matrix4x4()
    {
        m = new float[4 * 4];
    }

    //Responsabilità di chi chiama il costruttore a passare un vettore della giusta dimensione.
    public Matrix4x4(final float[] m)
    {
        this.m = m;
    }


    //************************** METODI STATIC PER LA CREAZIONE DI MATRICI ******************************* */
    

    //******************** TRASFORMAZIONI ********************************** */
    public static Matrix4x4 makeIdentity()
    {
        final float[] m = new float[Matrix4x4.size * Matrix4x4.size];

        m[0 * Matrix4x4.size + 0] = 1.0f;
        m[1 * Matrix4x4.size + 1] = 1.0f;
        m[2 * Matrix4x4.size + 2] = 1.0f;
        m[3 * Matrix4x4.size + 3] = 1.0f;
    
        return new Matrix4x4(m);
    }


    public static Matrix4x4 makeTranslation(float x, float y, float z)
    { 
        final float[] m = new float[Matrix4x4.size * Matrix4x4.size];

        m[0 * Matrix4x4.size + 0] = 1.0f;
        m[0 * Matrix4x4.size + 3] = x;
        
        m[1 * Matrix4x4.size + 1] = 1.0f;
        m[1 * Matrix4x4.size + 3] = y;
        
        m[2 * Matrix4x4.size + 2] = 1.0f;
        m[2 * Matrix4x4.size + 3] = z;
        
        m[3 * Matrix4x4.size + 3] = 1.0f;

        return new Matrix4x4(m);
    }

    //DA CONTROLLARE POSSIBILI ERRORI NELLE MATRICI DI ROTAZIONE.
    public static Matrix4x4 makeRotationX(float angle)
    {
        final float[] m = new float[Matrix4x4.size * Matrix4x4.size];

        m[0 * Matrix4x4.size + 0] = 1.0f;

        m[1 * Matrix4x4.size + 1] = (float)Math.cos(angle);
        m[1 * Matrix4x4.size + 2] = -(float)Math.sin(angle);
        
        m[2 * Matrix4x4.size + 1] = (float)Math.sin(angle);
        m[2 * Matrix4x4.size + 2] = (float)Math.cos(angle);

        m[3 * Matrix4x4.size + 3] = 1.0f;

        return new Matrix4x4(m);
    }


    public static Matrix4x4 makeRotationY(float angle)
    {
        final float[] m = new float[Matrix4x4.size * Matrix4x4.size];

        m[0 * Matrix4x4.size + 0] = (float)Math.cos(angle);
        m[0 * Matrix4x4.size + 2] = (float)Math.sin(angle);

        m[1 * Matrix4x4.size + 1] = 1.0f;
 
        m[2 * Matrix4x4.size + 0] = -(float)Math.sin(angle);
        m[2 * Matrix4x4.size + 2] = (float)Math.cos(angle);
 
        m[3 * Matrix4x4.size + 3] = 1.0f;

        return new Matrix4x4(m);
    }


    public static Matrix4x4 makeRotationZ(float angle)
    {
        final float[] m = new float[Matrix4x4.size * Matrix4x4.size];

        m[0 * Matrix4x4.size + 0] = (float)Math.cos(angle);
        m[0 * Matrix4x4.size + 1] = -(float)Math.sin(angle);

        m[1 * Matrix4x4.size + 0] = (float)Math.sin(angle);
        m[1 * Matrix4x4.size + 1] = (float)Math.cos(angle);
        
        m[2 * Matrix4x4.size + 2] = 1.0f;
       
        m[3 * Matrix4x4.size + 3] = 1.0f;

        return new Matrix4x4(m);
    }


    public static Matrix4x4 makeScaling(float x, float y, float z)
    {
        final float[] m = new float[Matrix4x4.size * Matrix4x4.size];

        m[0 * Matrix4x4.size + 0] = x;

        m[1 * Matrix4x4.size + 1] = y;

        m[2 * Matrix4x4.size + 2] = z;

        m[3 * Matrix4x4.size + 3] = 1.0f;

        return new Matrix4x4(m);
    }


    public static Matrix4x4 makeAffineTransformation(Vector4D scaling, Matrix4x4 rotation, Vector4D position)
    {
        Matrix4x4 w = multiplication(rotation, makeScaling(scaling.getCoordinate(0), 
                        scaling.getCoordinate(1), scaling.getCoordinate(2)));

        return multiplication(makeTranslation(position.getCoordinate(0), position.getCoordinate(1), 
                position.getCoordinate(2)), w);
    }

    //******************** FINE TRASFORMAZIONI ********************************** */

    //********************** PROIEZIONI **************************************** */
    //DA CONTROLLARE MATRICI PROIEZIONI
    /* 
    La matrice utilizzata per le proiezioni è la stessa utilizzata da opengl. Per la documentazione su come ricavare questa matrice:
    https://www.scratchapixel.com/lessons/3d-basic-rendering/perspective-and-orthographic-projection-matrix/opengl-perspective-projection-matrix
    */
    public static Matrix4x4 makeProjection(float near, float far, float aspectRatio, float fov)
    {
        final float[] m = new float[Matrix4x4.size * Matrix4x4.size];

        //Per la spiegazione della divisione dell'angolo in 2, vedere articolo strech
        float fovRad = fov * 0.5f / 180.0f * 3.1415926535f;

        float top = (float)Math.tan(fovRad) * near;
        float bottom = -top;

        float right = top * aspectRatio;
        float left = -right;

        m[0 * Matrix4x4.size + 0] = 2.0f * near / (right - left);
		m[0 * Matrix4x4.size + 2] = (right + left) / (right - left);
		
		m[1 * Matrix4x4.size + 1] = 2.0f * near / (top - bottom);
		m[1 * Matrix4x4.size + 2] = (top + bottom) / (top - bottom);

		m[2 * Matrix4x4.size + 2] = - (far  + near) / (far - near);
		m[2 * Matrix4x4.size + 3] = - 2.0f * far * near / (far - near);

		m[3 * Matrix4x4.size + 2] = -1.0f;

        return new Matrix4x4(m);
    }

    /* 
    Questa matrice permette di trasformare le coordinate dal NDC (normalized device coordinates) al window space.
    x e y corrispondo all'angolo sinistro in basso. widht e height a larghezza e lunghezza della finestra.
    far e near corrispondono ai due piani definiti per la matrice di proiezione.
    */
    public static Matrix4x4 makeViewport(int originX, int originY,  float near, float far, int width, int height)
    {
        final float[] m = new float[Matrix4x4.size * Matrix4x4.size];
        
        m[0 * Matrix4x4.size + 0] = width / 2;
        m[0 * Matrix4x4.size + 3] = originX + width / 2;

        m[1 * Matrix4x4.size + 1] = height / 2;
        m[1 * Matrix4x4.size + 3] = originY + height / 2;
        
        //Nell'articolo diceva di inserire 1/2, ma in altri articoli consigliava di basarsi sui piani.
        m[2 * Matrix4x4.size + 2] = (far - near) / 2;
        m[2 * Matrix4x4.size + 3] = (far + near) / 2;
        
        m[3 * Matrix4x4.size + 3] = 1.0f;


        return new Matrix4x4(m);
    }

    //********************** FINE PROIEZIONI **************************************** */

    //************************************* OPERAZIONI GENERALI TRA MATRICI, MATRICI E VETTORI, MATRICI E TRIANGOLI. */

    //PESSIMA VERSIONE INEFFICIENTE, PROVAR A TROVARE ALTRO
    public static Matrix4x4 makeInverse(Matrix4x4 m)
    {
        //printMatrix(m);
        m = Matrix4x4.makeTranspose(m);


        var A2323 = m.m[2 * Matrix4x4.size + 2] * m.m[3 * Matrix4x4.size + 3] - m.m[2 * Matrix4x4.size + 3] * m.m[3 * Matrix4x4.size + 2] ;
        var A1323 = m.m[2 * Matrix4x4.size + 1] * m.m[3 * Matrix4x4.size + 3] - m.m[2 * Matrix4x4.size + 3] * m.m[3 * Matrix4x4.size + 1] ;
        var A1223 = m.m[2 * Matrix4x4.size + 1] * m.m[3 * Matrix4x4.size + 2] - m.m[2 * Matrix4x4.size + 2] * m.m[3 * Matrix4x4.size + 1] ;
        var A0323 = m.m[2 * Matrix4x4.size + 0] * m.m[3 * Matrix4x4.size + 3] - m.m[2 * Matrix4x4.size + 3] * m.m[3 * Matrix4x4.size + 0] ;
        var A0223 = m.m[2 * Matrix4x4.size + 0] * m.m[3 * Matrix4x4.size + 2] - m.m[2 * Matrix4x4.size + 2] * m.m[3 * Matrix4x4.size + 0] ;
        var A0123 = m.m[2 * Matrix4x4.size + 0] * m.m[3 * Matrix4x4.size + 1] - m.m[2 * Matrix4x4.size + 1] * m.m[3 * Matrix4x4.size + 0] ;
        var A2313 = m.m[1 * Matrix4x4.size + 2] * m.m[3 * Matrix4x4.size + 3] - m.m[1 * Matrix4x4.size + 3] * m.m[3 * Matrix4x4.size + 2] ;
        var A1313 = m.m[1 * Matrix4x4.size + 1] * m.m[3 * Matrix4x4.size + 3] - m.m[1 * Matrix4x4.size + 3] * m.m[3 * Matrix4x4.size + 1] ;
        var A1213 = m.m[1 * Matrix4x4.size + 1] * m.m[3 * Matrix4x4.size + 2] - m.m[1 * Matrix4x4.size + 2] * m.m[3 * Matrix4x4.size + 1] ;
        var A2312 = m.m[1 * Matrix4x4.size + 2] * m.m[2 * Matrix4x4.size + 3] - m.m[1 * Matrix4x4.size + 3] * m.m[2 * Matrix4x4.size + 2] ;
        var A1312 = m.m[1 * Matrix4x4.size + 1] * m.m[2 * Matrix4x4.size + 3] - m.m[1 * Matrix4x4.size + 3] * m.m[2 * Matrix4x4.size + 1] ;
        var A1212 = m.m[1 * Matrix4x4.size + 1] * m.m[2 * Matrix4x4.size + 2] - m.m[1 * Matrix4x4.size + 2] * m.m[2 * Matrix4x4.size + 1] ;
        var A0313 = m.m[1 * Matrix4x4.size + 0] * m.m[3 * Matrix4x4.size + 3] - m.m[1 * Matrix4x4.size + 3] * m.m[3 * Matrix4x4.size + 0] ;
        var A0213 = m.m[1 * Matrix4x4.size + 0] * m.m[3 * Matrix4x4.size + 2] - m.m[1 * Matrix4x4.size + 2] * m.m[3 * Matrix4x4.size + 0] ;
        var A0312 = m.m[1 * Matrix4x4.size + 0] * m.m[2 * Matrix4x4.size + 3] - m.m[1 * Matrix4x4.size + 3] * m.m[2 * Matrix4x4.size + 0] ;
        var A0212 = m.m[1 * Matrix4x4.size + 0] * m.m[2 * Matrix4x4.size + 2] - m.m[1 * Matrix4x4.size + 2] * m.m[2 * Matrix4x4.size + 0] ;
        var A0113 = m.m[1 * Matrix4x4.size + 0] * m.m[3 * Matrix4x4.size + 1] - m.m[1 * Matrix4x4.size + 1] * m.m[3 * Matrix4x4.size + 0] ;
        var A0112 = m.m[1 * Matrix4x4.size + 0] * m.m[2 * Matrix4x4.size + 1] - m.m[1 * Matrix4x4.size + 1] * m.m[2 * Matrix4x4.size + 0] ;

        var det = m.m[0 * Matrix4x4.size + 0] * ( m.m[1 * Matrix4x4.size + 1] * A2323 - m.m[1 * Matrix4x4.size + 2] * A1323 + m.m[1 * Matrix4x4.size + 3] * A1223 ) 
        - m.m[0 * Matrix4x4.size + 1] * ( m.m[1 * Matrix4x4.size + 0] * A2323 - m.m[1 * Matrix4x4.size + 2] * A0323 + m.m[1 * Matrix4x4.size + 3] * A0223 ) 
        + m.m[0 * Matrix4x4.size + 2] * ( m.m[1 * Matrix4x4.size + 0] * A1323 - m.m[1 * Matrix4x4.size + 1] * A0323 + m.m[1 * Matrix4x4.size + 3] * A0123 ) 
        - m.m[0 * Matrix4x4.size + 3] * ( m.m[1 * Matrix4x4.size + 0] * A1223 - m.m[1 * Matrix4x4.size + 1] * A0223 + m.m[1 * Matrix4x4.size + 2] * A0123 ) ;
        
        //System.out.println("Det : " + det);

        det = 1 / det;

        //CHECK DEL DEL DETERMINANTE DIVERSO DA 0

        final float[] qw = new float[Matrix4x4.size * Matrix4x4.size];
    
        qw[0 * Matrix4x4.size + 0] = det *   ( m.m[1 * Matrix4x4.size + 1] * A2323 - m.m[1 * Matrix4x4.size + 2] * A1323 + m.m[1 * Matrix4x4.size + 3] * A1223 );
        qw[0 * Matrix4x4.size + 1] = det * - ( m.m[0 * Matrix4x4.size + 1] * A2323 - m.m[0 * Matrix4x4.size + 2] * A1323 + m.m[0 * Matrix4x4.size + 3] * A1223 );
        qw[0 * Matrix4x4.size + 2] = det *   ( m.m[0 * Matrix4x4.size + 1] * A2313 - m.m[0 * Matrix4x4.size + 2] * A1313 + m.m[0 * Matrix4x4.size + 3] * A1213 );
        qw[0 * Matrix4x4.size + 3] = det * - ( m.m[0 * Matrix4x4.size + 1] * A2312 - m.m[0 * Matrix4x4.size + 2] * A1312 + m.m[0 * Matrix4x4.size + 3] * A1212 );
        qw[1 * Matrix4x4.size + 0] = det * - ( m.m[1 * Matrix4x4.size + 0] * A2323 - m.m[1 * Matrix4x4.size + 2] * A0323 + m.m[1 * Matrix4x4.size + 3] * A0223 );
        qw[1 * Matrix4x4.size + 1] = det *   ( m.m[0 * Matrix4x4.size + 0] * A2323 - m.m[0 * Matrix4x4.size + 2] * A0323 + m.m[0 * Matrix4x4.size + 3] * A0223 );
        qw[1 * Matrix4x4.size + 2] = det * - ( m.m[0 * Matrix4x4.size + 0] * A2313 - m.m[0 * Matrix4x4.size + 2] * A0313 + m.m[0 * Matrix4x4.size + 3] * A0213 );
        qw[1 * Matrix4x4.size + 3] = det *   ( m.m[0 * Matrix4x4.size + 0] * A2312 - m.m[0 * Matrix4x4.size + 2] * A0312 + m.m[0 * Matrix4x4.size + 3] * A0212 );
        qw[2 * Matrix4x4.size + 0] = det *   ( m.m[1 * Matrix4x4.size + 0] * A1323 - m.m[1 * Matrix4x4.size + 1] * A0323 + m.m[1 * Matrix4x4.size + 3] * A0123 );
        qw[2 * Matrix4x4.size + 1] = det * - ( m.m[0 * Matrix4x4.size + 0] * A1323 - m.m[0 * Matrix4x4.size + 1] * A0323 + m.m[0 * Matrix4x4.size + 3] * A0123 );
        qw[2 * Matrix4x4.size + 2] = det *   ( m.m[0 * Matrix4x4.size + 0] * A1313 - m.m[0 * Matrix4x4.size + 1] * A0313 + m.m[0 * Matrix4x4.size + 3] * A0113 );
        qw[2 * Matrix4x4.size + 3] = det * - ( m.m[0 * Matrix4x4.size + 0] * A1312 - m.m[0 * Matrix4x4.size + 1] * A0312 + m.m[0 * Matrix4x4.size + 3] * A0112 );
        qw[3 * Matrix4x4.size + 0] = det * - ( m.m[1 * Matrix4x4.size + 0] * A1223 - m.m[1 * Matrix4x4.size + 1] * A0223 + m.m[1 * Matrix4x4.size + 2] * A0123 );
        qw[3 * Matrix4x4.size + 1] = det *   ( m.m[0 * Matrix4x4.size + 0] * A1223 - m.m[0 * Matrix4x4.size + 1] * A0223 + m.m[0 * Matrix4x4.size + 2] * A0123 );
        qw[3 * Matrix4x4.size + 2] = det * - ( m.m[0 * Matrix4x4.size + 0] * A1213 - m.m[0 * Matrix4x4.size + 1] * A0213 + m.m[0 * Matrix4x4.size + 2] * A0113 );
        qw[3 * Matrix4x4.size + 3] = det *   ( m.m[0 * Matrix4x4.size + 0] * A1212 - m.m[0 * Matrix4x4.size + 1] * A0212 + m.m[0 * Matrix4x4.size + 2] * A0112 );


        return Matrix4x4.makeTranspose(new Matrix4x4(qw));
    }


    public static Matrix4x4 makeTranspose(final Matrix4x4 a)
    {
        final float[] m = new float[Matrix4x4.size * Matrix4x4.size];
        for (int i = 0; i < Matrix4x4.size; ++i)
        {
            for (int j = 0; j < Matrix4x4.size; ++j)
            {
                //da sistemare
                m[j * Matrix4x4.size + i] = a.m[i * Matrix4x4.size + j]; //CONTROLLARE CACHE LOCALITY DI QUESTA VERSIONE 
            }
        }

        return new Matrix4x4(m); 
    }


    //Questa procedura corrisponde ad a * b, dove a e b sono due matrici quadrate 4 x 4.
    //da leggere da destra verso sinistra visto che utilizzo column major order.
    public static Matrix4x4 multiplication(final Matrix4x4 a, final Matrix4x4 b)
    {
        final float[] m = new float[Matrix4x4.size * Matrix4x4.size];

        for (int i = 0; i < Matrix4x4.size; ++i)
        {
            for (int k = 0; k < Matrix4x4.size; ++k)
            {
                for (int j = 0; j < Matrix4x4.size; ++j)
                {
                    m[i * Matrix4x4.size + j] += a.m[i * Matrix4x4.size + k] * b.m[k * Matrix4x4.size + j];
                }
            }
        }

        return new Matrix4x4(m);
    }


    public static Vector4D multiplyByVector(final Matrix4x4 a, final Vector4D v)
    {
        final float[] w = new float[4];

        for (int i = 0; i < Matrix4x4.size; ++i)
        {
            for (int j = 0; j < Matrix4x4.size; ++j)
            {
                w[i] += a.m[i * Matrix4x4.size + j] * v.getCoordinate(j);
            }
        }

        return new Vector4D(w);
    }


    public static Triangle multiplyByTriangle(final Matrix4x4 a, final Triangle t)
    {
        Vector4D v = Matrix4x4.multiplyByVector(a, t.getVertex(0));
        Vector4D w = Matrix4x4.multiplyByVector(a, t.getVertex(1));
        Vector4D b = Matrix4x4.multiplyByVector(a, t.getVertex(2));



        return new Triangle(new Vector4D[] {v, w, b}, t.getNormal(), t.getColor());
    }

    //************************** FINE METODI STATIC PER LA CREAZIONE DI MATRICI **************************  */


    public static void printMatrix(final Matrix4x4 m)
    {
        for (int i = 0; i < Matrix4x4.size; ++i)
        {
            for (int j = 0; j < Matrix4x4.size; ++j)
            {
                System.out.print(m.m[i * Matrix4x4.size + j] + " ");
            }
            System.out.println();
        }
    }
}