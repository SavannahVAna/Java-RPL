import java.io.*;
import java.net.Socket;
import java.util.Scanner;
//cette classe sert a se connecter au server dans le cas d'un utilisateur remote
//on peut egalement logger avec cett classe
public class CalcRemote {
    Socket _socket = null; // socket representing connecton to remote machine
    PrintWriter send = null; // write to this to send data to remote server
    BufferedReader receive = null;
    InputStream in = System.in;
    LogWriter logWriter;
    Thread tread;
    boolean remote;
    Scanner sc;
    String[] input;
    PileRPL pile = new PileRPL();
    boolean use = true;
    boolean write;
    public void init(boolean wr, boolean rm) throws IOException {
        int remoteSocketNumber = 12345;
        if(wr) {
            logWriter = new LogWriter("log.txt");
        }
        try {
            _socket = new Socket("127.0.0.1", remoteSocketNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(_socket !=null) {
            try {
                send = new PrintWriter(_socket.getOutputStream(), true);
                receive = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
                tread = new Thread(() -> {
                    try {
                        String serverResponse;
                        while ((serverResponse = receive.readLine()) != null) {
                            System.out.println(serverResponse);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                tread.start();

                // Read messages from the console and send to the server
                Scanner scanner = new Scanner(in);
                String userInput;
                while (! tread.isInterrupted()) {
                    userInput = scanner.nextLine();
                    if(wr){
                        logWriter.log(userInput);
                    }
                    send.println(userInput);
                    if("r".equalsIgnoreCase(userInput.trim())){
                        remote = false;
                        break;
                    }
                    if ("q".equalsIgnoreCase(userInput.trim())) {
                        if (wr) {
                            logWriter.close();
                        }
                        tread.interrupt();
                        use = false;
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //tcp server client
    }
    public void run(boolean wr, boolean rm) throws Exception {
        remote = rm;
        while (use) {
            if (rm) {
                CalcRemote calcRemote = new CalcRemote();
                calcRemote.init(wr, rm);
            } else {

                System.out.println("Please enter your calculation, q to quit");
                queryInput();
                handleOperation();
                System.out.println(pile.toString());
                if(remote){
                    break;
                }

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
        if (write){
            logWriter.log(in);}
        input = in.split(" ");
    }

    /*public void run() throws Exception {
        while (use) {
            System.out.println("Please enter your calculation, q to quit");
            queryInput();
            handleOperation();
            System.out.println(pile.toString());
        }
    }*/

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
            else if (str.equals("r")){
                remote = true;
            }
        }
    }
}
