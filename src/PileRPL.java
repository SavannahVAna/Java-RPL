import javax.smartcardio.Card;
import java.util.ArrayList;

public class PileRPL {
    // empiler des objets, dépiler des objets, gérer plusieurs types d'objet
    //notion de dimension
    private ArrayList<ObjetEmpilable> objets;
    private int pointeurHautDePile;

    public PileRPL() {
        objets = new ArrayList<>();
        pointeurHautDePile = 0;
    }

    public void empile(ObjetEmpilable objet) {
        objets.add(objet);
        pointeurHautDePile++;
    }

    public String toString(){
        return objets.toString();
    }

    public void removeObject(int index) {
        objets.remove(index);
    }

    public void addition(){
        ObjetEmpilable prem = objets.getLast();
        ObjetEmpilable sec = objets.getLast();
        pointeurHautDePile -= 1;
        objets.add(prem.addition(sec));
    }
}
