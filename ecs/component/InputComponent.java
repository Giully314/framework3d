package framework3d.ecs.component;

/*
Questo è uno dei pochi componenti che sarà a gerarchia. Attraverso questo componente, gestisco l'input di 
una entità, cioè come deve tradurre i dati in input (esempio i tasti della tastiera, intelligenza artificiale ecc),
in azioni (esempio, rotazione spostamento, spari, ecc)
*/
public class InputComponent implements Component
{
    public boolean forward;
    public boolean back;
    public boolean right;
    public boolean left;

    //aggiunta di rotazione, spari, ecc. oppure creare altri componenti che ereditano. Oppure, altra soluzione,
    //creare componenti diversi per ognuna di queste azioni.
}