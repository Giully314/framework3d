package framework3d.ecs.system;

import java.util.HashMap;

import framework3d.ecs.component.Component;
import java.util.ArrayList;

public abstract class BaseSystem implements ComponentSystem
{
    protected HashMap<Class<? extends Component>, ArrayList<? extends Component>> components = new HashMap<>();
}