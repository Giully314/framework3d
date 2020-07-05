package framework3d.ecs.entity;

import framework3d.ecs.component.*;

/*
Per semplicità, tutte le entità nel gioco sono dinamiche. Questa semplificazione è dovuta al fatto che la libreria
è "statica", cioè non permette l'aggiunta di componenti a runtime. In questo modo il sistema che gestirà la fisica
sarà molto più semplice da sviluppare.
*/

public interface DynamicEntity extends ControllableEntity
{
    MovementComponent getMovementComponent();
}