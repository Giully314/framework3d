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

public class Matrix4x4 
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
        m[0 * Matrix4x4.size + 0] = -(float)Math.sin(angle);

        m[1 * Matrix4x4.size + 1] = (float)Math.sin(angle);
        m[1 * Matrix4x4.size + 1] = (float)Math.cos(angle);
        
        m[2 * Matrix4x4.size + 2] = 1.0f;
       
        m[3 * Matrix4x4.size + 3] = 1.0f;

        return new Matrix4x4(m);
    }


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

    //************************** FINE METODI STATIC PER LA CREAZIONE DI MATRICI **************************  */
}