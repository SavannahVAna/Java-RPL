import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
//classe de base pour la calculette en solo, supporte la création de logs grace au boolean write passé a la construction (true pour logger)
public class CalcRPLImproved {
    Scanner sc;
    String[] input;
    PileRPL pile;
    boolean use = true;
    LogWriter logWriter;
    boolean write;

    public CalcRPLImproved(boolean write) throws IOException {
        sc = new Scanner(System.in);
        pile = new PileRPL();
        this.logWriter = new LogWriter("log.txt");
        this.write = write;
    }

    private boolean checkInt(String in) {
        return in.matches("\\d*");
    }

    private boolean checkVector(String in) {
        return in.matches("(\\d+,)+\\d+");
    }

    private void queryInput() throws IOException {
        String in = sc.nextLine();
        if (write){
            logWriter.log(in);}
        input = in.split(" ");
    }

    public void run() throws Exception {
        while (use) {
            System.out.println("Please enter your calculation, q to quit");
            queryInput();
            handleOperation();
            System.out.println(pile.toString());
        }
    }

    private ObjetEmpilable separateVectors(String in){
        //String str = in.substring(1, in.length() - 1);
        String[] split = in.split(",");
        int[] vector = new int[split.length];
        for(int i = 0; i < split.length; i++){
            vector[i] = Integer.parseInt(split[i]);
        }
        //System.out.println(Arrays.toString(vector));
        return new ObjetEmpilable(vector);
    }

    private void handleOperation() throws Exception {
        int a;
        for (String str : input) {
            if (checkInt(str)) {
                a= Integer.parseInt(str);
                int[] d = {a};
                ObjetEmpilable obj = new ObjetEmpilable(d);
                try {
                    pile.empile(obj);
                }catch (Exception e){
                    System.out.println("fail to empile objet (are they the same size?)");
                    pile.removeLast();
                }
            }
            else if (checkVector(str)) {
                try {
                    pile.empile(separateVectors(str));
                }catch (Exception e){
                    System.out.println("fail to empile objet (are they the same size?)");
                    pile.removeLast();
                }
            }
            else if (str.equals("+")) {
                if (pile.getObjetLen() >1) {
                    pile.addition();
                }
                else {
                    System.out.println("insuffisant number of elements for operation");
                }
            }
            else if (str.equals("-")) {
                if (pile.getObjetLen() >1) {
                    pile.soustraction();
                }
                else {
                    System.out.println("insuffisant number of elements for operation");
                }
            }
            else if (str.equals("q")) {
                use = false;
                logWriter.close();
            } else if (str.equals("/")) {
                if (pile.getObjetLen() >1) {
                    pile.division();
                }
                else {
                    System.out.println("insuffisant number of elements for operation");
                }
            }
            else if (str.equals("*")) {
                if (pile.getObjetLen() >1) {
                    pile.multiplication();
                }
                else {
                    System.out.println("insuffisant number of elements for operation");
                }
            }
        }
    }
}
