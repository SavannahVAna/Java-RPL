import java.util.Scanner;

public class CalcRPL {
    public static void main(String[] args) throws Exception {
        PileRPL pile = new PileRPL();
        System.out.println("Welcome to pileRPL");
        Scanner scan = new Scanner(System.in);
        String input;
        int y;
        int d =-1;
        boolean use = true;
        while(use){
            System.out.println("Do you wish to add a number to the pile? -> press e\n To add or substract contents press -/+\n press q to quit");
            System.out.println("The contents of the pile are : " + pile.toString());
            input = scan.nextLine();
            if(input.equalsIgnoreCase("e")){
                if(d==-1) {
                    System.out.println("Please enter a number, or enter v if you wish to enter a vector");
                    input = scan.nextLine();
                    if (input.equalsIgnoreCase("v")) {
                        System.out.println("Please enter the number of dimensions");
                        input = scan.nextLine();
                        d = Integer.parseInt(input);
                        int[] l = new int[d];
                        for (int i = 1; i < d + 1; i++) {
                            System.out.println("Dimension: " + i + " enter a number");
                            input = scan.nextLine();
                            l[i - 1] = Integer.parseInt(input);
                        }
                        ObjetEmpilable obj2 = new ObjetEmpilable(l);
                        pile.empile(obj2);
                    } else {
                        y = Integer.parseInt(input);
                        int[] t = {y};
                        ObjetEmpilable obj = new ObjetEmpilable(t);
                        pile.empile(obj);
                    }
                }
                else {
                    int[] f = new int[d];
                    for (int i = 1; i < d + 1; i++) {
                        System.out.println("Dimension: " + i + " enter a number");
                        input = scan.nextLine();
                        f[i - 1] = Integer.parseInt(input);
                    }
                    ObjetEmpilable obj3 = new ObjetEmpilable(f);
                    pile.empile(obj3);
                }
            }
            else if(input.equalsIgnoreCase("+")){
                pile.addition();
            }
            else if(input.equalsIgnoreCase("-")){
                pile.soustraction();
            }
            else if(input.equalsIgnoreCase("q")){
                use = false;
            }
            else{
                System.out.println("Invalid input");
            }
        }
    }
}
