package framework3d.ecs.system;

import framework3d.ecs.entity.EntityRef;

import framework3d.ecs.Engine;

/*
Interfaccia comune a tutti i sistemi. I sistemi potrebbero eseguire delle azioni per inizializzare il loro stato
(esempio lettura di dati da file) e spegnersi in modo sicuro (salvando i dati su file).

Nota su una possibile implementazione più avanzata:
Ogni sistema gestisce i componenti in un array. In questo modo si ha un'ottimizzazione per via della cache ma si ha un utilizzo della
memoria più espansivo, poiché ci potrebbero essere entità che non hanno un dato componente, sprecando così un posto nell'array.
Un modo per risolvere questo problema, quando si hanno centinaia o migliaia di entità e creare un sistema a doppio indirizzamento.
In questo modo la memoria utilizzata in caso di "buchi" nell'array di componenti è inesistente poiché i buchi saranno nella prima 
tabella, ma saranno poco espansivi poiché stiamo trattando degli interi.
Nel nostro caso, il numero di entità è infinitesimo, rispetto ad un normale gioco (il gioco utilizza circa dalle 10 alle 15 entità)
e quindi uno spreco momentaneo di uno slot all'interno di un'array è insignificante.


Nota:
Per una migliore caratterizzazione, si potrebbe implementare a sua volta, derivando da questa interfaccia, 
un'interfaccia per ogni tipo di sistema. Esempio potrei avere più sistemi di rendering che fanno cose diverse
ma che condividono una stessa interfaccia. Questa è solo un'idea, nel caso si voglia scrivere una libreria 
più completa e flessibile.
*/

public interface ComponentSystem 
{
    default void initialize()
    {

    }


    void registerEntity(EntityRef e);

    Engine getEngine();
    void setEngine(Engine e);

    default void shutdown()
    {

    }
}