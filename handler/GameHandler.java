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
    
    //******* FINE SISTEMI ********** */

    
    //******** INTERFACCE ENTITA' ****** */
    
    private ArrayList<DynamicEntity> dynamicEntities;
    private ArrayList<RenderableEntity> renderableEntities;
    
    //******** FINE INTERFACCE ENTITA' ****** */
    



}