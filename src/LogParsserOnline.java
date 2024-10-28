import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class LogParsserOnline {
    Socket _socket = null; // socket representing connecton to remote machine
    PrintWriter _send = null; // write to this to send data to remote server
    BufferedReader _receive = null;
    InputStream in = System.in;
    public void init() throws IOException {
        int remoteSocketNumber = 12345;

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
                //Scanner scanner = new Scanner(in);
                String userInput;
                BufferedReader br  = new BufferedReader(new FileReader("log.txt"));
                br.readLine();
                while ((userInput = br.readLine()) != null) {
                    if (userInput.equals("---")){
                        break;
                    }
                    _send.println(userInput);  // Envoie chaque ligne lue dans le fichier
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //tcp server client
    }
    public void run() throws IOException {
        LogParsserOnline calcRemote = new LogParsserOnline();
        calcRemote.init();
    }
}
