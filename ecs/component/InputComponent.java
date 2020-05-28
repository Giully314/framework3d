package framework3d.ecs.component;

import framework3d.ecs.entity.*;


/*
Questa classe si occupa di gestire l'input di un'entità. Per input intendo un insieme di informazioni che provengono
da un componente AI, tastiera, mouse ecc, e che vengono trasformate in informazioni per il componente transform
così da essere "capite" da quest'ultimo.
*/

public abstract class InputComponent 
{
    public InputComponent()
    {
    }

    public abstract InputTransformData getInputData();
}