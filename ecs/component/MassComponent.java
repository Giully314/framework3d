package framework3d.ecs.component;

public class MassComponent extends Component
{
    public long mass = 0;

    @Override
    public void printComponent()
    {
        System.out.println("MassComponent");
        System.out.println("mass: " + mass);
    }
}