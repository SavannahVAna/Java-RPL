import java.io.*;
import java.net.Socket;
import java.util.Scanner;
//cette classe sert a se connecter au server dans le cas d'un utilisateur remote
//on peut egalement logger avec cett classe
public class CalcRemote {
    Socket _socket = null; // socket representing connecton to remote machine
    PrintWriter _send = null; // write to this to send data to remote server
    BufferedReader _receive = null;
    InputStream in = System.in;
    LogWriter logWriter;
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
                _send = new PrintWriter(_socket.getOutputStream(), true);
                _receive = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
                new Thread(() -> {
                    try {
                        String serverResponse;
                        while ((serverResponse = _receive.readLine()) != null) {
                            System.out.println(serverResponse);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                // Read messages from the console and send to the server
                Scanner scanner = new Scanner(in);
                String userInput;
                while (true) {
                    userInput = scanner.nextLine();
                    if(wr){
                        logWriter.log(userInput);
                    }
                    _send.println(userInput);

                    if ("q".equalsIgnoreCase(userInput.trim())) {
                        if (wr) {
                            logWriter.close();
                        }
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
