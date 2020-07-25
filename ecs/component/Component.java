package framework3d.ecs.component;

/*
Semplice classe che viene implementata per comunicare che la classe è un component.
Un component NON deve avere nessun metodo logico che cambi i dati. 
E' semplicemente un contenitore di dati a cui viene data una interpretazione.

Nota: in origine questa era un'interfaccia vuota. L'ho resa una classe per semplificare il concetto di 
componente attivo/spento.
*/
public abstract class Component 
{
    private boolean state = false;

    public void printComponent()
    {
        
    }


    public void activateComponent()
    {
        state = true;
    }

    public void deactivateComponent()
    {
        state = false;
    }


    public boolean getState()
    {
        return state;
    }

    public void executeAction()
    {
        
    } 
}