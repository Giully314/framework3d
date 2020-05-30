package framework3d.ecs.system;

import java.util.ArrayList;

import framework3d.ecs.entity.DynamicEntity;

/*
Questo sistema si occupa di fare da tramite tra input basso livello (tastiera, mouse, AI) e input alto livello
(InputComponent).
*/

//NOTA: QUESTO SISTEMA è DA RISCRIVERE UTILIZZANDO PARALLEL STREAM 
public class InputSystem 
{
    public static <T extends DynamicEntity> void update(ArrayList<T> entities)
    {
        for (DynamicEntity d : entities)
        {
            var ic = d.getInputComponent();
            var i = d.getInputInterface();

            i.updateInput(ic);
        }
    }
}