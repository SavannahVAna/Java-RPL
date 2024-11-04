import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
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
    boolean remote;
    ServerSocket serverSocket;
    Socket socket;
    PrintStream out;
    boolean replay;

    public CalcRPLImproved(boolean write, boolean rm, boolean replay) throws IOException {
        serverSocket = new ServerSocket(12345);
        this.replay = replay;
        this.remote = rm;
        if(remote){
            System.out.println("listening for connection");
            socket = serverSocket.accept();
            sc = new Scanner(socket.getInputStream());
            out = new PrintStream(socket.getOutputStream());
        }
        else {
            sc = new Scanner(System.in);
            out = System.out;
        }

        pile = new PileRPL();
        if (write) {
            this.logWriter = new LogWriter("log.txt");
        }
        if (replay){
            sc = new Scanner(new FileReader("log.txt"));
            //sc.useDelimiter("\n");
            sc.nextLine();
        }

        this.write = write;
    }

    private void changeMode() throws IOException {
        if (!replay) {
            remote = !remote;
            if (remote) {
                System.out.println("listening for connection");
                socket = serverSocket.accept();
                sc = new Scanner(socket.getInputStream());
                out = new PrintStream(socket.getOutputStream());
            }
            if (!remote) {
                socket.close();  // Fermer la connexion au serveur
                System.out.println("Disconnected from remote server, switched to command line mode.");
                sc = new Scanner(System.in);
                out = System.out;
            }
        }
    }

    private boolean checkInt(String in) {
        return in.matches("\\d*");
    }

    private boolean checkVector(String in) {
        return in.matches("(\\d+,)+\\d+");
    }

    private void queryInput() throws IOException {
        String in = sc.nextLine();
        System.out.println(in);
        if (write){
            logWriter.log(in);}
        input = in.split(" ");
    }

    public void run() throws Exception {
        while (use) {
            out.println("Please enter your calculation, q to quit, s to switch");
            queryInput();
            handleOperation();
            //System.out.println(pile.toString());
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
                if (write){
                logWriter.close();}
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
            else if (str.equals("s")){
                changeMode();
            }
            out.println(pile.toString());
        }
    }
}
