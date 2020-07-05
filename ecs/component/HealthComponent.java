package framework3d.ecs.component;

public class HealthComponent implements Component
{
    private int health;


    //******************************* GET ******************************* */
    
    public int getHealth()
    {
        return health;
    }

    //******************************* FINE GET ******************************* */


    //******************************* SET ******************************* */
    
    public void setHealth(int health)
    {
        this.health = health;
    }

    //******************************* FINE SET ******************************* */
}