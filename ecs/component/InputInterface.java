package framework3d.ecs.component;

/*
"Traduce" dati in input provenienti da altri componenti (esempio tastiera, AI) in azioni, impostando InputComponent
Per specializzare il comportamento, bisogna ereditare da questa classe.
*/
public abstract class InputInterface 
{
    public abstract void updateInput(InputComponent inputComponent);
}