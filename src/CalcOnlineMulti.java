import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

//does not yet support vectorial calculation
public final class CalcOnlineMulti extends Thread {
    final Scanner sc;
    String[] input;
    PileRPL pile;
    boolean use = true;
    PrintStream out;
    //LogWriter logWriter;
    Socket socket;


    public CalcOnlineMulti(Socket socket) throws IOException {

        pile = new PileRPL();
        //logWriter = new LogWriter("log.txt");
        this.socket = socket;
        BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
        sc = new Scanner(in);
        this.out = new PrintStream( socket.getOutputStream() );
    }

    private boolean checkInt(String in) {
        return in.matches("\\d*");
    }

    private boolean checkVector(String in) {
        return in.matches("\\d*,\\d*");
    }

    private synchronized void queryInput() throws IOException {
        String in = sc.nextLine();
        //logWriter.log(in);
        input = in.split(" ");
    }

    public synchronized void run() {
        while (use) {
            out.println("Please enter your calculation, q to quit");
            try {
                queryInput();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                handleOperation();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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

    private synchronized void handleOperation() throws Exception {
        int a;
        for (String str : input) {
            if (checkInt(str)) {
                a= Integer.parseInt(str);
                int[] d = {a};
                ObjetEmpilable obj = new ObjetEmpilable(d);
                pile.empile(obj);
            }
            else if (checkVector(str)) {
                pile.empile(separateVectors(str));
            }
            else if (str.equals("+")) {
                pile.addition();
            }
            else if (str.equals("-")) {
                pile.soustraction();
            }
            else if (str.equals("q")) {
                use = false;
            }
        }
    }
}
