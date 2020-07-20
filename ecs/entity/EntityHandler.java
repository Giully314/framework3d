package framework3d.ecs.entity;

import java.util.LinkedList;

public class EntityHandler 
{
    private int nextAvailableID;
    private LinkedList<Integer> availableID;


    public EntityHandler()
    {
        nextAvailableID = 0;
        availableID = new LinkedList<>();
    }

    /******************************************** CREATE AND DESTROY ENTITY ************************************************ */
    
    public EntityRef entityCreate()
    {
        if (availableID.isEmpty())
        {
            fillListOfID();
        }

        int id = availableID.remove();
    
        return new EntityRef(id);
    }


    public void entityDestroy(EntityRef e)
    {
        availableID.addFirst(e.getID());

        //Disattivare i componenti associati all'entità.
    }

    /******************************************** FINE CREATE AND DESTROY ENTITY ************************************************ */

    private void fillListOfID()
    {
        int numberOfElementsToInsert = 10;
        for (int i = nextAvailableID; i < nextAvailableID + numberOfElementsToInsert; ++i)
        {
            availableID.add(i);
        }

        //Non c'è pericolo di overflow poiché questo gioco utilizza un numero piccolissimo di entità contemporaneamente.
        //(Non superiore a 30).
        nextAvailableID += numberOfElementsToInsert;
    }
}