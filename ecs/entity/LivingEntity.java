package framework3d.ecs.entity;

import framework3d.ecs.component.HealthComponent;

public interface LivingEntity 
{
    HealthComponent getHealthComponent();
}