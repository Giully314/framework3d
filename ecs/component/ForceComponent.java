package framework3d.ecs.component;

import framework3d.geometry.Vector4D;

//Forza misurata in newton.
public class ForceComponent extends Component
{   
    /*
    Questo valore serve solo per le entità controllate da un'intelligenza artificiale o da un giocatore, così da simulare i limiti
    dei motori di una navicella e soprattutto un aumento graduale della spinta. Gli altri oggetti non faranno uso di questa variabile
    poiché la forza verrà calcolata come forza di gravità tra i vari corpi presenti.
    */
    public int maxForceValue = 1000; 
    
    public Vector4D force = new Vector4D();    
}