public class FooRPL {
    public static void main(String[] args) {
        PileRPL pile = new PileRPL();
        PileRPL pile2 = new PileRPL();
        ObjetEmpilable obj1 = new ObjetEmpilable(new int[]{1});
        ObjetEmpilable obj2 = new ObjetEmpilable(new int[]{2});
        ObjetEmpilable obj3 = new ObjetEmpilable(new int[]{3,10});
        ObjetEmpilable obj4 = new ObjetEmpilable(new int[]{4,20});
        try {
            pile.empile(obj1);
            pile.empile(obj2);
            pile2.empile(obj3);
            pile2.empile(obj4);
            pile.addition();
            pile2.addition();
            System.out.println(pile);
            System.out.println(pile2);
        } catch (Exception e){
            System.out.println("pas la meme taille");
        }
    }
}
