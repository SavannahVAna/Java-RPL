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
    public void init(boolean wr) throws IOException {
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

                    if ("q".equalsIgnoreCase(userInput.trim())) {
                        if (wr) {
                            logWriter.close();
                        }
                        tread.interrupt();
                        System.exit(0);
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //tcp server client
    }
    public void run(boolean wr) throws IOException {
        CalcRemote calcRemote = new CalcRemote();
        calcRemote.init(wr);
    }
}
