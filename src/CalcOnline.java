import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class CalcOnline {
    Scanner sc;
    String[] input;
    PileRPL pile;
    boolean use = true;
    PrintStream out;
    //LogWriter logWriter;

//calconline est la classe lancée par le server en mode pile individuelle
    public CalcOnline(BufferedReader in, PrintStream out) throws IOException {
        sc = new Scanner(in);
        pile = new PileRPL();
        this.out = out;
        //if (write) {
        //    this.logWriter = new LogWriter("log.txt");
        //}
    }

    private boolean checkInt(String in) {
        return in.matches("\\d*");
    }

    private boolean checkVector(String in) {
        return in.matches("(\\d+,)+\\d+");
    }

    private void queryInput() throws IOException {
        String in = sc.nextLine();
        //if (write){
        //    logWriter.log(in);}
        input = in.split(" ");
    }

    public void run() throws Exception {
        while (use) {
            out.println("Please enter your calculation, q to quit");
            queryInput();
            handleOperation();
            out.println(pile.toString());
        }
    }

    private ObjetEmpilable separateVectors(String in){
        //String str = in.substring(1, in.length() - 1);
        String[] split = in.split(",");
        int[] vector = new int[split.length];
        for(int i = 0; i < split.length; i++){
            vector[i] = Integer.parseInt(split[i]);
        }
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
