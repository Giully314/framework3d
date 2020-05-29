package framework3d.ecs.entity;

import framework3d.ecs.component.HealthComponent;
import framework3d.ecs.component.ImageComponent;
import framework3d.ecs.component.PositionComponent;


public interface StaticEntity 
{
    PositionComponent getPositionComponent();
    ImageComponent getImageComponent();
    HealthComponent getHealthComponent();
}