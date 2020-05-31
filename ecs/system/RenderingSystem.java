package framework3d.ecs.system;

import java.awt.Graphics;

import java.util.ArrayList;

import framework3d.ecs.entity.RenderableEntity;

//PROVARE A PARALLELIZZARE CON PARALLEL STREAM. MOLTO PIù DIFFICILE POICHé BISOGNA RISPETTARE UN CERTO ORDINE
//QUINDI NON SO SE VALGA LA PENA PROVARE A FARLO.
public class RenderingSystem 
{
    //Se al posto di passare un array di entità passassi direttamente un buffeer con i soli triangoli
    //già ordinati? 
    public <T extends RenderableEntity> void render(ArrayList<T> renderableEntities, Graphics g)
    {   
        for (RenderableEntity e : renderableEntities)
        {
            
        }
    }    
}