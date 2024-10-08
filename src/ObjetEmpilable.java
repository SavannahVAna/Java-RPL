import java.util.Arrays;

public class ObjetEmpilable { //creer des objets empilables et les empiler
    //addition soustraction
    private int[] contenu;

    public ObjetEmpilable(final int[] contenu) {
        this.contenu = contenu;
    }

    public int[] getContenu() {
        return contenu;
    }

    public ObjetEmpilable addition(ObjetEmpilable obj2) throws Exception {
        int[] result = new int[contenu.length];
        if (obj2.getContenu().length != contenu.length) {
            throw new Exception("pas de meme longueur");
        }
        else {

            for (int i = 0; i < contenu.length; i++) {
                 result[i] = contenu[i] + obj2.getContenu()[i];
            }
        }
        return new ObjetEmpilable(result);
    }

    public ObjetEmpilable soustraction(ObjetEmpilable obj2) throws Exception {
        int[] result = new int[contenu.length];
        if (obj2.getContenu().length != contenu.length) {
            throw new Exception("pas de meme longueur");
        }
        else {

            for (int i = 0; i < contenu.length; i++) {
                result[i] = contenu[i] - obj2.getContenu()[i];
            }
        }
        return new ObjetEmpilable(result);
    }

    public String toString() {
        return Arrays.toString(contenu);
    }

}
