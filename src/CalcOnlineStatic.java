import java.io.*;
import java.net.*;
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

    public CalcOnlineStatic(BufferedReader in, PrintStream out) throws IOException {
        this.sc = new Scanner(in);
        this.out = out;
    }
    public CalcOnlineStatic() {
        // Constructeur vide
    }

    private boolean checkInt(String in) {
        return in.matches("\\d+");
    }

    private boolean checkVector(String in) {
        return in.matches("\\d+,\\d+");
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
                out.println(pile.toString());  // Afficher la pile partagée
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                    pile.empile(obj);
                } else if (checkVector(str)) {
                    pile.empile(separateVectors(str));
                } else if (str.equals("+")) {
                    pile.addition();
                } else if (str.equals("-")) {
                    pile.soustraction();
                } else if (str.equals("q")) {
                    use = false;
                } else if (str.equals("/")) {
                    pile.division();
                }
                else if (str.equals("*")) {
                    pile.multiplication();
                }
            } finally {
                pileLock.unlock();  // Libérer le lock après l'opération
            }
        }
    }

    // Classe pour démarrer un serveur multi-utilisateur
    public static class CalcServer {
        private ServerSocket serverSocket;

        public CalcServer(int port) throws IOException {
            serverSocket = new ServerSocket(port);
            pile = new PileRPL();  // Pile commune à tous les utilisateurs
        }

        public void start() {
            System.out.println("Server started...");
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected");
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintStream out = new PrintStream(clientSocket.getOutputStream());

                    // Lancer un nouveau thread pour chaque client
                    Thread clientThread = new Thread(new CalcOnlineStatic(in, out));
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
