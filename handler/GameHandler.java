package framework3d.handler;

import java.util.ArrayList;

import framework3d.ecs.system.*;
import framework3d.ecs.entity.*;

/*
Questa classe rappresenta il code del gioco. Gestisce e sincronizza tutte le operazioni dei vari sistemi.
*/
public class GameHandler 
{
    //******* SISTEMI ********** */
    
    private TransformSystem transformSystem;
    private InputSystem inputSystem;
    private RenderingSystem renderingSystem;
    
    //******* FINE SISTEMI ********** */

    
    //******** INTERFACCE ENTITA' ****** */
    
    private ArrayList<DynamicEntity> dynamicEntities;
    private ArrayList<RenderableEntity> renderableEntities;
    
    //******** FINE INTERFACCE ENTITA' ****** */
    

    //********* FINESTRA ****** */
    
    private WindowHandler window;

    //********* FINE FINESTRA ****** */


    /********************** COSTRUTTORI ***************************************** */
    public GameHandler()
    {
        init();
    }

    /********************** FINE COSTRUTTORI ***************************************** */



    /****************************** METODI DI SETUP *********************************** */
    
    private void init()
    {
        transformSystem = new TransformSystem();
        inputSystem = new InputSystem();
    }
    
    /****************************** FINE METODI DI SETUP *********************************** */


    //********************************* AGGIUNTA ENTITA' ********************************************** */
    
    public void addDynamicEntity(DynamicEntity e)
    {
        dynamicEntities.add(e);
    }
    
    //********************************* FINE AGGIUNTA ENTITA' ********************************************** */


    /*********************************** RIMOZIONE ENTITA' ********************************************** */
    
    public void removeDynamicEntity(DynamicEntity e)
    {
        dynamicEntities.remove(e);
    }
    
    /*********************************** FINE RIMOZIONE ENTITA' ********************************************** */


    /************************************* GAME LOOP *********************************************************** */

    //Calcolo del tempo, passare il tempo ai vari sistemi.
    public void gameLoop()
    {
        int updatePerSecond = 60;
		long nanoSecond = 1000000000L;
		float timePerUpdate = nanoSecond / updatePerSecond;
		float elapsedTime = 0;
		long currentTime;
		long lastTime = System.nanoTime();
        float nsPerFrame;
        
        float secPerFrame;

        while (true)
		{
			//IMPOSTARE GAME LOOP OGNI SESSANTESIMO DI SECONDO TRAMITE UN IF.
			currentTime = System.nanoTime();

			elapsedTime += (currentTime - lastTime) / timePerUpdate;
			
			nsPerFrame = currentTime - lastTime;
			
			lastTime = currentTime;

			//non chiamare il game loop ogni sessantesimo di secondo, ma ottimizzare calcolando lo stesso il frame
			//da sistemare.
			//possibile soluzione, multi thread, con update e rendering in due thread diversi.
			if (elapsedTime >= 1.0)
			{
                // nsPerFrame / 1.0E9 Converto in secondi.
                
                secPerFrame = nsPerFrame / 1.0E9f;
                
                updateInputComponents(secPerFrame);
                updateTransformComponents(secPerFrame);

                --elapsedTime;
			}
			//da controllare se fare il rendering del frame più velocemente possibile e non vincolato dall'update dello stato
			//del gioco ogni 1/60, sia una cosa positiva alle performance generali e alla visione.
		}
    }


    //MODIFICARE I SISTEMI COSì DA PRENDERE IN INPUT NELLA FUNZIONE UPDATE IL TEMPO PASSATO.
    private void updateInputComponents(float elapsedTime)
    {
        inputSystem.update(dynamicEntities);
    }


    private void updateTransformComponents(float elapsedTime)
    {
        transformSystem.update(dynamicEntities);
    }
    
    /************************************* FINE GAME LOOP *********************************************************** */


}