import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//calconlinestatic est la classe pour une pile partagée. Elle comprend la partie calcul et la partie server combinée
public class CalcOnlineStatic implements Runnable {
    private static PileRPL pile;  // Pile partagée par tous les utilisateurs
    private static Lock pileLock = new ReentrantLock();  // Lock pour synchroniser l'accès à la pile
    private Scanner sc;
    private String[] input;
    private boolean use = true;
    private PrintStream out;
    private BufferedReader in;
    private CalcServer server;
    public CalcOnlineStatic(BufferedReader in, PrintStream out, CalcServer serv) throws IOException {
        this.sc = new Scanner(in);
        this.out = out;
        this.server = serv;
    }
    public CalcOnlineStatic() {
        // Constructeur vide
    }

    private boolean checkInt(String in) {
        return in.matches("\\d+");
    }

    private boolean checkVector(String in) {
        return in.matches("(\\d+,)+\\d+");
    }

    private void queryInput() throws IOException {
        String in = sc.nextLine();
        input = in.split(" ");
    }

    @Override
    public void run() {
        try {
            while (use) {
                out.println("Please enter your calculation, q to quit");
                queryInput();
                handleOperation();
                broadcastPileState();
                //out.println(pile.toString());  // Afficher la pile partagée
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            synchronized (server.clientOutputs) {
                server.clientOutputs.remove(out);  // Retirer le flux lors de la déconnexion
            }
        }
    }

    private ObjetEmpilable separateVectors(String in) {
        String[] split = in.split(",");
        int[] vector = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            vector[i] = Integer.parseInt(split[i]);
        }
        return new ObjetEmpilable(vector);
    }

    private void handleOperation() throws Exception {
        int a;
        for (String str : input) {
            pileLock.lock();  // Synchroniser l'accès à la pile
            try {
                if (checkInt(str)) {
                    a = Integer.parseInt(str);
                    int[] d = { a };
                    ObjetEmpilable obj = new ObjetEmpilable(d);
                    try {
                        pile.empile(obj);
                    }catch (Exception e){
                        System.out.println("fail to empile objet (are they the same size?)");
                        pile.removeLast();
                    }

                } else if (checkVector(str)) {
                    try {
                        pile.empile(separateVectors(str));
                    }catch (Exception e){
                        System.out.println("fail to empile objet (are they the same size?)");
                        pile.removeLast();
                    }
                } else if (str.equals("+")) {
                    if (pile.getObjetLen() >1) {
                        pile.addition();
                    }
                    else {
                        System.out.println("insuffisant number of elements for operation");
                    }
                } else if (str.equals("-")) {
                    if (pile.getObjetLen() >1) {
                        pile.soustraction();
                    }
                    else {
                        System.out.println("insuffisant number of elements for operation");
                    }
                } else if (str.equals("q")) {
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
                    if (pile.getObjetLen() >1){
                        pile.multiplication();
                    }
                    else {
                        System.out.println("insuffisant number of elements for operation");
                    }
                }

            } finally {
                pileLock.unlock();  // Libérer le lock après l'opération
            }
        }
    }

    private void broadcastPileState() {
        synchronized (server.clientOutputs) {  // Synchroniser l'accès à la liste
            for (PrintStream clientOut : server.clientOutputs) {
                clientOut.println("Current pile state: " + pile.toString());
            }
        }
    }



    // Classe pour démarrer un serveur multi-utilisateur
    public static class CalcServer {
        private ServerSocket serverSocket;
        private ArrayList<PrintStream> clientOutputs;
        public CalcServer(int port) throws IOException {
            serverSocket = new ServerSocket(port);
            pile = new PileRPL();  // Pile commune à tous les utilisateurs
            clientOutputs = new ArrayList<>();
        }

        public void start() {
            System.out.println("Server started...");
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected");
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintStream out = new PrintStream(clientSocket.getOutputStream());
                    synchronized (clientOutputs) {  // Protéger l'accès à la liste
                        clientOutputs.add(out);
                    }
                    // Lancer un nouveau thread pour chaque client
                    Thread clientThread = new Thread(new CalcOnlineStatic(in, out, this));
                    clientThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void runSevrer() {
        // Démarrer le serveur sur le port 12345
        CalcServer server = null;
        try {
            server = new CalcServer(12345);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.start();
    }
}
