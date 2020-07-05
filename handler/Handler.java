package framework3d.handler;

/*
Questa classe è un "contenitore" da passare in caso diverse classi hanno bisogno di diverse istante delle classi
presenti nel GameHandler. Piuttosto che passare le singole istanze e fare funzioni con più parametri, passo
un Handler che ha tutto quello che serve con i vari metodi get.
*/

public class Handler 
{
    private final RawInputHandler inputHandler;

    public Handler(RawInputHandler inputHandler)
    {
        this.inputHandler = inputHandler;
    }

    
    //metodi get per InputHandler, Sistemi, ecc


    public RawInputHandler getInputHandler()
    {
        return inputHandler;
    }



}