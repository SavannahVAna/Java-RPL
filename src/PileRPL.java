import javax.smartcardio.Card;
import java.util.ArrayList;
//TODO paramètre de taille + afficher toute la liste
public class PileRPL {
    // empiler des objets, dépiler des objets, gérer plusieurs types d'objet
    //notion de dimension
    private ArrayList<ObjetEmpilable> objets;
    private int pointeurHautDePile;
    private int dimension;

    public PileRPL() {
        objets = new ArrayList<>();
        pointeurHautDePile = 0;
        dimension = 0;
    }

    public void empile(ObjetEmpilable objet) throws Exception {
        objets.add(objet);
        pointeurHautDePile++;
        if(pointeurHautDePile == 1){
            dimension = objet.getContenu().length;
        }
        else {
            if (dimension != objet.getContenu().length) {
                throw new Exception("Pas la bonne taille");
            }
        }
    }

    public String toString(){
        return objets.toString();
    }

    public void removeObject(int index) {
        objets.remove(index);
    }

    public void addition(){
        ObjetEmpilable prem = objets.getLast();
        objets.removeLast();
        ObjetEmpilable sec = objets.getLast();
        objets.removeLast();
        pointeurHautDePile -= 1;
        try {
            objets.add(prem.addition(sec));
        } catch (Exception e) {
            System.out.println("ça devrait pas arriver");
        }

    }

    public void soustraction(){
        ObjetEmpilable prem = objets.getLast();
        objets.removeLast();
        ObjetEmpilable sec = objets.getLast();
        objets.removeLast();
        pointeurHautDePile -= 1;
        try {
            objets.add(prem.soustraction(sec));
        } catch (Exception e) {
            System.out.println("ça devrait pas arriver");
        }

    }
}
