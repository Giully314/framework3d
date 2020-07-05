package framework3d.ecs.entity;

import framework3d.ecs.component.PositionComponent;

/*
Descrive tutte quelle entità statiche, cioè solo dotate di un componente di posizione. Tutte le entità sono di base
statiche.
*/
public interface StaticEntity 
{
    PositionComponent getPositionComponent();
}